import AccountEventMetricQuery, {
	AccountEventMetricsData,
	AccountEventMetricsVariables
} from 'shared/queries/AccountEventMetricQuery';
import AccountEventsTrendQuery, {
	AccountEventsTrendData,
	AccountEventsTrendVariables
} from 'shared/queries/AccountEventsTrendQuery';
import ActivitiesChart from 'contacts/components/ActivitiesChart';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import moment from 'moment';
import React, {useState} from 'react';
import {fetchPolicyDefinition} from 'shared/util/graphql';
import {getDateRangeLabel} from 'shared/util/date';
import {getIcon, getStatsColor} from 'shared/util/metrics';
import {getSafeRangeSelectors} from 'shared/util/util';
import {Interval, RangeSelectors} from 'shared/types';
import {isNil} from 'lodash';
import {mapListResultsToProps} from 'shared/util/mappers';
import {SessionEntityTypes} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {Text} from '@clayui/core';
import {toRounded, toThousands} from 'shared/util/numbers';
import {TrendClassification} from 'segment/types';
import {useQuery} from '@apollo/client';
import {useSelectedPoint} from 'shared/hooks/useSelectedPoint';
import {WrapSafeResults} from 'shared/hoc/util';

// TODO LPD-85735: remove when backend is deployed.
const USE_MOCK_DATA = true;

const RANGE_KEY_DAYS: Record<string, number> = {
	180: 180,
	28: 28,
	30: 30,
	365: 365,
	7: 7,
	90: 90
};

type BucketUnit = 'hour' | 'day' | 'week' | 'month';

const BUCKET_SCALE: Record<BucketUnit, number> = {
	day: 1,
	hour: 1 / 24,
	month: 30,
	week: 7
};

const getMockBucketing = (
	interval: Interval,
	rangeSelectors: RangeSelectors
): {bucketCount: number; bucketUnit: BucketUnit; startDate: moment.Moment} => {
	const rangeKey = String(rangeSelectors.rangeKey ?? 30);

	if (rangeKey === '0') {
		return {
			bucketCount: 24,
			bucketUnit: 'hour',
			startDate: moment.utc().startOf('hour').subtract(23, 'hours')
		};
	}

	if (rangeKey === '1') {
		return {
			bucketCount: 24,
			bucketUnit: 'hour',
			startDate: moment.utc().subtract(1, 'day').startOf('day')
		};
	}

	const days = RANGE_KEY_DAYS[rangeKey] ?? 30;
	const today = moment.utc().startOf('day');

	if (interval === 'W') {
		const bucketCount = Math.max(1, Math.ceil(days / 7));

		return {
			bucketCount,
			bucketUnit: 'week',
			startDate: today.clone().subtract(bucketCount - 1, 'weeks')
		};
	}

	if (interval === 'M') {
		const bucketCount = Math.max(1, Math.ceil(days / 30));

		return {
			bucketCount,
			bucketUnit: 'month',
			startDate: today
				.clone()
				.startOf('month')
				.subtract(bucketCount - 1, 'months')
		};
	}

	return {
		bucketCount: days,
		bucketUnit: 'day',
		startDate: today.clone().subtract(days - 1, 'days')
	};
};

const buildMockMetricResponse = (
	interval: Interval,
	rangeSelectors: RangeSelectors
): {
	data: AccountEventMetricsData;
	error: undefined;
	loading: false;
	refetch: () => Promise<unknown>;
} => {
	const {bucketCount, bucketUnit, startDate} = getMockBucketing(
		interval,
		rangeSelectors
	);

	const scale = BUCKET_SCALE[bucketUnit];

	const metrics = Array.from({length: bucketCount}, (_, i) => {
		const date = startDate.clone().add(i, bucketUnit);
		const baseEvent =
			6000 +
			Math.round(2500 * Math.sin(i / 3.5) + 1500 * Math.cos(i / 5));
		const eventValue = Math.max(1, Math.round(baseEvent * scale));
		const sessionValue = Math.max(1, Math.round(eventValue / 90));

		return {
			eventKey: date.toISOString(),
			eventValue,
			sessionValue
		};
	});

	const totalEvents = metrics.reduce(
		(acc, {eventValue}) => acc + eventValue,
		0
	);
	const totalSessions = metrics.reduce(
		(acc, {sessionValue}) => acc + sessionValue,
		0
	);

	return {
		data: {
			eventMetric: {
				totalEventsMetric: {
					histogram: {
						metrics: metrics.map(({eventKey, eventValue}) => ({
							key: eventKey,
							value: eventValue,
							valueKey: 'totalEvents'
						})),
						total: totalEvents
					},
					value: totalEvents
				},
				totalSessionsMetric: {
					histogram: {
						metrics: metrics.map(({eventKey, sessionValue}) => ({
							key: eventKey,
							value: sessionValue,
							valueKey: 'totalSessions'
						})),
						total: totalSessions
					},
					value: totalSessions
				}
			}
		},
		error: undefined,
		loading: false,
		refetch: () => Promise.resolve()
	};
};

const buildMockTrendResponse = (): {
	data: AccountEventsTrendData;
} => ({
	data: {
		eventsByUserSessions: {
			totalEventsMetric: {
				previousValue: 46,
				trend: {
					percentage: 22.5,
					trendClassification: TrendClassification.Positive
				},
				value: 56
			}
		}
	}
});

interface IActivityStreamCardProps {
	accountId: string;
	channelId: string;
	interval: Interval;
	rangeSelectors: RangeSelectors;
}

const ActivityStreamCard: React.FC<IActivityStreamCardProps> = ({
	accountId,
	channelId,
	interval,
	rangeSelectors
}) => {
	const {hasSelectedPoint, onPointSelect, selectedPoint} = useSelectedPoint();

	const [keywords] = useState<string>('');

	const safeRangeSelectors = getSafeRangeSelectors(rangeSelectors);

	const realMetricResponse = useQuery<
		AccountEventMetricsData,
		AccountEventMetricsVariables
	>(AccountEventMetricQuery, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		skip: USE_MOCK_DATA,
		variables: {
			accountId,
			channelId,
			entityType: SessionEntityTypes.Individual,
			interval,
			keywords,
			...safeRangeSelectors
		}
	});

	const metricResponse = USE_MOCK_DATA
		? buildMockMetricResponse(interval, rangeSelectors)
		: realMetricResponse;

	const {
		error,
		items: activityHistory,
		loading,
		refetch,
		total: activityTotal
	} = mapListResultsToProps(metricResponse, ({eventMetric}) => ({
		items: eventMetric.totalEventsMetric.histogram.metrics?.map(
			({key, value}, index: number) => {
				const totalSessions =
					eventMetric?.totalSessionsMetric?.histogram?.metrics?.[
						index
					].value;

				return {
					intervalInitDate: moment.utc(key).valueOf(),
					totalEvents: value,
					totalSessions,

					// TODO LPD-85735: source from backend once unique-visitors
					// metric is exposed; mock derives ~one visitor per six
					// sessions.
					uniqueVisitors: USE_MOCK_DATA
						? Math.max(1, Math.round((totalSessions ?? 0) / 6))
						: undefined
				};
			}
		),
		total: eventMetric.totalEventsMetric?.value
	}));

	const realTrendResponse = useQuery<
		AccountEventsTrendData,
		AccountEventsTrendVariables
	>(AccountEventsTrendQuery, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		skip: USE_MOCK_DATA,
		variables: {
			accountId,
			channelId,
			entityType: SessionEntityTypes.Individual,
			keywords,
			...safeRangeSelectors
		}
	});

	const trendResponse = USE_MOCK_DATA
		? buildMockTrendResponse()
		: realTrendResponse;

	const handleChangeSelection = (index: number | null) => {
		onPointSelect(index ?? undefined);
	};

	const trendMetric =
		trendResponse.data?.eventsByUserSessions?.totalEventsMetric;

	const trendValue = trendMetric?.value ?? 0;
	const trendPercentage = trendMetric?.trend?.percentage ?? 0;
	const trendClassification = trendMetric?.trend?.trendClassification;

	const dateRangeLabel = getDateRangeLabel(
		activityHistory,
		interval,
		'intervalInitDate'
	);

	return (
		<WrapSafeResults
			className='flex-grow-1 loading-root'
			error={error}
			errorProps={{
				className: 'flex-grow-1',
				onReload: refetch
			}}
			loading={loading}
			page={false}
			pageDisplay={false}
		>
			<Card.Body>
				<div className='account-activity-stream'>
					<div className='trend-summary mb-4'>
						<div className='text-weight-semi-bold'>
							<Text size={7}>
								{sub(Liferay.Language.get('x-activities'), [
									trendValue
								])}
							</Text>
						</div>

						<div className='text-secondary'>
							{!isNil(trendClassification) &&
								trendClassification !==
									TrendClassification.Neutral && (
									<ClayIcon
										style={{
											color: getStatsColor(
												trendClassification
											)
										}}
										symbol={getIcon(trendPercentage) ?? ''}
									/>
								)}

							{sub(
								Liferay.Language.get('x-vs-previous-period'),
								[
									<span
										className='mr-1'
										key='percentage'
										style={{
											color:
												getStatsColor(
													trendClassification || ''
												) || TrendClassification.Neutral
										}}
									>
										{`${toRounded(
											Math.abs(trendPercentage),
											2
										)}%`}
									</span>
								],
								false
							)}
						</div>
					</div>

					<ActivitiesChart
						alwaysShowSelectedTooltip
						hasSelectedPoint={hasSelectedPoint}
						history={activityHistory}
						interval={interval}
						onPointSelect={handleChangeSelection}
						rangeSelectors={rangeSelectors}
						selectedPoint={selectedPoint}
						tooltipRenderRows={({
							totalEvents,
							totalSessions,
							uniqueVisitors
						}) => [
							{
								label: Liferay.Language.get('unique-visitors'),
								value: (uniqueVisitors ?? 0).toLocaleString()
							},
							{
								label: Liferay.Language.get('events'),
								value: totalEvents.toLocaleString()
							},
							{
								label: Liferay.Language.get('sessions'),
								value: (totalSessions ?? 0).toLocaleString()
							}
						]}
					/>

					<div className='chart-footer mt-3'>
						<Text color='secondary' size={3} weight='semi-bold'>
							{sub(Liferay.Language.get('account-s-events-x'), [
								dateRangeLabel
							])}
						</Text>

						<div className='mt-1'>
							<Text size={3}>
								{sub(
									Liferay.Language.get('x-events'),
									[
										<b key='count'>
											{toThousands(activityTotal ?? 0)}
										</b>
									],
									false
								)}
							</Text>
						</div>
					</div>
				</div>
			</Card.Body>
		</WrapSafeResults>
	);
};

export default ActivityStreamCard;

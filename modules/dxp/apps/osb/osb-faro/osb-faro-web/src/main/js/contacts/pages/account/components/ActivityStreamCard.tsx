import AccountEventMetricQuery, {
	AccountEventMetricsData,
	AccountEventMetricsVariables
} from 'shared/queries/AccountEventMetricQuery';
import AccountEventsTrendQuery, {
	AccountEventsTrendData,
	AccountEventsTrendVariables
} from 'shared/queries/AccountEventsTrendQuery';
import AccountUserSessionQuery, {
	AccountUserSession,
	AccountUserSessionData,
	AccountUserSessionVariables
} from 'shared/queries/AccountUserSessionQuery';
import ActivitiesChart from 'contacts/components/ActivitiesChart';
import ActivityStreamTimeline from './ActivityStreamTimeline';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import moment from 'moment';
import React, {useEffect, useState} from 'react';
import SearchInput from 'shared/components/SearchInput';
import URLConstants from 'shared/util/url-constants';
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
import {useStatefulPagination} from 'shared/hooks/useStatefulPagination';
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

const MOCK_EVENT_NAMES = [
	'pageViewed',
	'pageLoaded',
	'pageDepthReached',
	'identity',
	'assetClicked'
];

interface MockSessionDef {
	dayOffset: number;
	deviceType: string;
	eventCount: number;
	hour: number;
	inProgress?: boolean;
	userName: string | null;
}

const MOCK_SESSION_DEFS: MockSessionDef[] = [
	{
		dayOffset: 0,
		deviceType: 'mobile',
		eventCount: 51,
		hour: 20,
		inProgress: true,
		userName: 'Michelle de Rue'
	},
	{
		dayOffset: 0,
		deviceType: 'desktop',
		eventCount: 8,
		hour: 19,
		userName: 'Sarah Chen'
	},
	{
		dayOffset: 1,
		deviceType: 'desktop',
		eventCount: 12,
		hour: 14,
		userName: 'David Kim'
	},
	{
		dayOffset: 1,
		deviceType: 'mobile',
		eventCount: 5,
		hour: 11,
		userName: null
	},
	{
		dayOffset: 1,
		deviceType: 'tablet',
		eventCount: 7,
		hour: 9,
		userName: 'Priya Patel'
	}
];

interface MockSession {
	events: AccountUserSession['events'];
	meta: Omit<AccountUserSession, 'events'>;
}

const buildMockDataset = (keywords: string): MockSession[] =>
	MOCK_SESSION_DEFS.map(def => {
		const sessionStart = moment
			.utc()
			.subtract(def.dayOffset, 'days')
			.startOf('day')
			.add(def.hour, 'hours');

		const events = Array.from({length: def.eventCount}, (_, eventIdx) => ({
			applicationId: 'WebContent',
			assetTitle: '',
			canonicalUrl: 'www.liferay.com',
			createDate: sessionStart
				.clone()
				.add(eventIdx * 2, 'minutes')
				.toISOString(),
			name: MOCK_EVENT_NAMES[eventIdx % MOCK_EVENT_NAMES.length],
			pageDescription: '',
			pageKeywords: '',
			pageTitle: keywords
				? `Search: ${keywords} – event ${eventIdx + 1}`
				: 'Liferay: Digital experience software tailored to your needs',
			referrer: '',
			url: 'www.liferay.com'
		}));

		return {
			events,
			meta: {
				browserName: 'Chrome',
				completeDate: def.inProgress
					? null
					: sessionStart
							.clone()
							.add(def.eventCount * 2 + 5, 'minutes')
							.toISOString(),
				contentLanguageId: 'en-GB',
				createDate: sessionStart.toISOString(),
				devicePixelRatio: 2,
				deviceType: def.deviceType,
				languageId: 'en-US',
				screenHeight: 774,
				screenWidth: 2518,
				timezoneOffset: '-07:00',
				userAgent:
					'Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36',
				userName: def.userName
			}
		};
	});

const buildMockUserSessionResponse = (
	page: number,
	size: number,
	keywords: string
): {
	data: AccountUserSessionData;
	error: undefined;
	loading: false;
	refetch: () => Promise<unknown>;
} => {
	const dataset = buildMockDataset(keywords);

	// Pagination is event-based per the LPD-85735 contract: `size` is the max
	// events per page, not sessions, and a session can appear on consecutive
	// pages with its metadata duplicated. Flatten events with their session
	// reference, slice by page, then re-group by session preserving order.
	const flatEvents: {eventInSessionIdx: number; session: MockSession}[] = [];

	dataset.forEach(session => {
		session.events.forEach((_, eventInSessionIdx) => {
			flatEvents.push({eventInSessionIdx, session});
		});
	});

	const totalEvents = flatEvents.length;

	const start = page * size;
	const end = Math.min(start + size, totalEvents);
	const pageEvents = flatEvents.slice(start, end);

	const userSessions: AccountUserSession[] = [];
	let lastSession: MockSession | null = null;

	pageEvents.forEach(({eventInSessionIdx, session}) => {
		if (lastSession !== session) {
			userSessions.push({...session.meta, events: []});
			lastSession = session;
		}

		userSessions[userSessions.length - 1].events.push(
			session.events[eventInSessionIdx]
		);
	});

	return {
		data: {
			eventsByUserSessions: {
				totalEventsMetric: {value: totalEvents},
				userSessions
			}
		},
		error: undefined,
		loading: false,
		refetch: () => Promise.resolve()
	};
};

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

	const [keywords, setKeywords] = useState<string>('');
	const [searchValue, setSearchValue] = useState<string>('');

	const {delta, onDeltaChange, onPageChange, page, resetPage} =
		useStatefulPagination();

	useEffect(() => {
		resetPage();
	}, [rangeSelectors.rangeKey]);

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

	const realSessionsResponse = useQuery<
		AccountUserSessionData,
		AccountUserSessionVariables
	>(AccountUserSessionQuery, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		skip: USE_MOCK_DATA,
		variables: {
			accountId,
			channelId,
			entityType: SessionEntityTypes.Individual,
			keywords,
			page: page - 1,
			size: delta,
			...safeRangeSelectors
		}
	});

	const sessionsResponse = USE_MOCK_DATA
		? buildMockUserSessionResponse(page - 1, delta, keywords)
		: realSessionsResponse;

	const sessionsData = sessionsResponse.data?.eventsByUserSessions;
	const userSessions = sessionsData?.userSessions ?? [];
	const totalSessionEvents = sessionsData?.totalEventsMetric?.value ?? 0;

	const handleChangeSelection = (index: number | null) => {
		onPointSelect(index ?? undefined);
	};

	const handleQuerySubmit = (value: string) => {
		setKeywords(value);
		setSearchValue(value);
		resetPage();
	};

	const handleClearSearch = () => {
		setKeywords('');
		setSearchValue('');
		resetPage();
	};

	const trendMetric =
		trendResponse.data?.eventsByUserSessions?.totalEventsMetric;

	const trendValue = trendMetric?.value ?? 0;
	const trendPercentage = trendMetric?.trend?.percentage ?? 0;
	const trendClassification = trendMetric?.trend?.trendClassification;

	const isChartEmpty =
		!activityHistory.length ||
		activityHistory.every(({totalEvents}) => !totalEvents);

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

					<div className='position-relative'>
						<ActivitiesChart
							alwaysShowSelectedTooltip
							hasSelectedPoint={hasSelectedPoint}
							hideGrid={isChartEmpty}
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

						{isChartEmpty && (
							<div
								className='position-absolute d-flex flex-column align-items-center justify-content-center text-center px-3'
								style={{
									inset: 0,
									pointerEvents: 'none'
								}}
							>
								<div
									className='font-weight-semi-bold mb-2'
									style={{pointerEvents: 'auto'}}
								>
									{Liferay.Language.get(
										'there-is-no-data-for-account-activities'
									)}
								</div>

								<div
									className='text-secondary mb-2'
									style={{pointerEvents: 'auto'}}
								>
									{Liferay.Language.get(
										'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
									)}
								</div>

								<ClayLink
									decoration='underline'
									href={
										URLConstants.AccountActivitiesDocumentationLink
									}
									style={{pointerEvents: 'auto'}}
									target='_blank'
								>
									{Liferay.Language.get(
										'learn-more-about-account-activities'
									)}
								</ClayLink>
							</div>
						)}
					</div>

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

			<Card.Body className='pt-0'>
				<SearchInput
					onChange={setSearchValue}
					onSubmit={handleQuerySubmit}
					placeholder={Liferay.Language.get('search')}
					value={searchValue}
				/>

				<ActivityStreamTimeline
					delta={delta}
					hasQuery={!!keywords}
					loading={sessionsResponse.loading ?? false}
					onClearQuery={handleClearSearch}
					onDeltaChange={onDeltaChange}
					onPageChange={onPageChange}
					page={page}
					sessions={userSessions}
					totalEvents={totalSessionEvents}
				/>
			</Card.Body>
		</WrapSafeResults>
	);
};

export default ActivityStreamCard;

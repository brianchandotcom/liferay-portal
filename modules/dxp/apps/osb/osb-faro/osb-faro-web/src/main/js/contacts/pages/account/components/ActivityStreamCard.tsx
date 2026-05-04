import AccountEventMetricQuery, {
	AccountEventMetricsData,
	AccountEventMetricsVariables
} from 'shared/queries/AccountEventMetricQuery';
import AccountEventsTrendQuery, {
	AccountEventsTrendData,
	AccountEventsTrendVariables
} from 'shared/queries/AccountEventsTrendQuery';
import AccountUserSessionQuery, {
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

	const metricResponse = useQuery<
		AccountEventMetricsData,
		AccountEventMetricsVariables
	>(AccountEventMetricQuery, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		variables: {
			accountId,
			channelId,
			entityId: '',
			entityType: SessionEntityTypes.Individual,
			interval,
			keywords,
			...safeRangeSelectors
		}
	});

	const {
		error,
		items: activityHistory,
		loading,
		refetch,
		total: activityTotal
	} = mapListResultsToProps(metricResponse, ({eventMetric}) => ({
		items: eventMetric.totalEventsMetric.histogram.metrics?.map(
			({key, value}, index: number) => ({
				intervalInitDate: moment.utc(key).valueOf(),
				totalEvents: value,
				totalSessions:
					eventMetric?.totalSessionsMetric?.histogram?.metrics?.[
						index
					].value
			})
		),
		total: eventMetric.totalEventsMetric?.value
	}));

	const trendResponse = useQuery<
		AccountEventsTrendData,
		AccountEventsTrendVariables
	>(AccountEventsTrendQuery, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		variables: {
			accountId,
			channelId,
			entityId: '',
			entityType: SessionEntityTypes.Individual,
			keywords,
			...safeRangeSelectors
		}
	});

	const sessionsResponse = useQuery<
		AccountUserSessionData,
		AccountUserSessionVariables
	>(AccountUserSessionQuery, {
		fetchPolicy: fetchPolicyDefinition(rangeSelectors),
		variables: {
			accountId,
			channelId,
			entityId: '',
			entityType: SessionEntityTypes.Individual,
			keywords,
			page: page - 1,
			size: delta,
			...safeRangeSelectors
		}
	});

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
								totalSessions
							}) => [
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

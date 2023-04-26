import ActivitiesChart, {
	ChartPayload
} from 'contacts/components/ActivitiesChart';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import DropdownRangeKey from 'shared/hoc/DropdownRangeKey';
import EventMetricQuery, {
	EventMetricsData,
	EventMetricsVariables
} from 'shared/queries/EventMetricQuery';
import IntervalSelector from 'shared/components/IntervalSelector';
import moment from 'moment';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useMemo, useState} from 'react';
import SearchInput from 'shared/components/SearchInput';
import Toolbar from 'shared/components/toolbar';
import URLConstants from 'shared/util/url-constants';
import UserSessionQuery, {
	UserSessionData,
	UserSessionVariables
} from 'shared/queries/UserSessionQuery';
import useSelectedPoint from 'shared/hooks/useSelectedPoint';
import VerticalTimeline from 'shared/components/VerticalTimeline';
import {compose, withPaginationBar} from 'shared/hoc';
import {
	FORMAT,
	formatUTCDate,
	getDateRangeLabel,
	getDateRangeLabelFromDate,
	getEndDate
} from 'shared/util/date';
import {formatSessions, getActivityLabel} from 'shared/util/activities';
import {getSafeRangeSelectors} from 'shared/util/util';
import {Individual} from 'shared/util/records';
import {Interval, RangeSelectors, SafeRangeSelectors} from 'shared/types';
import {isHourlyRangeKey} from 'shared/util/time';
import {isNil} from 'lodash';
import {mapListResultsToProps} from 'shared/util/mappers';
import {RangeKeyTimeRanges, SessionEntityTypes} from 'shared/util/constants';
import {sub} from 'shared/util/lang';
import {useQuery} from '@apollo/react-hooks';
import {useStatefulPagination} from 'shared/hooks';
import {withEmpty} from 'cerebro-shared/hocs/utils';
import {withError, withLoading, WrapSafeResults} from 'shared/hoc/util';

const DEFAULT_SESSIONS_DELTA = 50;

const formatTimestamp = (timestamp: number) => {
	const date = new Date(timestamp);
	const hours = date.getUTCHours().toString().padStart(2, '0');
	const minutes = date.getUTCMinutes().toString().padStart(2, '0');
	const seconds = date.getUTCSeconds().toString().padStart(2, '0');

	return `${hours}:${minutes}:${seconds}`;
};

const PaginatedVerticalTimeline = compose<any>(
	withPaginationBar(),
	withLoading(),
	withError({page: false}),
	withEmpty()
)(VerticalTimeline);

interface IProfileCardProps extends React.HTMLAttributes<HTMLElement> {
	channelId: string;
	entity: Individual;
	interval: Interval;
	onChangeInterval: (interval: Interval) => void;
	onRangeSelectorsChange: (rangeSelectors: RangeSelectors) => void;
	rangeSelectors: RangeSelectors;
	tabId: string;
	timeZoneId: string;
}

const ProfileCard: React.FC<IProfileCardProps> = ({
	channelId,
	entity: {id: entityId},
	interval,
	onChangeInterval,
	onRangeSelectorsChange,
	rangeSelectors,
	timeZoneId
}) => {
	const [chartPayload, setChartPayload] = useState<ChartPayload>({
		date: '',
		intervalInitDate: 0,
		totalEvents: 0,
		totalSessions: 0
	});

	const {
		delta,
		onDeltaChange,
		onPageChange,
		onQueryChange,
		page,
		query,
		resetPage
	} = useStatefulPagination(null, {
		initialDelta: DEFAULT_SESSIONS_DELTA
	});

	const {hasSelectedPoint, onPointSelect, selectedPoint} = useSelectedPoint();
	const [searchValue, setSearchValue] = useState<string>('');

	const activityResponse = useQuery<EventMetricsData, EventMetricsVariables>(
		EventMetricQuery,
		{
			fetchPolicy: 'network-only',
			variables: {
				channelId,
				entityId,
				entityType: SessionEntityTypes.Individual,
				interval,
				keywords: query,
				...getSafeRangeSelectors(rangeSelectors)
			}
		}
	);

	const {
		error,
		items: activityHistory,
		loading,
		refetch,
		total: activityTotal
	} = mapListResultsToProps(activityResponse, ({eventMetric}) => ({
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

	const getDateRange = ({
		rangeEnd,
		rangeKey,
		rangeStart
	}: RangeSelectors): SafeRangeSelectors => {
		const {intervalInitDate} = activityHistory[selectedPoint] || {};
		const endDate = getEndDate(intervalInitDate, interval);

		const hasSelectedDate = !isNil(endDate) && !isNil(intervalInitDate);

		return getSafeRangeSelectors(
			hasSelectedDate
				? {
						rangeEnd: formatUTCDate(
							getEndDate(intervalInitDate, interval),
							FORMAT
						),
						rangeKey,
						rangeStart: formatUTCDate(intervalInitDate, FORMAT)
				  }
				: {rangeEnd, rangeKey, rangeStart}
		);
	};

	const startHour = formatTimestamp(chartPayload.intervalInitDate);
	const endHour = formatTimestamp(chartPayload.intervalInitDate + 59 * 60000);

	let newRangeSelectors = useMemo(() => getDateRange(rangeSelectors), [
		rangeSelectors
	]);

	if (
		rangeSelectors.rangeKey === RangeKeyTimeRanges.Last24Hours &&
		chartPayload.date
	) {
		newRangeSelectors = {
			rangeEnd: `${chartPayload.date}T${endHour}`,
			rangeKey: 0,
			rangeStart: `${chartPayload.date}T${startHour}`
		};
	}

	const sessionsResponse = useQuery<UserSessionData, UserSessionVariables>(
		UserSessionQuery,
		{
			fetchPolicy: 'network-only',
			variables: {
				...newRangeSelectors,
				channelId,
				entityId,
				entityType: SessionEntityTypes.Individual,
				keywords: query,
				page: page - 1,
				size: delta
			}
		}
	);

	const sessionsMappedResults = mapListResultsToProps(
		sessionsResponse,
		({eventsByUserSessions: {totalEvents, userSessions}}) => ({
			items: formatSessions(userSessions),
			total: totalEvents
		})
	);

	const handleChangeCustomRange = (rangeSelectors: RangeSelectors) => {
		onRangeSelectorsChange(rangeSelectors);
		onPointSelect(null);
	};

	const handleChangeInterval = (interval: Interval) => {
		onChangeInterval(interval);
		onPointSelect(null);
	};

	const handleChartSelect = ({
		index,
		payload
	}: {
		index: number;
		payload: ChartPayload;
	}) => {
		resetPage();
		onPointSelect(index);
		setChartPayload(payload);
	};

	const handleClearSelection = () => {
		resetPage();
		onPointSelect(null);
	};

	const handleQuery = (query: string) => {
		onQueryChange(query);
		setSearchValue(query);
	};

	const selected = hasSelectedPoint || selectedPoint;

	const {intervalInitDate, totalEvents = 0} =
		activityHistory[selectedPoint] || {};

	const date = selected
		? getDateRangeLabelFromDate(intervalInitDate, interval)
		: getDateRangeLabel(activityHistory, interval, 'intervalInitDate');

	return (
		<WrapSafeResults
			className='flex-grow-1'
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
				<div className='align-items-center d-flex justify-content-end mt-3'>
					<SearchInput
						autoFocus
						className='search-input mr-3'
						onChange={setSearchValue}
						onSubmit={handleQuery}
						placeholder={Liferay.Language.get('search')}
						value={searchValue}
					/>

					<IntervalSelector
						activeInterval={interval}
						className='mr-3'
						disabled={isHourlyRangeKey(rangeSelectors.rangeKey)}
						onChange={handleChangeInterval}
					/>

					<DropdownRangeKey
						legacy={false}
						onChange={handleChangeCustomRange}
						rangeSelectors={rangeSelectors}
					/>
				</div>

				<div className='individuals-activities-chart'>
					<ActivitiesChart
						alwaysShowSelectedTooltip
						hasSelectedPoint={hasSelectedPoint}
						history={activityHistory}
						interval={interval}
						onPointSelect={handleChartSelect}
						rangeSelectors={rangeSelectors}
						selectedPoint={selectedPoint}
					/>

					<div className='selected-info'>
						<div className='activities-date d-flex align-items-baseline'>
							<h4>
								{activityHistory?.length
									? sub(
											Liferay.Language.get(
												'individuals-events-x'
											),
											[date]
									  )
									: Liferay.Language.get(
											'individuals-events'
									  )}
							</h4>

							{selected && (
								<ClayButton
									className='button-root'
									displayType='unstyled'
									onClick={handleClearSelection}
									size='sm'
								>
									{Liferay.Language.get(
										'clear-date-selection'
									)}
								</ClayButton>
							)}
						</div>

						<div className='details'>
							{getActivityLabel(
								(selected
									? totalEvents
									: activityTotal
								)?.toLocaleString()
							)}
						</div>
					</div>
				</div>
			</Card.Body>

			<Toolbar
				onQueryChange={onQueryChange}
				onSearchValueChange={handleQuery}
				query={query}
				searchValue={searchValue}
				showCheckbox={false}
				showSearch={false}
				total={sessionsMappedResults.total}
			/>

			<PaginatedVerticalTimeline
				{...sessionsMappedResults}
				delta={delta}
				initialExpanded={false}
				noResultsRenderer={
					<NoResultsDisplay
						description={
							<>
								<span className='mr-1'>
									{Liferay.Language.get(
										'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
									)}
								</span>

								<a
									href={
										URLConstants.IndividualProfilesDocument
									}
									key='DOCUMENTATION'
									target='_blank'
								>
									{Liferay.Language.get(
										'learn-more-about-individuals'
									)}
								</a>
							</>
						}
						spacer
						title={Liferay.Language.get(
							'there-are-no-events-found'
						)}
					/>
				}
				onDeltaChange={onDeltaChange}
				onPageChange={onPageChange}
				page={page}
				timeZoneId={timeZoneId}
			/>
		</WrapSafeResults>
	);
};

export default ProfileCard;

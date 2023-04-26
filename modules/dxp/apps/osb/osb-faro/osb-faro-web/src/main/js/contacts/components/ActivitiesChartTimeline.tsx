import * as API from 'shared/api';
import ActivitiesChart from './ActivitiesChartDeprecated';
import Card from 'shared/components/Card';
import ClayButton from '@clayui/button';
import getCN from 'classnames';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React from 'react';
import SearchableVerticalTimeline from 'shared/components/SearchableVerticalTimelineDeprecated';
import URLConstants from 'shared/util/url-constants';
import {createOrderIOMap, START_TIME} from 'shared/util/pagination';
import {EntityTypes} from 'shared/util/constants';
import {
	formatSessions,
	getActivityLabel
} from 'shared/util/activitiesDeprecated';
import {
	getDateRangeLabel,
	getDateRangeLabelFromDate,
	getEndDate,
	getFirstDate,
	getLastDate
} from 'shared/util/date';
import {Interval, RangeSelectors} from 'shared/types';
import {sub} from 'shared/util/lang';
import {useStatefulPagination} from 'shared/hooks';
import {withSelectedPoint} from 'shared/hoc';

const getActivities = ({
	channelId,
	contactsEntityId,
	contactsEntityType,
	delta,
	endDate,
	groupId,
	page,
	query,
	startDate
}) =>
	API.activities
		.fetchGroup({
			channelId,
			contactsEntityId,
			contactsEntityType,
			cur: page,
			delta,
			endDate,
			groupId,
			orderIOMap: createOrderIOMap(START_TIME),
			query,
			startDate
		})
		.then(({items, total}) => ({
			items: formatSessions(items, groupId, channelId),
			total
		}));

interface IActivitiesChartTimelineProps {
	activitiesLabel: string;
	channelId: string;
	className: string;
	count: number;
	entityType: EntityTypes;
	groupId: string;
	hasSelectedPoint: boolean;
	history: {
		intervalInitDate: number;
		totalElements: number;
	}[];
	id: string;
	interval: Interval;
	onPointSelect: ({index}) => void;
	rangeSelectors: RangeSelectors;
	selectedPoint?: number;
	timeZoneId: string;
}

const ActivitiesChartTimeline: React.FC<IActivitiesChartTimelineProps> = ({
	activitiesLabel,
	channelId,
	className,
	count = 0,
	entityType,
	groupId,
	hasSelectedPoint,
	history,
	id,
	interval,
	onPointSelect,
	rangeSelectors,
	selectedPoint,
	timeZoneId
}) => {
	const {
		delta,
		onDeltaChange,
		onPageChange,
		onQueryChange,
		page,
		query,
		resetPage
	} = useStatefulPagination(null);

	const getDateRange = (): {
		endDate: number;
		startDate: number;
	} => {
		if (!hasSelectedPoint) {
			return {
				endDate: getLastDate(history, interval, 'intervalInitDate'),
				startDate: getFirstDate(history, 'intervalInitDate')
			};
		}

		const {intervalInitDate} = history[selectedPoint];

		return {
			endDate: getEndDate(intervalInitDate, interval),
			startDate: intervalInitDate
		};
	};

	const handleChartSelect = ({index}: {index: number}): void => {
		resetPage();

		onPointSelect({index});
	};

	const handleClearSelection = (): void => handleChartSelect({index: null});

	const {intervalInitDate, totalElements = 0} = history[selectedPoint] || {};

	const date = hasSelectedPoint
		? getDateRangeLabelFromDate(intervalInitDate, interval)
		: getDateRangeLabel(history, interval, 'intervalInitDate');

	return (
		<Card.Body
			className={getCN('activities-chart-timeline-root', className)}
			noPadding
		>
			<div className='activities-chart-container'>
				<ActivitiesChart
					alwaysShowSelectedTooltip
					hasSelectedPoint={hasSelectedPoint}
					history={history}
					interval={interval}
					onPointSelect={handleChartSelect}
					rangeSelectors={rangeSelectors}
					selectedPoint={selectedPoint}
				/>
			</div>

			{!!history.length && (
				<div className='selected-info'>
					<div className='d-flex align-items-baseline'>
						<h4>{sub(activitiesLabel, [date])}</h4>

						{hasSelectedPoint && (
							<ClayButton
								className='button-root'
								onClick={handleClearSelection}
								size='sm'
							>
								{Liferay.Language.get('clear-date-selection')}
							</ClayButton>
						)}
					</div>

					<div className='details'>
						{getActivityLabel(
							hasSelectedPoint ? totalElements : count
						)}
					</div>
				</div>
			)}

			{!!history.length && (
				<SearchableVerticalTimeline
					dataSourceFn={getActivities}
					dataSourceParams={{
						...getDateRange(),
						channelId,
						contactsEntityId: id,
						contactsEntityType: entityType,
						groupId
					}}
					delta={delta}
					entityLabel={Liferay.Language.get('activities')}
					groupId={groupId}
					headerLabels={{
						count: Liferay.Language.get('activity-count'),
						label: Liferay.Language.get('time'),
						title: Liferay.Language.get('session')
					}}
					initialExpanded={false}
					noResultsRenderer={() => (
						<NoResultsDisplay
							description={
								<>
									{Liferay.Language.get(
										'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
									)}

									<a
										className='d-block'
										href={
											URLConstants.AccountActivitiesDocumentationLink
										}
										key='DOCUMENTATION'
										target='_blank'
									>
										{Liferay.Language.get(
											'learn-more-about-account-activities'
										)}
									</a>
								</>
							}
							spacer
							title={Liferay.Language.get(
								'there-are-no-activities-found-on-the-selected-period'
							)}
						/>
					)}
					onDeltaChange={onDeltaChange}
					onPageChange={onPageChange}
					onQueryChange={onQueryChange}
					page={page}
					query={query}
					timeZoneId={timeZoneId}
				/>
			)}
		</Card.Body>
	);
};

export default withSelectedPoint(ActivitiesChartTimeline);

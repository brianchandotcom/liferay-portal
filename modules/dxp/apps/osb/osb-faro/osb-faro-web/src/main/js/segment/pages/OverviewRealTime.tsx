import * as API from 'shared/api';
import Button from '@clayui/button';
import Card from 'shared/components/Card';
import ClayLink from '@clayui/link';
import CriteriaCard from 'segment/components/criteria-card';
import Loading from 'shared/components/Loading';
import NoResultsDisplay from 'shared/components/NoResultsDisplay';
import React, {useMemo, useState} from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import URLConstants from 'shared/util/url-constants';
import {fetchMembershipChangesAggregations} from 'shared/api/individual-segment';
import {FilterOptionType} from 'shared/types';
import {formatUTCDate} from 'shared/util/date';
import {membershipChangesColumns} from 'shared/util/table-columns';
import {OrderByDirections, SegmentTypes} from 'shared/util/constants';
import {OrderedMap} from 'immutable';
import {OrderParams, Segment} from 'shared/util/records';
import {ReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {SegmentGrowthChart} from 'segment/components/Growth';
import {Text} from '@clayui/core';
import {useRequest} from 'shared/hooks/useRequest';
import {useStatefulPagination} from 'shared/hooks/useStatefulPagination';
import {useTimeZone} from 'shared/hooks/useTimeZone';

const DEFAULT_ORDER_BY_OPTIONS = [
	{
		label: Liferay.Language.get('name'),
		value: 'memberName'
	},
	{
		label: Liferay.Language.get('account-name'),
		value: 'accountName'
	},
	{
		label: Liferay.Language.get('first-seen'),
		value: 'firstSeenDate'
	},
	{
		label: Liferay.Language.get('last-active'),
		value: 'lastActive'
	},
	{
		label: Liferay.Language.get('profile-type'),
		value: 'profileType'
	}
];

const MEMBERSHIP_CHANGE_ORDER_BY_OPTION = {
	label: Liferay.Language.get('membership-change'),
	value: 'membershipChange'
};

const FILTER_BY_DEFAULT_OPTIONS: FilterOptionType[] = [
	{
		key: 'profileType',
		label: Liferay.Language.get('profile-type'),
		values: [
			{label: Liferay.Language.get('known'), value: 'known'},
			{label: Liferay.Language.get('anonymous'), value: 'anonymous'}
		]
	}
];

const MEMBERSHIP_CHANGE_FILTER_OPTION: FilterOptionType = {
	key: 'membershipChange',
	label: Liferay.Language.get('membership-change'),
	values: [
		{label: Liferay.Language.get('added'), value: 'ADDED'},
		{label: Liferay.Language.get('removed'), value: 'REMOVED'}
	]
};

type Data = {
	channelId: string;
	delta: number;
	groupId: string;
	id: string;
	selectedDate: string;
	orderIOMap: string;
	page: number;
	query: string;
};

interface IOverviewProps {
	channelId: string;
	groupId: string;
	segment: Segment;
}

const SelectedPointInfo = ({
	dateRange,
	selectedPointState,
	setSelectedPointState
}) => {
	const handleClearDateSelection = () => {
		setSelectedPointState({
			hasSelectedPoint: false,
			selectedPoint: null
		});
	};

	const membersLanguageKey = selectedPointState.hasSelectedPoint
		? Liferay.Language.get('members-on')
		: Liferay.Language.get('members-from');

	return (
		<div>
			<div className='selected-point-info'>
				<Text color='secondary' size={3}>
					{membersLanguageKey} {dateRange}
					{selectedPointState.hasSelectedPoint ? (
						<Button
							className='ml-3'
							displayType='unstyled'
							onClick={handleClearDateSelection}
						>
							<Text color='primary' size={3} weight='semi-bold'>
								{Liferay.Language.get('clear-date-selection')}
							</Text>
						</Button>
					) : null}
				</Text>
			</div>
		</div>
	);
};

const RealTimeSegmentOverview: React.FC<IOverviewProps> = ({
	channelId,
	groupId,
	segment
}) => {
	const {criteriaString, id, includeAnonymousUsers} = segment;

	const {timeZoneId} = useTimeZone();

	const {data, loading} = useRequest({
		dataSourceFn: fetchMembershipChangesAggregations,
		variables: {channelId, groupId, id, interval: 'day', max: 30}
	});

	const [selectedPointState, setSelectedPointState] = useState<{
		hasSelectedPoint: boolean;
		selectedPoint: number | null;
	}>({
		hasSelectedPoint: false,
		selectedPoint: null
	});

	const dateRange = useMemo(() => {
		if (data) {
			if (selectedPointState.hasSelectedPoint) {
				return `${formatUTCDate(
					data[selectedPointState.selectedPoint].intervalInitDate,
					'MMM DD, YYYY'
				)}`;
			}

			return `${formatUTCDate(
				data[0].intervalInitDate,
				'MMM DD, YYYY'
			)} - ${formatUTCDate(
				data[data.length - 1].intervalInitDate,
				'MMM DD, YYYY'
			)}`;
		}

		return null;
	}, [data, selectedPointState]);

	const getColumns = () => {
		const baseColumns = [
			membershipChangesColumns.individualName,
			membershipChangesColumns.accountNames,
			membershipChangesColumns.firstSeen,
			membershipChangesColumns.lastActive,
			membershipChangesColumns.profileType
		];

		const columns = selectedPointState.hasSelectedPoint
			? [...baseColumns, membershipChangesColumns.membershipChanges]
			: baseColumns;

		return columns;
	};

	const paginationParams = useStatefulPagination(null, {
		initialDelta: 20,
		initialOrderIOMap: OrderedMap({
			['memberName']: new OrderParams({
				field: 'memberName',
				sortOrder: OrderByDirections.Descending
			})
		}),
		initialPage: 0
	});

	const fetchMembers = async (data: Data) => {
		const {delta, groupId, id, orderIOMap, query, selectedDate} = data;

		return API.individualSegment.fetchRealTimeMembershipChanges({
			date: selectedDate,
			delta,
			groupId,
			orderIOMap,
			query,
			segmentId: id
		});
	};

	const orderByOptions = useMemo(
		() =>
			selectedPointState.hasSelectedPoint
				? [
						...DEFAULT_ORDER_BY_OPTIONS,
						MEMBERSHIP_CHANGE_ORDER_BY_OPTION
				  ]
				: [...DEFAULT_ORDER_BY_OPTIONS],
		[selectedPointState.hasSelectedPoint]
	);

	const filterByOptions = useMemo(
		() =>
			selectedPointState.hasSelectedPoint
				? [
						...FILTER_BY_DEFAULT_OPTIONS,
						MEMBERSHIP_CHANGE_FILTER_OPTION
				  ]
				: FILTER_BY_DEFAULT_OPTIONS,
		[selectedPointState.hasSelectedPoint]
	);

	const selectedDate = useMemo(
		() =>
			selectedPointState.hasSelectedPoint && data
				? data[selectedPointState.selectedPoint].intervalInitDate
				: undefined,
		[data, selectedPointState]
	);

	return (
		<div>
			<ReferencedObjectsProvider segment={segment}>
				<CriteriaCard
					criteriaString={criteriaString}
					includeAnonymousUsers={includeAnonymousUsers}
					segmentType={SegmentTypes.RealTime}
					timeZoneId={timeZoneId}
				/>
			</ReferencedObjectsProvider>

			<Card>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('segment-membership-trend')}
					</Card.Title>
				</Card.Header>

				<Card.Body className='segment-growth-root'>
					{loading ? (
						<Loading />
					) : (
						<>
							<div className='segment-growth-chart-container'>
								<SegmentGrowthChart
									alwaysShowSelectedTooltip
									data={data.map(item => ({
										added: item.addedIndividualsCount,
										anonymousCount:
											item.anonymousIndividualsCount,
										knownCount: item.knownIndividualsCount,
										modifiedDate: item.intervalInitDate,
										removed: item.removedIndividualsCount,
										value: item.individualsCount
									}))}
									hasSelectedPoint={
										selectedPointState.hasSelectedPoint
									}
									height={360}
									individualCounts={{
										anonymousCount: 0,
										knownCount: 0
									}}
									selectedPoint={
										selectedPointState.selectedPoint
									}
									setSelectedPointState={
										setSelectedPointState
									}
								/>
							</div>
							<SelectedPointInfo
								dateRange={dateRange}
								selectedPointState={selectedPointState}
								setSelectedPointState={setSelectedPointState}
							/>
							<SearchableEntityTable
								{...paginationParams}
								columns={getColumns()}
								dataSourceFn={fetchMembers}
								dataSourceParams={{
									channelId,
									groupId,
									id,
									selectedDate
								}}
								filterByOptions={[...filterByOptions]}
								key={
									selectedPointState.hasSelectedPoint
										? 'changes-view'
										: 'all-members-view'
								}
								noResultsRenderer={() => (
									<NoResultsDisplay
										description={
											<>
												<span className='mr-1'>
													{Liferay.Language.get(
														'check-back-later-to-verify-if-data-has-been-received-from-your-data-sources'
													)}
												</span>

												<ClayLink
													href={
														URLConstants.SegmentsMembershipDocumentationLink
													}
													key='DOCUMENTATION'
													target='_blank'
												>
													{Liferay.Language.get(
														'learn-more-about-individuals'
													)}
												</ClayLink>
											</>
										}
										spacer
										title={Liferay.Language.get(
											'there-are-no-members-found-on-the-selected-time-period'
										)}
									/>
								)}
								orderByOptions={orderByOptions}
								rowIdentifier='id'
							/>
						</>
					)}
				</Card.Body>
			</Card>
		</div>
	);
};

export default RealTimeSegmentOverview;

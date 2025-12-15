/* eslint-disable sort-keys */
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
import {formatUTCDate} from 'shared/util/date';
import {membershipChangesColumns} from 'shared/util/table-columns';
import {ReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {Segment} from 'shared/util/records';
import {SegmentGrowthChart} from 'segment/components/Growth';
import {SegmentTypes} from 'shared/util/constants';
import {Text} from '@clayui/core';
import {useRequest} from 'shared/hooks/useRequest';
import {useTimeZone} from 'shared/hooks/useTimeZone';

// type AllMembers = {
// 	memberName: string;
// 	email: string;
// 	accountName?: string;
// 	firstSeenDate: string;
// 	lastActive: string;
// 	profileType: string;
// };

// type MemberChanges = {
// 	memberName: string;
// 	email: string;
// 	accountName?: string;
// 	firstSeenDate: string;
// 	lastActive: string;
// 	profileType: string;
// 	membershipChange: {
// 		modifiedDate: string;
// 		type: string;
// 	};
// };

type Data = {
	channelId: string;
	delta: number;
	groupId: string;
	id: string;
	modifiedDate: string;
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

	return (
		<div>
			<div className='selected-point-info'>
				<Text color='secondary' size={3}>
					{selectedPointState.hasSelectedPoint
						? 'Members on: '
						: 'Members from: '}
					{dateRange}
					{selectedPointState.hasSelectedPoint ? (
						<Button
							className='ml-3'
							displayType='unstyled'
							onClick={handleClearDateSelection}
						>
							<Text color='primary' size={3} weight='semi-bold'>
								{'Clear Date Selection'}
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

	const getAllMembers = (data: Data) => {
		const {channelId, delta, groupId, id, orderIOMap, page, query} = data;

		return API.individuals.search({
			channelId,
			delta,
			groupId,
			individualSegmentId: id,
			orderIOMap,
			page,
			query
		});
	};

	const getMemberChanges = async (data: Data) => {
		const {delta, groupId, id, modifiedDate, orderIOMap, query} = data;

		// return {
		// 	items: [
		// 		{
		// 			id: '1',
		// 			memberName: 'Alice Johnson',
		// 			email: 'alice.johnson@example.com',
		// 			accountName: 'Acme Corp',
		// 			firstSeenDate: '2024-05-01T10:00:00Z',
		// 			lastActive: '2024-06-10T15:30:00Z',
		// 			profileType: 'Known',
		// 			membershipChange: {
		// 				modifiedDate: '2024-06-10T15:30:00Z',
		// 				type: 'ADDED'
		// 			}
		// 		},
		// 		{
		// 			id: '2',
		// 			memberName: 'Bob Smith',
		// 			email: 'bob.smith@example.com',
		// 			accountName: 'Beta LLC',
		// 			firstSeenDate: '2024-05-05T09:20:00Z',
		// 			lastActive: '2024-06-09T12:10:00Z',
		// 			profileType: 'Anonymous',
		// 			membershipChange: {
		// 				modifiedDate: '2024-06-09T12:10:00Z',
		// 				type: 'REMOVED'
		// 			}
		// 		}
		// 	],
		// 	total: 2
		// };

		return API.individualSegment.fetchMembershipChanges({
			delta,
			endDate: modifiedDate,
			groupId,
			id,
			orderIOMap,
			query,
			startDate: modifiedDate
		});
	};

	const fetchMembers = params => {
		const fetchMembersFn = selectedPointState.hasSelectedPoint
			? getMemberChanges
			: getAllMembers;

		return fetchMembersFn(params);
	};

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

				<Card.Body className='segment-growth-root' noPadding>
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
								columns={getColumns()}
								dataSourceFn={fetchMembers}
								dataSourceParams={{
									channelId,
									groupId,
									id
								}}
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

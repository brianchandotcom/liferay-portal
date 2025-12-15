import Button from '@clayui/button';
import Card from 'shared/components/Card';
import CriteriaCard from 'segment/components/criteria-card';
import Loading from 'shared/components/Loading';
import {Text} from '@clayui/core';
import React, {useMemo, useState} from 'react';
import {fetchMembershipChangesAggregations} from 'shared/api/individual-segment';
import {formatUTCDate} from 'shared/util/date';
import {ReferencedObjectsProvider} from 'segment/segment-editor/dynamic/context/referencedObjects';
import {Segment} from 'shared/util/records';
import {SegmentGrowthChart} from 'segment/components/Growth';
import {SegmentTypes} from 'shared/util/constants';
import {useRequest} from 'shared/hooks/useRequest';
import {useTimeZone} from 'shared/hooks/useTimeZone';

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
				<Text color='secondary' size={3} weight='semi-bold'>
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
							{/* <SearchableEntityTable /> */}
						</>
					)}
				</Card.Body>
			</Card>
		</div>
	);
};

export default RealTimeSegmentOverview;

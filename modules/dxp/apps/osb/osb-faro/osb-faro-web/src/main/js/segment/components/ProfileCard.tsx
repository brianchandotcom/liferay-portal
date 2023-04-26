import * as API from 'shared/api';
import Card from 'shared/components/Card';
import ClayIcon from '@clayui/icon';
import ClayLink from '@clayui/link';
import React from 'react';
import {mapGrowthHistory} from 'shared/hoc/mappers/segment';
import {Routes, toRoute} from 'shared/util/router';
import {Segment} from 'shared/util/records';
import {SegmentGrowthChart} from './Growth';
import {withRequest} from 'shared/hoc';

const MembershipChart = withRequest(
	API.individualSegment.fetchMembershipChangesAggregations,
	mapGrowthHistory,
	{
		page: false
	}
)(props => <SegmentGrowthChart {...props} />);

interface ISegmentProfileCardProps extends React.HTMLAttributes<HTMLElement> {
	channelId: string;
	groupId: string;
	id: string;
	segment: Segment;
}

const SegmentProfileCard: React.FC<ISegmentProfileCardProps> = ({
	channelId,
	groupId,
	id,
	segment: {anonymousIndividualCount, knownIndividualCount}
}) => (
	<Card className='segment-profile-card-root'>
		<Card.Header>
			<Card.Title>
				{Liferay.Language.get('segment-membership')}
			</Card.Title>

			<div className='subtitle-segment'>
				{Liferay.Language.get(
					'segment-membership-processes-daily-and-does-not-include-todays-activities'
				)}
			</div>
		</Card.Header>

		<Card.Body>
			<MembershipChart
				channelId={channelId}
				groupId={groupId}
				id={id}
				individualCounts={{
					anonymousCount: anonymousIndividualCount,
					knownCount: knownIndividualCount
				}}
			/>
		</Card.Body>

		<Card.Footer>
			<ClayLink
				className='button-root'
				displayType='secondary'
				href={toRoute(Routes.CONTACTS_SEGMENT_MEMBERSHIP, {
					channelId,
					groupId,
					id
				})}
			>
				{Liferay.Language.get('view-members')}

				<ClayIcon className='icon-root ml-2' symbol='angle-right' />
			</ClayLink>
		</Card.Footer>
	</Card>
);

export default SegmentProfileCard;

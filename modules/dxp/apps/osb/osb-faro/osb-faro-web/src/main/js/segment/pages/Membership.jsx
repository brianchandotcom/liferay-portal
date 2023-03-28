import * as API from 'shared/api';
import Card from 'shared/components/Card';
import React from 'react';
import SegmentGrowthWithList from 'segment/components/Growth';
import {connect} from 'react-redux';
import {mapGrowthHistory} from 'shared/hoc/mappers/segment';
import {PropTypes} from 'prop-types';
import {Segment} from 'shared/util/records';
import {withRequest} from 'shared/hoc';

export const MembershipChart = withRequest(
	API.individualSegment.fetchMembershipChangesAggregations,
	mapGrowthHistory,
	{
		alignCenter: true,
		page: false
	}
)(
	class extends React.Component {
		static propTypes = {
			channelId: PropTypes.string,
			data: PropTypes.array,
			groupId: PropTypes.string.isRequired,
			id: PropTypes.string.isRequired,
			individualCounts: PropTypes.shape({
				anonymousCount: PropTypes.number,
				knownCount: PropTypes.number
			}),
			timeZoneId: PropTypes.string
		};

		render() {
			const {
				channelId,
				data,
				groupId,
				id,
				individualCounts,
				timeZoneId
			} = this.props;

			return (
				<SegmentGrowthWithList
					channelId={channelId}
					data={data}
					groupId={groupId}
					id={id}
					individualCounts={individualCounts}
					timeZoneId={timeZoneId}
				/>
			);
		}
	}
);

@connect((store, {groupId}) => ({
	timeZoneId: store.getIn([
		'projects',
		groupId,
		'data',
		'timeZone',
		'timeZoneId'
	])
}))
export default class Membership extends React.Component {
	static propTypes = {
		groupId: PropTypes.string.isRequired,
		segment: PropTypes.instanceOf(Segment).isRequired,
		timeZoneId: PropTypes.string
	};

	render() {
		const {
			channelId,
			groupId,
			segment: {anonymousIndividualCount, id, knownIndividualCount},
			timeZoneId
		} = this.props;

		return (
			<Card className='segment-membership-root'>
				<Card.Header>
					<Card.Title>
						{Liferay.Language.get('segment-membership')}
					</Card.Title>
				</Card.Header>

				<MembershipChart
					anonymousIndividualCount
					channelId={channelId}
					groupId={groupId}
					id={id}
					individualCounts={{
						anonymousCount: anonymousIndividualCount,
						knownCount: knownIndividualCount
					}}
					timeZoneId={timeZoneId}
				/>
			</Card>
		);
	}
}

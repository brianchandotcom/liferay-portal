import PropTypes from 'prop-types';
import React from 'react';
import SegmentEditor from 'segment/segment-editor/dynamic';
import withBaseEdit from 'contacts/hoc/segment/WithBaseEdit';
import withPropertyGroups from 'segment/segment-editor/dynamic/hoc/WithPropertyGroups';
import {compose} from 'shared/hoc';
import {List} from 'immutable';
import {Segment} from 'shared/util/records';

export class DynamicSegmentEdit extends React.Component {
	static propTypes = {
		channelId: PropTypes.string,
		groupId: PropTypes.string,
		id: PropTypes.string,
		propertyGroupsIList: PropTypes.instanceOf(List),
		segment: PropTypes.instanceOf(Segment)
	};

	render() {
		const {id, propertyGroupsIList, segment} = this.props;

		return (
			<SegmentEditor
				{...this.props}
				id={id}
				propertyGroupsIList={propertyGroupsIList}
				segment={segment}
			/>
		);
	}
}

export default compose(withPropertyGroups, withBaseEdit)(DynamicSegmentEdit);

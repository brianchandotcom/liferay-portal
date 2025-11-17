import DynamicSegment from './edit/Dynamic';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import {get} from 'lodash';
import {optional} from 'shared/hoc';
import {PropTypes} from 'prop-types';
import {Segment} from 'shared/util/records';
import {SegmentTypes} from 'shared/util/constants';
import {withSegment} from 'shared/hoc/WithSegment';

export class Edit extends React.Component {
	static defaultProps = {
		type: SegmentTypes.Batch
	};

	static propTypes = {
		segment: PropTypes.instanceOf(Segment),
		type: PropTypes.oneOf([SegmentTypes.RealTime, SegmentTypes.Batch])
	};

	render() {
		const {segment, type, ...otherProps} = this.props;

		const segmentType = get(segment, 'segmentType') || type;

		if (segmentType) {
			return (
				<DynamicSegment
					{...omitDefinedProps(otherProps, Edit.propTypes)}
					segment={segment}
					type={segmentType}
				/>
			);
		}
	}
}

export default optional(withSegment(true))(Edit);

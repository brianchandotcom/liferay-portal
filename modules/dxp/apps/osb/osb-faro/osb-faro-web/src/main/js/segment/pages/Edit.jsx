import DynamicSegment from './edit/Dynamic';
import omitDefinedProps from 'shared/util/omitDefinedProps';
import React from 'react';
import StaticSegment from './edit/Static';
import {get} from 'lodash';
import {optional} from 'shared/hoc';
import {PropTypes} from 'prop-types';
import {Segment} from 'shared/util/records';
import {SegmentTypes} from 'shared/util/constants';
import {withSegment} from 'shared/hoc/WithSegment';

const PAGE_MAP = {
	[SegmentTypes.Dynamic]: DynamicSegment,
	[SegmentTypes.Static]: StaticSegment
};

export class Edit extends React.Component {
	static defaultProps = {
		type: SegmentTypes.Dynamic
	};

	static propTypes = {
		segment: PropTypes.instanceOf(Segment),
		type: PropTypes.oneOf([SegmentTypes.Dynamic, SegmentTypes.Static])
	};

	render() {
		const {segment, type, ...otherProps} = this.props;

		const segmentType = get(segment, 'segmentType') || type;

		const Page = PAGE_MAP[segmentType];

		if (Page) {
			return (
				<Page
					{...omitDefinedProps(otherProps, Edit.propTypes)}
					segment={segment}
					type={segmentType}
				/>
			);
		}
	}
}

export default optional(withSegment(true))(Edit);

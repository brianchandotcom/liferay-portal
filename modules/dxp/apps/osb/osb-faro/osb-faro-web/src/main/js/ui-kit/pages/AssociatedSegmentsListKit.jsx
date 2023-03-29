import * as data from 'test/data';
import AssociatedSegmentsList from 'contacts/components/AssociatedSegmentsList';
import Promise from 'metal-promise';
import React from 'react';

const dataSourceFn = () =>
	Promise.resolve(
		data.mockSearch(data.mockSegment, 20, [
			null,
			{
				count: 123,
				dateCreated: data.getTimestamp(-2),
				individualAddedDate: data.getTimestamp(-1)
			}
		])
	);

export default class AssociatedSegmentsListKit extends React.Component {
	render() {
		return (
			<div>
				<AssociatedSegmentsList
					dataSourceFn={dataSourceFn}
					groupId='23'
					id='test'
					total={20}
				/>
			</div>
		);
	}
}

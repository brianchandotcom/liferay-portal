import DropZone from 'components/criteria_builder/DropZone.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

const connectDnd = jest.fn(el => el);

describe('DropZone', () => {
	afterEach(cleanup);

	it('should render', () => {
		const OriginalDropZone = DropZone.DecoratedComponent;

		const {asFragment} = render(
			<OriginalDropZone
				connectDropTarget={connectDnd}
				dropIndex={0}
				groupId='group_01'
				onCriterionAdd={jest.fn()}
				onMove={jest.fn()}
				propertyKey='user'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});

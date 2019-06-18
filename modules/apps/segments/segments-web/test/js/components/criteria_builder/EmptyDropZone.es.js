import EmptyDropZone from 'components/criteria_builder/EmptyDropZone.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

const connectDnd = jest.fn(el => el);

describe('EmptyDropZone', () => {
	afterEach(cleanup);

	it('should render', () => {
		const OriginalEmptyDropZone = EmptyDropZone.DecoratedComponent;

		const {asFragment} = render(
			<OriginalEmptyDropZone
				connectDropTarget={connectDnd}
				emptyContributors={false}
				onCriterionAdd={jest.fn()}
				propertyKey='user'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});

	it('should render illustration when empty contributors', () => {
		const OriginalEmptyDropZone = EmptyDropZone.DecoratedComponent;

		const {asFragment} = render(
			<OriginalEmptyDropZone
				connectDropTarget={connectDnd}
				emptyContributors={true}
				onCriterionAdd={jest.fn()}
				propertyKey='user'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});

import CriteriaGroup from 'components/criteria_builder/CriteriaGroup.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

const connectDnd = jest.fn(el => el);

describe('CriteriaGroup', () => {
	afterEach(cleanup);

	it('should render', () => {
		const OriginalCriteriaGroup = CriteriaGroup.DecoratedComponent;

		const {asFragment} = render(
			<OriginalCriteriaGroup
				connectDragPreview={connectDnd}
				propertyKey='user'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});

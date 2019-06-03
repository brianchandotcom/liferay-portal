import CriteriaSidebarItem from 'components/criteria_sidebar/CriteriaSidebarItem.es';
import React from 'react';
import {cleanup, render} from 'react-testing-library';

const connectDnd = jest.fn(el => el);

describe('CriteriaSidebarItem', () => {
	afterEach(cleanup);

	it('should render', () => {
		const OriginalCriteriaSidebarItem =
			CriteriaSidebarItem.DecoratedComponent;

		const {asFragment} = render(
			<OriginalCriteriaSidebarItem
				connectDragSource={connectDnd}
				propertyKey='user'
			/>
		);

		expect(asFragment()).toMatchSnapshot();
	});
});

import React from 'react';
import withTableData from '../WithTableData';
import {shallow} from 'enzyme';

describe('WithTableData', () => {
	it('Renders table w/ data', () => {
		const MockComponent = WrappedComponent => val => (
			<WrappedComponent {...val} />
		);
		const WrappedComponent = withTableData(MockComponent, {
			defaultOrderByField: 'Test'
		});

		const component = shallow(<WrappedComponent />);

		expect(component).toMatchSnapshot();
	});
});

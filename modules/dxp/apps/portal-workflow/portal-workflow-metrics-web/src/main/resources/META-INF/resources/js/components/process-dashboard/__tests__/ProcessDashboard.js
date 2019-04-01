import fetch from '../../../test/mock/fetch';
import ProcessDashboard from '../ProcessDashboard';
import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../test/mock/MockRouter';

describe('ProcessDashboard', () => {
	it('Should render component', () => {
		const data = {
			dueAfterInstanceCount: 1,
			dueInInstanceCount: 0,
			id: 35315,
			instanceCount: 1,
			onTimeInstanceCount: 1,
			overdueInstanceCount: 0,
			title: 'Single Approver'
		};
		const component = renderer.create(
			<Router client={fetch(data)} idProcess="1">
				<ProcessDashboard />
			</Router>
		);
		const tree = component.toJSON();

		expect(tree).toMatchSnapshot();
	});
});
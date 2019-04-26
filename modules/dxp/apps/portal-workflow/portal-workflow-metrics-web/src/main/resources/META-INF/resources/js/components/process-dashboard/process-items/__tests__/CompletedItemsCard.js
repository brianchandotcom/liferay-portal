import { CompletedItemsCard } from '../ProcessItemsCard';
import fetch from '../../../../test/mock/fetch';
import fetchFailure from '../../../../test/mock/fetchFailure';
import React from 'react';
import { MockRouter as Router } from '../../../../test/mock/MockRouter';

test('Should render component', () => {
	const data = {
		id: 35315,
		instanceCount: 30,
		onTimeInstanceCount: 20,
		overdueInstanceCount: 10,
		title: 'Single Approver'
	};

	const component = mount(
		<Router client={fetch(data)}>
			<CompletedItemsCard processId="35315" />
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should render component with failure state', () => {
	const component = mount(
		<Router client={fetchFailure()}>
			<CompletedItemsCard processId="35315" />
		</Router>
	);

	expect(component).toMatchSnapshot();
});
import CompletedItemsCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/process-items/CompletedItemsCard';
import fetch from '../../../mock/fetch';
import fetchFailure from '../../../mock/fetchFailure';
import React from 'react';
import {MockRouter as Router} from '../../../mock/MockRouter';
import timeRangeStore from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/store/timeRangeStore';

test('Should render component', () => {
	const data = {
		getTitle: () => 'Single Approver',
		id: 35315,
		instanceCount: 30,
		onTimeInstanceCount: 20,
		overdueInstanceCount: 10
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

test('Should test filter', () => {
	const dataTime = {
		items: [
			{
				dateEnd: new Date(Date.UTC(2019, 5, 13)),
				dateStart: new Date(Date.UTC(2019, 5, 12)),
				defaultTimeRange: true,
				id: 0,
				key: 'today',
				name: 'Today'
			},
			{
				defaultTimeRange: false,
				key: 'all-time',
				name: 'All Time'
			}
		]
	};

	timeRangeStore.client = fetch(dataTime);

	timeRangeStore.setState({timeRanges: []});

	const data = {
		getTitle: () => 'Single Approver',
		id: 35315,
		instanceCount: 30,
		onTimeInstanceCount: 20,
		overdueInstanceCount: 10
	};

	const component = mount(
		<Router client={fetch(data)}>
			<CompletedItemsCard processId="123" />
		</Router>
	);

	const instance = component.find(CompletedItemsCard).instance();

	expect(instance.filter).toEqual(null);
});

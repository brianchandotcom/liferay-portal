import fetch from '../../../mock/fetch';
import fetchFailure from '../../../mock/fetchFailure';
import {getRequestUrl} from '../../../../src/main/resources/META-INF/resources/js/shared/components/filter/util/filterUtil';
import InstanceListCard from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/InstanceListCard';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should build request url with query filters', () => {
	const data = {
		items: [
			{
				assetTitle: 'Item Subject Test',
				assetType: 'Process',
				dateCreated: new Date(Date.UTC('2019', '01', '01')),
				id: 12351,
				slaStatus: 'Overdue',
				taskNames: ['Step 1', 'Step 2', 'Step 3'],
				userName: 'User Test'
			}
		],
		title: 'Single Approver',
		totalCount: 1
	};

	const component = mount(
		<Router client={fetch(data)}>
			<InstanceListCard
				page={1}
				pageSize={10}
				processId={12351}
				query="?filters.status%5B0%5D=pending&filters.slaStatus%5B0%5D=overdue&filters.slaStatus%5B0%5D=ontime"
			/>
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	const requestUrl = getRequestUrl(
		instance.props.query,
		'/processes/12351/instances?page=1&pageSize=10'
	);

	expect(requestUrl).toEqual(
		'/processes/12351/instances?page=1&pageSize=10&status=pending&slaStatus=overdue,ontime'
	);
});

test('Should component load process instances', () => {
	const data = {
		items: [
			{
				assetTitle: 'Item Subject Test',
				assetType: 'Process',
				dateCreated: new Date(Date.UTC('2019', '01', '01')),
				id: 12351,
				slaStatus: 'Overdue',
				taskNames: ['Step 1', 'Step 2', 'Step 3'],
				userName: 'User Test'
			}
		],
		title: 'Single Approver',
		totalCount: 1
	};

	const component = mount(
		<Router client={fetch(data)}>
			<InstanceListCard page={1} pageSize={10} processId={12351} />
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	instance.state.loading = false;

	return instance.loadInstances().then(data => {
		expect(data).toEqual(data);
	});
});

test('Should component receive props', () => {
	const data = {
		items: [
			{
				assetTitle: 'Item Subject Test',
				assetType: 'Process',
				dateCreated: new Date(Date.UTC('2019', '01', '01')),
				id: 12351,
				status: 'Overdue',
				taskNames: ['Step 1', 'Step 2', 'Step 3'],
				userName: 'User Test'
			}
		],
		title: 'Single Approver',
		totalCount: 1
	};

	const component = mount(
		<Router client={fetch(data)}>
			<InstanceListCard
				page={1}
				pageSize={10}
				processId={12351}
				query="?filters.slaStatus%5B0%5D=overdue"
			/>
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	instance.componentWillReceiveProps({
		...instance.props,
		page: 2,
		query:
			'filters.slaStatus%5B0%5D=overdue&filters.slaStatus%5B1%5D=ontime'
	});

	expect(component).toMatchSnapshot();
});

test('Should component set error state after request fails', () => {
	const component = mount(
		<Router client={fetchFailure()}>
			<InstanceListCard page={1} pageSize={10} processId={35315} />
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	instance.state.loading = false;

	return instance.loadInstances().catch(() => {
		expect(instance.state.errors).toEqual(
			'There was a problem retrieving data. Please try reloading the page.'
		);
	});
});

test('Should component shows empty state when items is undefined', () => {
	const component = mount(
		<Router client={fetch({})}>
			<InstanceListCard />
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	instance.setState({
		items: undefined
	});

	expect(component).toMatchSnapshot();
});

test('Should not reload component while loading state is true', () => {
	const component = mount(
		<Router client={fetch()}>
			<InstanceListCard page={1} pageSize={10} processId={35315} />
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	instance.state.loading = true;

	const result = instance.loadInstances({...instance.props, page: 2});

	expect(result).toEqual(undefined);
});

test('Should not reload process while loading state is true', () => {
	const component = mount(
		<Router client={fetch()}>
			<InstanceListCard page={1} pageSize={10} processId={35315} />
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	instance.state.loading = true;

	const result = instance.loadProcess({...instance.props});

	expect(result).toEqual(undefined);
});

test('Should render component', () => {
	const data = {
		items: [
			{
				assetTitle: 'Item Subject Test',
				assetType: 'Process',
				dateCreated: new Date(
					Date.UTC('2019', '05', '16', '18', '24', '10')
				),
				id: 12351,
				slaStatus: 'Untracked',
				taskNames: ['Step 1', 'Step 2', 'Step 3'],
				userName: 'User Test'
			}
		],
		title: 'Single Approver',
		totalCount: 1
	};

	const component = mount(
		<Router client={fetch(data)}>
			<InstanceListCard page={1} pageSize={10} processId={12351} />
		</Router>
	);

	const instance = component.find(InstanceListCard).instance();

	instance.componentWillReceiveProps({page: 2});

	expect(component).toMatchSnapshot();
});

test('Should render component with empty data', () => {
	const data = {
		items: [
			{
				dateCreated: new Date(
					Date.UTC('2019', '05', '16', '18', '24', '10')
				)
			}
		],
		totalCount: 0
	};

	const component = renderer.create(
		<Router client={fetch(data)}>
			<InstanceListCard processId={35315} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with error state', () => {
	const component = renderer.create(
		<Router client={fetchFailure()}>
			<InstanceListCard processId={35315} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

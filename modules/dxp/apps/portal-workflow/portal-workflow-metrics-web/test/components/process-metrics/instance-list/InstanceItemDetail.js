import fetch from 'test/mock/fetch';
import InstanceItemDetail from 'components/process-metrics/instance-list/InstanceItemDetail';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from 'test/mock/MockRouter';

const dateUTC = new Date(Date.UTC('2019', '05', '20', '17', '09', '30'));

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<InstanceItemDetail />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render Item component', () => {
	const component = renderer.create(
		<InstanceItemDetail.Item
			dateOverdue={dateUTC}
			name="test"
			onTime={false}
			remainingTime={-172800000}
			status
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render Item component onTime', () => {
	const component = renderer.create(
		<InstanceItemDetail.Item
			dateOverdue={dateUTC}
			name="test"
			onTime
			remainingTime={172800000}
			status
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render Item component status Paused', () => {
	const component = renderer.create(
		<InstanceItemDetail.Item
			dateOverdue={dateUTC}
			name="test"
			onTime
			remainingTime={172800000}
			status="Paused"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render Item component status Running', () => {
	const component = renderer.create(
		<InstanceItemDetail.Item
			dateOverdue={dateUTC}
			name="test"
			onTime
			remainingTime={172800000}
			status="Running"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render Item component status Running and overdue', () => {
	const component = renderer.create(
		<InstanceItemDetail.Item
			dateOverdue={dateUTC}
			name="test"
			onTime={false}
			remainingTime={-172800000}
			status="Running"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render Item component status Stopped', () => {
	const component = renderer.create(
		<InstanceItemDetail.Item
			dateOverdue="2019-05-20T17:09:30.000Z"
			name="test"
			onTime
			remainingTime={172800000}
			status="Stopped"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render Item component status Stopped overdue', () => {
	const component = renderer.create(
		<InstanceItemDetail.Item
			dateOverdue={dateUTC}
			name="test"
			onTime={false}
			remainingTime={-172800000}
			status="Stopped"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render SectionAttribute component', () => {
	const component = renderer.create(
		<InstanceItemDetail.SectionAttribute description="test" detail="test" />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render SectionSubTitle component', () => {
	const component = renderer.create(
		<InstanceItemDetail.SectionSubTitle>
			<span>{'test'}</span>
		</InstanceItemDetail.SectionSubTitle>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should test componentWillReceiveProps', () => {
	const data = {};

	const component = mount(
		<Router client={fetch(data)}>
			<InstanceItemDetail instanceId="123" processId="123" />
		</Router>
	);

	const instance = component.find(InstanceItemDetail).instance();

	instance.componentWillReceiveProps({
		instanceId: '321',
		processId: '321'
	});

	expect(component).toMatchSnapshot();
});

test('Should test fetchData', () => {
	const data = {};

	const component = mount(
		<Router client={fetch(data)}>
			<InstanceItemDetail instanceId="123" processId="123" />
		</Router>
	);

	const instance = component.find(InstanceItemDetail).instance();

	return instance.fetchData().then(data => {
		expect(data).toEqual(data);
	});
});

test('Should test render with status OnTime', () => {
	const data = {
		assetTitle: 'test',
		assetType: 'test',
		dateCreated: dateUTC,
		slaResults: [
			{
				dateOverdue: new Date(
					Date.UTC('2019', '05', '17', '02', '04', '06')
				),
				id: 41531,
				name: 'SLA 1',
				onTime: false,
				remainingTime: -172800000,
				status: 'Running'
			},
			{
				dateOverdue: new Date(
					Date.UTC('2019', '05', '22', '02', '02', '06')
				),
				id: 41774,
				name: 'SLA TEST',
				onTime: true,
				remainingTime: 172800000,
				status: 'Running'
			}
		],
		slaStatus: 'OnTime',
		status: 'Pending',
		taskNames: ['test', 'test2'],
		userName: 'test'
	};

	const component = mount(
		<Router client={fetch(data)}>
			<InstanceItemDetail instanceId="123" processId="123" />
		</Router>
	);

	const instance = component.find(InstanceItemDetail).instance();

	instance.setState(data, () => expect(component).toMatchSnapshot());

	instance.setState(
		{
			status: 'Completed'
		},
		() => expect(component).toMatchSnapshot()
	);

	instance.setState(
		{
			slaStatus: 'Overdue'
		},
		() => expect(component).toMatchSnapshot()
	);

	instance.setState(
		{
			slaStatus: 'Overdue',
			status: 'Pending'
		},
		() => expect(component).toMatchSnapshot()
	);

	instance.setState(
		{
			slaResults: [
				{
					dateOverdue: new Date(
						Date.UTC('2019', '05', '22', '02', '02', '06')
					),
					id: 41774,
					name: 'SLA TEST',
					onTime: true,
					remainingTime: 172800000,
					status: 'Stopped'
				},
				{
					dateOverdue: new Date(
						Date.UTC('2019', '05', '22', '02', '02', '06')
					),
					id: 41774,
					name: 'SLA TEST',
					onTime: true,
					remainingTime: 172800000,
					status: 'Stopped'
				}
			],
			slaStatus: 'Completed'
		},
		() => expect(component).toMatchSnapshot()
	);
});

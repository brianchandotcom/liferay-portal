import fetch from '../../mock/fetch';
import ProcessListCard from '../../../src/main/resources/META-INF/resources/js/components/process-list/ProcessListCard';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../mock/MockRouter';

test('Should render component', () => {
	const data = {items: [], totalCount: 0};
	const component = renderer.create(
		<Router client={fetch(data)}>
			<ProcessListCard />
		</Router>
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with 10 records', () => {
	const data = {
		items: [
			{
				instancesCount: 0,
				title: 'Single Approver 1'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 2'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 3'
			},
			{
				instancesCount: 1,
				title: 'Single Approver 4'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 5'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 6'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 7'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 8'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 9'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 10'
			}
		],
		totalCount: 10
	};
	const component = renderer.create(
		<Router client={fetch(data)}>
			<ProcessListCard />
		</Router>
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with 4 records', () => {
	const data = {
		items: [
			{
				instancesCount: 0,
				title: 'Single Approver 1'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 2'
			},
			{
				instancesCount: 0,
				title: 'Single Approver 3'
			},
			{
				instancesCount: 1,
				title: 'Single Approver 4'
			}
		],
		totalCount: 4
	};
	const component = renderer.create(
		<Router client={fetch(data)}>
			<ProcessListCard />
		</Router>
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page size', () => {
	const data = {items: [], totalCount: 0};
	const component = mount(
		<Router client={fetch(data)}>
			<ProcessListCard />
		</Router>
	);
	const instance = component.find(ProcessListCard).instance();

	instance
		.requestData({
			page: 1,
			pageSize: 20,
			sort: encodeURIComponent('title:asc')
		})
		.then(() => expect(component.state('pageSize')).toBe(20));
});

test('Should change page', () => {
	const data = {items: [], totalCount: 0};
	const component = mount(
		<Router client={fetch(data)}>
			<ProcessListCard />
		</Router>
	);
	const instance = component.find(ProcessListCard).instance();

	instance
		.requestData({
			page: 10,
			pageSize: 20,
			sort: encodeURIComponent('title:asc')
		})
		.then(() => expect(component.state('start')).toBe(2));
});

test('Should search', () => {
	const data = {items: [], totalCount: 0};
	const component = mount(
		<Router client={fetch(data)}>
			<ProcessListCard />
		</Router>
	);
	const instance = component.find(ProcessListCard).instance();

	return instance
		.requestData({
			page: 1,
			pageSize: 20,
			search: 'test',
			sort: encodeURIComponent('title:asc')
		})
		.then(() => {
			expect(instance.state['totalCount']).toBe(0);
		});
});

test('Should change state', () => {
	const data = {items: [], totalCount: 0};
	const component = mount(
		<Router client={fetch(data)}>
			<ProcessListCard />
		</Router>
	);
	const instance = component.find(ProcessListCard).instance();

	instance.setState(data);
	expect(instance.state['totalCount']).toBe(0);
});

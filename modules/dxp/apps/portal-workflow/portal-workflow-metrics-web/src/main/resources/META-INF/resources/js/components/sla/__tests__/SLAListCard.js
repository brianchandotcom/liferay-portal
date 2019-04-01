import fetch from '../../../test/mock/fetch';
import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../test/mock/MockRouter';
import SLAListCard from '../SLAListCard';

test('Should change page', () => {
	const data = { items: [], totalCount: 0 };

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);
	const instance = component.find(SLAListCard).instance();

	instance.setPage(2).then(() => expect(component.state('page')).toBe(2));
});

test('Should change page size', () => {
	const data = { items: [], totalCount: 0 };

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);
	const instance = component.find(SLAListCard).instance();

	instance
		.setPageSize(20)
		.then(() => expect(component.state('pageSize')).toBe(20));
});

test('Should render component', () => {
	const data = {
		items: [
			{
				description: 'Total time to complete the request.',
				duration: 1553879089,
				name: 'Total resolution time'
			}
		],
		totalCount: 0
	};

	const component = renderer.create(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component after item was removed', () => {
	const data = { items: [], totalCount: 0 };
	const component = renderer.create(
		<Router client={fetch(data)}>
			<SLAListCard itemRemoved={'test'} />
		</Router>
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render toast with SLA saved message', () => {
	const data = { items: [], totalCount: 0 };

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const instance = component.find(SLAListCard).instance();

	instance.context = {
		setStatus: () => {},
		status: 'sla-saved'
	};
	instance.showStatusMessage();

	expect(component).toMatchSnapshot();
});

test('Should render toast with SLA updated message', () => {
	const data = { items: [], totalCount: 0 };

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const instance = component.find(SLAListCard).instance();

	instance.context = {
		setStatus: () => {},
		status: 'sla-updated'
	};
	instance.showStatusMessage();

	expect(component).toMatchSnapshot();
});

test('Should remove a item', () => {
	const data = { items: [], totalCount: 0 };

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);
	const instance = component.find(SLAListCard).instance();

	instance.removeItem();
	expect(component).toMatchSnapshot();
});
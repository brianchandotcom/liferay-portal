import fetch from '../../mock/fetch';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../mock/MockRouter';
import SLAListCard from '../../../src/main/resources/META-INF/resources/js/components/sla/SLAListCard';

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
	const data = {items: [], totalCount: 0};
	const component = renderer.create(
		<Router client={fetch(data)}>
			<SLAListCard itemRemoved={'test'} />
		</Router>
	);
	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render toast with SLA saved message', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const instance = component.find(SLAListCard).instance();

	instance.showStatusMessage();

	expect(component).toMatchSnapshot();
});

test('Should render toast with SLA updated message', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);

	const instance = component.find(SLAListCard).instance();

	instance.showStatusMessage();

	expect(component).toMatchSnapshot();
});

test('Should remove a item', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);
	const instance = component.find(SLAListCard).instance();

	instance.removeItem();
	expect(component).toMatchSnapshot();
});

test('Should test props change', () => {
	const data = {items: [], totalCount: 0};

	const component = mount(
		<Router client={fetch(data)}>
			<SLAListCard />
		</Router>
	);
	const instance = component.find(SLAListCard).instance();

	instance.componentWillReceiveProps({});
	expect(component).toMatchSnapshot();
});

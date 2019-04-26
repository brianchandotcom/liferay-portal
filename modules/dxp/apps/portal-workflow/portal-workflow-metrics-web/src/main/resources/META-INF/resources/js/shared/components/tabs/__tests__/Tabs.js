import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../../test/mock/MockRouter';
import { Tabs } from '../Tabs';

const tabs = [
	{
		key: 'completed',
		name: Liferay.Language.get('completed'),
		params: {
			processId: 35135
		},
		path: '/dashboard/:processId/completed'
	},
	{
		key: 'pending',
		name: Liferay.Language.get('pending'),
		params: {
			processId: 35135
		},
		path: '/dashboard/:processId/pending'
	}
];

test('Should expand tab items', () => {
	const component = mount(
		<Router>
			<Tabs
				location={{
					pathname: '/dashboard/:processId/completed'
				}}
				tabs={tabs}
			/>
		</Router>
	);

	const instance = component.find(Tabs).instance();

	instance.toggleExpanded();

	expect(component).toMatchSnapshot();
});

test('Should hide tab items', () => {
	const component = mount(
		<Router>
			<Tabs
				location={{
					pathname: '/dashboard/:processId/completed'
				}}
				tabs={tabs}
			/>
		</Router>
	);

	const instance = component.find(Tabs).instance();

	instance.toggleExpanded();
	instance.hideNavbar();

	expect(component).toMatchSnapshot();
});

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<Tabs
				location={{
					pathname: '/dashboard/:processId/pending'
				}}
				tabs={tabs}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
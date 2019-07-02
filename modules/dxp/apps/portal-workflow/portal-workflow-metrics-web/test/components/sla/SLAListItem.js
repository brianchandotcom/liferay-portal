import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from 'test/mock/MockRouter';
import SLAListItem from 'components/sla/SLAListItem';

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<SLAListItem
				id={1234}
				instancesCount='10'
				onTime='5'
				overdue='5'
				processName='Process test'
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component', () => {
	const component = mount(
		<Router>
			<SLAListItem
				instancesCount='10'
				onTime='5'
				overdue='5'
				processName='Process test'
			/>
		</Router>
	);

	const instance = component.find(SLAListItem).instance();

	instance.context = {
		showConfirmDialog: () => {}
	};

	instance.showConfirmDialog();

	expect(component).toMatchSnapshot();
});

test('Should render component blocked', () => {
	const component = mount(
		<Router>
			<SLAListItem
				dateModified='2019-05-06T20:32:18.811Z'
				instancesCount='10'
				onTime='5'
				overdue='5'
				processName='Process test'
				status={2}
			/>
		</Router>
	);

	const instance = component.find(SLAListItem).instance();

	instance.context = {
		showConfirmDialog: () => {}
	};

	instance.showConfirmDialog();

	expect(component).toMatchSnapshot();
});

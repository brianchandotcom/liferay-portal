import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';
import Search from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/Search';

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<Search disabled />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change search value', () => {
	const component = mount(
		<Router>
			<Search />
		</Router>
	);

	component.find('input').simulate('keyPress', {
		target: {value: 'test'}
	});
	component.find('form').simulate('submit', {
		preventDefault: () => {}
	});
	expect(component).toMatchSnapshot();
});

import PageLink from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageLink';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should render component as type default', () => {
	const component = renderer.create(
		<Router>
			<PageLink page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as active', () => {
	const component = renderer.create(
		<Router>
			<PageLink disabled page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const component = shallow(
		<Router>
			<PageLink page={2} />
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should test change page when disabled', () => {
	const component = shallow(
		<Router>
			<PageLink disabled page={2} />
		</Router>
	);

	expect(component).toMatchSnapshot();
});

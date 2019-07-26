import PageItem from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/PageItem';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should render component as type default', () => {
	const component = renderer.create(
		<Router>
			<PageItem page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as active', () => {
	const component = renderer.create(
		<Router>
			<PageItem active page={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as type Next', () => {
	const component = renderer.create(
		<Router>
			<PageItem page={1} type="next" />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as type Previous', () => {
	const component = renderer.create(
		<Router>
			<PageItem page={1} type="prev" />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

xtest('Should change page', () => {
	const onChangePage = () => () => 'test';

	const component = mount(
		<Router>
			<PageItem onChangePage={onChangePage()} page={2} />
		</Router>
	);

	const instance = component.find(PageItem).instance();

	instance.setPage();

	expect(component).toMatchSnapshot();
});

xtest('Should test change page when disabled', () => {
	const onChangePage = () => () => 'test';

	const component = mount(
		<Router>
			<PageItem disabled onChangePage={onChangePage()} page={2} />
		</Router>
	);

	const instance = component.find(PageItem).instance();

	instance.setPage();

	expect(component).toMatchSnapshot();
});

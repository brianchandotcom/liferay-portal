import PageItem from '../PageItem';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component as type default', () => {
	const component = renderer.create(<PageItem page={1} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as active', () => {
	const component = renderer.create(<PageItem active page={1} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as type Next', () => {
	const component = renderer.create(<PageItem page={1} type="next" />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as type Previous', () => {
	const component = renderer.create(<PageItem page={1} type="prev" />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const onChangePage = () => () => 'test';

	const component = shallow(
		<PageItem onChangePage={onChangePage()} page={2} />
	);

	const instance = component.instance();

	instance.setPage();

	expect(component).toMatchSnapshot();
});
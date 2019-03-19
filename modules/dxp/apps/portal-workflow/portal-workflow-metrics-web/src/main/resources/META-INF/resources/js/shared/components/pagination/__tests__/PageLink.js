import PageLink from '../PageLink';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component as type default', () => {
	const component = renderer.create(<PageLink page={1} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component as active', () => {
	const component = renderer.create(<PageLink disabled page={1} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const onChangePage = () => () => 'test';

	const component = shallow(
		<PageLink onChangePage={onChangePage()} page={2} />
	);

	const instance = component.instance();

	instance.setPage();

	expect(component).toMatchSnapshot();
});

test('Should test change page when disabled', () => {
	const onChangePage = () => () => 'test';

	const component = shallow(
		<PageLink disabled onChangePage={onChangePage()} page={2} />
	);

	const instance = component.instance();

	instance.setPage();

	expect(component).toMatchSnapshot();
});
import Pagination from '../Pagination';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Pagination onSelectPage={onSelectPage()} pageSize={1} totalCount={10} />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const onSelectPage = () => page => page;

	const component = shallow(
		<Pagination onSelectPage={onSelectPage()} pageSize={1} totalCount={10} />
	);

	const instance = component.instance();

	instance.goToPage(2);
	expect(component).toMatchSnapshot();
});
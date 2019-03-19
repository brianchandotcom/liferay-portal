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

test('Should render component at page 1', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Pagination
			onSelectPage={onSelectPage()}
			page={1}
			pageSize={5}
			totalCount={26}
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 2', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Pagination
			onSelectPage={onSelectPage()}
			page={2}
			pageSize={5}
			totalCount={26}
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 3', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Pagination
			onSelectPage={onSelectPage()}
			page={3}
			pageSize={5}
			totalCount={26}
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 4', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Pagination
			onSelectPage={onSelectPage()}
			page={4}
			pageSize={5}
			totalCount={26}
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 5', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Pagination
			onSelectPage={onSelectPage()}
			page={5}
			pageSize={5}
			totalCount={26}
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
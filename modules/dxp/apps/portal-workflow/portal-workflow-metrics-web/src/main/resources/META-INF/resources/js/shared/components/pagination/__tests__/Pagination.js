import Pagination from '../Pagination';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../../test/mock/MockRouter';

test('Should render component', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Router>
			<Pagination onSelectPage={onSelectPage()} pageSize={1} totalCount={10} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const onSelectPage = () => page => page;

	const component = mount(
		<Router>
			<Pagination onSelectPage={onSelectPage()} pageSize={1} totalCount={10} />
		</Router>
	);

	const instance = component.find(Pagination).instance();

	instance.goToPage(2);
	expect(component).toMatchSnapshot();
});

test('Should render component at page 1', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Router>
			<Pagination
				onSelectPage={onSelectPage()}
				page={1}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 2', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Router>
			<Pagination
				onSelectPage={onSelectPage()}
				page={2}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 3', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Router>
			<Pagination
				onSelectPage={onSelectPage()}
				page={3}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 4', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Router>
			<Pagination
				onSelectPage={onSelectPage()}
				page={4}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 5', () => {
	const onSelectPage = () => page => page;

	const component = renderer.create(
		<Router>
			<Pagination
				onSelectPage={onSelectPage()}
				page={5}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
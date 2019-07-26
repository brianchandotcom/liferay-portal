import {Pagination} from '../../../../src/main/resources/META-INF/resources/js/shared/components/pagination/Pagination';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<Pagination
				match={{params: {page: 1}}}
				maxPages={10}
				pageSize={1}
				totalCount={10}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const component = mount(
		<Router>
			<Pagination
				match={{params: {page: 1}}}
				maxPages={10}
				pageSize={1}
				totalCount={10}
			/>
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should render component at page 1', () => {
	const component = renderer.create(
		<Router>
			<Pagination
				match={{params: {page: 1}}}
				maxPages={10}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 2', () => {
	const component = renderer.create(
		<Router>
			<Pagination
				match={{params: {page: 2}}}
				maxPages={10}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 3', () => {
	const component = renderer.create(
		<Router>
			<Pagination
				match={{params: {page: 3}}}
				maxPages={10}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 4', () => {
	const component = renderer.create(
		<Router>
			<Pagination
				match={{params: {page: 4}}}
				maxPages={10}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component at page 5', () => {
	const component = renderer.create(
		<Router>
			<Pagination
				match={{params: {page: 5}}}
				maxPages={10}
				pageSize={5}
				totalCount={26}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should test build menu', () => {
	const component = mount(
		<Router>
			<Pagination
				match={{params: {page: 5}}}
				maxPages={5}
				pageSize={5}
				totalCount={400}
			/>
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should test build menu', () => {
	const component = mount(
		<Router>
			<div>
				<Pagination
					match={{params: {page: 20}}}
					maxPages={5}
					pageSize={5}
					totalCount={400}
				/>
			</div>
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should test build menu with page 1', () => {
	const component = mount(
		<Router>
			<div>
				<Pagination
					match={{params: {page: 1}}}
					maxPages={5}
					pageSize={5}
					totalCount={400}
				/>
			</div>
		</Router>
	);

	expect(component).toMatchSnapshot();
});

test('Should test build menu without parameters', () => {
	const component = mount(
		<Router>
			<div>
				<Pagination
					match={{}}
					maxPages={5}
					pageSize={5}
					totalCount={400}
				/>
			</div>
		</Router>
	);

	expect(component).toMatchSnapshot();
});

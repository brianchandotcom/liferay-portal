import ProcessListPagination from '../ProcessListPagination';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const pageClickHandler = () => page => page;

	const component = renderer.create(
		<ProcessListPagination
			entry="1"
			pageClickHandler={pageClickHandler()}
			totalCount="10"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should change page', () => {
	const pageClickHandler = () => page => page;

	const component = shallow(
		<ProcessListPagination
			entry="1"
			pageClickHandler={pageClickHandler()}
			totalCount="10"
		/>
	);

	const instance = component.instance();

	instance.goToPage(2);
	expect(component).toMatchSnapshot();
});
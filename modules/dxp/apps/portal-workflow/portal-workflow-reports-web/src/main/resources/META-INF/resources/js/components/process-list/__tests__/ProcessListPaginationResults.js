import ProcessListPaginationResults from '../ProcessListPaginationResults';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(
		<ProcessListPaginationResults count="10" start="1" total="20" />
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
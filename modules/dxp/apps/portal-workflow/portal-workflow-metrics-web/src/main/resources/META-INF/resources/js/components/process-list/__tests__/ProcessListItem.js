import ProcessListItem from '../ProcessListItem';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component with one list item', () => {
	const component = renderer.create(
		<ProcessListItem
			id={36401}
			instanceCount={10}
			ontimeInstanceCount={5}
			overdueInstanceCount={5}
			title="Process test"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with 1 instance count', () => {
	const component = renderer.create(
		<ProcessListItem
			id={36401}
			instanceCount={1}
			ontimeInstanceCount={5}
			overdueInstanceCount={5}
			title="Process test"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
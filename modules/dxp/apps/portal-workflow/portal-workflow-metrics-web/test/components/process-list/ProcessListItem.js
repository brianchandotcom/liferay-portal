import ProcessListItem from 'components/process-list/ProcessListItem';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from 'test/mock/MockRouter';

test('Should render component with one list item', () => {
	const component = renderer.create(
		<Router>
			<ProcessListItem
				id={36401}
				instanceCount={10}
				onTimeInstanceCount={5}
				overdueInstanceCount={5}
				title='Process test'
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

test('Should render component with 1 instance count', () => {
	const component = renderer.create(
		<Router>
			<ProcessListItem
				id={36401}
				instanceCount={1}
				onTimeInstanceCount={5}
				overdueInstanceCount={5}
				title='Process test'
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});

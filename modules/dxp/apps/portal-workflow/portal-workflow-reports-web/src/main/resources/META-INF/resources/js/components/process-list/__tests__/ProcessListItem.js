import ProcessListItem from '../ProcessListItem';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const component = renderer.create(
		<ProcessListItem
			instancesCount="10"
			onTime="5"
			overdue="5"
			processName="Process test"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
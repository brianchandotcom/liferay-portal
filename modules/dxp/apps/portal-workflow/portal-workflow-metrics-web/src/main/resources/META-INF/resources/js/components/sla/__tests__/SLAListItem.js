import React from 'react';
import renderer from 'react-test-renderer';
import SLAListItem from '../SLAListItem';

test('Should render component', () => {
	const component = renderer.create(
		<SLAListItem
			instancesCount="10"
			onTime="5"
			overdue="5"
			processName="Process test"
		/>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
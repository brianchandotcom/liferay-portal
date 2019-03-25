import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../test/mock/MockRouter';
import SLAListItem from '../SLAListItem';

test('Should render component', () => {
	const component = renderer.create(
		<Router>
			<SLAListItem
				instancesCount="10"
				onTime="5"
				overdue="5"
				processName="Process test"
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
import ProcessListTable from '../ProcessListTable';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../test/mock/MockRouter';

test('Should render component', () => {
	const data = [
		{
			id: 36401,
			instancesCount: 0,
			title: 'test'
		}
	];

	const component = renderer.create(
		<Router>
			<ProcessListTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
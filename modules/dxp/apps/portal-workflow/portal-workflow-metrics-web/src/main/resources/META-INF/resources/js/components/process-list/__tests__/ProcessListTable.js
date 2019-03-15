import ProcessListTable from '../ProcessListTable';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const data = [
		{
			id: 36401,
			instancesCount: 0,
			title: 'test'
		}
	];

	const component = renderer.create(<ProcessListTable items={data} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
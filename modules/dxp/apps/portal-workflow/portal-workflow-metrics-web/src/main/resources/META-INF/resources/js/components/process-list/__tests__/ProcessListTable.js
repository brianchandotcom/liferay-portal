import ProcessListTable from '../ProcessListTable';
import React from 'react';
import renderer from 'react-test-renderer';

test('Should render component', () => {
	const data = [
		{
			instancesCount: 0,
			title: 'test'
		}
	];

	const component = renderer.create(<ProcessListTable processes={data} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
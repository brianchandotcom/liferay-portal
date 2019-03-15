import React from 'react';
import renderer from 'react-test-renderer';
import SLAListTable from '../SLAListTable';

test('Should render component', () => {
	const data = [
		{
			description: 'Total time to complete the request.',
			duration: '4d 6h 30min',
			name: 'Total resolution time'
		},
		{
			description: 'Total time to complete the request.',
			duration: '4d 6h 30min',
			name: 'Total resolution time'
		},
		{
			description: 'Total time to complete the request.',
			duration: '4d 6h 30min',
			name: 'Total resolution time'
		}
	];

	const component = renderer.create(<SLAListTable sla={data} />);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
import InstanceListTable from '../InstanceListTable';
import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../../test/mock/MockRouter';

test('Should render component', () => {
	const data = [
		{
			assetName: 'Item Subject Test',
			assetType: 'Process',
			createdBy: 'User Test',
			creationDate: new Date('2019', '01', '01'),
			id: 12351,
			processSteps: ['Step 1', 'Step 2', 'Step 3'],
			status: 'on-time'
		},
		{
			assetName: 'Item Subject Test 2',
			assetType: 'Process',
			createdBy: 'User Test',
			creationDate: new Date('2019', '01', '02'),
			id: 12351,
			processSteps: ['Step 1', 'Step 2'],
			status: 'overdue'
		},
		{
			assetName: 'Item Subject Test 3',
			assetType: 'Process',
			createdBy: 'User Test',
			creationDate: new Date('2019', '01', '03'),
			id: 12351,
			status: 'no-sla-counting'
		}
	];

	const component = renderer.create(
		<Router client={fetch(data)}>
			<InstanceListTable items={data} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
import InstanceListTable from '../../../../src/main/resources/META-INF/resources/js/components/process-metrics/instance-list/InstanceListTable';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should render component', () => {
	const data = [
		{
			assetTitle: 'Item Subject Test',
			assetType: 'Process',
			dateCreated: new Date(Date.UTC('2019', '01', '01')),
			id: 12351,
			slaStatus: 'OnTime',
			taskNames: ['Step 1', 'Step 2', 'Step 3'],
			userName: 'User Test'
		},
		{
			assetTitle: 'Item Subject Test 2',
			assetType: 'Process',
			dateCreated: new Date(Date.UTC('2019', '01', '02')),
			id: 12351,
			slaStatus: 'Overdue',
			taskNames: ['Step 1', 'Step 2'],
			userName: 'User Test'
		},
		{
			assetTitle: 'Item Subject Test 3',
			assetType: 'Process',
			dateCreated: new Date(Date.UTC('2019', '01', '03')),
			id: 12351,
			slaStatus: 'Untracked',
			userName: 'User Test'
		},
		{
			assetTitle: 'Item Subject Test 4',
			assetType: 'Process',
			dateCreated: new Date(Date.UTC('2019', '01', '04')),
			id: 12351,
			userName: 'User Test'
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

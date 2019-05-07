import { FilterResultsBar } from '../FilterResultsBar';
import React from 'react';
import renderer from 'react-test-renderer';
import { MockRouter as Router } from '../../../../test/mock/MockRouter';

test('Should not render component when the items are not selected', () => {
	const filters = [
		{
			items: [
				{
					active: false,
					key: 'overdue',
					name: 'Overdue'
				}
			],
			key: 'slaStatus',
			name: 'SLA Status'
		},
		{
			key: 'taskKeys',
			name: 'Process Step'
		}
	];

	const component = renderer.create(
		<Router>
			<FilterResultsBar filters={filters} totalCount={1} />
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toBeNull();
});

test('Should remove item', () => {
	const filters = [
		{
			items: [
				{
					active: true,
					key: 'overdue',
					name: 'Overdue'
				}
			],
			key: 'slaStatus',
			name: 'SLA Status'
		}
	];

	const mockHistory = {
		push: jest.fn()
	};

	const component = mount(
		<Router>
			<FilterResultsBar
				filters={filters}
				history={mockHistory}
				location={{ search: '?filters.slaStatus%5B0%5D=overdue' }}
				totalCount={1}
			/>
		</Router>
	);

	const instance = component.find(FilterResultsBar).instance();

	instance.onRemoveButtonClick(filters[0], filters[0].items[0]);

	expect(mockHistory.push).toHaveBeenCalled();
});

test('Should render component', () => {
	const filters = [
		{
			items: [
				{
					active: true,
					key: 'overdue',
					name: 'Overdue'
				},
				{
					active: false,
					key: 'ontime',
					name: 'On Time'
				}
			],
			key: 'slaStatus',
			name: 'SLA Status'
		}
	];

	const component = renderer.create(
		<Router>
			<FilterResultsBar
				filters={filters}
				location={{
					pathname: '/instances',
					search: '?filters.slaStatus%5B0%5D=overdue'
				}}
				totalCount={2}
			/>
		</Router>
	);

	const tree = component.toJSON();

	expect(tree).toMatchSnapshot();
});
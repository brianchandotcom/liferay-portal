import {FilterResultsBar} from '../../../../src/main/resources/META-INF/resources/js/shared/components/filter/FilterResultsBar';
import React from 'react';
import renderer from 'react-test-renderer';
import {MockRouter as Router} from '../../../mock/MockRouter';

test('Should clear filters', () => {
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
				location={{search: '?filters.slaStatus%5B0%5D=overdue'}}
				match={{params: {page: 3}, path: '/instances/:page'}}
				totalCount={1}
			/>
		</Router>
	);

	const instance = component.find(FilterResultsBar).instance();

	instance.onClearAllButtonClick();

	expect(mockHistory.push).toHaveBeenCalled();
});

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

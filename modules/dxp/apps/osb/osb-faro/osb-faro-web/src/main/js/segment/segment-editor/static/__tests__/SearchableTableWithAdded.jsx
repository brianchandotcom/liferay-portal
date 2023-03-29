import Promise from 'metal-promise';
import React from 'react';
import SearchableTableWithAdded from '../SearchableTableWithAdded';
import {cleanup, render} from '@testing-library/react';
import {createOrderIOMap} from 'shared/util/pagination';
import {MemoryRouter} from 'react-router';
import {mockIndividual} from 'test/data';
import {noop, times} from 'lodash';
import {OrderedMap} from 'immutable';

const COLUMNS = [
	{
		accessor: 'name',
		className: 'table-cell-expand',
		label: 'Name',
		title: true
	},
	{
		accessor: 'properties.email',
		label: 'Email'
	}
];

const TOTAL = 5;

const INDIVIDUALS = times(TOTAL, i =>
	mockIndividual(i, {email: `email${i}@liferay.com`})
);

const defaultProps = {
	addedItemsIOMap: new OrderedMap(INDIVIDUALS.map(item => [item.id, item])),
	columns: COLUMNS,
	dataSourceFn: () =>
		Promise.resolve({
			items: INDIVIDUALS,
			total: INDIVIDUALS.length
		}),
	onSelectEntirePage: noop,
	orderIOMap: createOrderIOMap('name'),
	rowIdentifier: 'id',
	selectedItemsIOMap: new OrderedMap(),
	showStaged: true
};

const DefaultComponent = props => (
	<MemoryRouter>
		<SearchableTableWithAdded {...defaultProps} {...props} />
	</MemoryRouter>
);

jest.unmock('react-dom');

describe('SearchableTableWithAdded', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DefaultComponent
				addedItemsIOMap={new OrderedMap()}
				dataSourceFn={() => Promise.resolve({items: [], total: 0})}
				showStaged={false}
				stagedProps={{}}
			/>
		);

		jest.runAllTimers();
		expect(container).toMatchSnapshot();
	});

	it('should show the staged table if showStaged is true', () => {
		const {container} = render(<DefaultComponent />);

		jest.runAllTimers();

		expect(container).toMatchSnapshot();
	});
});

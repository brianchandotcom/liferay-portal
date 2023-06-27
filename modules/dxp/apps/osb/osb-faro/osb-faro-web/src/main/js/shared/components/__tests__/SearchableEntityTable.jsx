import Promise from 'metal-promise';
import React from 'react';
import SearchableEntityTable from '../SearchableEntityTable';
import {cleanup, render} from '@testing-library/react';
import {mockIndividual} from 'test/data';
import {SelectionProvider} from 'shared/context/selection';
import {times} from 'lodash';
import {withStaticRouter} from 'test/mock-router';

jest.unmock('react-dom');

const COLUMNS = [
	{
		accessor: 'name',
		className: 'table-cell-expand',
		label: 'Name',
		title: true
	},
	{
		accessor: 'properties.salary',
		label: 'Salary'
	},
	{
		accessor: 'properties.jobTitle',
		label: 'Job Title'
	},
	{
		accessor: 'properties.country',
		label: 'Country'
	},
	{
		accessor: 'properties.age',
		label: 'Age'
	}
];

const TOTAL = 5;
const INDIVIDUALS = times(TOTAL, i => mockIndividual(i));

const DefaultComponent = withStaticRouter(SearchableEntityTable);

describe('SearchableEntityTable', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DefaultComponent
				columns={COLUMNS}
				dataSourceFn={() =>
					Promise.resolve({items: INDIVIDUALS, total: TOTAL})
				}
				groupId='23'
				rowIdentifier='id'
			/>
		);

		expect(container).toMatchSnapshot();
	});

	it('should render w/ Checkboxes', () => {
		const {container} = render(
			<SelectionProvider>
				<DefaultComponent
					columns={COLUMNS}
					dataSourceFn={() =>
						Promise.resolve({items: INDIVIDUALS, total: TOTAL})
					}
					groupId='23'
					rowIdentifier='id'
					showCheckbox
				/>
			</SelectionProvider>
		);

		jest.runAllTimers();

		expect(
			container.querySelectorAll('tr.clickable input[type=checkbox]')
				.length
		).toBe(5);
	});

	it('should render as loading if overrideLoading is true, even if loading is false', () => {
		const {container} = render(
			<SelectionProvider>
				<DefaultComponent
					columns={COLUMNS}
					dataSourceFn={() =>
						Promise.resolve({items: INDIVIDUALS, total: TOTAL})
					}
					groupId='23'
					overrideLoading
					rowIdentifier='id'
					showCheckbox
				/>
			</SelectionProvider>
		);

		jest.runAllTimers();
		expect(container.querySelector('.loading-root')).toBeTruthy();
	});
});

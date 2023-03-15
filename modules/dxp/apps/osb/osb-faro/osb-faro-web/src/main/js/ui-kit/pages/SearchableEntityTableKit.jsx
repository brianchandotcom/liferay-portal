import autobind from 'autobind-decorator';
import Promise from 'metal-promise';
import React from 'react';
import SearchableEntityTable from 'shared/components/SearchableEntityTable';
import {createOrderIOMap} from 'shared/util/pagination';
import {mockIndividual} from 'test/data';
import {noop, times} from 'lodash';

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

const INDIVIDUALS = times(TOTAL, i =>
	mockIndividual(i, {
		age: Math.round(Math.random() * 100),
		country: 'USA',
		jobTitle: 'Developer'
	})
);

class SearchableEntityTableKit extends React.Component {
	@autobind
	fetchData() {
		return Promise.resolve({items: INDIVIDUALS, total: TOTAL});
	}

	render() {
		return (
			<div>
				<h3>{'SearchableEntityTable'}</h3>

				<SearchableEntityTable
					checkDisabled={noop}
					columns={COLUMNS}
					dataSourceFn={this.fetchData}
					groupId='23'
					orderIOMap={createOrderIOMap('name')}
					rowIdentifier='id'
				/>

				<h3>{'SearchableEntityTable w/ Checkboxes'}</h3>

				<SearchableEntityTable
					checkDisabled={noop}
					columns={COLUMNS}
					dataSourceFn={this.fetchData}
					groupId='23'
					orderIOMap={createOrderIOMap('name')}
					rowIdentifier='id'
					showCheckbox
				/>
			</div>
		);
	}
}

export default SearchableEntityTableKit;

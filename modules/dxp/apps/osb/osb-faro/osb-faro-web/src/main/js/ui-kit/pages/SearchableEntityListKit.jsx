import autobind from 'autobind-decorator';
import Promise from 'metal-promise';
import React from 'react';
import SearchableEntityList from 'shared/components/SearchableEntityList';
import {mockIndividual} from 'test/data';
import {times} from 'lodash';

const TOTAL = 5;

const INDIVIDUALS = times(TOTAL, i =>
	mockIndividual(i, {
		age: Math.round(Math.random() * 100),
		country: 'USA',
		jobTitle: 'Developer'
	})
);

class SearchableEntityListKit extends React.Component {
	@autobind
	fetchData() {
		return Promise.resolve({items: INDIVIDUALS, total: TOTAL});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<h3>{'SearchableEntityList'}</h3>

				<SearchableEntityList
					dataSourceFn={this.fetchData}
					groupId='23'
				/>

				<h3>{'SearchableEntityList w/ Checkboxes'}</h3>

				<SearchableEntityList
					dataSourceFn={this.fetchData}
					groupId='23'
					showCheckbox
				/>
			</div>
		);
	}
}

export default SearchableEntityListKit;

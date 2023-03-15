import autobind from 'autobind-decorator';
import BaseResults from 'shared/components/BaseResults';
import Promise from 'metal-promise';
import React from 'react';
import {mockIndividual} from 'test/data';
import {noop, times} from 'lodash';

const TOTAL = 5;

const INDIVIDUALS = times(TOTAL, i =>
	mockIndividual(i, {
		age: Math.round(Math.random() * 100),
		country: 'USA',
		jobTitle: 'Developer'
	})
);

class SearchableEntityList extends React.Component {
	@autobind
	getData() {
		return Promise.resolve({items: INDIVIDUALS, total: TOTAL});
	}

	@autobind
	getDataFailure() {
		return Promise.reject({});
	}

	render() {
		return (
			<div
				className={
					this.props.className ? ` ${this.props.className}` : ''
				}
			>
				<h3>{'BaseResults'}</h3>

				<BaseResults
					dataSourceFn={this.getData}
					groupId='23'
					resultsRenderer={noop}
				/>

				<h3>{'BaseResults Error'}</h3>

				<BaseResults
					dataSourceFn={this.getDataFailure}
					groupId='23'
					resultsRenderer={noop}
				/>
			</div>
		);
	}
}

export default SearchableEntityList;

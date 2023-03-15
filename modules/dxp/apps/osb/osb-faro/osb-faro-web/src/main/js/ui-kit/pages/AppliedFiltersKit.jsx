import AppliedFilters from 'shared/components/filter/AppliedFilters';
import BasePage from 'shared/components/base-page';
import React from 'react';
import Row from '../components/Row';

export default class AppliedFiltersKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<h3>{'AppliedFilters'}</h3>

					<BasePage.Context.Provider
						value={{
							filters: {},
							rangeKey: {
								defaultValue: '',
								lastValue: ''
							},
							router: {query: ''},
							sidebarId: null
						}}
					>
						<AppliedFilters
							filters={{devices: ['smartphone', 'desktop']}}
						/>
					</BasePage.Context.Provider>
				</Row>
			</div>
		);
	}
}

import BasePage from 'shared/components/base-page';
import DropdownRangeKey from 'shared/components/DropdownRangeKey';
import React from 'react';
import Row from '../components/Row';
import {range} from 'lodash';

const mockItems = range(3).map(i => ({
	description: `foo description${i}`,
	label: `foo label${i}`,
	value: `foo value${i}`
}));

export default class DropdownRangeKeyKit extends React.Component {
	render() {
		return (
			<div>
				<Row>
					<h3>{'DropdownRangeKey'}</h3>

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
						<DropdownRangeKey
							items={mockItems}
							rangeKey='foo value1'
						/>
					</BasePage.Context.Provider>
				</Row>
			</div>
		);
	}
}

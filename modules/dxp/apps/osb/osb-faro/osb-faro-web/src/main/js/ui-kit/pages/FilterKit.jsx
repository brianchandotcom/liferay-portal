import Filter from 'shared/components/filter';
import React from 'react';
import Row from '../components/Row';
import {range} from 'lodash';

const mockItems = range(1).map(i => ({
	items: [
		{
			category: 'FOO NAME',
			hasSearch: true,
			inputType: 'radio',
			label: `child foo label${i}`,
			value: `child foo value${i}`
		}
	],
	label: `foo label${i}`,
	name: 'FOO NAME',
	value: `${i}`
}));

export default class FilterKit extends React.Component {
	handleApplyFilters(appliedFilters) {
		console.log('applied filters changed!', appliedFilters); // eslint-disable-line no-console
	}

	render() {
		return (
			<div>
				<Row>
					<h3>{'Filter'}</h3>

					<div style={{height: '50vh', position: 'relative'}}>
						<Filter
							items={mockItems}
							onChange={this.handleApplyFilters}
						/>
					</div>
				</Row>
			</div>
		);
	}
}

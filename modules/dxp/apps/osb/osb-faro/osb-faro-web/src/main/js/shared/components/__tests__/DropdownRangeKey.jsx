import DropdownRangeKey from '../DropdownRangeKey';
import React from 'react';
import {cleanup, render} from '@testing-library/react';

jest.unmock('react-dom');

const MOCK_ITEMS = [
	{
		description: '19 Sep, 08 PM - 20 Sep, 07 PM',
		label: 'Last 24 hours',
		value: '0'
	},
	{
		description: '19 Sep, 12 AM - 19 Sep, 11 PM',
		label: 'Yesterday',
		value: '1'
	},
	{
		description: '13 Sep, 12 AM - 19 Sep, 12 AM',
		label: 'Last 7 days',
		value: '7'
	},
	{
		description: '23 Aug, 12 AM - 19 Sep, 12 AM',
		label: 'Last 28 days',
		value: '28'
	},
	{
		description: '21 Aug, 12 AM - 19 Sep, 12 AM',
		label: 'Last 30 days',
		value: '30'
	},
	{
		description: '24 Jun, 12 AM - 16 Sep, 12 AM',
		label: 'Last 90 days',
		value: '90'
	}
];

describe('DropdownRangeKey', () => {
	afterEach(cleanup);

	it('should render', () => {
		const {container} = render(
			<DropdownRangeKey items={MOCK_ITEMS} rangeKey='30' />
		);

		expect(container).toMatchSnapshot();
	});
});

import DropdownRangeKey, {formatDate} from '../DropdownRangeKey';
import mockStore from 'test/mock-store';
import moment from 'moment-timezone';
import React from 'react';
import {cleanup, render} from '@testing-library/react';
import {createMemoryHistory} from 'history';
import {Provider} from 'react-redux';
import {Router} from 'react-router-dom';

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
		const history = createMemoryHistory();

		const {container} = render(
			<Provider store={mockStore()}>
				<Router history={history}>
					<DropdownRangeKey items={MOCK_ITEMS} rangeKey='30' />
				</Router>
			</Provider>
		);

		expect(container).toMatchSnapshot();
	});
});

describe.only('DropdownRangeKey formatDate', () => {
	it.each`
		timeZoneId               | result                         | isAfter
		${'Asia/Tokyo'}          | ${'1970-01-01T09:00:00+00:00'} | ${true}
		${'Australia/Sydney'}    | ${'1970-01-01T10:00:00+00:00'} | ${true}
		${'Asia/Shanghai'}       | ${'1970-01-01T08:00:00+00:00'} | ${true}
		${'Asia/Riyadh'}         | ${'1970-01-01T03:00:00+00:00'} | ${true}
		${'Pacific/Midway'}      | ${'1969-12-31T13:00:00+00:00'} | ${false}
		${'America/Los_Angeles'} | ${'1969-12-31T16:00:00+00:00'} | ${false}
		${'America/Vancouver'}   | ${'1969-12-31T16:00:00+00:00'} | ${false}
		${'America/Recife'}      | ${'1969-12-31T21:00:00+00:00'} | ${false}
	`(
		'returns formatted dates for timezone $timeZoneId',
		({isAfter, result, timeZoneId}) => {
			expect(formatDate(timeZoneId, moment(0)).format()).toEqual(result);

			expect(formatDate(timeZoneId, moment(0)).isAfter(moment(0))).toBe(
				isAfter
			);
		}
	);
});

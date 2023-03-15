jest.unmock('clay-charts');

import {mapResultToProps} from '../dropdown-range-key';

const timeRange = {
	data: {
		timeRange: [
			{
				default: false,
				endDate: '2018-09-19T00:00',
				key: 'last-seven-days',
				label: 'Last 7 days',
				rangeKey: 7,
				startDate: '2018-09-13T00:00'
			},
			{
				default: false,
				endDate: '2018-09-20T19:00',
				key: 'last-24-hours',
				label: 'Last 24 hours',
				rangeKey: 0,
				startDate: '2018-09-19T20:00'
			},
			{
				default: false,
				endDate: '2018-09-19T00:00',
				key: 'last-28-days',
				label: 'Last 28 days',
				rangeKey: 28,
				startDate: '2018-08-23T00:00'
			},
			{
				default: true,
				endDate: '2018-09-19T00:00',
				key: 'last-30-days',
				label: 'Last 30 days',
				rangeKey: 30,
				startDate: '2018-08-21T00:00'
			},
			{
				default: false,
				endDate: '2018-09-16T00:00',
				key: 'last-90-days',
				label: 'Last 90 days',
				rangeKey: 90,
				startDate: '2018-06-24T00:00'
			},
			{
				default: false,
				endDate: '2018-09-19T23:00',
				key: 'yesterday',
				label: 'Yesterday',
				rangeKey: 1,
				startDate: '2018-09-19T00:00'
			}
		]
	}
};

describe('dropdown-range-key', () => {
	it('should render map result to props', () => {
		const result = mapResultToProps(timeRange);

		expect(result).toMatchSnapshot();
	});
});

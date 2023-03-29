jest.unmock('clay-charts');

import {getMetricsMapper} from '../metrics';

const data = {
	forms: {
		views: {
			histogram: {
				asymmetricComparison: false,
				metrics: [
					{
						key: '2018-05-17T00:00',
						previousValue: 108,
						previousValueKey: '2018-05-10T00:00',
						trend: {
							percentage: 10,
							trendClassification: 'POSITIVE'
						},
						value: 4,
						valueKey: '2018-05-17T00:00'
					},
					{
						key: '2018-05-18T00:00',
						previousValue: 134,
						previousValueKey: '2018-05-11T00:00',
						trend: {
							percentage: -20,
							trendClassification: 'POSITIVE'
						},
						value: 315,
						valueKey: '2018-05-18T00:00'
					},
					{
						key: '2018-05-19T00:00',
						previousValue: 71,
						previousValueKey: '2018-05-12T00:00',
						trend: {
							percentage: -30,
							trendClassification: 'POSITIVE'
						},
						value: 60,
						valueKey: '2018-05-19T00:00'
					},
					{
						key: '2018-05-20T00:00',
						previousValue: 79,
						previousValueKey: '2018-05-13T00:00',
						trend: {
							percentage: 20,
							trendClassification: 'NEUTRAL'
						},
						value: 46,
						valueKey: '2018-05-20T00:00'
					},
					{
						key: '2018-05-21T00:00',
						previousValue: 250,
						previousValueKey: '2018-05-14T00:00',
						trend: {
							percentage: -20.79353416605437,
							trendClassification: 'NEGATIVE'
						},
						value: 183,
						valueKey: '2018-05-21T00:00'
					},
					{
						key: '2018-05-22T00:00',
						previousValue: 543,
						previousValueKey: '2018-05-15T00:00',
						trend: {
							percentage: -20.222,
							trendClassification: 'NEGATIVE'
						},
						value: 241,
						valueKey: '2018-05-22T00:00'
					},
					{
						key: '2018-05-23T00:00',
						previousValue: 176,
						previousValueKey: '2018-05-16T00:00',
						trend: {
							percentage: -14,
							trendClassification: 'NEGATIVE'
						},
						value: 229,
						valueKey: '2018-05-23T00:00'
					}
				]
			},
			trend: {
				percentage: 100,
				trendClassification: 'NEGATIVE'
			},
			value: 1078
		}
	}
};

describe('Shared HOCs Mappers - Metrics', () => {
	it('should map metrics information', () => {
		const mapper = getMetricsMapper(result => result.forms, [
			{
				name: 'views',
				title: 'Views',
				tooltipTitle: 'Avg. Views',
				type: 'number'
			}
		]);

		expect(
			mapper.props({
				data,
				ownProps: {
					rangeSelectors: {
						rangeEnd: null,
						rangeKey: '7',
						rangeStart: null
					}
				}
			})
		).toMatchSnapshot();
	});
});

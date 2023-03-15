import {mapPropsToOptions, mapResultToProps} from '../individual-metrics-query';
import {range} from 'lodash';

const mockMetric = {
	histogram: {metrics: range(5).map(i => ({value: i}))},
	trend: {percentage: 25.4},
	value: 10
};

const mockData = {
	data: {
		individualMetric: {
			anonymousIndividualsMetric: mockMetric,
			knownIndividualsMetric: mockMetric,
			totalIndividualsMetric: mockMetric
		}
	}
};

describe('Individual Metrics Query Mapper', () => {
	describe('mapPropsToOptions', () => {
		it('should map props to options', () => {
			const props = {
				interval: 'day',
				rangeSelectors: {
					rangeKey: '30'
				}
			};

			expect(mapPropsToOptions(props)).toEqual(
				expect.objectContaining({
					variables: {
						interval: 'day',
						rangeEnd: null,
						rangeKey: 30,
						rangeStart: null
					}
				})
			);
		});
	});

	describe('mapResultToProps', () => {
		xit('should map results to props', () => {
			expect(mapResultToProps(mockData).items).toEqual(
				expect.arrayContaining([
					expect.objectContaining({
						change: expect.any(Number),
						data: expect.any(Array),
						id: expect.any(String),
						info:
							expect.objectContaining({
								content: expect.any(String),
								title: expect.any(String)
							}) || undefined,
						title: expect.any(String),
						total: expect.any(Number)
					})
				])
			);
		});
	});
});

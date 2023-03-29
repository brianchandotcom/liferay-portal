import {CompositionTypes} from 'shared/util/constants';
import {
	getMapResultToProps,
	mapCardPropsToOptions,
	mapPropsToOptions
} from '../composition-query';

const channelId = '321';

const mockData = {
	siteInterests: {
		compositions: [{foo: 'bar'}],
		maxCount: 85,
		total: 123,
		totalCount: 321
	}
};

const mockProps = {
	channelId,
	delta: 5,
	page: 2,
	rangeSelectors: {
		rangeEnd: null,
		rangeKey: '90',
		rangeStart: null
	}
};

describe('Composition Query Mapper', () => {
	describe('getMapResultToProps', () => {
		it('should map interests list query result to props', () => {
			expect(
				getMapResultToProps(CompositionTypes.SiteInterests)({
					data: mockData
				})
			).toEqual(
				expect.objectContaining({
					items: expect.any(Array),
					maxCount: expect.any(Number),
					total: expect.any(Number),
					totalCount: expect.any(Number)
				})
			);
		});
	});

	describe('mapCardPropsToOptions', () => {
		it('should map interests list query card props to options', () => {
			const rangeKey = '30';
			const channelId = '321';

			expect(
				mapCardPropsToOptions({
					channelId,
					rangeSelectors: {
						rangeKey
					}
				})
			).toEqual(
				expect.objectContaining({
					variables: {
						activeTabId: null,
						channelId,
						rangeEnd: null,
						rangeKey: parseInt(rangeKey),
						rangeStart: null,
						size: 5,
						start: 0
					}
				})
			);
		});
	});

	describe('mapPropsToOptions', () => {
		it('should map interests list query props to options', () => {
			const {
				delta,
				rangeSelectors: {rangeKey}
			} = mockProps;

			expect(mapPropsToOptions(mockProps)).toEqual(
				expect.objectContaining({
					variables: {
						channelId,
						rangeKey: parseInt(rangeKey),
						size: parseInt(delta),
						start: 5
					}
				})
			);
		});
	});
});

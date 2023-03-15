import {
	mapPropsToOptions,
	mapResultToProps
} from '../touchpoint-assets-list-query';

const data = {
	assets: [
		{
			assetId: 'c87b5e99-11d9-4d4d-6b6f-d78c28f1d2c6',
			assetTitle: '',
			assetType: 'document',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '2092152',
			assetTitle: '',
			assetType: 'journal',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '229317896',
			assetTitle:
				'Liferay DXP Performance - Benchmark Study of Liferay Digital Enterprise 7.0',
			assetType: 'form',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '178554285',
			assetTitle: '',
			assetType: 'journal',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '146518',
			assetTitle: '',
			assetType: 'journal',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '231733645',
			assetTitle: '',
			assetType: 'journal',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '231819306',
			assetTitle: 'Data Protection for Liferay Services and Software',
			assetType: 'form',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '3805997',
			assetTitle: '',
			assetType: 'journal',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '1649254',
			assetTitle: 'Portal Best Practices',
			assetType: 'form',
			defaultMetric: {
				value: 1000
			}
		},
		{
			assetId: '7a3b7957-41ed-4d64-bdea-a0ebbc3a178f',
			assetTitle: '',
			assetType: 'form',
			defaultMetric: {
				value: 1000
			}
		}
	]
};

const props = {
	rangeSelectors: {rangeKey: '7'},
	router: {
		params: {
			title: 'Liferay'
		}
	},
	touchpoint: 'https://www.liferay.com/downloads'
};

describe('TouchpointsAssetsListQuery Mappers', () => {
	it('should extract items from result', () => {
		const props = mapResultToProps({data});

		expect(props).toMatchSnapshot();
	});

	it('should include options', () => {
		const options = mapPropsToOptions(props);

		expect(options).toEqual({
			variables: {
				rangeEnd: null,
				rangeKey: 7,
				rangeStart: null,
				title: 'Liferay',
				touchpoint: 'https://www.liferay.com/downloads'
			}
		});
	});

	it('should update the touchpoint in variables', () => {
		const options = mapPropsToOptions({
			...props,
			touchpoint: 'https://www.liferay.com/about'
		});

		expect(options).toEqual({
			variables: {
				rangeEnd: null,
				rangeKey: 7,
				rangeStart: null,
				title: 'Liferay',
				touchpoint: 'https://www.liferay.com/about'
			}
		});
	});
});

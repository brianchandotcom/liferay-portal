jest.unmock('clay-charts');

import {getLocationsMapper, getLocationsMapperCountries} from '../locations';

const ASSET_ID = 'formId';
const TOUCHPOINT = null;

const data = {
	form: {
		submissionsMetric: {
			geolocation: [
				{
					metrics: [
						{
							value: 100,
							valueKey: 'Unknown'
						},
						{
							value: 100,
							valueKey: 'Pernambuco'
						},
						{
							value: 100,
							valueKey: 'Sao Paulo'
						},
						{
							value: 100,
							valueKey: 'Parana'
						},
						{
							value: 100,
							valueKey: 'Rio Grande do Sul'
						}
					],
					value: 100,
					valueKey: 'Brazil'
				},
				{
					metrics: [
						{
							value: 100,
							valueKey: 'Unknown'
						},
						{
							value: 100,
							valueKey: 'Catalonia'
						},
						{
							value: 100,
							valueKey: 'Madrid'
						},
						{
							value: 100,
							valueKey: 'Andalucia'
						},
						{
							value: 100,
							valueKey: 'Castilla y Leon'
						},
						{
							value: 100,
							valueKey: 'Others'
						}
					],
					value: 100,
					valueKey: 'Spain'
				},
				{
					metrics: [
						{
							value: 100,
							valueKey: 'California'
						},
						{
							value: 100,
							valueKey: 'Unknown'
						},
						{
							value: 100,
							valueKey: 'Georgia'
						},
						{
							value: 100,
							valueKey: 'New Jersey'
						},
						{
							value: 100,
							valueKey: 'Florida'
						},
						{
							value: 100,
							valueKey: 'Others'
						}
					],
					value: 100,
					valueKey: 'United States'
				}
			]
		}
	}
};

describe('Shared HOCs Mappers - Locations', () => {
	it('should map locations information', () => {
		const mapper = getLocationsMapper(
			result => result.form.submissionsMetric
		);

		expect(
			mapper.props(
				{
					data,
					ownProps: {
						rangeSelectors: {rangeKey: '0'}
					}
				},
				{
					filters: {
						location: ['Any']
					}
				}
			)
		).toMatchSnapshot();

		expect(
			mapper.options({
				filters: {
					location: ['Any']
				},
				rangeSelectors: {rangeKey: '0'},
				router: {
					params: {
						assetId: ASSET_ID,
						touchpoint: TOUCHPOINT
					}
				}
			})
		).toMatchSnapshot();
	});

	it('should map locations information with region Brazil', () => {
		const mapper = getLocationsMapper(
			result => result.form.submissionsMetric
		);

		expect(
			mapper.props(
				{
					data,
					ownProps: {
						rangeSelectors: {rangeKey: '0'}
					}
				},
				{
					filters: {
						location: ['Brazil']
					}
				}
			)
		).toMatchSnapshot();

		expect(
			mapper.options({
				filters: {
					location: ['Brazil']
				},
				rangeSelectors: {rangeKey: '0'},
				router: {
					params: {
						assetId: ASSET_ID,
						touchpoint: TOUCHPOINT
					}
				}
			})
		).toMatchSnapshot();
	});

	it('should map locations information without geolocation', () => {
		const mapper = getLocationsMapper(
			result => result.form.submissionsMetric
		);

		expect(
			mapper.props(
				{
					data: {
						form: {
							submissionsMetric: {
								geolocation: []
							}
						}
					},
					ownProps: {
						rangeSelectors: {rangeKey: '0'}
					}
				},
				{
					filters: {
						location: ['Any']
					}
				}
			)
		).toMatchSnapshot();
	});
});

describe('Shared HOCs Mappers - Locations Countries', () => {
	it('should return countries information', () => {
		const mapper = getLocationsMapperCountries(
			result => result.form.submissionsMetric
		);

		expect(
			mapper.props(
				{
					data,
					ownProps: {
						rangeSelectors: {rangeKey: '0'}
					}
				},
				{
					filters: {
						location: ['Any']
					}
				}
			)
		).toMatchSnapshot();

		expect(
			mapper.options({
				filters: {
					location: ['Any']
				},
				rangeSelectors: {rangeKey: '0'},
				router: {
					params: {
						assetId: ASSET_ID,
						touchpoint: TOUCHPOINT
					}
				}
			})
		).toMatchSnapshot();
	});

	it('should return countries information indepent if Brazil is selected', () => {
		const mapper = getLocationsMapperCountries(
			result => result.form.submissionsMetric
		);

		expect(
			mapper.props(
				{
					data,
					ownProps: {
						rangeSelectors: {rangeKey: '0'}
					}
				},
				{
					filters: {
						location: ['Brazil']
					}
				}
			)
		).toMatchSnapshot();

		expect(
			mapper.options({
				filters: {
					location: ['Brazil']
				},
				rangeSelectors: {rangeKey: '0'},
				router: {
					params: {
						assetId: ASSET_ID,
						touchpoint: TOUCHPOINT
					}
				}
			})
		).toMatchSnapshot();
	});
});

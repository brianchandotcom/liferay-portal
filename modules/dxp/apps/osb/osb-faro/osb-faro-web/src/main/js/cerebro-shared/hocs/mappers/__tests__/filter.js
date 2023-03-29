import {getFiltersMapper} from '../filter';

const data = {
	data: {
		forms: {
			submissions: {
				device: [
					{
						metrics: [
							{
								value: 2065.0,
								valueKey: 'Windows'
							},
							{
								value: 995.0,
								valueKey: 'macOS'
							},
							{
								value: 136.0,
								valueKey: 'Linux'
							},
							{
								value: 107.0,
								valueKey: 'Mac OS X'
							},
							{
								value: 53.0,
								valueKey: 'Ubuntu'
							},
							{
								value: 3.0,
								valueKey: 'ChromeOS'
							},
							{
								value: 2.0,
								valueKey: 'Fedora'
							},
							{
								value: 1.0,
								valueKey: 'Unknown'
							}
						],
						value: 3362.0,
						valueKey: 'Desktop'
					},
					{
						metrics: [
							{
								value: 151.0,
								valueKey: 'Android'
							},
							{
								value: 61.0,
								valueKey: 'iOS'
							},
							{
								value: 9.0,
								valueKey: 'Unknown'
							},
							{
								value: 8.0,
								valueKey: 'FreeBSD'
							},
							{
								value: 1.0,
								valueKey: 'Windows Phone'
							}
						],
						value: 230.0,
						valueKey: 'SmartPhone'
					},
					{
						metrics: [
							{
								value: 67.0,
								valueKey: 'iOS'
							},
							{
								value: 12.0,
								valueKey: 'Android'
							}
						],
						value: 79.0,
						valueKey: 'Tablet'
					}
				],
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
	}
};

describe('Shared HOCs Mappers - Filter', () => {
	it('should map filter information', () => {
		const mapper = getFiltersMapper(result => result.forms.submissions);

		expect(mapper.props(data)).toMatchSnapshot();
	});
});

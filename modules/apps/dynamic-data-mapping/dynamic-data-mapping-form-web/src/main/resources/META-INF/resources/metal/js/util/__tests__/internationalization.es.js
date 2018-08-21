import {setLocalizedValue} from '../internationalization.es';

describe(
	'Internationlization',
	() => {
		it(
			'should create a localized property for a specific object string value',
			() => {
				const value = 'the title will be localized';
				const obj = {
					title: ''
				};

				setLocalizedValue(obj, 'en_US', 'title', value);

				expect(obj).toMatchObject({
					title: value,
					localizedTitle: {
						en_US: value
					}
				});
			}
		);

		it(
			'should replace a localized value for a specific object string',
			() => {
				const value = 'the title will be localized';
				const obj = {
					title: '',
					localizedTitle: {
						en_US: '',
					},
				};

				setLocalizedValue(obj, 'en_US', 'title', value);

				expect(obj).toMatchObject({
					title: value,
					localizedTitle: {
						en_US: value,
					},
				});
			}
		);
	}
);
import {setLocalizedValue} from '../i18n.es';

describe(
	'Internationlization',
	() => {
		it(
			'should create a localized property for a specific object string value',
			() => {
				const obj = {
					title: ''
				};
				const value = 'the title will be localized';

				setLocalizedValue(obj, 'en_US', 'title', value);

				expect(obj).toMatchObject(
					{
						localizedTitle: {
							en_US: value
						},
						title: value
					}
				);
			}
		);

		it(
			'should replace a localized value for a specific object string',
			() => {
				const obj = {
					localizedTitle: {
						en_US: ''
					},
					title: ''
				};
				const value = 'the title will be localized';

				setLocalizedValue(obj, 'en_US', 'title', value);

				expect(obj).toMatchObject(
					{
						localizedTitle: {
							en_US: value
						},
						title: value
					}
				);
			}
		);
	}
);
import {PagesVisitor} from '../../../util/visitors.es';

export const updateSettingsContextOptions = (field, locale, newOptions) => {

	return {
		...field,
		value: {
			...field.value,
			[locale]: newOptions.map(
				option => {
					return option;
				}
			)
		}
	};
};

export const updateSettingsContextProperty = (state, settingsContext, propertyName, propertyValue) => {
	const {locale} = state;
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields(
			field => {
				if (propertyName === field.fieldName) {
					if (propertyName === 'options') {
						field = updateSettingsContextOptions(field, locale, propertyValue);
					}
					else {
						field = {
							...field,
							value: propertyValue
						};
					}

					if (field.localizable) {
						field.localizedValue = {
							...field.localizedValue,
							[locale]: propertyValue
						};
					}
				}

				return field;
			}
		)
	};
};
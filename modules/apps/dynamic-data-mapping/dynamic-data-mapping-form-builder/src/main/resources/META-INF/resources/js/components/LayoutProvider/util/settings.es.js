import {PagesVisitor} from '../../../util/visitors.es';

export const updateSettingsContextProperty = (
	editingLanguageId,
	settingsContext,
	propertyName,
	propertyValue
) => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields(field => {
			if (propertyName === field.fieldName) {
				field = {
					...field,
					value: propertyValue
				};

				if (field.localizable) {
					field.localizedValue = {
						...field.localizedValue,
						[editingLanguageId]: propertyValue
					};
				}
			}

			return field;
		})
	};
};

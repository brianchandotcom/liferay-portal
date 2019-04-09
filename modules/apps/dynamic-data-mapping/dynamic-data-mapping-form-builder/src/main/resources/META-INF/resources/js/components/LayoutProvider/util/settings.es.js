import {PagesVisitor} from '../../../util/visitors.es';

export const updateLocalizedOptions = (field, defaultLanguageId, editingLanguageId, newOptions = []) => {
	let localizedValue = field.value;

	if (defaultLanguageId === editingLanguageId) {
		localizedValue = {
			...localizedValue,
			[defaultLanguageId]: newOptions
		};
	}
	else {
		const defaultOptions = localizedValue[defaultLanguageId];

		localizedValue = {
			...localizedValue,
			[editingLanguageId]: defaultOptions
				.filter(
					({value}) => !!value
				)
				.map(
					(option, index) => ({
						...option,
						label: newOptions[index].label
					})
				)
		};
	}

	return {
		...field,
		value: localizedValue
	};
};

export const updateSettingsContextProperty = (defaultLanguageId, editingLanguageId, settingsContext, propertyName, propertyValue) => {
	const visitor = new PagesVisitor(settingsContext.pages);

	return {
		...settingsContext,
		pages: visitor.mapFields(
			field => {
				if (propertyName === field.fieldName) {
					if (field.type === 'options') {
						field = updateLocalizedOptions(field, defaultLanguageId, editingLanguageId, propertyValue);
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
							[editingLanguageId]: propertyValue
						};
					}
				}

				return field;
			}
		)
	};
};
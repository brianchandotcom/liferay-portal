import {PagesVisitor} from './visitors.es';

export const formatFieldName = (instanceId, locale, value) => {
	return `ddm$$${value}$${instanceId}$0$$${locale}`;
};

export const generateInstanceId = length => {
	let text = '';

	const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

	for (let i = 0; i < length; i++) {
		text += possible.charAt(Math.floor(Math.random() * possible.length));
	}

	return text;
};

/**
 * Makes sure fields have its settings form filled up with some default values.
 */

export const normalizeSettingsContextPages = (pages, namespace, fieldType, generatedFieldName) => {
	const translationManager = Liferay.component(`${namespace}translationManager`);
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(
		field => {
			const {fieldName} = field;

			if (fieldName === 'name') {
				field = {
					...field,
					value: generatedFieldName,
					visible: true
				};
			}
			else if (fieldName === 'label') {
				field = {
					...field,
					localizedValue: {
						...field.localizedValue,
						[translationManager.get('editingLocale')]: fieldType.label
					},
					type: 'text',
					value: fieldType.label
				};
			}
			else if (fieldName === 'type') {
				field = {
					...field,
					value: fieldType.name
				};
			}
			else if (fieldName === 'validation') {
				field = {
					...field,
					validation: {
						...field.validation,
						fieldName: generatedFieldName
					}
				};
			}
			return {
				...field
			};
		}
	);
};

/**
 * Converts the settings Form of a field into an object of field properties.
 */

export const getFieldPropertiesFromSettingsContext = settingsContext => {
	const properties = {};
	const visitor = new PagesVisitor(settingsContext.pages);

	visitor.mapFields(
		({fieldName, value}) => {
			properties[fieldName] = value;
		}
	);

	return properties;
};
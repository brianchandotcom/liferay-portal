import {findFieldByName} from '../../Form/FormSupport.es';
import {PagesVisitor} from '../../../util/visitors.es';

export const generateFieldName = (pages, desiredName, currentName = null) => {
	let counter = 0;
	let name = normalizeFieldName(desiredName);

	let existingField = findFieldByName(pages, name);

	while (existingField && existingField.fieldName !== currentName) {
		if (counter > 0) {
			name = normalizeFieldName(desiredName) + counter;
		}

		existingField = findFieldByName(pages, name);

		counter++;
	}

	return normalizeFieldName(name);
};

/**
 * Checks if a given character is valid for use in a field name.
 * @param {string} character
 * @return {Boolean} Returns true if the character is invalid.
 */
export const checkInvalidFieldNameCharacter = character => {
	return /[\\~`!@#$%^&*(){}[\];:"'<,.>?/\-+=]/g.test(character);
};

/**
 * Normalizes a field name to contain only valid characters.
 * @param {string} fieldName
 * @return {string} The field name normalized.
 */
export function normalizeFieldName(fieldName) {
	let nextUpperCase = false;
	let normalizedFieldName = '';

	fieldName = fieldName.trim();

	for (let i = 0; i < fieldName.length; i++) {
		let item = fieldName[i];

		if (item === ' ') {
			nextUpperCase = true;

			continue;
		}
		else if (checkInvalidFieldNameCharacter(item)) {
			continue;
		}

		if (nextUpperCase) {
			item = item.toUpperCase();

			nextUpperCase = false;
		}

		normalizedFieldName += item;
	}

	if (/^\d/.test(normalizedFieldName)) {
		normalizedFieldName = `_${normalizedFieldName}`;
	}

	return normalizedFieldName;
}

export const getFieldValue = (pages, fieldName) => {
	return getFieldProperty(pages, fieldName, 'value');
};

export const getFieldProperty = (pages, fieldName, propertyName) => {
	const visitor = new PagesVisitor(pages);
	let propertyValue;

	visitor.mapFields(
		field => {
			if (field.fieldName === fieldName) {
				propertyValue = field[propertyName];
			}
		}
	);

	return propertyValue;
};

export const getField = (pages, fieldName) => {
	const visitor = new PagesVisitor(pages);
	let field;

	visitor.mapFields(
		currentField => {
			if (currentField.fieldName === fieldName) {
				currentField = field;
			}
		}
	);

	return field;
};

export const updateFieldValidationProperty = (pages, fieldName, propertyName, propertyValue) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(
		field => {
			if (field.fieldName === 'validation' && field.value) {
				let expression = field.value.expression;

				if (propertyName === 'fieldName' && expression) {
					expression = expression.replace(fieldName, propertyValue);
				}

				field = {
					...field,
					validation: {
						...field.validation,
						[propertyName]: propertyValue
					},
					value: {
						...field.value,
						expression
					}
				};
			}

			return field;
		}
	);
};
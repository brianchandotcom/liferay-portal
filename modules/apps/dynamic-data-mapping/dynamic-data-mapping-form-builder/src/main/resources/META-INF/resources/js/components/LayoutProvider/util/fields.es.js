/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	FormSupport,
	PagesVisitor,
	normalizeFieldName,
} from 'dynamic-data-mapping-form-renderer';

import {getDefaultFieldName} from '../../../util/fieldSupport.es';

export const generateFieldName = (
	pages,
	desiredName,
	currentName = null,
	blacklist = [],
	generateFieldNameUsingFieldLabel
) => {
	let fieldName;
	let existingField;

	if (generateFieldNameUsingFieldLabel) {
		let counter = 0;

		fieldName = normalizeFieldName(desiredName);

		existingField = FormSupport.findFieldByFieldName(pages, fieldName);

		while (
			(existingField && existingField.fieldName !== currentName) ||
			blacklist.includes(fieldName)
		) {
			if (counter > 0) {
				fieldName = normalizeFieldName(desiredName) + counter;
			}

			existingField = FormSupport.findFieldByFieldName(pages, fieldName);

			counter++;
		}

		return normalizeFieldName(fieldName);
	}
	else {
		fieldName = desiredName;

		existingField = FormSupport.findFieldByFieldName(pages, fieldName);

		while (
			(existingField && existingField.fieldName !== currentName) ||
			blacklist.includes(fieldName)
		) {
			fieldName = getDefaultFieldName();

			existingField = FormSupport.findFieldByFieldName(pages, fieldName);
		}

		return fieldName;
	}
};

export const getFieldProperty = (pages, fieldName, propertyName) => {
	const visitor = new PagesVisitor(pages);
	let propertyValue;

	visitor.mapFields(
		(field) => {
			if (field.fieldName === fieldName) {
				propertyValue = field[propertyName];
			}
		},
		true,
		true
	);

	return propertyValue;
};

export const getFieldValue = (pages, fieldName) => {
	return getFieldProperty(pages, fieldName, 'value');
};

export const getField = (pages, fieldName) => {
	const visitor = new PagesVisitor(pages);
	let field;

	visitor.mapFields((currentField) => {
		if (currentField.fieldName === fieldName) {
			field = currentField;
		}
	});

	return field;
};

export const getFieldLocalizedValue = (pages, fieldName, locale) => {
	const fieldLocalizedValue = getFieldProperty(
		pages,
		fieldName,
		'localizedValue'
	);

	return fieldLocalizedValue[locale];
};

export const updateFieldValidationProperty = (
	pages,
	fieldName,
	propertyName,
	propertyValue
) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields((field) => {
		if (field.fieldName === 'validation' && field.value) {
			const expression = field.value.expression;

			if (
				propertyName === 'fieldName' &&
				expression &&
				expression.value
			) {
				expression.value = expression.value.replace(
					fieldName,
					propertyValue
				);
			}

			field = {
				...field,
				validation: {
					...field.validation,
					[propertyName]: propertyValue,
				},
				value: {
					...field.value,
					expression,
				},
			};
		}

		return field;
	});
};

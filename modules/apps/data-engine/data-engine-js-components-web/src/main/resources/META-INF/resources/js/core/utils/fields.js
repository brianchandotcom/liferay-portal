/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {findFieldByFieldName} from '../../utils/FormSupport.es';
import {
	generateInstanceId,
	getDefaultFieldName,
	localizeField,
} from '../../utils/fieldSupport';
import {normalizeFieldName} from '../../utils/fields.es';
import {
	getSettingsContextProperty,
	updateField,
	updateFieldLabel,
	updateSettingsContextInstanceId,
	updateSettingsContextProperty,
} from '../../utils/settingsContext';
import {sub} from '../../utils/strings';
import {PagesVisitor} from '../../utils/visitors.es';

export function generateFieldName(
	pages,
	desiredName,
	currentName = null,
	blacklist = [],
	generateFieldNameUsingFieldLabel
) {
	let fieldName;
	let existingField;

	if (generateFieldNameUsingFieldLabel) {
		let counter = 0;

		fieldName = normalizeFieldName(desiredName);

		existingField = findFieldByFieldName(pages, fieldName);

		while (
			(existingField && existingField.fieldName !== currentName) ||
			blacklist.includes(fieldName)
		) {
			if (counter > 0) {
				fieldName = normalizeFieldName(desiredName) + counter;
			}

			existingField = findFieldByFieldName(pages, fieldName);

			counter++;
		}

		return normalizeFieldName(fieldName);
	}
	else {
		fieldName = desiredName;

		existingField = findFieldByFieldName(pages, fieldName);

		while (
			(existingField && existingField.fieldName !== currentName) ||
			blacklist.includes(fieldName)
		) {
			fieldName = getDefaultFieldName();

			existingField = findFieldByFieldName(pages, fieldName);
		}

		return fieldName;
	}
}

export function getFieldProperty(pages, fieldName, propertyName) {
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
}

export function getFieldValue(pages, fieldName) {
	return getFieldProperty(pages, fieldName, 'value');
}

export function getField(pages, fieldName) {
	const visitor = new PagesVisitor(pages);
	let field;

	visitor.mapFields((currentField) => {
		if (currentField.fieldName === fieldName) {
			field = currentField;
		}
	});

	return field;
}

export function getFieldLocalizedValue(pages, fieldName, locale) {
	const fieldLocalizedValue = getFieldProperty(
		pages,
		fieldName,
		'localizedValue'
	);

	return fieldLocalizedValue[locale];
}

export function getLabel(originalField, defaultLanguageId, editingLanguageId) {
	const labelFieldLocalizedValue = getFieldLocalizedValue(
		originalField.settingsContext.pages,
		'label',
		editingLanguageId
	);

	if (!labelFieldLocalizedValue) {
		return;
	}

	return sub(Liferay.Language.get('copy-of-x'), [labelFieldLocalizedValue]);
}

export function updateFieldValidationProperty(
	pages,
	fieldName,
	propertyName,
	propertyValue
) {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields((field) => {
		if (field.fieldName === 'validation' && field.value) {
			const expression = field.value.expression;

			if (
				propertyName === 'fieldName' &&
				expression &&
				expression.value
			) {
				expression.value = expression.value.replaceAll(
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
}

export function getValidation(originalField) {
	const validation = getSettingsContextProperty(
		originalField.settingsContext,
		'validation'
	);

	return validation;
}

export function createDuplicatedField(originalField, props, blacklist = []) {
	const {
		availableLanguageIds,
		defaultLanguageId,
		editingLanguageId,
		fieldNameGenerator,
		generateFieldNameUsingFieldLabel,
	} = props;
	const newFieldName = fieldNameGenerator(
		getDefaultFieldName(),
		null,
		blacklist
	);

	let duplicatedField = updateField(
		props,
		originalField,
		'name',
		newFieldName
	);

	duplicatedField = updateField(
		props,
		duplicatedField,
		'fieldReference',
		newFieldName
	);

	duplicatedField.instanceId = generateInstanceId();

	availableLanguageIds.forEach((availableLanguageId) => {
		const label = getLabel(
			originalField,
			defaultLanguageId,
			availableLanguageId
		);

		if (label) {
			duplicatedField = updateFieldLabel(
				defaultLanguageId,
				availableLanguageId,
				fieldNameGenerator,
				duplicatedField,
				generateFieldNameUsingFieldLabel,
				label
			);
		}
	});

	if (duplicatedField.nestedFields?.length > 0) {
		duplicatedField.nestedFields = duplicatedField.nestedFields.map(
			(field) => {
				const newDuplicatedNestedField = createDuplicatedField(
					field,
					props,
					blacklist
				);

				blacklist.push(newDuplicatedNestedField.fieldName);

				let {rows = []} = duplicatedField;

				if (typeof rows === 'string') {
					rows = JSON.parse(rows);
				}

				const visitor = new PagesVisitor([
					{
						rows,
					},
				]);

				const layout = visitor.mapColumns((column) => {
					return {
						...column,
						fields: column.fields.map((fieldName) => {
							if (fieldName === field.fieldName) {
								return newDuplicatedNestedField.fieldName;
							}

							return fieldName;
						}),
					};
				});

				duplicatedField.rows = layout[0].rows;

				return newDuplicatedNestedField;
			}
		);

		duplicatedField.settingsContext = updateSettingsContextProperty(
			defaultLanguageId,
			props.editingLanguageId,
			duplicatedField.settingsContext,
			'rows',
			duplicatedField.rows
		);

		duplicatedField.ddmStructureLayoutId = '';

		duplicatedField.settingsContext = updateSettingsContextProperty(
			defaultLanguageId,
			props.editingLanguageId,
			duplicatedField.settingsContext,
			'ddmStructureLayoutId',
			duplicatedField.ddmStructureLayoutId
		);
	}

	const settingsContext = updateSettingsContextInstanceId(duplicatedField);

	const settingsVisitor = new PagesVisitor(settingsContext.pages);

	duplicatedField.settingsContext = {
		...settingsContext,
		pages: settingsVisitor.mapFields((field) =>
			localizeField(field, defaultLanguageId, editingLanguageId)
		),
	};

	return updateField(
		props,
		duplicatedField,
		'validation',
		getValidation(duplicatedField)
	);
}

export function findInvalidFieldReference(focusedField, pages, value) {
	let hasInvalidFieldReference = false;

	const visitor = new PagesVisitor(pages);

	visitor.mapFields(
		(field) => {
			const fieldReference = getSettingsContextProperty(
				field.settingsContext,
				'fieldReference'
			);

			if (
				focusedField.fieldName !== field.fieldName &&
				fieldReference?.toLowerCase() === value?.toLowerCase()
			) {
				hasInvalidFieldReference = true;
			}
		},
		true,
		true
	);

	return hasInvalidFieldReference;
}

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {FieldSupport, normalizeFieldName} from 'data-engine-js-components-web';

export function random(a) {
	return a
		? (a ^ ((Math.random() * 16) >> (a / 4))).toString(16)
		: ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, random);
}

export function compose(...fns) {
	return fns.reduceRight((f, g) => (...xs) => {
		const r = g(...xs);

		return Array.isArray(r) ? f(...r) : f(r);
	});
}

export function isOptionValueGenerated(
	defaultLanguageId,
	editingLanguageId,
	options,
	option
) {
	if (defaultLanguageId !== editingLanguageId) {
		return false;
	}

	if (option.value === '') {
		return true;
	}

	const optionIndex = options.indexOf(option);
	const duplicated = options.some(({value}, index) => {
		return value === option.value && index !== optionIndex;
	});

	if (duplicated) {
		return true;
	}

	if (option.edited) {
		return false;
	}

	if (
		new RegExp(`^${Liferay.Language.get('option')}\\d*$`).test(option.value)
	) {
		return true;
	}

	if (
		new RegExp(`^${option.value.replace(/\d+$/, '')}\\d*`).test(
			normalizeFieldName(option.label)
		)
	) {
		return true;
	}

	return true;
}

/**
 * Deduplicates the value by checking if there is a
 * value in the fields, always incrementing an integer
 * in front of the value to be friendly for the user.
 */
export function dedupValue(fields, value, id, generateValueUsingLabel) {
	let counter = 0;

	const recursive = (fields, currentValue) => {
		const field = fields.find(
			(field) =>
				field.value?.toLowerCase() === currentValue?.toLowerCase()
		);

		if (field && field.id !== id) {
			if (generateValueUsingLabel) {
				counter += 1;
				recursive(fields, value + counter);
			}
			else {
				recursive(fields, FieldSupport.getDefaultFieldName(true));
			}
		}
		else {
			value = currentValue;
		}
	};

	recursive(fields, value);

	return value;
}

export function findDuplicateReference(fields, currentIndex, currentReference) {
	return fields
		.filter((field, index) => index !== currentIndex)
		.some(
			({reference}) =>
				reference?.toLowerCase() === currentReference?.toLowerCase()
		);
}

export function getDefaultOptionValue(generateValueUsingLabel, optionLabel) {
	const defaultValue = generateValueUsingLabel
		? optionLabel
		: FieldSupport.getDefaultFieldName(true);

	return defaultValue;
}

export function normalizeReference(fields, currentField, index) {
	const {reference, value} = currentField;

	if (!reference || findDuplicateReference(fields, index, reference)) {
		return value ? value : FieldSupport.getDefaultFieldName(true);
	}

	return reference;
}

/**
 * If the value is null or undefined, normalize follows a
 * verification order and the final stage of normalization
 * is to deduplicate the value if necessary.
 *
 * 1. If the current value is null, use the default value that can be the label
 * or the default option name, the parameter generateValueUsingLabel
 * decides which of these two values will be used.
 * 2. If the default value is null, use the string Option.
 */
export function normalizeValue(
	allowSpecialCharacters,
	currentField,
	fields,
	generateValueUsingLabel
) {
	const {label, value: prevValue} = currentField;

	let value = prevValue
		? prevValue
		: getDefaultOptionValue(generateValueUsingLabel, label);

	if (!value) {
		value = Liferay.Language.get('option');
	}

	value = dedupValue(fields, value, currentField.id, generateValueUsingLabel);

	return allowSpecialCharacters ? value : normalizeFieldName(value);
}

export function normalizeFields(
	allowSpecialCharacters,
	fields,
	generateValueUsingLabel
) {
	return fields.map((field, index) => {
		const value = normalizeValue(
			allowSpecialCharacters,
			field,
			fields,
			generateValueUsingLabel
		);

		return {
			...field,
			reference: normalizeReference(
				fields,
				{
					...field,
					value,
				},
				index
			),
			value,
		};
	});
}

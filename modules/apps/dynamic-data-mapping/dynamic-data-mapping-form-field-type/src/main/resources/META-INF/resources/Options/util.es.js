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

import {getDefaultFieldName} from 'dynamic-data-mapping-form-builder/js/util/fieldSupport.es';
import {normalizeFieldName} from 'dynamic-data-mapping-form-renderer';

export const random = (a) => {
	return a
		? (a ^ ((Math.random() * 16) >> (a / 4))).toString(16)
		: ([1e7] + -1e3 + -4e3 + -8e3 + -1e11).replace(/[018]/g, random);
};

export const compose = (...fns) =>
	fns.reduceRight((f, g) => (...xs) => {
		const r = g(...xs);

		return Array.isArray(r) ? f(...r) : f(r);
	});

export const isOptionValueGenerated = (
	defaultLanguageId,
	editingLanguageId,
	options,
	option
) => {
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
};

/**
 * Deduplicates the value by checking if there is a
 * value in the fields, always incrementing an integer
 * in front of the value to be friendly for the user.
 */
export const dedupValue = (
	fields,
	value,
	id,
	generateOptionValueUsingOptionLabel,
	propertyName
) => {
	if (generateOptionValueUsingOptionLabel) {
		let counter = 0;

		const recursive = (fields, currentValue) => {
			const field = fields.find(
				(field) => field[propertyName] === currentValue
			);

			if (field && field.id !== id) {
				counter += 1;
				recursive(fields, value + counter);
			}
			else {
				value = currentValue;
			}
		};

		recursive(fields, value);

		return value;
	}
	else {
		const recursive = (fields, currentValue) => {
			const field = fields.find(
				(field) => field[propertyName] === currentValue
			);

			if (field && field.id !== id) {
				recursive(fields, getDefaultFieldName(true));
			}
			else {
				value = currentValue;
			}
		};

		recursive(fields, value);

		return value;
	}
};

export const getDefaultOptionValue = (
	generateOptionValueUsingOptionLabel,
	optionLabel
) => {
	const defaultValue = generateOptionValueUsingOptionLabel
		? optionLabel
		: getDefaultFieldName(true);

	return defaultValue;
};

/**
 * If the value is null or undefined, normalize follows a
 * verification order and the final stage of normalization
 * is to deduplicate the value if necessary.
 *
 * 1. If the current value is null, use the default value that can be the label
 * or the default option name, the parameter generateOptionValueUsingOptionLabel
 * decides which of these two values will be used.
 * 2. If the default value is null, use the string Option.
 */
export const normalizeValue = (
	fields,
	currentField,
	generateOptionValueUsingOptionLabel,
	propertyName
) => {
	const {label} = currentField;
	let value = currentField[propertyName]
		? currentField[propertyName]
		: getDefaultOptionValue(generateOptionValueUsingOptionLabel, label);

	if (!value) {
		value = Liferay.Language.get('option');
	}

	value = dedupValue(
		fields,
		value,
		currentField.id,
		generateOptionValueUsingOptionLabel,
		propertyName
	);

	return normalizeFieldName(value);
};

export const normalizeFields = (
	fields,
	generateOptionValueUsingOptionLabel
) => {
	return fields.map((field) => {
		return {
			...field,
			reference: normalizeValue(
				fields,
				field,
				generateOptionValueUsingOptionLabel,
				'reference'
			),
			value: normalizeValue(
				fields,
				field,
				generateOptionValueUsingOptionLabel,
				'value'
			),
		};
	});
};

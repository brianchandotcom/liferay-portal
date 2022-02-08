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

import type {Locale, LocalizedValue} from '../types';

function assertOptionParameters<T>({
	editingLanguageId,
	multiple,
	option,
	valueArray,
}: {
	editingLanguageId: Locale;
	multiple: boolean;
	option: Option<T>;
	valueArray: T[];
}): {
	active: boolean;
	checked: boolean;
	label: string | undefined;
	type: 'checkbox' | 'item';
	value: T;
} {
	const included = valueArray.includes(option.value);

	const label =
		typeof option.label === 'string'
			? option.label
			: option.label[editingLanguageId];

	return {
		...option,
		active: !multiple && included,
		checked: multiple && included,
		label,
		type: multiple ? 'checkbox' : 'item',
	};
}

export function normalizeOptions({
	editingLanguageId,
	fixedOptions,
	multiple,
	options,
	showEmptyOption,
	valueArray,
}: {
	editingLanguageId: Locale;
	fixedOptions: Option<string>[];
	multiple: boolean;
	options: Option<string>[];
	showEmptyOption: boolean;
	valueArray: string[];
}) {
	const newOptions: {
		active?: boolean;
		checked?: boolean;
		label: string | undefined;
		separator?: true;
		type?: 'checkbox' | 'item';
		value: string | null;
	}[] = [];

	if (showEmptyOption && !multiple) {
		newOptions.push({
			label: Liferay.Language.get('choose-an-option'),
			value: null,
		});
	}

	const populateNewOptions = (selectOptions: Option<string>[]) => {
		selectOptions.forEach((option) => {
			const newOption = assertOptionParameters<string>({
				editingLanguageId,
				multiple,
				option,
				valueArray,
			});
			if (newOption.value !== '') {
				newOptions.push(newOption);
			}
		});
	};

	populateNewOptions(options);

	if (fixedOptions.length) {
		if (options.length) {
			newOptions[options.length - 1].separator = true;
		}

		populateNewOptions(fixedOptions);
	}

	return newOptions;
}

export function normalizeValue<T>({
	localizedValueEdited,
	multiple,
	normalizedOptions,
	predefinedValueArray,
	valueArray,
}: {
	localizedValueEdited: boolean;
	multiple: boolean;
	normalizedOptions: {value: T}[];
	predefinedValueArray: T[];
	valueArray: T[];
}) {
	const assertValue =
		valueArray.length || (valueArray.length === 0 && localizedValueEdited)
			? valueArray
			: predefinedValueArray;

	const normalizedValues = multiple ? assertValue : [assertValue[0]];

	const normalizedOptionSet = new Set(
		normalizedOptions.map(({value}) => value)
	);

	return normalizedValues.filter((value) => normalizedOptionSet.has(value));
}
interface Option<T> {
	label: LocalizedValue<string>;
	value: T;
}

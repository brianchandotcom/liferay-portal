/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		valueArray.length || (!valueArray.length && localizedValueEdited)
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

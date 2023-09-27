/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import type {Locale, LocalizedValue} from '../types';
export declare function normalizeOptions({
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
}): {
	active?: boolean | undefined;
	checked?: boolean | undefined;
	label: string | undefined;
	separator?: true | undefined;
	type?: 'checkbox' | 'item' | undefined;
	value: string | null;
}[];
export declare function normalizeValue<T>({
	localizedValueEdited,
	multiple,
	normalizedOptions,
	predefinedValueArray,
	valueArray,
}: {
	localizedValueEdited: boolean;
	multiple: boolean;
	normalizedOptions: {
		value: T;
	}[];
	predefinedValueArray: T[];
	valueArray: T[];
}): T[];
interface Option<T> {
	label: LocalizedValue<string>;
	value: T;
}
export {};

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

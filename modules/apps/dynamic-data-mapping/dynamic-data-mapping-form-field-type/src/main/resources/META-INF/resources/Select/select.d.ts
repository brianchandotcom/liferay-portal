/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale, LocalizedValue} from '../types';

interface Item {
	_key?: string;
	active: boolean;
	checked: boolean;
	label: string;
	reference: string;
	type: string;
	value: string;
}

interface MainProps {
	defaultSearch?: boolean;
	editingLanguageId?: Locale;
	errorMessage?: string;
	fixedOptions?: Option<string>[];
	id?: string;
	label: string;
	localizedValue?: any;
	localizedValueEdited?: any;
	multiple?: boolean;
	name: string;
	onBlur?: any;
	onChange: any;
	onFocus?: any;
	onSelectionChange?: (value: React.Key) => void;
	options: any[];
	placeholder?: string;
	predefinedValue?: string[] | string;
	readOnly: boolean;
	required?: boolean;
	selectedKey: string;
	showEmptyOption: boolean;
	tip?: string;
	value?: string[] | string;
	visible?: boolean;
}

interface MultiSelectProps
	extends Omit<
		MainProps,
		'editingLanguageId' | 'selectedKey' | 'showEmptyOption' | 'value'
	> {
	value: string[];
}

interface Option<T> {
	label: LocalizedValue<string>;
	value: T;
}

interface SelectProps
	extends Omit<MainProps, 'editingLanguageId' | 'value' | 'selectedKey'> {
	selectedKey?: string;
	viewMode: unknown;
}

type MultiSelectItem = {
	label: string;
	reference: string | null;
	value: string;
};

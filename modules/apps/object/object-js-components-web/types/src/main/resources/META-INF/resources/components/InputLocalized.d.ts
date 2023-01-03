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

/// <reference types="react" />

import './InputLocalized.scss';
interface InputLocalizedProps {
	className?: string;
	disableFlag?: boolean;
	disabled?: boolean;
	error?: string;
	id?: string;
	label: string;
	name?: string;
	onChange: (value: LocalizedValue<string>, locale: InputLocale) => void;
	onSelectedLocaleChange?: (locale: Locale) => void;
	placeholder?: string;
	required?: boolean;
	selectedLocale?: Locale;
	translations: LocalizedValue<string>;
}
interface InputLocale {
	label: Locale;
	symbol: string;
}
export declare function InputLocalized({
	disableFlag,
	disabled,
	error,
	id,
	label,
	name,
	onChange,
	onSelectedLocaleChange,
	placeholder,
	required,
	selectedLocale,
	translations,
	...otherProps
}: InputLocalizedProps): JSX.Element;
export {};

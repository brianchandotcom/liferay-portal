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
export declare function InputLocalized({
	defaultLanguageId,
	disabled,
	error,
	id,
	label,
	locales,
	name,
	onSelectedLocaleChange,
	onTranslationsChange,
	placeholder,
	required,
	selectedLocale,
	translations,
	...otherProps
}: IProps): JSX.Element;
interface ILocale {
	label: string;
	symbol: string;
}
interface IProps {
	className?: string;
	defaultLanguageId: Locale;
	disabled?: boolean;
	error?: string;
	id?: string;
	label: string;
	locales: ILocale[];
	name?: string;
	onSelectedLocaleChange: (value: ILocale) => void;
	onTranslationsChange: (value: LocalizedValue<string>) => void;
	placeholder?: string;
	required?: boolean;
	selectedLocale: ILocale;
	translations: LocalizedValue<string>;
}
export {};

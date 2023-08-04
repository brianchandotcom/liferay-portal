/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
import {LocalizedValue} from '../../index';
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
	onSelectedLocaleChange?: (locale: Liferay.Language.Locale) => void;
	placeholder?: string;
	required?: boolean;
	resultFormatter?: (val: string) => React.ReactNode;
	selectedLocale?: Liferay.Language.Locale;
	translations: LocalizedValue<string>;
}
interface InputLocale {
	label: Liferay.Language.Locale;
	symbol: string;
}
export default function InputLocalized({
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
	resultFormatter,
	selectedLocale,
	translations,
	...otherProps
}: InputLocalizedProps): JSX.Element;
export {};

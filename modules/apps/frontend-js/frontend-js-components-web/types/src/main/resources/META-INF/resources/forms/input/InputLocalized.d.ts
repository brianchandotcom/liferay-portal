/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React, {FocusEventHandler} from 'react';
import './InputLocalized.scss';
interface InputLocalizedProps {
	className?: string;
	disableFlag?: boolean;
	disabled?: boolean;
	error?: string;
	id?: string;
	label: string;
	name?: string;
	onBlur?: FocusEventHandler<HTMLInputElement>;
	onChange: (
		value: Liferay.Language.LocalizedValue<string>,
		locale: InputLocale
	) => void;
	onSelectedLocaleChange?: (locale: Liferay.Language.Locale) => void;
	placeholder?: string;
	required?: boolean;
	resultFormatter?: (value: string) => React.ReactNode;
	selectedLocale?: Liferay.Language.Locale;
	tooltip?: string;
	translations: Liferay.Language.LocalizedValue<string>;
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
	onBlur,
	onChange,
	onSelectedLocaleChange,
	placeholder,
	required,
	resultFormatter,
	selectedLocale,
	tooltip,
	translations,
	...otherProps
}: InputLocalizedProps): JSX.Element;
export {};

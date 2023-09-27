/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayLocalizedInput from '@clayui/localized-input';
import classNames from 'classnames';
import React, {FocusEventHandler, useEffect, useState} from 'react';

import FieldBase from '../common/FieldBase';

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

const defaultLanguageId = Liferay.ThemeDisplay.getDefaultLanguageId();

const availableLocales = Object.keys(Liferay.Language.available)
	.sort((languageId) => (languageId === defaultLanguageId ? -1 : 1))
	.map((language) => ({
		label: language as Liferay.Language.Locale,
		symbol: language.replace(/_/g, '-').toLowerCase(),
	}));

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
	resultFormatter = () => null,
	selectedLocale,
	tooltip,
	translations,
	...otherProps
}: InputLocalizedProps) {
	const [locale, setLocale] = useState<InputLocale>(availableLocales[0]);

	useEffect(() => {
		if (disableFlag) {
			const localizationButton = document.querySelector(
				'.dropdown-toggle'
			);

			localizationButton?.setAttribute('disabled', 'true');
		}

		const locale =
			availableLocales.find(({label}) => label === selectedLocale)! ??
			availableLocales[0];
		setLocale(locale);
	}, [disableFlag, selectedLocale]);

	return (
		<FieldBase
			className="input-localized"
			disabled={disabled}
			errorMessage={error}
			id={id}
			label={label}
			required={required}
			tooltip={tooltip}
		>
			<ClayLocalizedInput
				{...otherProps}
				className={classNames({
					'input-localized--rtl':
						Liferay.Language.direction[locale.label] === 'rtl',
				})}
				disabled={disabled}
				id={id}
				label=""
				locales={availableLocales}
				name={name}
				onBlur={onBlur}
				onSelectedLocaleChange={(locale) => {
					setLocale(locale as InputLocale);
					onChange(translations, locale as InputLocale);
					if (onSelectedLocaleChange) {
						onSelectedLocaleChange((locale as InputLocale).label);
					}
				}}
				onTranslationsChange={(value) => onChange(value, locale)}
				placeholder={placeholder}
				resultFormatter={resultFormatter}
				selectedLocale={locale}
				translations={translations}
			/>
		</FieldBase>
	);
}

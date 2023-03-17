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

import ClayLocalizedInput from '@clayui/localized-input';
import classNames from 'classnames';
import React, {useEffect, useState} from 'react';

import {FieldBase} from './FieldBase';

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
	selectedLocale?: Liferay.Language.Locale;
	translations: LocalizedValue<string>;
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

export function InputLocalized({
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
			className="lfr-objects__input-localized"
			disabled={disabled}
			errorMessage={error}
			id={id}
			label={label}
			required={required}
		>
			<ClayLocalizedInput
				{...otherProps}
				className={classNames({
					'lfr-objects__input-localized--rtl':
						Liferay.Language.direction[locale.label] === 'rtl',
				})}
				disabled={disabled}
				id={id}
				label=""
				locales={availableLocales}
				name={name}
				onSelectedLocaleChange={(locale) => {
					setLocale(locale as InputLocale);
					onChange(translations, locale as InputLocale);
					if (onSelectedLocaleChange) {
						onSelectedLocaleChange((locale as InputLocale).label);
					}
				}}
				onTranslationsChange={(value) => onChange(value, locale)}
				placeholder={placeholder}
				selectedLocale={locale}
				translations={translations}
			/>
		</FieldBase>
	);
}

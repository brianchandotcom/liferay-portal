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
import React from 'react';

import {FieldBase} from './FieldBase';

import './InputLocalized.scss';

export function InputLocalized({
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
}: IProps) {
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

						// @ts-ignore

						Liferay.Language.direction[selectedLocale.label] ===
						'rtl',
				})}
				disabled={disabled}
				id={id}
				label=""
				locales={locales.sort((a) =>
					a.label === defaultLanguageId ? -1 : 1
				)}
				name={name}
				onSelectedLocaleChange={onSelectedLocaleChange}
				onTranslationsChange={onTranslationsChange}
				placeholder={placeholder}
				selectedLocale={selectedLocale}
				translations={translations}
			/>
		</FieldBase>
	);
}

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

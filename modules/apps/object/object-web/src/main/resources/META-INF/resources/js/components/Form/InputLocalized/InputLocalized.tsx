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

import ClayForm from '@clayui/form';
import ClayLocalizedInput from '@clayui/localized-input';
import classNames from 'classnames';
import React from 'react';

import ErrorFeedback from '../ErrorFeedback';
import RequiredMask from '../RequiredMask';

import './InputLocalized.scss';

export default function InputLocalized({
	className,
	disabled = false,
	error,
	id,
	label,
	locales,
	name,
	onSelectedLocaleChange,
	onTranslationsChange,
	required = false,
	selectedLocale,
	translations,
	...otherProps
}: IProps) {
	return (
		<ClayForm.Group
			className={classNames(
				'lfr-objects__input-localized',
				{'has-error': error},
				className
			)}
		>
			<label className={classNames({disabled})} htmlFor={id}>
				{label}

				{required && <RequiredMask />}
			</label>

			<ClayLocalizedInput
				{...otherProps}
				id={id}
				label=""
				locales={locales}
				name={name}
				onSelectedLocaleChange={onSelectedLocaleChange}
				onTranslationsChange={onTranslationsChange}
				selectedLocale={selectedLocale}
				translations={translations}
			/>

			{error && <ErrorFeedback error={error} />}
		</ClayForm.Group>
	);
}

interface ILocale {
	label: string;
	symbol: string;
}

interface IProps {
	className?: string;
	disabled?: boolean;
	error?: string;
	id?: string;
	label: string;
	locales: ILocale[];
	name?: string;
	onSelectedLocaleChange: (value: ILocale) => void;
	onTranslationsChange: (value: LocalizedValue<string>) => void;
	required?: boolean;
	selectedLocale: ILocale;
	translations: LocalizedValue<string>;
}

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

const InputLocalized: React.FC<IInputLocalizedProps> = ({
	className,
	disabled = false,
	error,
	id,
	label,
	locales,
	onSelectedLocaleChange,
	onTranslationsChange,
	required = false,
	selectedLocale,
	translations,
	...otherProps
}) => {
	return (
		<ClayForm.Group
			className={classNames(className, 'input-localized', {
				'has-error': error,
			})}
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
				onSelectedLocaleChange={onSelectedLocaleChange}
				onTranslationsChange={onTranslationsChange}
				selectedLocale={selectedLocale}
				translations={translations}
			/>

			{error && <ErrorFeedback error={error} />}
		</ClayForm.Group>
	);
};

interface IInputLocalizedProps {
	className?: string;
	disabled?: boolean;
	error?: string;
	id: string;
	label: string;
	locales: TLocale[];
	onSelectedLocaleChange: (value: TLocale) => void;
	onTranslationsChange: (value: TTranslations) => void;
	required?: boolean;
	selectedLocale: TLocale;
	translations: TTranslations;
}

type TTranslations = {
	[key: string]: string;
};

type TLocale = {
	label: string;
	symbol: string;
};

export default InputLocalized;

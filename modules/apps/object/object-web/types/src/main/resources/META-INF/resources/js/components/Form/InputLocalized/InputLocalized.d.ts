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

import React from 'react';
import './InputLocalized.scss';
declare const InputLocalized: React.FC<IInputLocalizedProps>;
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
declare type TTranslations = {
	[key: string]: string;
};
declare type TLocale = {
	label: string;
	symbol: string;
};
export default InputLocalized;

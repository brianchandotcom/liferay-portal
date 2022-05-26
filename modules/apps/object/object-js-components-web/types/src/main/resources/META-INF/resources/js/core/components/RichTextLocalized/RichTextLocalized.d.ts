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
import './RichTextLocalized.scss';
export declare function RichTextLocalized({
	ariaLabels,
	editorConfig,
	helpMessage,
	label,
	locales,
	onSelectedLocaleChange,
	onTranslationsChange,
	selectedLocale,
	translations,
}: IProps): JSX.Element;
interface IItem {
	label: string;
	symbol: string;
}
interface IProps extends React.InputHTMLAttributes<HTMLInputElement> {
	ariaLabels?: {
		default: string;
		openLocalizations: string;
		translated: string;
		untranslated: string;
	};
	editorConfig: string;
	helpMessage?: string;
	label: string;
	locales: Array<IItem>;
	onSelectedLocaleChange: (val: IItem) => void;
	onTranslationsChange: (val: LocalizedValue<string>) => void;
	selectedLocale: IItem;
	translations: LocalizedValue<string>;
}
export {};

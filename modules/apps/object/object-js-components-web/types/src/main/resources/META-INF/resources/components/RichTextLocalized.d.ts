/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="ckeditor4" />

import React from 'react';
import './RichTextLocalized.scss';
export declare function RichTextLocalized({
	ariaLabels,
	editorConfig,
	helpMessage,
	label,
	onSelectedLocaleChange,
	onTranslationsChange,
	selectedLocale,
	translations,
}: IProps): JSX.Element;
interface IItem {
	label: Liferay.Language.Locale;
	symbol: string;
}
interface IProps extends React.InputHTMLAttributes<HTMLInputElement> {
	ariaLabels?: {
		default: string;
		openLocalizations: string;
		translated: string;
		untranslated: string;
	};
	editorConfig: CKEDITOR.config;
	helpMessage?: string;
	label: string;
	onSelectedLocaleChange: (val: IItem) => void;
	onTranslationsChange: (val: LocalizedValue<string>) => void;
	selectedLocale: Liferay.Language.Locale;
	translations: LocalizedValue<string>;
}
export {};

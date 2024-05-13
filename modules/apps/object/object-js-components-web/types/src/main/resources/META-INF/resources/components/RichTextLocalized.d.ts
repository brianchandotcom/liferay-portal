/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="ckeditor4" />

import React from 'react';
import './RichTextLocalized.scss';
interface LabelSymbolObject {
	label: Liferay.Language.Locale;
	symbol: string;
}
interface RichTextLocalizedProps
	extends React.InputHTMLAttributes<HTMLInputElement> {
	ariaLabels?: {
		default: string;
		openLocalizations: string;
		translated: string;
		untranslated: string;
	};
	editorConfig: CKEDITOR.config;
	helpMessage?: string;
	label: string;
	onSelectedLocaleChange: (val: LabelSymbolObject) => void;
	onTranslationsChange: (val: LocalizedValue<string>) => void;
	readOnly?: boolean;
	selectedLocale: Liferay.Language.Locale;
	translations: LocalizedValue<string>;
}
export declare function RichTextLocalized({
	ariaLabels,
	editorConfig,
	helpMessage,
	label,
	onSelectedLocaleChange,
	onTranslationsChange,
	readOnly,
	selectedLocale,
	translations,
}: RichTextLocalizedProps): JSX.Element;
export {};

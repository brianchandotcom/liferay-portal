/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export interface Locale {
	displayName: string;
	id: string;
	label: string;
	symbol: string;
}
export interface Translations {
	activeLanguageIds?: string[];
	ariaLabels?: {
		default?: string;
		manageTranslations?: string;
		managementToolbar?: string;
		notTranslated?: string;
		translated?: string;
	};
	availableLocales: Locale[];
	defaultLanguageId: string;
	translations?: {
		[key: string]: unknown;
	};
}
interface IProps extends Translations {
	onAddLocale?: (localeId: string) => void;
	onCancel?: React.MouseEventHandler<HTMLButtonElement>;
	onDone?: React.MouseEventHandler<HTMLButtonElement>;
	onRemoveLocale?: (localeId: string) => void;
}
export default function TranslationAdminContent({
	ariaLabels,
	activeLanguageIds: initialActiveLanguageIds,
	availableLocales: initialAvailableLocales,
	defaultLanguageId,
	onAddLocale,
	onCancel,
	onDone,
	onRemoveLocale,
	translations,
}: IProps): JSX.Element;
export {};

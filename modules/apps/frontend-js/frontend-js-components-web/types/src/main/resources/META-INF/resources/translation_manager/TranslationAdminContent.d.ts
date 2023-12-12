/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import React from 'react';
export interface Locale {
	displayName: string;
	id: Liferay.Language.Locale;
	label: Liferay.Language.Locale;
	symbol: string;
}
export interface Translations {
	activeLanguageIds?: Liferay.Language.Locale[];
	ariaLabels?: {
		default?: string;
		manageTranslations?: string;
		managementToolbar?: string;
		notTranslated?: string;
		translated?: string;
	};
	availableLocales: Locale[];
	defaultLanguageId: Liferay.Language.Locale;
	translations?: Record<Liferay.Language.Locale, string> | null;
}
interface IProps extends Translations {
	onAddLocale?: (localeId: Liferay.Language.Locale) => void;
	onCancel?: React.MouseEventHandler<HTMLButtonElement>;
	onDone?: React.MouseEventHandler<HTMLButtonElement>;
	onRemoveLocale?: (localeId: Liferay.Language.Locale) => void;
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

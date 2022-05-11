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

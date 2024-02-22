/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Locale} from 'frontend-js-components-web';
export declare type Field = Record<Liferay.Language.Locale, string>;
export declare type Fields = Record<string, Field>;
export interface TranslationsWrapper {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Fields;
	locales: Locale[];
	selectedLanguageId: Liferay.Language.Locale;
}
export default function TranslationsWrapper({
	defaultLanguageId,
	fields: initialFields,
	locales,
	selectedLanguageId: initialSelectedLanguageId,
}: TranslationsWrapper): JSX.Element;
export declare function fieldToTranslations(
	fields: Record<string, Field>
): {
	fieldName: string;
	languages: Liferay.Language.Locale[];
}[];
export declare function getAllLocalizableFields(
	initialFields: Record<string, Field>
): {};

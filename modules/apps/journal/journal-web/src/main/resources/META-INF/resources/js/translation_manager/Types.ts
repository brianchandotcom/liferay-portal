/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locale} from 'frontend-js-components-web';

export interface TranslationManagerProps {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Fields;
	locales: Locale[];
	namespace: string;
	selectedLanguageId: Liferay.Language.Locale;
}
export type Field = Record<Liferay.Language.Locale, string>;
export type Fields = Record<string, Field>;

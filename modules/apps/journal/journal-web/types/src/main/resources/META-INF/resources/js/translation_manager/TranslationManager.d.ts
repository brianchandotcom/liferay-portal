/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Locale} from 'frontend-js-components-web';
declare type Field = Record<Liferay.Language.Locale, string>;
interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Record<string, Field>;
	locales: Locale[];
	selectedLanguageId: Liferay.Language.Locale;
}
export default function TranslationManager({
	defaultLanguageId,
	fields,
	locales,
	selectedLanguageId: initialSelectedLanguageId,
}: Props): JSX.Element;
export {};

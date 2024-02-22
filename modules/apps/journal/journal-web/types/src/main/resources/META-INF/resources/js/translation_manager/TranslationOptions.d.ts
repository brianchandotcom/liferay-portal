/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TranslationProgress} from 'frontend-js-components-web';
declare type Field = Record<Liferay.Language.Locale, string>;
interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	fields: Record<string, Field>;
	getLocalizableFields: () => void;
	selectedLanguageId: Liferay.Language.Locale;
	translationProgress: TranslationProgress | null;
}
export default function TranslationOptions({
	defaultLanguageId,
	fields: initialFields,
	getLocalizableFields,
	selectedLanguageId,
	translationProgress,
}: Props): JSX.Element;
export {};

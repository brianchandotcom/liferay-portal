/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {TranslationProgress} from 'frontend-js-components-web';
import {Field, TranslationManagerProps} from './Types';
export default function useTranslationProgress({
	defaultLanguageId: initialDefaultLanguageId,
	fields: initialFields,
	locales,
	namespace,
	selectedLanguageId: initialSelectedLanguageId,
}: TranslationManagerProps): {
	defaultLanguageId:
		| 'ar_SA'
		| 'ca_ES'
		| 'de_DE'
		| 'en_US'
		| 'es_ES'
		| 'fi_FI'
		| 'fr_FR'
		| 'hu_HU'
		| 'nl_NL'
		| 'ja_JP'
		| 'pt_BR'
		| 'sv_SE'
		| 'zh_CN'
		| 'zh_Hans_CN'
		| 'zh_Hant_TW'
		| 'zh_TW';
	selectedLanguageId: Liferay.Language.Locale;
	translationProgress: TranslationProgress | null | undefined;
	translations: {
		fieldName: string;
		languages: Liferay.Language.Locale[];
	}[];
	updateTranslations: () => void;
};
export declare function fieldToTranslations(
	fields: Record<string, Field>
): {
	fieldName: string;
	languages: Liferay.Language.Locale[];
}[];
export declare function getAllLocalizableFields(
	initialFields: Record<string, Field>
): {};

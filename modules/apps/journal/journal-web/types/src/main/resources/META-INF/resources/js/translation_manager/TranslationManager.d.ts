/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TranslationProgress} from 'frontend-js-components-web';
import {Fields, TranslationsWrapper} from './TranslationsWrapper';
interface IProps extends TranslationsWrapper {
	getLocalizableFields: () => void;
	setFields: (fields: Fields) => void;
	setSelectedLanguageId: (languageId: Liferay.Language.Locale) => void;
	setTranslations: (translations: Translation[]) => void;
	translationProgress: TranslationProgress | null;
	updateTranslations: (fields: Fields) => void;
}
interface Translation {
	fieldName: string;
	languages: Liferay.Language.Locale[];
}
export default function TranslationManager({
	defaultLanguageId,
	fields,
	getLocalizableFields,
	locales,
	selectedLanguageId,
	setSelectedLanguageId,
	translationProgress,
	updateTranslations,
}: IProps): JSX.Element;
export {};

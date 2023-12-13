/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {TranslationProgress} from './TranslationAdminSelector';
interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	labels?: {
		default?: string;
		notTranslated?: string;
		translated?: string;
	};
	languageId: Liferay.Language.Locale;
	languageName: string;
	localeValue: string | null;
	translationProgress: TranslationProgress | null;
}
export default function TranslationAdminStatusLabel({
	defaultLanguageId,
	labels,
	languageId,
	languageName,
	localeValue,
	translationProgress,
}: Props): JSX.Element;
export {};

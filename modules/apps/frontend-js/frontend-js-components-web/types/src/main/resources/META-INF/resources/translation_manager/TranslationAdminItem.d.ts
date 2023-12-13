/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Locale} from './TranslationAdminContent';
import {TranslationProgress} from './TranslationAdminSelector';
interface Props {
	defaultLanguageId: Liferay.Language.Locale;
	item: Locale;
	labels?: {
		default?: string;
		notTranslated?: string;
		translated?: string;
	};
	localeValue: string | null;
	showOnlyFlags?: boolean;
	translationProgress?: TranslationProgress | null;
}
export default function TranslationAdminItem({
	defaultLanguageId,
	item,
	labels,
	localeValue,
	showOnlyFlags,
	translationProgress,
}: Props): JSX.Element;
export {};

/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Translations} from './TranslationAdminContent';
interface IProps extends Translations {
	adminMode?: boolean;
	displayType?: 'default' | 'horizontal';
	onActiveLanguageIdsChange?: (
		languageIds: Liferay.Language.Locale[]
	) => void;
	onSelectedLanguageIdChange?: (languageId: Liferay.Language.Locale) => void;
	selectedLanguageId: Liferay.Language.Locale;
	showOnlyFlags?: boolean;
	small?: boolean;
}
export default function TranslationAdminSelector({
	activeLanguageIds: initialActiveLanguageIds,
	adminMode,
	ariaLabels,
	availableLocales,
	defaultLanguageId,
	displayType,
	onActiveLanguageIdsChange,
	onSelectedLanguageIdChange,
	selectedLanguageId: initialSelectedLanguageId,
	showOnlyFlags,
	small,
	translations,
}: IProps): JSX.Element;
export {};

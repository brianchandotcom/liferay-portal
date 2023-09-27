/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Translations} from './TranslationAdminContent';
interface IProps extends Translations {
	adminMode?: boolean;
	onActiveLanguageIdsChange?: (languageIds: string[]) => void;
	onSelectedLanguageIdChange?: (languageId: string) => void;
	selectedLanguageId: string;
	showOnlyFlags?: boolean;
	small?: boolean;
}
export default function TranslationAdminSelector({
	activeLanguageIds: initialActiveLanguageIds,
	adminMode,
	ariaLabels,
	availableLocales,
	defaultLanguageId,
	onActiveLanguageIdsChange,
	onSelectedLanguageIdChange,
	selectedLanguageId: initialSelectedLanguageId,
	showOnlyFlags,
	small,
	translations,
}: IProps): JSX.Element;
export {};

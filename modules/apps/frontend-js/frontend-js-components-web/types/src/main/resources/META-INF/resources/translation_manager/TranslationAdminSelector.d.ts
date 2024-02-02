/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

import {Translations} from './TranslationAdminContent';
declare const DISPLAY_TYPE: {
	readonly DEFAULT: 'DEFAULT';
	readonly HORIZONTAL: 'HORIZONTAL';
};
declare type DisplayType = typeof DISPLAY_TYPE[keyof typeof DISPLAY_TYPE];
interface IProps extends Translations {
	adminMode?: boolean;
	displayType?: DisplayType;
	onActiveLanguageIdsChange?: (
		languageIds: Liferay.Language.Locale[]
	) => void;
	onSelectedLanguageIdChange?: (languageId: Liferay.Language.Locale) => void;
	onTriggerClick?: () => void;
	selectedLanguageId: Liferay.Language.Locale;
	showOnlyFlags?: boolean;
	small?: boolean;
	translationProgress?: TranslationProgress | null;
}
export interface TranslationProgress {
	totalItems: number;
	translatedItems: Record<string, number>;
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
	onTriggerClick,
	selectedLanguageId: initialSelectedLanguageId,
	showOnlyFlags,
	small,
	translationProgress,
	translations,
}: IProps): JSX.Element;
export {};

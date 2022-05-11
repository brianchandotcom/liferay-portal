/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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

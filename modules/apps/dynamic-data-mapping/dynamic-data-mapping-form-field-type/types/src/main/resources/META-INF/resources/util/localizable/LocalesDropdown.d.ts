/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/// <reference types="react" />

interface Locale {
	displayName: string;
	icon: string;
	isDefault?: boolean;
	isTranslated?: boolean;
	localeId: Liferay.Language.Locale;
}
interface LocalesDropdownProps {
	availableLocales: Locale[];
	editingLocale: Locale;
	fieldName: string;
	onLanguageClicked: (localeId: Liferay.Language.Locale) => void;
}
declare const LocalesDropdown: ({
	availableLocales,
	editingLocale,
	fieldName,
	onLanguageClicked,
}: LocalesDropdownProps) => JSX.Element;
export default LocalesDropdown;

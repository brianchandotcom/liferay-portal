import {LocalizedValue} from '../../types';

/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	AvailableLocale,
	EditingLocale,
} from '../../util/localizable/LocalesDropdown';

export function getEditingLocales(
	availableLocales: AvailableLocale[],
	defaultLocale: AvailableLocale,
	value: {} | LocalizedValue<unknown>
): EditingLocale[] {
	let initializedValue: undefined | LocalizedValue<unknown>;

	if (!Object.keys(value).length) {
		initializedValue = {[defaultLocale.localeId]: false};
	}

	return availableLocales.map((locale) => ({
		...locale,
		isDefault: locale.localeId === defaultLocale.localeId,
		isTranslated: Object.hasOwn(initializedValue ?? value, locale.localeId),
	}));
}

export function getLocale(
	editingLocales: EditingLocale[],
	fallbackLocale: AvailableLocale,
	id: string
): EditingLocale {
	const editingLocale = editingLocales.find(({localeId}) => localeId === id);

	if (editingLocale) {
		return editingLocale;
	}

	const defaultEditingLocale = editingLocales.find(
		({isDefault}) => isDefault
	);

	if (defaultEditingLocale) {
		return defaultEditingLocale;
	}

	return {...fallbackLocale, isDefault: true, isTranslated: false};
}

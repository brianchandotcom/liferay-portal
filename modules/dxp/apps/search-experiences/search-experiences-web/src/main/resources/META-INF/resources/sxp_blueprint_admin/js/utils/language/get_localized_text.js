/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import formatLocaleWithDashes from './format_locale_with_dashes';

/**
 * Used for getting the blueprint or element title and description. Titles
 * and descriptions handle both string `'title'` and a localized object
 * `{'en_US': 'Title'}`. This also accommodates for when elements
 * have titles and descriptions that use the BCP 47 language code,
 * such as `{'en-US': 'Title'}`.
 * @param {string|Object} value
 * @param {string} locale
 */
export default function getLocalizedText(value, locale) {
	if (!value) {
		return '';
	}
	else if (value[locale]) {
		return value[locale];
	}
	else if (value[formatLocaleWithDashes(locale)]) {
		return value[formatLocaleWithDashes(locale)];
	}
	else if (typeof value === 'string' || value instanceof String) {
		return value;
	}
	else if (value[Liferay.ThemeDisplay.getDefaultLanguageId()]) {
		return value[Liferay.ThemeDisplay.getDefaultLanguageId()];
	}
	else if (
		value[
			formatLocaleWithDashes(Liferay.ThemeDisplay.getDefaultLanguageId())
		]
	) {
		return value[
			formatLocaleWithDashes(Liferay.ThemeDisplay.getDefaultLanguageId())
		];
	}
	else if (Object.keys(value).length) {
		return value[Object.keys(value)[0]];
	}

	return '';
}

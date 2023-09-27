/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import isEmpty from '../functions/is_empty';
import formatLocaleWithDashes from '../language/format_locale_with_dashes';
import renameKeys from '../language/rename_keys';
import transformLocale from '../language/transform_locale';

/**
 * Used for getting the localized title and description from the sxpElement
 * by checking both the stringified `title` and object `title_i18n`. Returns
 * [title, description] together in the same locale.
 *
 * This accommodates for when elements have titles and descriptions in
 * the BCP 47 language code, such as `{'en-US': 'Title'}`, and non BCP 47
 * language code `{'en_US': 'Title'}`.
 * @param {Object} sxpElement
 * @param {string} locale
 * @return {Array}
 */
export default function getSXPElementTitleAndDescription(sxpElement, locale) {
	const {
		description = '',
		description_i18n = {},
		title = '',
		title_i18n = {},
	} = sxpElement;

	// Display `title` and `description` whenever available.

	if (title || isEmpty(title_i18n)) {
		return [title, description];
	}

	// In the case of custom elements when no `title` is available, check `_i18n`
	// objects. First, convert any 'zh-Hans-CN' or 'zh-Hans-TW' keys.

	const descriptionObject = renameKeys(description_i18n, transformLocale);
	const titleObject = renameKeys(title_i18n, transformLocale);

	// Identify the locale to display by checking existence of preferred
	// or default language in `title_i18n`. For consistency, have
	// `description` be in the same locale.

	let displayLocale = '';

	if (titleObject[locale]) {
		displayLocale = locale;
	}
	else if (titleObject[formatLocaleWithDashes(locale)]) {
		displayLocale = formatLocaleWithDashes(locale);
	}
	else if (titleObject[Liferay.ThemeDisplay.getDefaultLanguageId()]) {
		displayLocale = Liferay.ThemeDisplay.getDefaultLanguageId();
	}
	else if (
		titleObject[
			formatLocaleWithDashes(Liferay.ThemeDisplay.getDefaultLanguageId())
		]
	) {
		displayLocale = formatLocaleWithDashes(
			Liferay.ThemeDisplay.getDefaultLanguageId()
		);
	}
	else if (Object.keys(titleObject).length) {
		displayLocale = Object.keys(titleObject)[0];
	}

	if (displayLocale) {
		return [
			titleObject[displayLocale],
			descriptionObject[displayLocale] || '',
		];
	}

	// Default to empty values for `title` and `description`.

	return ['', ''];
}

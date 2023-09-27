/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

/**
 * Returns localized information used to link to a resource, like Liferay Learn
 * articles. The json object `learnMessages` contains the messages and urls.
 *
 * Example:
 * getLocalizedLearnMessageObject("general", {
 *	"general": {
 *		"en_US": {
 *			"message": "Tell me more",
 *			"url": "https://learn.liferay.com/"
 *		}
 *	}
 * })
 * => {
 *			"message": "Tell me more",
 *			"url": "https://learn.liferay.com/"
 *		}
 *
 * @param {string} resourceKey Identifies which resource to render
 * @param {Object} learnMessages Contains the messages and urls
 * @param {string} [locale=Liferay.ThemeDisplay.getLanguageId()]
 * @param {string} [defaultLocale=Liferay.ThemeDisplay.getDefaultLanguageId()]
 * @return {Object}
 */
export default function getLocalizedLearnMessageObject(
	resourceKey,
	learnMessages = {},
	locale = Liferay.ThemeDisplay.getLanguageId(),
	defaultLocale = Liferay.ThemeDisplay.getDefaultLanguageId()
) {
	const keyObject = learnMessages[resourceKey] || {en_US: {}};

	return (
		keyObject[locale] ||
		keyObject[defaultLocale] ||
		keyObject[Object.keys(keyObject)[0]]
	);
}

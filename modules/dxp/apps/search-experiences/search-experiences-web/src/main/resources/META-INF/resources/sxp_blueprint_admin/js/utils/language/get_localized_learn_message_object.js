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

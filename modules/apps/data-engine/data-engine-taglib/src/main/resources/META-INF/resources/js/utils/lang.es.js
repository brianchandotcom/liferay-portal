/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export function getLocalizedValue(defaultLanguageId, localizedValues) {
	const languageId = themeDisplay.getLanguageId();

	if (localizedValues[languageId]) {
		return localizedValues[languageId];
	}

	return localizedValues[defaultLanguageId];
}

const replaceString = (langKey, args) => {
	const SPLIT_REGEX = /({\d+})/g;

	const keyArray = langKey
		.split(SPLIT_REGEX)
		.filter((val) => val.length !== 0);

	for (let i = 0; i < args.length; i++) {
		const arg = args[i];

		const indexKey = `{${i}}`;

		let argIndex = keyArray.indexOf(indexKey);

		while (argIndex >= 0) {
			keyArray.splice(argIndex, 1, arg);

			argIndex = keyArray.indexOf(indexKey);
		}
	}

	return keyArray;
};

export function sub(langKey, args) {
	return replaceString(langKey, args).join('');
}

export function subComp(langKey, args) {
	return replaceString(langKey, args);
}

export function getPluralMessage(
	singular,
	plural,
	count = 0,
	toString,
	subArray
) {
	const message = count > 1 ? plural : singular;

	return sub(message, subArray || [count.toLocaleString()], toString);
}

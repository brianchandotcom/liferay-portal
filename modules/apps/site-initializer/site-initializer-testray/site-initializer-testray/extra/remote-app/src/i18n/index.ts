/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../services/liferay';
import en_US from './en_US';

export const languages = {
	en_US,
};

export function translate(
	word: string,
	languageId = Liferay.ThemeDisplay.getLanguageId()
): string {
	const languageProperties =
		(languages as any)[languageId] ||
		Liferay.ThemeDisplay.getDefaultLanguageId();

	return languageProperties[word] || word;
}

export function sub(word: string, words: string[] | string): string {
	if (!Array.isArray(words)) {
		words = [words];
	}

	let translatedWord = translate(word);

	words.forEach((value, index) => {
		const translatedKey = translate(value);
		const key = `{${index}}`;
		translatedWord = translatedWord.replace(key, translatedKey);
	});

	return translatedWord;
}

const i18n = {
	sub,
	translate,
};

export default i18n;

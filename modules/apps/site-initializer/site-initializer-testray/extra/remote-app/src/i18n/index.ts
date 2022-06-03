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

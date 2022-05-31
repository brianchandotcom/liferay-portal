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

import {Liferay} from '../services/liferay';

import en_US from './en_US.json';

export const languages = {
	en_US,
};

const translate = (word, languageId = Liferay.ThemeDisplay.getLanguageId()) => {
	const languageProperties = languages[languageId] || languages.en_US;

	return languageProperties[word] || word;
};

const sub = (word, words) => {
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
};

const pluralize = (count, word, plural = word + 's') => {
	return translate(count <= 1 ? word : plural);
};

const i18n = {
	pluralize,
	sub,
	translate,
};

export default i18n;

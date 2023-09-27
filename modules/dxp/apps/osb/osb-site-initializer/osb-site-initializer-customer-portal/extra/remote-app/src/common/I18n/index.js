/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Liferay} from '../services/liferay';

import en_US from './Language.json';
import es_ES from './Language_es_ES.json';
import ja_JP from './Language_ja_JP.json';
import pt_BR from './Language_pt_BR.json';

export const languages = {
	en_US,
	es_ES,
	ja_JP,
	pt_BR,
};

const translate = (word, languageId = Liferay.ThemeDisplay.getLanguageId()) => {
	const languageProperties = languages[languageId] || languages.en_US;

	return languageProperties[word] || languages.en_US[word] || word;
};

const getKeyByValue = (word, dictionary) => {
	const wordTranslated = Object.entries(dictionary).find(
		([_key, value]) => value === word
	);

	if (wordTranslated) {
		const translatedWord = wordTranslated[0];
		const capitalizedWord = `${translatedWord
			.charAt(0)
			.toUpperCase()}${translatedWord.slice(1)}`;
		const formattedWord = capitalizedWord.replace('-', ' ');

		return formattedWord;
	}

	return word;
};

const translateForAPI = (
	word,
	languageId = Liferay.ThemeDisplay.getLanguageId()
) => {
	const languageProperties = languages[languageId] || languages.en_US;

	return getKeyByValue(word, languageProperties);
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
	translateForAPI,
};

export default i18n;

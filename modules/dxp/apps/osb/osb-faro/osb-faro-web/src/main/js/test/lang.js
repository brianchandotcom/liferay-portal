/**
 * Reads our `Language.properties` and should return them for any calls
 * to `Liferay.Language.get`. This module will report an error if the file
 * cannot be read, and will probably fail most of our tests.
 */

import fs from 'fs';
import LanguageIgnoreList from './language-ignore-list';
import path from 'path';
import properties from 'properties';

const LANG_PATH = path.resolve(
	'src',
	'main',
	'resources',
	'content',
	'Language.properties'
);

let keys = {};

try {
	const buffer = fs.readFileSync(LANG_PATH);

	keys = properties.parse(buffer.toString('utf8'));
} catch (e) {
	// eslint-disable-next-line no-console
	console.error(`Failed to read lang key file: ${LANG_PATH}`);
}

/**
 * Returns the value for a given language key. Throws an error if
 * no value is found for the key.
 * @param {string} key - The language key
 * @returns {string} The language key's value
 */
export default function lang(key) {
	const value = keys[key];

	if (!value) {
		if (LanguageIgnoreList.includes(key)) {
			return key;
		}

		throw new Error(`Language key not found: ${key}`);
	}

	return value;
}

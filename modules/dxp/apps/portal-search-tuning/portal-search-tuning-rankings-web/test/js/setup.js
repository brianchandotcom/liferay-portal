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
 * Reads our `Language.properties` and should return them for any calls
 * to `Liferay.Language.get`. This module will report an error if the file
 * cannot be read, and will probably fail most of our tests.
 */

import fs from 'fs';
import path from 'path';
import properties from 'properties';
import '@testing-library/jest-dom/extend-expect';

const GLOBAL_LANG_PATH = path.resolve(
	'..',
	'..',
	'..',
	'..',
	'..',
	'portal-impl',
	'src',
	'content',
	'Language.properties'
);

const LANG_PATH = path.resolve(
	'src',
	'main',
	'resources',
	'content',
	'Language.properties'
);

const LANG_PATHS = [GLOBAL_LANG_PATH, LANG_PATH];

let keys = {};

try {
	const bufferArray = LANG_PATHS.map(langPath =>
		Buffer.concat([fs.readFileSync(langPath), Buffer.from('\n')])
	);

	const buffer = Buffer.concat(bufferArray);

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
function lang(key) {
	const value = keys[key];

	if (!value) {
		throw new Error(`Language key not found: ${key}`);
	}

	return value;
}

window.Liferay = {
	Language: {
		get: key => lang(key)
	}
};

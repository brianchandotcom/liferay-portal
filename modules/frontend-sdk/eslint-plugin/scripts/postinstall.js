/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const fs = require('fs');
const path = require('path');

/**
 * From: https://eslint.org/docs/user-guide/configuring#configuration-file-formats
 */
const eslintrcFilenames = [
	'.eslintrc.js',
	'.eslintrc.yaml',
	'.eslintrc.yml',
	'.eslintrc.json',
	'.eslintrc',
];

function findConfig(filename) {
	return fs.existsSync(filename) ? filename : null;
}

function detectPackageJSONConfig() {
	try {
		const packageJSON = require('./package.json');

		return typeof packageJSON.eslintConfig === 'object';
	}
	catch (_error) {
		return false;
	}
}

function log(message) {
	console.log(message); // eslint-disable-line no-console
}

const config = eslintrcFilenames.find(findConfig);

if (config) {
	log(`Preserving .eslintrc file found at ${config}`);
}
else if (detectPackageJSONConfig()) {
	log('Existing "eslintConfig" property found in package.json');
}
else {
	fs.writeFileSync(
		config,
		fs.readFileSync(path.join(__dirname, '../.eslintrc.js'), {
			encoding: 'utf8',
		})
	);

	log(`Created ${config}`);
}

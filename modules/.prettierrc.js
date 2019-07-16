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

/**
 * This fallback config exists so that users who have not installed the
 * NPM modules yet (eg. in a pre-build, clean checkout) will still have
 * standard Prettier config in their development environments.
 */
const FALLBACK_CONFIG = {
	bracketSpacing: false,
	endOfLine: 'lf',
	jsxSingleQuote: false,
	singleQuote: true,
	tabWidth: 4,
	trailingComma: 'none',
	useTabs: true
};

/* eslint-disable no-console */

function getConfig() {
	let config;

	try {
		config = require('liferay-npm-scripts/src/config/prettier');
	} catch (error) {
		console.log(`info: using fallback config in ${__filename}`);
		return FALLBACK_CONFIG;
	}

	if (JSON.stringify(FALLBACK_CONFIG) !== JSON.stringify(config)) {
		console.warn(
			`warning: The fallback config in ${__filename} is out of sync ` +
				'with the one in liferay-npm-scripts and should be updated'
		);
	}

	return config;
}

module.exports = getConfig();

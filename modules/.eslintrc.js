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
 * We use liferay-npm-scripts to perform linting in a controlled way, but we
 * also try to expose its configuration here so it can be picked up by editors.
 */
let config = {};

try {
	config = require('liferay-npm-scripts/src/config/eslint.config');
} catch (error) {
	throw new Error(
		'liferay-npm-scripts is not installed; please run "ant setup-sdk"'
	);
}

config = {
	...config,
	globals: {
		...config.globals,
		MODULE_PATH: true
	},
	rules: {
		'no-empty': ['error', {allowEmptyCatch: true}]
	}
};

module.exports = config;

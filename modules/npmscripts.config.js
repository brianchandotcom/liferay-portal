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
 * We need to (redundantly) include both "*.es.js" and "*.js" in the JS
 * globs in order to pacify ESLint. If we use "*.js" alone, then it will
 * complain if we try to use an `.eslintignore` file with contents like
 * this:
 *
 *      *.js
 *      !*.es.js
 *
 * ie. if the intent is to lint only "modern" ("*.es.js") files, and we
 * pass just "*.js" as a glob, ESLint will error:
 *
 *      You are linting "*.js", but all of the files matching the glob
 *      pattern "*.js" are ignored.
 *
 * With this hack, we can still run Prettier over all "*.js", but focus ESLint
 * on "*.es.js" alone.
 */
const CHECK_AND_FIX_GLOBS = [
	'*.js',
	'.*.js',
	'apps/*/*/.eslintrc.js',
	'apps/*/*/npmscripts.config.js',
	'apps/*/*/{src,test}/**/*.es.js',
	'apps/*/*/{src,test}/**/*.js',
	'apps/*/*/{src,test}/**/*.scss'
];

module.exports = {
	check: CHECK_AND_FIX_GLOBS,
	fix: CHECK_AND_FIX_GLOBS,
	preset: 'liferay-npm-scripts/src/presets/standard'
};

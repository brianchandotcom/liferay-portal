/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const stylelint = require('stylelint');

const noBlockComments = require('./rules/no-block-comments.js');
const noImportExtension = require('./rules/no-import-extension.js');
const singleImports = require('./rules/single-imports.js');
const sortImports = require('./rules/sort-imports.js');
const trimComments = require('./rules/trim-comments.js');

const rulesPlugins = [
	noBlockComments,
	noImportExtension,
	singleImports,
	sortImports,
	trimComments,
].map((rule) => stylelint.createPlugin(rule.ruleName, rule));

module.exports = rulesPlugins;

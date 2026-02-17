/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const plugins = require('./plugins.js');

const defaultConfig = {
	rules: {
		'at-rule-no-unknown': [
			true,
			{
				ignoreAtRules: [
					'at-root',
					'each',
					'else',
					'extend',
					'for',
					'forward',
					'function',
					'if',
					'include',
					'mixin',
					'return',
					'use',
					'warn',
					'while',
				],
			},
		],
		'block-no-empty': true,
		'color-hex-case': 'lower',
		'color-hex-length': 'short',
		'color-no-invalid-hex': true,
		'comment-no-empty': true,
		'declaration-block-no-shorthand-property-overrides': true,
		'font-family-no-duplicate-names': true,
		'function-calc-no-invalid': true,
		'function-name-case': 'lower',
		'function-url-no-scheme-relative': true,
		'function-url-quotes': 'never',
		'length-zero-no-unit': true,
		'liferay/no-block-comments': true,
		'liferay/no-import-extension': true,
		'liferay/sort-imports': true,
		'liferay/trim-comments': true,
		'media-feature-name-no-unknown': true,
		'no-duplicate-at-import-rules': true,
		'no-extra-semicolons': true,
		'number-leading-zero': 'always',
		'number-no-trailing-zeros': true,
		'property-case': 'lower',
		'property-no-unknown': [
			true,
			{
				ignoreProperties: ['aspect-ratio'],
			},
		],
		'selector-pseudo-class-no-unknown': true,
		'selector-pseudo-element-no-unknown': [
			true,
			{
				ignorePseudoElements: ['ng-deep'],
			},
		],
		'selector-type-no-unknown': [
			true,
			{
				ignore: 'custom-elements',
			},
		],
		'shorthand-property-no-redundant-values': true,
		'unit-case': 'lower',
		'unit-no-unknown': true,
		'value-keyword-case': 'lower',
	},
};

plugins.defaultConfig = defaultConfig;

module.exports = plugins;

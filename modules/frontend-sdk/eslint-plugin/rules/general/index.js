/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

module.exports = {
	'array-is-array': require('./lib/rules/array-is-array'),
	'destructure-requires': require('./lib/rules/destructure-requires'),
	'empty-line-between-elements': require('./lib/rules/empty-line-between-elements'),
	'expect-assert': require('./lib/rules/expect-assert'),
	'group-imports': require('./lib/rules/group-imports'),
	'import-extensions': require('./lib/rules/import-extensions'),
	'imports-first': require('./lib/rules/imports-first'),
	'no-abbreviations': require('./lib/rules/no-abbreviations'),
	'no-absolute-import': require('./lib/rules/no-absolute-import'),
	'no-anonymous-exports': require('./lib/rules/no-anonymous-exports'),
	'no-arrow': require('./lib/rules/no-arrow'),
	'no-conditional-object-keys': require('./lib/rules/no-conditional-object-keys'),
	'no-duplicate-class-names': require('./lib/rules/no-duplicate-class-names'),
	'no-duplicate-imports': require('./lib/rules/no-duplicate-imports'),
	'no-dynamic-require': require('./lib/rules/no-dynamic-require'),
	'no-extraneous-dependencies': require('./lib/rules/no-extraneous-dependencies'),
	'no-get-data-attribute': require('./lib/rules/no-get-data-attribute'),
	'no-it-should': require('./lib/rules/no-it-should'),
	'no-length-jsx-expression': require('./lib/rules/no-length-jsx-expression'),
	'no-require-and-call': require('./lib/rules/no-require-and-call'),
	'no-use-strict-in-module': require('./lib/rules/no-use-strict-in-module'),
	'padded-test-blocks': require('./lib/rules/padded-test-blocks'),
	'prefer-length-check': require('./lib/rules/prefer-length-check'),
	'ref-name-suffix': require('./lib/rules/ref-name-suffix'),
	'sort-class-names': require('./lib/rules/sort-class-names'),
	'sort-exports': require('./lib/rules/sort-exports'),
	'sort-import-destructures': require('./lib/rules/sort-import-destructures'),
	'sort-imports': require('./lib/rules/sort-imports'),
	'trim-class-names': require('./lib/rules/trim-class-names'),
	'use-state-naming-pattern': require('./lib/rules/use-state-naming-pattern'),
};

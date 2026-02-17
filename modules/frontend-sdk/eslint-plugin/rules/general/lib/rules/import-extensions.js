/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const path = require('path');

const {isLocal} = require('../common/imports');

const BAD_EXTENSIONS = new Set(['.js', '.ts', '.tsx']);

function stripExtension(value) {
	if (isLocal(value)) {
		const extension = path.extname(value);

		if (BAD_EXTENSIONS.has(extension)) {
			return value.slice(0, -extension.length);
		}
	}

	return value;
}

const messages = {
	badExport: 'unnecessary extension in export',
	badImport: 'unnecessary extension in import',
	badRequire: 'unnecessary extension in require',
};

module.exports = {
	create(context) {
		function fix(node, messageId) {
			let delimiter;
			let original;
			let stripped;

			if (node.type === 'TemplateLiteral' && node.quasis.length === 1) {

				// Only deal with template literals if they are static (ie. no
				// interpolation).

				delimiter = '`';
				original = node.quasis[0].value.raw;
				stripped = stripExtension(original);
			}
			else if (node.type === 'Literal') {
				delimiter = node.raw[0];
				original = node.value;
				stripped = stripExtension(original);
			}
			else {
				return;
			}

			if (stripped !== original) {
				context.report({
					fix: (fixer) =>
						fixer.replaceText(
							node,
							`${delimiter}${stripped}${delimiter}`
						),
					messageId,
					node,
				});
			}
		}

		function check(node) {
			if (!node) {
				return;
			}

			if (
				node.type === 'CallExpression' &&
				node.arguments &&
				node.arguments.length &&
				(node.arguments[0].type === 'Literal' ||
					node.arguments[0].type === 'TemplateLiteral')
			) {
				fix(node.arguments[0], 'badRequire');
			}
			else if (
				(node.type === 'ExportNamedDeclaration' ||
					node.type === 'ExportAllDeclaration') &&
				node.source &&
				(node.source.type === 'Literal' ||
					node.source.type === 'TemplateLiteral')
			) {
				fix(node.source, 'badExport');
			}
			else if (
				node.type === 'ImportDeclaration' &&
				(node.source.type === 'Literal' ||
					node.source.type === 'TemplateLiteral')
			) {
				fix(node.source, 'badImport');
			}
		}

		return {
			CallExpression(node) {
				if (node.callee.name === 'require') {
					check(node);
				}
			},

			ExportAllDeclaration(node) {
				check(node);
			},

			ExportNamedDeclaration(node) {
				check(node);
			},

			ImportDeclaration(node) {
				check(node);
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description:
				'imports and exports should use/omit extensions consistently',
			recommended: false,
			url: 'https://github.com/liferay/eslint-config-liferay/issues/137',
		},
		fixable: 'code',
		messages,
		schema: [],
		type: 'problem',
	},
};

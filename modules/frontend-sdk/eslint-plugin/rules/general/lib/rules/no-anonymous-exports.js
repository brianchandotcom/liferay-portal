/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

module.exports = {
	create(context) {
		return {
			ExportDefaultDeclaration(node) {
				if (
					node.declaration &&
					node.declaration.type === 'ArrowFunctionExpression'
				) {
					context.report({
						message:
							"Use named function for export. Example: 'function fooBar() {}'",
						node,
					});
				}
			},
			ExportNamedDeclaration(node) {
				if (
					node.declaration &&
					node.declaration.type === 'VariableDeclaration' &&
					node.declaration.declarations &&
					node.declaration.declarations[0] &&
					node.declaration.declarations[0].init &&
					node.declaration.declarations[0].init.type ===
						'ArrowFunctionExpression'
				) {
					context.report({
						message:
							"Use named function for export instead of arrow function. Example: 'function fooBar() {}'",
						node,
					});
				}
			},
		};
	},
	meta: {
		category: 'Best Practices',
		description: 'Prefer exporting named functions',
		recommended: false,
		url: 'https://github.com/liferay/liferay-frontend-projects/issues/25',
	},
};

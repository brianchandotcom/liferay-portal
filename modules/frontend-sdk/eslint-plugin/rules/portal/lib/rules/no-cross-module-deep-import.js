/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const path = require('path');

function parseImportPath(importPath) {
	const parts = importPath.split('/');

	let moduleName;
	let subpath;

	if (importPath.startsWith('@')) {
		moduleName = parts.slice(0, 2).join('/');
		subpath = parts.slice(2).join('/');
	}
	else {
		moduleName = parts[0];
		subpath = parts.slice(1).join('/');
	}

	return {moduleName, subpath};
}

function checkImportPath(node, nodeScriptsConfig, context) {
	if (
		node.source &&
		node.source.type === 'Literal' &&
		!node.source.value.startsWith('.') &&
		!node.source.value.startsWith('/')
	) {
		const importPath = node.source.value;

		const {moduleName, subpath} = parseImportPath(importPath);

		if (subpath) {
			const submodules = nodeScriptsConfig.imports[moduleName];

			if (submodules) {
				const match = submodules.some(
					(submodule) =>
						path.join(moduleName, submodule) === importPath
				);

				if (!match) {
					context.report({
						message: `'${moduleName}' does not export submodule: '${subpath}'. Do not import files deeply from another module.`,
						node,
					});
				}
			}
		}
	}
}

module.exports = {
	create(context) {
		const nodeScriptsConfig = context.options[0]?.nodeScriptsConfig;

		if (!nodeScriptsConfig) {
			return {};
		}

		return {
			ExportDefaultDeclaration(node) {
				checkImportPath(node, nodeScriptsConfig, context);
			},
			ExportNamedDeclaration(node) {
				checkImportPath(node, nodeScriptsConfig, context);
			},
			ImportDeclaration(node) {
				checkImportPath(node, nodeScriptsConfig, context);
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: 'you cannot import files deeply from another module',
			recommended: false,
		},
		fixable: null,
		schema: [{type: 'object'}],
		type: 'problem',
	},
};

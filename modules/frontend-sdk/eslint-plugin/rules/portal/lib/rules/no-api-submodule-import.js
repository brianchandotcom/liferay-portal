/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const path = require('path');

const DESCRIPTION =
	'you cannot import files outside of the "/api" directory for your submodule.';

const API_PATH = 'src/main/resources/META-INF/resources/js/api/';

function isPathEscapingAPIDir(filePath, relativePath) {
	const resolvedTarget = path.resolve(path.dirname(filePath), relativePath);

	return !resolvedTarget.includes(API_PATH);
}

function checkImportPath(node, context) {
	if (
		context.getFilename().includes(API_PATH) &&
		node.source &&
		node.source.type === 'Literal' &&
		node.source.value.startsWith('.')
	) {
		if (isPathEscapingAPIDir(context.getFilename(), node.source.value)) {
			context.report({
				message: DESCRIPTION,
				node,
			});
		}
	}
}

module.exports = {
	create(context) {
		return {
			ExportDefaultDeclaration(node) {
				checkImportPath(node, context);
			},
			ExportNamedDeclaration(node) {
				checkImportPath(node, context);
			},
			ImportDeclaration(node) {
				checkImportPath(node, context);
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
		},
		fixable: null,
		schema: [],
		type: 'problem',
	},
};

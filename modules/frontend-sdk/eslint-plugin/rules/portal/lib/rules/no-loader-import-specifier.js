/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const description = 'SCSS resources should be imported only for side-effects';

const messageId = 'noLoaderImportSpecifiers';

module.exports = {
	create(context) {
		return {
			ImportDeclaration(node) {
				if (
					node.specifiers.length &&
					node.source.type === 'Literal' &&
					node.source.value.endsWith('.scss')
				) {
					context.report({
						messageId,
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description,
			recommended: false,
			url: 'https://github.com/liferay/eslint-config-liferay/issues/122',
		},
		fixable: null,
		messages: {
			noLoaderImportSpecifiers: description,
		},
		schema: [],
		type: 'problem',
	},
};

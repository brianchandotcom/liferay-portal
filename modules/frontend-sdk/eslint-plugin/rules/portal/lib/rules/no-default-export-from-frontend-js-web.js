/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION =
	'frontend-js-web contains no default export, you need to import named items.';

module.exports = {
	create(context) {
		return {
			ImportDeclaration(node) {
				if (
					node.source &&
					node.source.type === 'Literal' &&
					(node.source.value === 'frontend-js-web' ||
						node.source.value === '@liferay/frontend-js-web') &&
					node.specifiers &&
					node.specifiers[0] &&
					node.specifiers[0].type === 'ImportDefaultSpecifier'
				) {
					context.report({
						message: DESCRIPTION,
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-projects/issues/918',
		},
		fixable: null,
		schema: [],
		type: 'problem',
	},
};

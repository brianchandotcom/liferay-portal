/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION = 'use Array.isArray()';

module.exports = {
	create(context) {
		return {
			BinaryExpression(node) {
				if (
					node.operator === 'instanceof' &&
					node.right.type === 'Identifier' &&
					node.right.name === 'Array'
				) {
					const source = context.getSourceCode();

					context.report({
						fix: (fixer) => {
							return fixer.replaceText(
								node,
								`Array.isArray(${source.getText(node.left)})`
							);
						},
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
			url: 'https://github.com/liferay/eslint-config-liferay/issues/139',
		},
		fixable: 'code', // or "code" or "whitespace"
		schema: [],
		type: 'problem',
	},
};

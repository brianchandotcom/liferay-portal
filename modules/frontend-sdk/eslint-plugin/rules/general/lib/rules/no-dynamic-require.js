/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION = 'require() arguments should be static';

module.exports = {
	create(context) {
		return {
			CallExpression(node) {
				if (
					node.callee.type === 'Identifier' &&
					node.callee.name === 'require'
				) {
					const argument = node.arguments[0];

					if (
						argument &&
						argument.type === 'Literal' &&
						typeof argument.value === 'string'
					) {
						return;
					}
					else if (
						argument &&
						argument.type === 'TemplateLiteral' &&
						!argument.expressions.length
					) {
						return;
					}

					context.report({
						message: DESCRIPTION,
						node: argument || node,
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
				'https://github.com/liferay/liferay-frontend-guidelines/issues/60',
		},
		fixable: null, // or "code" or "whitespace"
		schema: [],
		type: 'problem',
	},
};

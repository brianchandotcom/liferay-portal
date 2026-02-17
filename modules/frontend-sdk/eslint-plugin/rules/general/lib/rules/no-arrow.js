/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message = 'arrow functions are not allowed';

module.exports = {
	create(context) {
		return {
			ArrowFunctionExpression(node) {
				context.report({
					message,
					node,
				});
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: message,
			recommended: false,
			url: 'https://github.com/liferay/eslint-config-liferay/issues/179',
		},
		fixable: 'code',
		schema: [],
		type: 'problem',
	},
};

/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message = "this is a shortcut to A.one('body'), use document.body";

module.exports = {
	create(context) {
		return {
			MemberExpression(node) {
				if (
					node.object.name === 'A' &&
					node.property.name === 'getBody'
				) {
					context.report({
						message,
						node,
					});
				}
			},
		};
	},
	meta: {
		docs: {
			category: 'Best Practices',
			description: message,
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-projects/issues/617',
		},
		fixable: 'code',
		schema: [],
		type: 'problem',
	},
};

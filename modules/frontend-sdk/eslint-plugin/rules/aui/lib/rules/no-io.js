/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message = 'use Liferay.Util.fetch instead';

module.exports = {
	create(context) {
		return {
			MemberExpression(node) {
				if (node.object.name === 'A' && node.property.name === 'io') {
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

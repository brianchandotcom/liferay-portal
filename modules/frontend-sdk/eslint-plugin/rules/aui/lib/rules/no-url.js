/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message = 'use the vanilla URL class instead of A.Url';

module.exports = {
	create(context) {
		return {
			CallExpression(node) {
				const {callee} = node;

				if (
					callee &&
					callee.property &&
					callee.object.name === 'A' &&
					callee.property.name === 'Url'
				) {
					context.report({
						message,
						node,
					});
				}
			},
			NewExpression(node) {
				const {callee} = node;

				if (
					callee &&
					callee.object &&
					callee.object.name === 'A' &&
					callee.property.name === 'Url'
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
				'https://github.com/liferay/liferay-frontend-projects/issues/652',
		},
		fixable: 'code',
		schema: [],
		type: 'problem',
	},
};

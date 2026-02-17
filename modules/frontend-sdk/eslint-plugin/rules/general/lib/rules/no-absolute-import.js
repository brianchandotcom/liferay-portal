/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const {getSource, isAbsolute} = require('../common/imports');

const DESCRIPTION = 'import sources should not use absolute paths';

module.exports = {
	create(context) {
		function check(node, source) {
			if (isAbsolute(source)) {
				context.report({
					message: DESCRIPTION,
					node,
				});
			}
		}

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
						check(argument, argument.value);
					}
					else if (
						argument &&
						argument.type === 'TemplateLiteral' &&
						!argument.expressions.length &&
						argument.quasis[0] &&
						argument.quasis[0].type === 'TemplateElement'
					) {
						check(argument, argument.quasis[0].value.cooked);
					}
				}
			},

			ImportDeclaration(node) {
				check(node.source, getSource(node));
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

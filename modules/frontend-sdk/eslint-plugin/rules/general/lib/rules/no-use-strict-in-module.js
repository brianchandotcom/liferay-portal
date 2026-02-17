/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message = `'use strict' is unnecessary inside of modules`;

module.exports = {
	create(context) {
		const useStrictExpressions = [];
		let esModule;

		const checkEsModule = () => {
			if (!esModule) {
				esModule = true;
			}
		};

		return {
			'ExportDefaultDeclaration': checkEsModule,
			'ExportNamedDeclaration': checkEsModule,
			'ExpressionStatement'(node) {
				if (
					node.expression.type === 'Literal' &&
					node.expression.value === 'use strict'
				) {
					useStrictExpressions.push(node.expression);
				}
			},
			'ImportDeclaration': checkEsModule,
			'ImportNamespaceSpecifier': checkEsModule,
			'Program:exit': () => {
				if (esModule) {
					useStrictExpressions.forEach((expression) => {
						context.report({
							message,
							node: expression,
						});
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
				'https://github.com/liferay/liferay-frontend-projects/issues/20',
		},
		fixable: 'code',
		schema: [],
		type: 'problem',
	},
};

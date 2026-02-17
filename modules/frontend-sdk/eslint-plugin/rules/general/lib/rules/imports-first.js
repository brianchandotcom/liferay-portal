/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const {
	getRequireStatement,
	getSource,
	isRequireStatement,
	withScope,
} = require('../common/imports');

module.exports = {
	create(context) {
		const {scope, visitors} = withScope();

		let lastImportIndex = -1;
		let lastNonImportIndex = -1;

		function isDirective(node) {
			return (
				node &&
				node.type === 'ExpressionStatement' &&
				node.expression.type === 'Literal' &&
				typeof node.expression.value === 'string'
			);
		}

		function isImportDeclaration(node) {
			return node && node.type === 'ImportDeclaration';
		}

		function isRequireExpression(node) {
			return (
				node &&
				node.type === 'ExpressionStatement' &&
				node.expression.type === 'CallExpression' &&
				node.expression.callee.name === 'require'
			);
		}

		function check(node) {
			let current = node;

			const directives = [];

			while (current) {
				if (lastNonImportIndex === -1) {

					// Haven't seen a non-import yet, so must search.

					const token = context.getTokenBefore(current);

					if (!token) {
						break;
					}

					current = context.getNodeByRangeIndex(token.range[0]);

					if (isDirective(current)) {

						// Remember this, for now (actual check occurs below).

						directives.push(current);

						continue;
					}

					if (
						isRequireExpression(current) ||
						isRequireStatement(current) ||
						isImportDeclaration(current)
					) {
						if (token.range[1] <= lastImportIndex) {

							// This is a known-good import, so we can stop.

							break;
						}
						else {
							lastImportIndex = current.range[1];

							if (!directives.length) {

								// Haven't seen a directive yet, so keep going.
								//
								// If we saw a directive on a previous
								// iteration but we got here (by not
								// reaching a known-good import), that
								// means the directive wasn't the at
								// the start of the file, so `node` is
								// below a non-top-of-file, non-import
								// statement, and should be reported;
								// which we'll do by falling through
								// below.

								continue;
							}
						}
					}

					lastNonImportIndex = token.range[1];
				}

				context.report({
					message: `import of ${JSON.stringify(
						getSource(node)
					)} must come before other statements`,
					node,
				});

				break;
			}
		}

		return {
			...visitors,

			CallExpression(node) {
				if (scope.length) {

					// Only consider `require` calls at the top level.

					return;
				}

				check(getRequireStatement(node));
			},

			ImportDeclaration(node) {
				check(node);
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: 'imports must come before other statements',
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-guidelines/issues/60',
		},
		fixable: null,
		schema: [],
		type: 'problem',
	},
};

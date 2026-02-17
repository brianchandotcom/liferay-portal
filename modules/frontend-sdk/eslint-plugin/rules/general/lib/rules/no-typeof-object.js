/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message =
	'checking "typeof x === \'object\'" can be falsy if null. Use "x !== null && typeof x === \'object\'"';

module.exports = {
	create(context) {
		return {
			BinaryExpression(node) {
				if (
					node.left &&
					node.left.type === 'UnaryExpression' &&
					node.left.operator === 'typeof' &&
					node.right &&
					node.right.value === 'object'
				) {
					const variableName = node.left.argument.name;

					const logicalLeft =
						node.parent &&
						node.parent.type === 'LogicalExpression' &&
						node.parent.left;

					let hasNullCheck = false;

					if (logicalLeft) {
						let leftSibling;

						if (logicalLeft.type === 'BinaryExpression') {
							leftSibling = logicalLeft;
						}

						if (logicalLeft.type === 'LogicalExpression') {
							leftSibling =
								logicalLeft.right.type === 'BinaryExpression' &&
								logicalLeft.right;
						}

						hasNullCheck =
							leftSibling &&
							leftSibling.left &&
							leftSibling.left.name === variableName &&
							leftSibling.operator === '!==' &&
							leftSibling.right.value === null;

						if (logicalLeft.type === 'UnaryExpression') {
							hasNullCheck =
								logicalLeft.operator === '!' &&
								logicalLeft.argument.name === variableName;
						}
					}

					if (!hasNullCheck) {
						context.report({
							fix: (fixer) => {
								return fixer.insertTextBefore(
									node,
									`${variableName} !== null && `
								);
							},
							message,
							node,
						});
					}
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
				'https://github.com/liferay/liferay-frontend-projects/issues/19',
		},
		fixable: true,
		schema: [],
		type: 'problem',
	},
};

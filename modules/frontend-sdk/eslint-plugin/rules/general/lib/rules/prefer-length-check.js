/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message = 'prefer using .length';
const STRICT_EQUALITY = '===';
const MORE_THAN = '>';
const LESS_THAN = '<';
const LENGTH = 'length';

module.exports = {
	create(context) {
		return {
			BinaryExpression(node) {
				const {left, operator, right} = node;

				if (
					operator &&
					(operator === STRICT_EQUALITY ||
						operator === MORE_THAN ||
						operator === LESS_THAN)
				) {
					const source = context.getSourceCode();
					let replacement;

					if (
						right.property &&
						right.property.name === LENGTH &&
						left.value === 0
					) {
						replacement =
							operator === STRICT_EQUALITY
								? `!${source.getText(right)}`
								: `!!${source.getText(right)}`;

						context.report({
							fix: (fixer) =>
								fixer.replaceText(node, replacement),
							message,
							node,
						});
					}

					if (
						left.property &&
						left.property.name === LENGTH &&
						right.value === 0
					) {
						replacement =
							operator === STRICT_EQUALITY
								? `!${source.getText(left)}`
								: `!!${source.getText(left)}`;

						context.report({
							fix: (fixer) =>
								fixer.replaceText(node, replacement),
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

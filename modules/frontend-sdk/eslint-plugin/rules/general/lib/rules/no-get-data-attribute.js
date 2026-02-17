/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

function camelize(val) {
	return val
		.split('-')
		.map((item, i) => {
			return i !== 0
				? item.charAt(0).toUpperCase() + item.slice(1)
				: item;
		})
		.join('');
}

module.exports = {
	create(context) {
		const sourceCode = context.getSourceCode();

		return {
			CallExpression(node) {
				if (
					node.callee &&
					node.callee.type === 'MemberExpression' &&
					node.callee.property &&
					node.callee.property.name === 'getAttribute' &&
					node.arguments &&
					node.arguments[0] &&
					node.arguments[0].value &&
					node.arguments[0].value.startsWith('data-')
				) {
					const argumentValue = node.arguments[0].value;

					const argumentName = camelize(
						argumentValue.replace('data-', '')
					);

					context.report({
						fix: (fixer) => {
							if (node.callee.object.type === 'Identifier') {
								return fixer.replaceText(
									node,
									`${node.callee.object.name}.dataset.${argumentName}`
								);
							}
							else if (
								node.callee.object.type === 'MemberExpression'
							) {
								const memberExpressionString = sourceCode.getText(
									node.callee.object
								);

								return fixer.replaceText(
									node,
									`${memberExpressionString}.dataset.${argumentName}`
								);
							}
						},
						message: `Use "dataset.${argumentName}" instead of "getAttribute('${argumentValue}')"`,
						node: node.callee.property,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: 'use dataset instead of getAttribute("data-*")',
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-projects/issues/311',
		},
		fixable: 'code',
		schema: [],
		type: 'problem',
	},
};

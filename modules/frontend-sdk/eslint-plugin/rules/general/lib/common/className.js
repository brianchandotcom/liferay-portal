/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const CLASS_NAME_REGEX = /(?:\bc|[a-z]C)lassName$/;

/**
 * A visitor function that calls `callback` for any "className" `JSXAttribute`.
 */
function checkJSXAttribute(node, callback, context = null, options = {}) {
	const {allowTemplateLiteralExpressions} = options;

	if (!CLASS_NAME_REGEX.test(node.name.name) || !node.value) {
		return;
	}

	if (node.value.type === 'JSXExpressionContainer') {
		if (
			node.value.expression.type === 'Literal' &&
			typeof node.value.expression.value === 'string'
		) {
			const {raw, value} = node.value.expression;

			callback(node.value.expression, value, raw.charAt(0));
		}
		else if (node.value.expression.type === 'TemplateLiteral') {
			const {expression} = node.value;

			if (!expression.expressions.length) {
				const {raw} = expression.quasis[0].value;

				callback(expression, raw, '`');
			}
			else if (allowTemplateLiteralExpressions) {
				callback(
					expression,
					context.getSourceCode().getText(expression).slice(1, -1),
					'`'
				);
			}
		}
	}
	else if (
		node.value.type === 'Literal' &&
		typeof node.value.value === 'string'
	) {
		const {raw, value} = node.value;

		callback(node.value, value, raw.charAt(0));
	}
}

module.exports = {
	checkJSXAttribute,
};

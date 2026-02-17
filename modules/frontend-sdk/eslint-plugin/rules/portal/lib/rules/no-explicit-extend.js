/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const path = require('path');

const DISALLOWED_EXTENSIONS = {
	'.babelrc.js': {
		messageId: 'noExplicitPreset',
		property: 'presets',
		values: new Set(['@babel/preset-env', '@babel/preset-react']),
	},
	'.eslintrc.js': {
		messageId: 'noExplicitExtend',
		property: 'extends',
		values: new Set(['liferay/portal', 'liferay/react']),
	},
};

const noExplicitExtend =
	'`liferay/portal` and `liferay/react` apply automatically and can be omitted';

const noExplicitPreset =
	'`@babel/preset-env` and `@babel/preset-react` apply automatically and can be omitted';

/**
 * Expects `nodesToRemove` to be either:
 *
 * - a Set of literals to prune from an ArrayExpression; or:
 * - a Property to prune from an ObjectExpression.
 */
function fix(nodesToRemove, context, fixer) {
	const source = context.getSourceCode();

	let items;

	if (nodesToRemove instanceof Set) {

		// Removing elements from an ArrayExpression.

		const parent = [...nodesToRemove][0].parent;

		items = parent.elements.slice();
	}
	else {

		// Removing property from an ObjectExpression.

		const parent = nodesToRemove.parent;

		// Special case: when removing last property, kill all
		// internal whitespace.

		if (parent.properties.length === 1) {
			return fixer.replaceText(parent, '{}');
		}

		nodesToRemove = new Set([nodesToRemove]);
		items = parent.properties.slice();
	}

	const lastIndex = items.length - 1;
	const start = items[0].range[0];
	const end = items[lastIndex].range[1];

	// Record index of last `item` in `items` that will remain after we've
	// deleted `nodesToRemove`.

	const lastRemaining = items.reduce((last, item, index) => {
		if (nodesToRemove.has(item)) {
			return last;
		}
		else {
			return index;
		}
	}, NaN);

	// Remove array elements or an object property, preserving
	// whitespace between items.

	return fixer.replaceTextRange(
		[start, end],
		items.slice().reduce((text, item, index) => {
			const atEnd = index >= lastRemaining;

			const trailingWhitespace = atEnd
				? ''
				: source
						.getText()
						.slice(item.range[1], items[index + 1].range[0]);

			if (nodesToRemove.has(item)) {
				return text;
			}
			else {
				const itemText = source.getText(item);

				return text + itemText + trailingWhitespace;
			}
		}, '')
	);
}

module.exports = {
	create(context) {
		const filename = path.basename(context.getFilename());

		const disallowed = DISALLOWED_EXTENSIONS[filename];

		if (!disallowed) {
			return {};
		}

		const {messageId} = disallowed;

		const pendingDeletions = new Map();

		return {
			'ArrayExpression:exit'(node) {
				if (pendingDeletions.has(node)) {

					// Special case: when removing all array items, remove
					// entire property instead.

					if (
						pendingDeletions.get(node).size === node.elements.length
					) {
						pendingDeletions.set(node.parent, node.parent);
					}
					else {
						context.report({
							fix: (fixer) =>
								fix(pendingDeletions.get(node), context, fixer),
							messageId,
							node,
						});
					}
				}
			},

			'Literal'(node) {
				if (!disallowed.values.has(node.value)) {
					return;
				}

				if (
					node.parent &&
					node.parent.type === 'ArrayExpression' &&
					node.parent.parent &&
					node.parent.parent.type === 'Property' &&
					node.parent.parent.key &&
					node.parent.parent.key.type === 'Identifier' &&
					node.parent.parent.key.name === disallowed.property
				) {
					if (!pendingDeletions.has(node.parent)) {
						pendingDeletions.set(node.parent, new Set());
					}

					pendingDeletions.get(node.parent).add(node);
				}
				else if (
					node.parent &&
					node.parent.type === 'Property' &&
					node.parent.key &&
					node.parent.key.type === 'Identifier' &&
					node.parent.key.name === disallowed.property
				) {
					pendingDeletions.set(node.parent, node);
				}
			},

			'Property:exit'(node) {
				if (pendingDeletions.has(node)) {
					context.report({
						fix: (fixer) => fix(node, context, fixer),
						messageId,
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: 'default configs and presets can be omitted',
			recommended: false,
			url: 'https://github.com/liferay/eslint-config-liferay/pull/53',
		},
		fixable: 'code',
		messages: {
			noExplicitExtend,
			noExplicitPreset,
		},
		schema: [],
		type: 'problem',
	},
};

/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION =
	'consecutive test blocks should be separated by a blank line';

const messageId = 'paddedTestBlocks';

/**
 * These are the globals listed here: https://jestjs.io/docs/en/api
 *
 * For ease of processing, we use a dot to separate elements of interest; ie.
 * we have "describe.each.`table`" here even though you would write it as
 * "describe.each`table`" in tests.
 */
const CALL_EXPRESSIONS = [
	'afterAll',
	'afterEach',
	'beforeAll',
	'beforeEach',
	'describe',
	'describe.each',
	'describe.each.`table`',
	'describe.only',
	'describe.skip',
	'fdescribe',
	'fdescribe.each',
	'fdescribe.each.`table`',
	'fit',
	'fit.each',
	'fit.each.`table`',
	'it',
	'it.each',
	'it.each.`table`',
	'it.only',
	'it.only.each',
	'it.skip',
	'it.skip.each',
	'it.skip.each.`table`',
	'test',
	'test.each',
	'test.each.`table`',
	'test.only',
	'test.only.each',
	'test.skip',
	'test.skip.each',
	'test.todo',
	'xdescribe',
	'xdescribe.each',
	'xdescribe.each.`table`',
	'xit',
	'xit.each',
	'xit.each.`table`',
	'xtest',
	'xtest.each',
	'xtest.each.`table`',
].map((format) => {

	// Split apart the format and return a matcher function that returns `true`
	// if a node matches. For example, given:
	//
	// - "it", return a matcher that recognizes an Identifier with name "it".
	// - "test.todo", return a matcher that recognizes a MemberExpression
	//   whose object is the Identfier with name "test" and whose property is
	//   the Identifier with name "todo".
	// - "it.skip.each.`table`", return a matcher that recognizes a
	//   TaggedTemplateExpression whose tag is a MemberExpression whose
	//   property is the Identifier with name "each" and whose object
	//   is a MemberExpression corresponding to "it.skip" (via recursion).

	const match = (node, parts) => {
		if (parts.length === 1) {
			return node.type === 'Identifier' && node.name === parts[0];
		}
		else {

			// Recurse.

			if (node.type === 'MemberExpression') {
				return (
					match(node.object, parts.slice(0, -1)) &&
					match(node.property, parts.slice(-1))
				);
			}
			else if (
				node.type === 'TaggedTemplateExpression' &&
				parts[parts.length - 1].startsWith('`')
			) {
				return match(node.tag, parts.slice(0, -1));
			}
		}

		return false;
	};

	return (node) => match(node.callee, format.split('.'));
});

module.exports = {
	create(context) {
		const source = context.getSourceCode();

		return {
			CallExpression(node) {
				for (const isMatch of CALL_EXPRESSIONS) {
					if (isMatch(node)) {
						const token = context.getTokenBefore(node);

						if (token) {
							const previous = context.getNodeByRangeIndex(
								token.range[0]
							);

							if (
								previous.type === 'ExpressionStatement' &&
								previous.expression.type === 'CallExpression' &&
								CALL_EXPRESSIONS.some((isMatch) => {
									return isMatch(previous.expression);
								})
							) {
								const start = previous.range[1];
								const end = node.range[0];
								const between = source.text.slice(start, end);

								let newlines = 0;

								const expanded = between.replace(
									/(\r\n|\n)([ \t]*)/g,
									(_match, newline, indent) => {
										newlines++;

										return newline + newline + indent;
									}
								);

								if (newlines === 1) {
									context.report({
										fix: (fixer) => {
											return fixer.replaceTextRange(
												[start, end],
												expanded
											);
										},
										messageId,
										node,
									});
								}
							}
						}

						break;
					}
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Test style',
			description: DESCRIPTION,
			recommended: false,
			url: 'https://github.com/liferay/eslint-config-liferay/issues/65',
		},
		fixable: 'whitespace',
		messages: {
			[messageId]: DESCRIPTION,
		},
		schema: [],
		type: 'layout',
	},
};

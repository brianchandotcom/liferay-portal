/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const stylelint = require('stylelint');

const ruleName = 'liferay/no-block-comments';
const messages = stylelint.utils.ruleMessages(ruleName, {
	expected:
		'No block-based comments (/* ... */); use line-based (//) comment syntax instead',
});

const rule = (actual) => {
	return function (root, result) {
		const validOptions = stylelint.utils.validateOptions(result, ruleName, {
			actual,
		});
		if (!validOptions) {
			return;
		}
		root.walkComments((comment) => {
			if (!comment.raws.inline) {
				stylelint.utils.report({
					message: messages.expected,
					node: comment,
					result,
					ruleName,
				});
			}
		});
	};
};

rule.ruleName = ruleName;
rule.messages = messages;

module.exports = rule;

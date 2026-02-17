/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const stylelint = require('stylelint');
const ruleName = 'liferay/trim-comments';
const messages = stylelint.utils.ruleMessages(ruleName, {
	noLeadingBlanks: 'No blank leading lines in comments',
	noTrailingBlanks: 'No blank trailing lines in comments',
});

// In practice, PostCSS represents whitespace-only comments with empty
// strings, but just to be safe, we employ a looser check.

const BLANK_REGEX = /^\s*$/;

function isLeadingComment(comment, prev) {
	if (!prev) {
		return true;
	}
	if (prev.type !== 'comment') {
		return true;
	}
	const newlines = comment.raws.before.match(/\n/g);
	if (newlines && newlines.length > 1) {

		// Special case: previous node is a comment, but we have at least one
		// intervening blank line.

		return true;
	}

	return false;
}

function isTrailingComment(_comment, next) {
	if (!next) {
		return true;
	}
	if (next.type !== 'comment') {
		return true;
	}
	const newlines = next.raws.before.match(/\n/g);
	if (newlines && newlines.length > 1) {

		// Special case: next node is a comment, but we have at least one
		// intervening blank line.

		return true;
	}

	return false;
}

const rule = (options, secondaryOptions, context) => {
	return function (root, result) {
		const validOptions = stylelint.utils.validateOptions(
			result,
			ruleName,
			{
				actual: options,
				possible: [true, false],
			},
			{
				actual: secondaryOptions,
				optional: true,
				possible: {
					disableFix: [true, false],
				},
			}
		);
		if (!validOptions || !options) {
			return;
		}
		const disableFix = secondaryOptions && secondaryOptions.disableFix;
		const fix = context ? context.fix && !disableFix : false;
		root.walkComments((comment) => {
			if (comment.raws.inline) {
				if (BLANK_REGEX.test(comment.text)) {
					const report = (message) => {
						stylelint.utils.report({
							message,
							node: comment,
							result,
							ruleName,
						});
					};
					const prev = comment.prev();
					const next = comment.next();
					if (isLeadingComment(comment, prev)) {
						if (fix) {

							// PostCSS will kill preceding blank lines,
							// so we have to reattach them to the next
							// node.

							const blanks = comment.raws.before.match(/^\n+/);
							if (blanks && next) {
								next.raws.before = '\n' + next.raws.before;
							}
							comment.remove();
						}
						else {
							report(messages.noLeadingBlanks);
						}
					}
					if (isTrailingComment(comment, next)) {
						if (fix) {

							// PostCSS doesn't touch following blank lines.

							comment.remove();
						}
						else {
							report(messages.noTrailingBlanks);
						}
					}
				}
			}
		});
	};
};

rule.ruleName = ruleName;
rule.messages = messages;

module.exports = rule;

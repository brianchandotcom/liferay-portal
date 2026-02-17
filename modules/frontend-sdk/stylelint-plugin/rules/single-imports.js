/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const stylelint = require('stylelint');

const ruleName = 'liferay/single-imports';

const messages = stylelint.utils.ruleMessages(ruleName, {
	single: 'one import rule per resource',
});

const MATCHERS = Object.entries({
	DOUBLE_QUOTED_STRING: /^"[^"]*"/,
	SEPARATOR: /^,/,
	SINGLE_QUOTED_STRING: /^'[^']*'/,
	URL: /^url\s*\([^)]*\)/,
	WHITESPACE: /^\s+/,
});

function tokenize(params) {
	const tokens = [];

	// Slow but simple iteration through string.

	let i = 0;
	for (; i < params.length; ) {
		const remaining = params.slice(i);
		const matched = MATCHERS.some(([kind, matcher]) => {
			const match = matcher.exec(remaining);
			if (match) {
				const text = match[0];
				tokens.push({
					kind,
					text: match[0],
				});
				i += text.length;

				return true;
			}
		});
		if (!matched) {
			throw new Error(
				`Unable to tokenize ${JSON.stringify(params)} at index ${i}`
			);
		}
	}

	return tokens;
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
		root.walkAtRules('import', (rule) => {
			const tokens = tokenize(rule.params);
			const resources = tokens.filter((token) => {
				return (
					token.kind === 'DOUBLE_QUOTED_STRING' ||
					token.kind === 'SINGLE_QUOTED_STRING' ||
					token.kind === 'URL'
				);
			});
			if (resources.length > 1) {
				if (fix) {
					const replacements = resources.map((resource) =>
						rule.clone({
							params: resource.text,
						})
					);
					rule.replaceWith(...replacements);

					// If the original rule had a blank line before it, we
					// must trim the blank from all but the first
					// replacement.
					//
					// Note that changes to "raws" have to be made after
					// the `replaceWith` call, or they won't have any
					// effect.

					const trailing = /(.*?)(\n+)([ \t]*)$/.exec(
						rule.raws.before
					);
					replacements.forEach((resource, i) => {
						if (i) {
							if (trailing && trailing[2].length > 1) {
								resource.raws.before =
									trailing[1] +
									trailing[2].slice(1) +
									trailing[3];
							}
						}
					});
				}
				else {
					stylelint.utils.report({
						message: messages.single,
						node: rule,
						result,
						ruleName,
					});
				}
			}
		});
	};
};

rule.ruleName = ruleName;
rule.messages = messages;

module.exports = rule;

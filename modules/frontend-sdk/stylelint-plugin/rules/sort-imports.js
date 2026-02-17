/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const stylelint = require('stylelint');

const ruleName = 'liferay/sort-imports';

const messages = stylelint.utils.ruleMessages(ruleName, {
	sort: 'imports must be sorted by target name',
});

function precededByBlankLine(node) {
	const match = node.raws.before.match(/\n/g);

	return match && match.length > 1;
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
		const imports = [];
		let group;
		root.walkAtRules('import', (rule) => {
			const prev = rule.prev();
			if (!prev || prev.type !== 'atrule' || precededByBlankLine(rule)) {
				group = [];
				imports.push(group);
			}
			group.push(rule);
		});
		for (let i = 0; i < imports.length; i++) {
			const group = imports[i];
			for (let j = 0; j < group.length; j++) {
				const desired = [...group].sort((a, b) => {
					const aName = a.params.slice(1, -1);
					const bName = b.params.slice(1, -1);
					if (aName < bName) {
						return -1;
					}
					else if (aName > bName) {
						return 1;
					}
					else {
						return 0;
					}
				});

				// Try to make error messages (somewhat) minimal
				// by only reporting from the first to the last
				// mismatch (ie. not a full Myers diff algorithm).

				const [firstMismatch, lastMismatch] = group.reduce(
					([first, last], node, index) => {
						if (node !== desired[index]) {
							if (first === -1) {
								first = index;
							}
							last = index;
						}

						return [first, last];
					},
					[-1, -1]
				);
				if (firstMismatch !== -1) {
					if (fix) {
						for (let k = firstMismatch; k <= lastMismatch; k++) {
							group[k].replaceWith(
								group[k].clone({
									params: desired[k].params,
								})
							);
						}
					}
					else {
						const order = desired
							.slice(firstMismatch, lastMismatch + 1)
							.map((node) => node.params)
							.join(' << ');
						stylelint.utils.report({
							message: `${messages.sort} (expected: ${order})`,
							node: group[firstMismatch],
							result,
							ruleName,
						});
					}
				}
			}
		}
	};
};

rule.ruleName = ruleName;
rule.messages = messages;

module.exports = rule;

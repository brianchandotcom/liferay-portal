/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const stylelint = require('stylelint');

const ruleName = 'liferay/no-block-comments';

const messages = stylelint.utils.ruleMessages(ruleName, {
	expected:
		'No block-based comments (/* ... */); use line-based (//) comment syntax instead',
});

module.export = {
	messages,
	ruleName,
};

module.exports = stylelint.createPlugin(ruleName, (actual) => {
	return function (root, result) {
		const validOptions = stylelint.utils.validateOptions(result, ruleName, {
			actual,
		});

		// .css files don't support line-based '//' comments

		if (root.source.input.file.endsWith('.css')) {
			return;
		}

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
});

/**
 * SPDX-FileCopyrightText: © 2021 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const message = 'Expected an empty line after the copyright notice.';

module.exports = {
	create(context) {
		return {
			Program() {
				const comments = context.getSourceCode().getAllComments();

				const copyrightComment = comments.find((item) =>
					item.value.match('SPDX-FileCopyrightText:')
				);

				if (!copyrightComment) {
					return;
				}

				const endLine = copyrightComment.loc.end.line;

				const firstNode = context.getSourceCode().ast.body[0];

				if (firstNode && firstNode.loc.start.line === endLine + 1) {
					context.report({
						fix: (fixer) => {
							return fixer.insertTextAfter(
								copyrightComment,
								'\n'
							);
						},
						message,
						node: firstNode,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: message,
			recommended: false,
		},
		fixable: 'code',
		schema: [],
		type: 'problem',
	},
};

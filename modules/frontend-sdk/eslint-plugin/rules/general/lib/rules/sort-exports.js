/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const {
	getLeadingComments,
	getTrailingComments,
	isAbsolute,
	isRelative,
	withScope,
} = require('../common/imports');

const DESCRIPTION = 'exports must be sorted';

/**
 * Given two sort keys `a` and `b`, return -1, 0 or 1 to indicate
 * their relative ordering.
 */
function compare(aKey, bKey) {
	const [aPrefix, aName, aTieBreaker] = aKey.split(':');
	const [bPrefix, bName, bTieBreaker] = bKey.split(':');

	const cmp = (a, b) => {
		return a < b ? -1 : a > b ? 1 : 0;
	};

	return (
		cmp(aPrefix, bPrefix) ||
		cmp(ranking(aName), ranking(bName)) ||
		cmp(aName, bName) ||
		cmp(aTieBreaker, bTieBreaker)
	);
}

/**
 * Returns a ranking for `source`. Lower-numbered ranks are considered more
 * important and will be sorted first in the file.
 *
 * - 0: NodeJS built-ins and dependencies declared in "package.json" files.
 * - 1: Absolute paths.
 * - 2: Relative paths.
 */
function ranking(source) {
	return isRelative(source) ? 2 : isAbsolute(source) ? 1 : 0;
}

module.exports = {
	create(context) {
		const exportNodes = [];

		const {visitors} = withScope();

		function getRangeForNode(node) {
			const commentsBefore = getLeadingComments(node, context);
			const commentsAfter = getTrailingComments(node, context);

			const first = commentsBefore[0] || node;

			const last = commentsAfter[commentsAfter.length - 1] || node;

			return [first.range[0], last.range[1]];
		}

		return {
			...visitors,
			ExportNamedDeclaration(node) {

				/**
				 * Only sort exports if they have a source. Skip exports like:
				 *       export function Foo() {}
				 *       export default function Bar() {}
				 *       export {Baz};
				 */
				if (node.source) {
					exportNodes.push(node);
				}
			},

			['Program:exit'](_node) {
				const desired = [...exportNodes].sort((a, b) =>
					compare(a.source.value, b.source.value)
				);

				// Try to make error messages (somewhat) minimal by only
				// reporting from the first to the last mismatch (ie.
				// not a full Myers diff algorithm).

				let firstMismatch = -1;
				let lastMismatch = -1;

				for (let i = 0; i < exportNodes.length; i++) {
					if (exportNodes[i] !== desired[i]) {
						firstMismatch = i;
						break;
					}
				}

				for (let i = exportNodes.length - 1; i >= 0; i--) {
					if (exportNodes[i] !== desired[i]) {
						lastMismatch = i;
						break;
					}
				}

				if (firstMismatch === -1) {
					return;
				}

				const description = desired
					.slice(firstMismatch, lastMismatch + 1)
					.map((node) => {
						const source = JSON.stringify(node.source.value);

						return source;
					})
					.join(' << ');

				const message =
					'exports must be sorted by module name ' +
					`(expected: ${description})`;

				context.report({
					fix: (fixer) => {
						const fixings = [];

						const code = context.getSourceCode();

						const sources = new Map();

						// Pass 1: Extract copy of text.

						for (let i = firstMismatch; i <= lastMismatch; i++) {
							const node = exportNodes[i];
							const range = getRangeForNode(node);
							const text = code.getText().slice(...range);

							sources.set(exportNodes[i], {text});
						}

						// Pass 2: Write text into expected positions.

						for (let i = firstMismatch; i <= lastMismatch; i++) {
							fixings.push(
								fixer.replaceTextRange(
									getRangeForNode(exportNodes[i]),
									sources.get(desired[i]).text
								)
							);
						}

						return fixings;
					},
					message,
					node: exportNodes[firstMismatch],
				});
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
		},
		fixable: 'code',
		schema: [],
		type: 'problem',
	},
};

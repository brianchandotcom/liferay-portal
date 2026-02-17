/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION =
	'Direct usage of `document.cookie` is discouraged in favour of our wrapped version that checks user consent status; import `[get|set|remove]Cookie` from frontend-js-web instead or use the global `Liferay.Util.Cookie`.';

module.exports = {
	create(context) {
		const isDocumentCookie = (node) =>
			node.object.name === 'document' && node.property.name === 'cookie';

		return {
			MemberExpression(node) {
				if (isDocumentCookie(node)) {
					context.report({
						messageId: 'noDocumentCookie',
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
			url: 'https://issues.liferay.com/browse/IFI-3334',
		},
		fixable: null,
		messages: {
			noDocumentCookie: DESCRIPTION,
		},
		schema: [],
		type: 'problem',
	},
};

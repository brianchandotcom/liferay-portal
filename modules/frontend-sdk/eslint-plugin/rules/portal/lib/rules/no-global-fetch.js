/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION =
	'Direct usage of the `fetch` API is discouraged in favour of our wrapped version that takes care of the necessary security configuration; import fetch from frontend-js-web instead';

module.exports = {
	create(context) {
		let foundFetchImport = false;

		const isFetchIdentifier = (node) => {
			return node.type === 'Identifier' && node.name === 'fetch';
		};

		const isValidDefaultImport = (node) => {
			return (
				(node.source.value.endsWith('/fetch') ||
					node.source.value.endsWith('/fetch.es')) &&
				node.specifiers.find(
					(specifier) => specifier.type === 'ImportDefaultSpecifier'
				)
			);
		};

		const isValidNamedImport = (node) => {
			return (
				node.source.value === 'frontend-js-web' &&
				node.specifiers.find((specifier) => {
					return (
						specifier.imported &&
						specifier.imported.name === 'fetch'
					);
				})
			);
		};

		return {
			CallExpression(node) {
				if (isFetchIdentifier(node.callee) && !foundFetchImport) {
					context.report({
						messageId: 'noGlobalFetch',
						node,
					});
				}
			},

			ImportDeclaration(node) {
				if (
					node.source &&
					node.source.type === 'Literal' &&
					(isValidDefaultImport(node) || isValidNamedImport(node))
				) {
					foundFetchImport = true;
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
			url: 'https://issues.liferay.com/browse/LPS-98283',
		},
		fixable: null,
		messages: {
			noGlobalFetch: DESCRIPTION,
		},
		schema: [],
		type: 'problem',
	},
};

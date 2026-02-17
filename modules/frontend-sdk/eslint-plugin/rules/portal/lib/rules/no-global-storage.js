/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION =
	'Direct usage of the Web Storage APIs is discouraged in favour of our wrapped version that checks user consent status; import `localStorage` or `sessionStorage` from frontend-js-web instead';

module.exports = {
	create(context) {
		let foundStorageImport = false;

		const isStorageIdentifier = (node) => {
			return (
				node.type === 'Identifier' &&
				(node.name === 'localStorage' || node.name === 'sessionStorage')
			);
		};

		const isValidDefaultImport = (node) => {
			return (
				(node.source.value.endsWith('/local_storage') ||
					node.source.value.endsWith('/session_storage')) &&
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
						(specifier.imported.name === 'localStorage' ||
							specifier.imported.name === 'sessionStorage')
					);
				})
			);
		};

		return {
			Identifier(node) {
				if (isStorageIdentifier(node) && !foundStorageImport) {
					context.report({
						messageId: 'noGlobalStorage',
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
					foundStorageImport = true;
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
			url: 'https://issues.liferay.com/browse/LPS-155901',
		},
		fixable: null,
		messages: {
			noGlobalStorage: DESCRIPTION,
		},
		schema: [],
		type: 'problem',
	},
};

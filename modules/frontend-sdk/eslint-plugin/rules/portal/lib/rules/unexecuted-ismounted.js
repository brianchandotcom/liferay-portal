/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const DESCRIPTION = '"isMounted" is a function that returns a boolean value';

const parentTypes = [
	'ConditionalExpression',
	'LogicalExpression',
	'IfStatement',
	'WhileStatement',
];

module.exports = {
	create(context) {
		let foundFrontendJSReactWebImport = false;
		let useIsMountedIsCalled = false;

		return {
			CallExpression(node) {
				if (
					node.callee.name === 'useIsMounted' &&
					node.parent.type === 'VariableDeclarator' &&
					foundFrontendJSReactWebImport
				) {
					useIsMountedIsCalled = true;
				}
			},
			Identifier(node) {
				if (
					node.name === 'isMounted' &&
					useIsMountedIsCalled &&
					parentTypes.includes(node.parent.type)
				) {
					context.report({
						message: DESCRIPTION,
						node,
					});
				}
			},
			ImportDeclaration(node) {
				if (
					node.source &&
					node.source.type === 'Literal' &&
					node.source.value === '@liferay/frontend-js-react-web'
				) {
					foundFrontendJSReactWebImport = true;
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Best Practices',
			description: DESCRIPTION,
			recommended: false,
			url:
				'https://github.com/liferay/liferay-frontend-projects/issues/13',
		},
		fixable: null,
		schema: [],
		type: 'problem',
	},
};

/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

module.exports = {
	create(context) {
		return {
			CallExpression(node) {
				if (
					node.callee.type === 'MemberExpression' &&
					node.callee.property &&
					node.callee.property.type === 'Identifier' &&
					node.callee.property.name === 'sideNavigation'
				) {
					context.report({
						messageId: 'noSideNavigation',
						node,
					});
				}
			},

			JSXAttribute(node) {
				if (
					node.type === 'JSXAttribute' &&
					node.name.type === 'JSXIdentifier' &&
					node.name.name === 'data-toggle' &&
					node.value &&
					node.value.type === 'Literal' &&
					typeof node.value.value === 'string' &&
					node.value.value.match(/^\s*sidenav\s*$/)
				) {
					context.report({
						messageId: 'noDataToggleSidenav',
						node,
					});
				}
			},
		};
	},

	meta: {
		docs: {
			category: 'Deprecated APIs',
			description:
				'jQuery `sideNavigation` plugin is deprecated; use Liferay.SideNavigation instead',
			recommended: false,
			url: 'https://issues.liferay.com/browse/LPS-96486',
		},
		fixable: null,
		messages: {
			noDataToggleSidenav:
				'data-toggle="sidenav" is deprecated; use data-toggle="liferay-sidenav" instead',
			noSideNavigation:
				'sideNavigation() is deprecated; use Liferay.SideNavigation() instead',
		},
		schema: [],
		type: 'problem',
	},
};

/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-side-navigation');

const parserOptions = {
	ecmaFeatures: {
		jsx: true,
	},
};

const ruleTester = new MultiTester({parserOptions});

ruleTester.run('no-side-navigation', rule, {
	invalid: [
		{
			code: '$(toggler).sideNavigation()',
			errors: [
				{
					messageId: 'noSideNavigation',
					type: 'CallExpression',
				},
			],
		},
		{
			code: "$(toggler).sideNavigation('hide')",
			errors: [
				{
					messageId: 'noSideNavigation',
					type: 'CallExpression',
				},
			],
		},
		{
			code: '<span data-toggle="sidenav"></span>',
			errors: [
				{
					messageId: 'noDataToggleSidenav',
					type: 'JSXAttribute',
				},
			],
		},
		{
			code: '<span data-toggle="    sidenav    "></span>',
			errors: [
				{
					messageId: 'noDataToggleSidenav',
					type: 'JSXAttribute',
				},
			],
		},
	],

	valid: [
		{
			code: 'Liferay.SideNavigation.intialize(toggler)',
		},
		{
			code: 'Liferay.SideNavigation.hide(toggler)',
		},
		{
			code: '<span data-toggle="liferay-sidenav"></span>',
		},
	],
});

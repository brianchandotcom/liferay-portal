/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/no-dynamic-require');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

ruleTester.run('no-dynamic-require', rule, {
	invalid: [
		{
			code: `
				const thing = require(\`./plugins/\${myPlugin}\`);
				const other = require('./plugins/' + otherPlugin);
				const missing = require();
				const tagged = require(foo\`./stuff\`);
			`,
			errors: [
				{
					message: 'require() arguments should be static',
					type: 'TemplateLiteral',
				},
				{
					message: 'require() arguments should be static',
					type: 'BinaryExpression',
				},
				{
					message: 'require() arguments should be static',
					type: 'CallExpression',
				},
				{
					message: 'require() arguments should be static',
					type: 'TaggedTemplateExpression',
				},
			],
		},
	],

	valid: [
		{
			code: `
				const thing = require(\`./plugins/thing\`);
				const other = require('./plugins/other');
			`,
		},
	],
});

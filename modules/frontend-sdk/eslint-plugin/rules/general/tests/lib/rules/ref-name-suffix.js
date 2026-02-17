/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/ref-name-suffix');

const parserOptions = {
	parserOptions: {
		ecmaVersion: 6,
		sourceType: 'module',
	},
};

const ruleTester = new MultiTester(parserOptions);

const message = 'useRef values should be suffixed with `Ref`';

ruleTester.run('ref-name-suffix', rule, {
	invalid: [
		{
			code: `
				const node = useRef(null);

				node.current = 'test';

				node.current = 'foo';
			 `,
			errors: [
				{
					message,
					type: 'Identifier',
				},
				{
					message: 'ref variable (renamed to nodeRef)',
					type: 'Identifier',
				},
				{
					message: 'ref variable (renamed to nodeRef)',
					type: 'Identifier',
				},
			],
			output: `
				const nodeRef = useRef(null);

				nodeRef.current = 'test';

				nodeRef.current = 'foo';
			 `,
		},

		{
			code: `
				const node = React.useRef(null);
			 `,
			errors: [
				{
					message,
					type: 'Identifier',
				},
			],
			output: `
				const nodeRef = React.useRef(null);
			 `,
		},
	],

	valid: [
		{
			code: "variableName !== 'ref'",
		},
		{
			code: 'const nodeRef = useRef(null);',
		},
		{
			code: 'const nodeRef = React.useRef(null);',
		},
	],
});

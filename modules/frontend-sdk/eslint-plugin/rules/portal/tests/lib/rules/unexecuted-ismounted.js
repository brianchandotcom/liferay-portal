/**
 * SPDX-FileCopyrightText: © 2017 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

const MultiTester = require('../../../../../scripts/MultiTester');
const rule = require('../../../lib/rules/unexecuted-ismounted');

const parserOptions = {
	ecmaVersion: 6,
	sourceType: 'module',
};

const ruleTester = new MultiTester({parserOptions});

ruleTester.run('unexecuted-ismounted', rule, {
	invalid: [
		{
			code: `
				import {useIsMounted} from '@liferay/frontend-js-react-web';

				const isMounted = useIsMounted();

				if (isMounted) {}
			`,
			errors: [
				{
					message:
						'"isMounted" is a function that returns a boolean value',
					type: 'Identifier',
				},
			],
		},
	],

	valid: [
		{
			code: `
			import {useIsMounted} from '@liferay/frontend-js-react-web';

			const isMounted = useIsMounted();

			if (isMounted()) {}
		`,
		},
	],
});

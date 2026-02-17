/**
 * SPDX-FileCopyrightText: © 2020 Liferay, Inc. <https://liferay.com>
 * SPDX-License-Identifier: MIT
 */

import assert from 'node:assert';
import {describe, test} from 'node:test';
import {format} from 'prettier';

import * as liferayPrettierPlugin from '../index.mjs';

const baseConfig = {
	bracketSpacing: false,
	plugins: [liferayPrettierPlugin],
	singleQuote: true,
	tabWidth: 4,
	useTabs: true,
};

const babelConfig = {
	...baseConfig,
	parser: 'babel',
};

const tsConfig = {
	...baseConfig,
	parser: 'typescript',
};

const fixtures = [
	{
		_name: 'if/else newline',
		code: `if (true) {
}    else           {

}`,
		expected: `if (true) {
}
else {
}
`,
	},
	{
		_name: 'if/elseif/else newline',
		code: `if (foo) {
	if (foo) {
	} else if (bar) {
	} else if (baz) {
	} else {
	}
} else {}
`,
		expected: `if (foo) {
	if (foo) {
	}
	else if (bar) {
	}
	else if (baz) {
	}
	else {
	}
}
else {
}
`,
	},
	{
		_name: 'multiple nested if/else',
		code: `if (1) {
	if (2) {
		if (3) {
		} else {}
	} else if (4) {
		if (5) {
		} else {}
	}
	if (6) {
	} else {}
}
`,
		expected: `if (1) {
	if (2) {
		if (3) {
		}
		else {
		}
	}
	else if (4) {
		if (5) {
		}
		else {
		}
	}
	if (6) {
	}
	else {
	}
}
`,
	},
	{
		_name: 'try/catch newline',
		code: `if (true) {
	try {
	} catch {
	}
}
`,
		expected: `if (true) {
	try {
	}
	catch {}
}
`,
	},
	{
		_name: 'try/finally newline',
		code: `try {
} finally {
}`,
		expected: `try {
}
finally {
}
`,
	},
	{
		_name: 'try/catch/finally newline1',
		code: `try {
} catch {} finally {
}`,
		expected: `try {
}
catch {
}
finally {
}
`,
	},
	{
		_name: 'try/catch/finally newline2',
		code: `try {
			} catch {}

try {
} catch {} finally {
}`,
		expected: `try {
}
catch {}

try {
}
catch {
}
finally {
}
`,
	},
	{
		_name: 'kitchen sink',
		code: `if (true) {
	try {
	} catch {
	}

	if (true) {
	} else {
	}

	try {
	} catch {}

	try {
	} catch {}
	finally {}
}
`,
		expected: `if (true) {
	try {
	}
	catch {}

	if (true) {
	}
	else {
	}

	try {
	}
	catch {}

	try {
	}
	catch {
	}
	finally {
	}
}
`,
	},
	{
		_name: 'long conditional for if/else',
		code: `if (
			AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA && BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
) {
} else {
}
`,
		expected: `if (
	AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA &&
	BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB
) {
}
else {
}
`,
	},

	{
		_name: 'Works when the file contains a literal newline character',
		code: `if (true) {var foo = '\\n'}`,
		expected: `if (true) {
	var foo = '\\n';
}
`,
	},

	{
		_name: 'Works with "else if"',
		code: `if (true) {
} else if (false) {
}
`,
		expected: `if (true) {
}
else if (false) {
}
`,
	},

	{
		_name: 'Works with "else if"',
		code: `if (1) {} else if (2) {} else if (3) {}`,
		expected: `if (1) {
}
else if (2) {
}
else if (3) {
}
`,
	},
	{
		_name: 'Works with "else if"',
		code: `if (1) {} else if (2) {} else if (3) {}`,
		expected: `if (1) {
}
else if (2) {
}
else if (3) {
}
`,
	},
];

describe('babel', () => {
	fixtures.forEach((fixture) => {
		const {_config = {}, _name, code, expected, ...testConfig} = fixture;

		test(_name, testConfig, async () => {
			assert.equal(
				await format(code, {
					...babelConfig,
					..._config,
				}),
				expected
			);
		});
	});
});

describe('typescript', () => {
	fixtures.forEach((fixture) => {
		const {_config = {}, _name, code, expected, ...testConfig} = fixture;

		test(_name, testConfig, async () => {
			assert.equal(
				await format(code, {...tsConfig, ..._config}),
				expected
			);
		});
	});
});

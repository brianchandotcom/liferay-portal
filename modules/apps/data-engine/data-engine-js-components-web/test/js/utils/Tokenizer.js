/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Token} from '../../../src/main/resources/META-INF/resources/js/utils/Token';
import {Tokenizer} from '../../../src/main/resources/META-INF/resources/js/utils/Tokenizer';

describe('Tokenizer', () => {
	it('tokenizes single digit expressions', () => {
		expect(Tokenizer.tokenize('4[a] + 1')).toEqual([
			new Token('Literal', '4'),
			new Token('Operator', '*'),
			new Token('Variable', 'a'),
			new Token('Operator', '+'),
			new Token('Literal', '1'),
		]);
	});

	it('tokenizes multiple digit expressions', () => {
		expect(Tokenizer.tokenize('456[a] + 125')).toEqual([
			new Token('Literal', '456'),
			new Token('Operator', '*'),
			new Token('Variable', 'a'),
			new Token('Operator', '+'),
			new Token('Literal', '125'),
		]);
	});

	it('tokenizes floating point expressions', () => {
		expect(Tokenizer.tokenize('7.346 + 13.44 * 567')).toEqual([
			new Token('Literal', '7.346'),
			new Token('Operator', '+'),
			new Token('Literal', '13.44'),
			new Token('Operator', '*'),
			new Token('Literal', '567'),
		]);
	});

	it('tokenizes multiple letter variables', () => {
		expect(Tokenizer.tokenize('[Field1] * [Field2] + [Field3]')).toEqual([
			new Token('Variable', 'Field1'),
			new Token('Operator', '*'),
			new Token('Variable', 'Field2'),
			new Token('Operator', '+'),
			new Token('Variable', 'Field3'),
		]);
	});

	it('tokenizes expressions with functions', () => {
		expect(Tokenizer.tokenize('2 * 5 + sum([Field2])')).toEqual([
			new Token('Literal', '2'),
			new Token('Operator', '*'),
			new Token('Literal', '5'),
			new Token('Operator', '+'),
			new Token('Function', 'sum'),
			new Token('Left Parenthesis', '('),
			new Token('Variable', 'Field2'),
			new Token('Right Parenthesis', ')'),
		]);
	});
});

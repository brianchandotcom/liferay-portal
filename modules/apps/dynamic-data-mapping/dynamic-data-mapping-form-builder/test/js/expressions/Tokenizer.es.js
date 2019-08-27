/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import Token from '../../../src/main/resources/META-INF/resources/js/expressions/Token.es';
import Tokenizer from '../../../src/main/resources/META-INF/resources/js/expressions/Tokenizer.es';

describe('Tokenizer', () => {
	it('tokenizes single digit expressions', () => {
		expect(Tokenizer.tokenize('4[a] + 1')).toEqual([
			new Token('Literal', '4'),
			new Token('Operator', '*'),
			new Token('Variable', 'a'),
			new Token('Operator', '+'),
			new Token('Literal', '1')
		]);
	});

	it('tokenizes multiple digit expressions', () => {
		expect(Tokenizer.tokenize('456[a] + 125')).toEqual([
			new Token('Literal', '456'),
			new Token('Operator', '*'),
			new Token('Variable', 'a'),
			new Token('Operator', '+'),
			new Token('Literal', '125')
		]);
	});

	it('tokenizes floating point expressions', () => {
		expect(Tokenizer.tokenize('7.346 + 13.44 * 567')).toEqual([
			new Token('Literal', '7.346'),
			new Token('Operator', '+'),
			new Token('Literal', '13.44'),
			new Token('Operator', '*'),
			new Token('Literal', '567')
		]);
	});

	it('tokenizes multiple letter variables', () => {
		expect(Tokenizer.tokenize('[Field1] * [Field2] + [Field3]')).toEqual([
			new Token('Variable', 'Field1'),
			new Token('Operator', '*'),
			new Token('Variable', 'Field2'),
			new Token('Operator', '+'),
			new Token('Variable', 'Field3')
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
			new Token('Right Parenthesis', ')')
		]);
	});
});

import Token from 'source/expressions/Token.es';
import Tokenizer from 'source/expressions/Tokenizer.es';

describe('Tokenizer', () => {
	it('should tokenize single digit expressions', () => {
		expect(Tokenizer.tokenize('4[a] + 1')).toEqual([
			new Token('Literal', '4'),
			new Token('Operator', '*'),
			new Token('Variable', 'a'),
			new Token('Operator', '+'),
			new Token('Literal', '1')
		]);
	});

	it('should tokenize multiple digit expressions', () => {
		expect(Tokenizer.tokenize('456[a] + 125')).toEqual([
			new Token('Literal', '456'),
			new Token('Operator', '*'),
			new Token('Variable', 'a'),
			new Token('Operator', '+'),
			new Token('Literal', '125')
		]);
	});

	it('should tokenize floating point expressions', () => {
		expect(Tokenizer.tokenize('7.346 + 13.44 * 567')).toEqual([
			new Token('Literal', '7.346'),
			new Token('Operator', '+'),
			new Token('Literal', '13.44'),
			new Token('Operator', '*'),
			new Token('Literal', '567')
		]);
	});

	it('should tokenize multiple letter variables', () => {
		expect(Tokenizer.tokenize('[Field1] * [Field2] + [Field3]')).toEqual([
			new Token('Variable', 'Field1'),
			new Token('Operator', '*'),
			new Token('Variable', 'Field2'),
			new Token('Operator', '+'),
			new Token('Variable', 'Field3')
		]);
	});

	it('should tokenize expressions with functions', () => {
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

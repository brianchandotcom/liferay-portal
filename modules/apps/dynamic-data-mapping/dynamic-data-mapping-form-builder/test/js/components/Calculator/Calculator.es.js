import '../../__fixtures__/MockField.es';
import Calculator from 'source/components/Calculator/Calculator.es';
import Token from 'source/expressions/Token.es';

let component;

const fields = [
	{
		dataType: 'number',
		fieldName: 'option1repeatablefieldName',
		name: 'option1 repeatable',
		options: [],
		repeatable: true,
		title: 'option1repeatablefieldName',
		type: 'checkbox',
		value: 'option1repeatablefieldName'
	},
	{
		dataType: 'number',
		fieldName: 'option1nonrepeatablefieldName',
		name: 'option1',
		options: [],
		repeatable: false,
		title: 'option1nonrepeatablefieldName',
		type: 'checkbox',
		value: 'option1nonrepeatablefieldName'
	}
];

const spritemap = 'icons.svg';

const getBaseConfig = () => ({
	expression: '',
	fields,
	functions: [
		{
			label: 'sum',
			tooltip: '',
			value: 'sum'
		}
	],
	index: 0,
	spritemap
});

describe('Calculator', () => {
	describe('Acceptance Criteria', () => {
		afterEach(() => {
			component.dispose();
		});

		beforeEach(() => {
			jest.useFakeTimers();

			component = new Calculator(getBaseConfig());
		});

		describe('addTokenToExpression(tokenType, tokenValue)', () => {
			it('should add a token to an expression', () => {
				component.expression = '4*[Field1]+';

				component.addTokenToExpression(Token.VARIABLE, 'Field2');

				expect(component.expression).toEqual('4*[Field1]+[Field2]');
			});

			it('should add implicit multiplications to an expression when applicable', () => {
				component.expression = '1+[Field1]';

				component.addTokenToExpression(Token.VARIABLE, 'Field2');

				expect(component.expression).toEqual('1+[Field1]*[Field2]');
			});
		});

		describe('removeTokenFromExpression()', () => {
			it('should remove the last token from the expression', () => {
				component.expression = '4*[Field1]';

				component.removeTokenFromExpression();

				expect(component.expression).toEqual('4*');
			});

			it('should remove both the left parenthesis and the function when last tokens are an openning of a function', () => {
				component.expression = '1+sum(';

				component.removeTokenFromExpression();

				expect(component.expression).toEqual('1+');
			});
		});

		describe('getStateBasedOnExpression(expression)', () => {
			it('should disable functions, numbers and operators when last token is a sum function', () => {
				const result = component.getStateBasedOnExpression('4*sum(');

				expect(result.disableFunctions).toBe(true);
				expect(result.disableNumbers).toBe(true);
				expect(result.disableOperators).toBe(true);
			});

			it('should not disable functions, numbers and operators when last token is not a sum function', () => {
				const result = component.getStateBasedOnExpression(
					'4*sum(Field2)+4'
				);

				expect(result.disableFunctions).toBe(false);
				expect(result.disableNumbers).toBe(false);
				expect(result.disableOperators).toBe(false);
			});
		});

		describe('shouldAddImplicitMultiplication(tokens, newToken)', () => {
			it('should return true if new token is a Left Parenthesis and last token is a Literal', () => {
				const tokens = [new Token(Token.LITERAL, '5')];

				const result = component.shouldAddImplicitMultiplication(
					tokens,
					new Token(Token.LEFT_PARENTHESIS, '(')
				);

				expect(result).toEqual(true);
			});

			it('should return true if new token is a Left Parenthesis and last token is a Variable', () => {
				const tokens = [new Token(Token.VARIABLE, 'Field1')];

				const result = component.shouldAddImplicitMultiplication(
					tokens,
					new Token(Token.LEFT_PARENTHESIS, '(')
				);

				expect(result).toEqual(true);
			});

			it('should return true if new token is a Function and last token is a Variable', () => {
				const tokens = [new Token(Token.VARIABLE, 'Field1')];

				const result = component.shouldAddImplicitMultiplication(
					tokens,
					new Token(Token.FUNCTION, 'sum')
				);

				expect(result).toEqual(true);
			});

			it('should return true if new token is a Variable and last token is a Variable', () => {
				const tokens = [new Token(Token.VARIABLE, 'Field1')];

				const result = component.shouldAddImplicitMultiplication(
					tokens,
					new Token(Token.VARIABLE, 'Field2')
				);

				expect(result).toEqual(true);
			});

			it('should return true if new token is a Variable and last token is a Literal', () => {
				const tokens = [new Token(Token.LITERAL, '345')];

				const result = component.shouldAddImplicitMultiplication(
					tokens,
					new Token(Token.VARIABLE, 'Field1')
				);

				expect(result).toEqual(true);
			});

			it('should return true if new token is a Literal and last token is a Variable', () => {
				const tokens = [new Token(Token.VARIABLE, 'Field1')];

				const result = component.shouldAddImplicitMultiplication(
					tokens,
					new Token(Token.LITERAL, '111')
				);

				expect(result).toEqual(true);
			});

			it('should return true if new token is a Literal and last token is a Function', () => {
				const tokens = [new Token(Token.FUNCTION, 'sum')];

				const result = component.shouldAddImplicitMultiplication(
					tokens,
					new Token(Token.LITERAL, '111')
				);

				expect(result).toEqual(true);
			});
		});
	});
});

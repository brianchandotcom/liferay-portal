import Token from './Token.es';

const OPERATORS = ['*', '/', '+', '-'];

/**
 * Tokenizer.
 * Transforms an expression into tokens and token into an expression
 */
class Tokenizer {
	static stringifyTokens(tokens) {
		return tokens.reduce((expression, token) => {
			let {value} = token;

			if (token.type === Token.VARIABLE) {
				value = `[${value}]`;
			}

			return expression + value;
		}, '');
	}

	static tokenize(str) {
		const result = [];

		str = str.replace(/\s/g, '');

		let functionBuffer = [];
		let numberBuffer = [];
		let variableBuffer = [];

		const emptyNumberBuffer = () => {
			if (numberBuffer.length) {
				result.push(new Token(Token.LITERAL, numberBuffer.join('')));
				numberBuffer = [];
			}
		};

		const emptyFunctionBuffer = () => {
			result.push(new Token(Token.FUNCTION, functionBuffer.join('')));
			functionBuffer = [];
		};

		const emptyVariableBuffer = () => {
			result.push(new Token(Token.VARIABLE, variableBuffer.join('')));
			variableBuffer = [];
		};

		const inputBuffer = str.split('');

		while (inputBuffer.length) {
			let char = inputBuffer.shift();

			if (this.isDigit(char)) {
				numberBuffer.push(char);
			} else if (char === '.') {
				numberBuffer.push(char);
			} else if (this.isLeftBracket(char)) {
				if (numberBuffer.length) {
					emptyNumberBuffer();

					result.push(new Token(Token.OPERATOR, '*'));
				}

				do {
					char = inputBuffer.shift();

					if (this.isRightBracket(char)) {
						emptyVariableBuffer();

						break;
					} else {
						variableBuffer.push(char);
					}
				} while (inputBuffer.length);
			} else if (this.isLetter(char)) {
				if (numberBuffer.length) {
					emptyNumberBuffer();

					result.push(new Token(Token.OPERATOR, '*'));
				}

				do {
					functionBuffer.push(char);

					char = inputBuffer.shift();
				} while (this.isLetter(char));

				if (char !== undefined) {
					inputBuffer.unshift(char);
				}

				emptyFunctionBuffer();
			} else if (this.isOperator(char)) {
				emptyNumberBuffer();

				result.push(new Token(Token.OPERATOR, char));
			} else if (this.isLeftParenthesis(char)) {
				if (numberBuffer.length) {
					emptyNumberBuffer();

					result.push(new Token(Token.OPERATOR, '*'));
				}

				result.push(new Token(Token.LEFT_PARENTHESIS, char));
			} else if (this.isRightParenthesis(char)) {
				emptyNumberBuffer();

				result.push(new Token(Token.RIGHT_PARENTHESIS, char));
			} else {
				throw new Error(`Unsupported character ${char}`);
			}
		}

		if (numberBuffer.length) {
			emptyNumberBuffer();
		}
		if (variableBuffer.length) {
			emptyVariableBuffer();
		}

		return result;
	}

	static isDigit(char) {
		return char !== undefined && /[0-9]/.test(char);
	}

	static isLetter(char) {
		return char !== undefined && /[a-zA-Z]/.test(char);
	}

	static isLeftBracket(char) {
		return char === '[';
	}

	static isRightBracket(char) {
		return char === ']';
	}

	static isLeftParenthesis(char) {
		return char === '(';
	}

	static isRightParenthesis(char) {
		return char === ')';
	}

	static isOperator(char) {
		return OPERATORS.includes(char);
	}
}

export default Tokenizer;

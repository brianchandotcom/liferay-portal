/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Token} from './Token';

const OPERATORS = ['*', '/', '+', '-'];

/**
 * Tokenizer.
 * Transforms an expression into tokens and token into an expression
 */
export class Tokenizer {
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
			}
			else if (this.isDot(char)) {
				if (!numberBuffer.includes('.')) {
					numberBuffer.push(char);
				}
			}
			else if (this.isLeftBracket(char)) {
				if (numberBuffer.length) {
					emptyNumberBuffer();

					result.push(new Token(Token.OPERATOR, '*'));
				}

				do {
					char = inputBuffer.shift();

					if (this.isRightBracket(char)) {
						emptyVariableBuffer();

						break;
					}
					else {
						variableBuffer.push(char);
					}
				} while (inputBuffer.length);
			}
			else if (this.isLetter(char)) {
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
			}
			else if (this.isOperator(char)) {
				emptyNumberBuffer();

				result.push(new Token(Token.OPERATOR, char));
			}
			else if (this.isLeftParenthesis(char)) {
				if (numberBuffer.length) {
					emptyNumberBuffer();

					result.push(new Token(Token.OPERATOR, '*'));
				}

				result.push(new Token(Token.LEFT_PARENTHESIS, char));
			}
			else if (this.isRightParenthesis(char)) {
				emptyNumberBuffer();

				result.push(new Token(Token.RIGHT_PARENTHESIS, char));
			}
			else {
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
	static isDot(char) {
		return char === '.';
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

	static isValid(str) {
		const tokens = Tokenizer.tokenize(str);

		const leftParentheses = tokens.filter(
			({type}) => type === Token.LEFT_PARENTHESIS
		);
		const rightParentheses = tokens.filter(
			({type}) => type === Token.RIGHT_PARENTHESIS
		);
		const isTokensValid = tokens
			.map(({type}, index) => {
				if (type === Token.OPERATOR) {

					// Checks if there are any token on the left and right side
					// of the operator.

					return (
						Boolean(tokens[index - 1]) && Boolean(tokens[index + 1])
					);
				}

				if (type === Token.FUNCTION) {

					// Checks if there is any Token after the
					// Token.LEFT_PARENTHESIS.

					return Boolean(tokens[index + 2]);
				}

				return true;
			})
			.every((result) => result === true);

		return (
			leftParentheses.length === rightParentheses.length && isTokensValid
		);
	}
}

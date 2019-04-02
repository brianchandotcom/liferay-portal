/**
 * Token.
 */

class Token {
	static FUNCTION = 'Function';
	static LEFT_PARENTHESIS = 'Left Parenthesis';
	static LITERAL = 'Literal';
	static OPERATOR = 'Operator';
	static RIGHT_PARENTHESIS = 'Right Parenthesis';
	static VARIABLE = 'Variable';

	constructor(type, value) {
		this.type = type;
		this.value = value;
	}
}

export default Token;
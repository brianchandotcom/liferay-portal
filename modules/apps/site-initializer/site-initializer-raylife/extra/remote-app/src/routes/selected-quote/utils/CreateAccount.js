import {NUMBER_REGEX, SYMBOL_REGEX} from '~/common/utils/patterns';

import {
	CHECK_VALUE,
	INITIAL_VALIDATION,
	NATURAL_VALUE,
	UNCHECKED_VALUE,
} from '../components/Steps/CreateAnAccount/constants';

const getValueFromValidation = (condition) =>
	condition ? CHECK_VALUE : UNCHECKED_VALUE;

export const SendAccountRequest = (email, password) => {
	/* eslint-disable no-console */
	console.log(email, password);

	return CHECK_VALUE;
};

export function validadePassword(confirmPassword, password) {
	const rules = {...INITIAL_VALIDATION};

	if (confirmPassword && password) {
		if (password !== confirmPassword) {
			rules.samePassword = UNCHECKED_VALUE;
		} else {
			rules.samePassword =
				password === confirmPassword ? CHECK_VALUE : NATURAL_VALUE;
		}
	}

	if (password) {
		rules.qtdCharacter = getValueFromValidation(password.length >= 8);

		const uniqueValues = new Set();
		for (let i = 0; i < password.length; i++) {
			uniqueValues.add(password.charAt(i));
			if (uniqueValues.size >= 5) {
				rules.uniqueCharacter = CHECK_VALUE;
				break;
			} else {
				rules.uniqueCharacter = UNCHECKED_VALUE;
			}
		}

		const regexSymbol = new RegExp(SYMBOL_REGEX);

		rules.symbolCharacter = getValueFromValidation(
			regexSymbol.test(password)
		);

		const regexNumber = new RegExp(NUMBER_REGEX);

		rules.numberCharacter = getValueFromValidation(
			regexNumber.test(password)
		);

		rules.noSpace = getValueFromValidation(!password.includes(' '));
	}

	return rules;
}

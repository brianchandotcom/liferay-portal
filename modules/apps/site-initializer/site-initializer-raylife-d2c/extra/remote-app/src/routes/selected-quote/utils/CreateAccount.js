/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getItem} from '../../../common/services/liferay/storage';
import {NUMBER_REGEX, SYMBOL_REGEX} from '../../../common/utils/patterns';

import {
	CHECK_VALUE,
	INITIAL_VALIDATION,
	NATURAL_VALUE,
	UNCHECKED_VALUE,
} from '../components/Steps/CreateAnAccount/constants';
import {createUserAccount} from '../services/Account';

const getValueFromValidation = (condition) =>
	condition ? CHECK_VALUE : UNCHECKED_VALUE;

export async function SendAccountRequest(email, password, captcha) {
	const {
		basics: {businessInformation},
	} = JSON.parse(getItem('raylife-application-form'));

	const data = await createUserAccount(
		businessInformation.firstName,
		businessInformation.lastName,
		email,
		password,
		captcha
	);

	return data;
}

export function validadePassword(confirmPassword, password) {
	const rules = {...INITIAL_VALIDATION};

	if (confirmPassword && password) {
		if (password !== confirmPassword) {
			rules.samePassword = UNCHECKED_VALUE;
		}
		else {
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
			}
			else {
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

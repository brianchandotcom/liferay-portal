/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ellipsize from './ellipsize';

const _MAX_DELIMITED_NUMBER_LENGTH = 10;

const _THOUSANDS_DELIMITER_REGEX = /\B(?=(\d{3})+(?!\d))/g;

function getDelimiter(key, defaultValue) {
	const delimiter = Liferay.Language.get(key);

	if (delimiter === key) {
		return defaultValue;
	}

	return delimiter;
}

export function formatNumber(number, delimit) {
	let formattedNumber = number.toString();

	const formattedNumberParts = formattedNumber.split('.');

	const formattedDecimal = formattedNumberParts[1];

	const formattedInteger = formattedNumberParts[0].replace(
		_THOUSANDS_DELIMITER_REGEX,
		getDelimiter('thousands-delimiter', ',')
	);

	formattedNumber =
		formattedInteger +
		(formattedDecimal && Number(formattedDecimal) !== 0
			? getDelimiter('decimal-delimiter', '.') + formattedDecimal
			: '');

	if (delimit && formattedNumber.length > _MAX_DELIMITED_NUMBER_LENGTH) {
		formattedNumber = ellipsize(
			formattedNumber,
			_MAX_DELIMITED_NUMBER_LENGTH
		);
	}

	return formattedNumber;
}

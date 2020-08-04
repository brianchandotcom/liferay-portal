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

/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import numeral from 'numeral';

import ellipsize from './ellipsize.es';

const _MAX_DELIMITED_NUMBER_LENGTH = 10;

function getDelimiter(key, defaultValue) {
	const delimiter = Liferay.Language.get(key);

	if (delimiter == key) {
		return defaultValue;
	}

	return delimiter;
}

function setupLocale(locale) {
	numeral.register('locale', locale, {
		delimiters: {
			decimal: getDelimiter('decimal-delimiter', '.'),
			thousands: getDelimiter('thousands-delimiter', ','),
		},
	});

	numeral.locale(locale);
}

setupLocale(Liferay.ThemeDisplay.getLanguageId());

export function formatNumber(number, delimit) {
	let formattedNumber = number.toString();

	const formattedNumberParts = formattedNumber.split('.');

	const formattedDecimal = formattedNumberParts[1];

	const formattedInteger = formattedNumberParts[0].replace(
		/\B(?=(\d{3})+(?!\d))/g,
		getDelimiter('thousands-delimiter', ',')
	);

	formattedNumber =
		formattedInteger +
		(formattedDecimal && formattedDecimal != '0'
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

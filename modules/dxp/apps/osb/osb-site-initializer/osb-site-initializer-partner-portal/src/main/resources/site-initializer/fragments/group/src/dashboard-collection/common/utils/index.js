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

const naNToZero = (value) => (Number.isNaN(value) ? 0 : value);

const percentFormat = (newValue, odlValue) => (newValue / odlValue) * 100;

const currencyFormat = (value, currencyData = 'USD') => {
	let formatedValue = value;
	let valueSufix = '';

	if (value >= 1000000000) {
		formatedValue = parseFloat(value / 1000000000).toFixed(2);
		valueSufix = 'b';
	}
	else if (value >= 1000000) {
		formatedValue = parseFloat(value / 1000000).toFixed(2);
		valueSufix = 'm';
	}

	const formatCurrencies = (currencyData) => {
		return Intl.NumberFormat(Liferay.ThemeDisplay.getBCP47LanguageId(), {
			currency: currencyData,
			maximumFractionDigits: 2,
			minimumFractionDigits: 0,
			style: 'currency',
		});
	};

	return formatCurrencies(currencyData).format(formatedValue) + valueSufix;
};

export {naNToZero, percentFormat, currencyFormat};

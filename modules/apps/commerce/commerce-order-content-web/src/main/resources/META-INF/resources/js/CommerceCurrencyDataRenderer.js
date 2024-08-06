/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function CommerceCurrencyDataRenderer({
	itemData: {currencyCode, currencySymbol},
	value,
}) {
	const formattedValue = new Intl.NumberFormat(
		Liferay.ThemeDisplay.getBCP47LanguageId(),
		{
			currency: currencyCode ? currencyCode : 'USD',
			minimumFractionDigits: 2,
			style: 'currency',
		}
	).format(value);

	if (currencySymbol) {
		return formattedValue.replace(currencySymbol, currencySymbol + ' ');
	}

	return formattedValue;
}

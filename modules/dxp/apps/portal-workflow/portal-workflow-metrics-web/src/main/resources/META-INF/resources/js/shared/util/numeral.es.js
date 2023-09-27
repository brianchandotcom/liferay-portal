/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import numeral from 'numeral';

function setupLocale(locale) {
	numeral.register('locale', locale, {
		abbreviations: {
			billion: Liferay.Language.get('billion-abbreviation'),
			million: Liferay.Language.get('million-abbreviation'),
			thousand: Liferay.Language.get('thousand-abbreviation'),
			trillion: Liferay.Language.get('trillion-abbreviation'),
		},
		delimiters: {
			decimal: Liferay.Language.get('decimal-delimiter'),
			thousands: Liferay.Language.get('thousands-delimiter'),
		},
	});

	numeral.locale(locale);
}

setupLocale(Liferay.ThemeDisplay.getLanguageId());

export function formatNumber(number, pattern) {
	return numeral(number).format(pattern);
}

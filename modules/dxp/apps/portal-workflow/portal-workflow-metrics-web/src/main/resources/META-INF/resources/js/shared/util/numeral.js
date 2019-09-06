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

function setupLocale(locale) {
	numeral.register('locale', locale, {
		abbreviations: {
			billion: Liferay.Language.get('billion-abbreviation'),
			million: Liferay.Language.get('million-abbreviation'),
			thousand: Liferay.Language.get('thousand-abbreviation'),
			trillion: Liferay.Language.get('trillion-abbreviation')
		},
		delimiters: {
			decimal: Liferay.Language.get('decimal-delimiter'),
			thousands: Liferay.Language.get('thousands-delimiter')
		}
	});

	numeral.locale(locale);
}

setupLocale(Liferay.ThemeDisplay.getLanguageId());

export function formatNumber(number, pattern) {
	return numeral(number).format(pattern);
}

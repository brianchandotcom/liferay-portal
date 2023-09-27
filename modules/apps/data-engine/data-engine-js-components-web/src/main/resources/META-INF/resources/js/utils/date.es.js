/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from 'moment/min/moment-with-locales';

export function formatDate(date, locale) {
	const dateFormat = moment.localeData(locale).longDateFormat('L');

	return moment(date).format(dateFormat);
}

export function parseDate(locale, value) {
	const dateFormat = moment.localeData(locale).longDateFormat('L');

	return moment(value, dateFormat).toDate();
}

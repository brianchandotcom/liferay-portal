/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import moment from './moment.es';

const defaultDateFormat = 'YYYY-MM-DD\\THH:mm:ss\\Z';

const formatDate = (date, format = 'L', fromFormat = null) => {
	return moment(date, fromFormat).format(format);
};

const getLocaleDateFormat = (format = 'L') => {
	return moment.localeData().longDateFormat(format);
};

const getMaskByDateFormat = (format) => {
	const mask = [];

	for (let i = 0; i < format.length; i++) {
		if (/[a-z]/i.test(format[i])) {
			mask.push(/\d/);
		}
		else {
			mask.push(`${format[i]}`);
		}
	}

	return mask;
};

const isValidDate = (date, format = 'L') => {
	return moment(date, format, true).isValid();
};

export {
	defaultDateFormat,
	formatDate,
	getLocaleDateFormat,
	getMaskByDateFormat,
	isValidDate,
};

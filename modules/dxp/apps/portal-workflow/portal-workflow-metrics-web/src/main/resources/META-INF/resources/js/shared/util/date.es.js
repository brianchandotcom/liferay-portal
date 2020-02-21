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

import moment from './moment.es';

const defaultDateFormat = 'YYYY-MM-DD\\THH:mm:ss\\Z';

const formatDate = (date, format = 'L', fromFormat = null) => {
	return moment.utc(date, fromFormat).format(format);
};

const getLocaleDateFormat = (format = 'L') => {
	return moment.localeData().longDateFormat(format);
};

const getMaskByDateFormat = format => {
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
	return moment.utc(date, format, true).isValid();
};

export {
	defaultDateFormat,
	formatDate,
	getLocaleDateFormat,
	getMaskByDateFormat,
	isValidDate,
};

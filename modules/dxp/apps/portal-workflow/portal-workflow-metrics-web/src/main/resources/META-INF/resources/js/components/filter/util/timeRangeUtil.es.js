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

import {
	defaultDateFormat,
	formatDate,
	getLocaleDateFormat,
	isValidDate,
} from '../../../shared/util/date.es';
import moment from '../../../shared/util/moment.es';

const convertQueryDate = (date = '', format = 'L') => {
	return moment.utc(decodeURIComponent(date), null, 'en').format(format);
};

const formatDateTime = (date, format, isEndDate) => {
	let dateTime = parseDateMoment(date, format || 'L');

	dateTime = isEndDate ? dateTime.endOf('day') : dateTime.startOf('day');

	return dateTime.format(defaultDateFormat);
};

const formatDescriptionDate = date => {
	return formatDate(
		decodeURIComponent(date),
		getLocaleDateFormat('ll'),
		defaultDateFormat
	);
};

const formatTimeRange = (timeRange, isAmPm) => {
	const {dateEnd, dateStart} = timeRange;

	if (!dateEnd && !dateStart) {
		return null;
	}

	const dateEndMoment = moment.utc(dateEnd);
	const dateStartMoment = moment.utc(dateStart);

	const {dateEndPattern, dateStartPattern} = getFormatPattern(
		dateEndMoment,
		dateStartMoment,
		isAmPm
	);

	return `${dateStartMoment.format(
		dateStartPattern
	)} - ${dateEndMoment.format(dateEndPattern)}`;
};

const getCustomTimeRange = (dateEnd, dateStart) => {
	const customTimeRange = {
		active: false,
		dateEnd: decodeURIComponent(dateEnd),
		dateStart: decodeURIComponent(dateStart),
		dividerAfter: true,
		id: 'custom',
		name: Liferay.Language.get('custom-range'),
	};

	customTimeRange.resultName = `${formatDescriptionDate(
		dateStart
	)} - ${formatDescriptionDate(dateEnd)}`;

	return customTimeRange;
};

const getFormatPattern = (dateEndMoment, dateStartMoment, isAmPm) => {
	let dateStartPattern = Liferay.Language.get('mmm-dd-yyyy');

	if (dateEndMoment.diff(dateStartMoment, 'days') <= 1) {
		if (isAmPm) {
			dateStartPattern = Liferay.Language.get('mmm-dd-hh-mm-a');
		}
		else {
			dateStartPattern = Liferay.Language.get('mmm-dd-hh-mm');
		}
	}
	else if (dateEndMoment.diff(dateStartMoment, 'years') < 1) {
		dateStartPattern = Liferay.Language.get('mmm-dd');
	}

	let dateEndPattern = dateStartPattern;

	if (dateEndMoment.diff(dateStartMoment, 'days') > 90) {
		dateEndPattern = Liferay.Language.get('mmm-dd-yyyy');
	}

	return {
		dateEndPattern,
		dateStartPattern,
	};
};

const getTimeRangeParams = (dateStartEncoded = '', dateEndEncoded = '') => {
	let params = {};

	const dateEnd = decodeURIComponent(dateEndEncoded);
	const dateStart = decodeURIComponent(dateStartEncoded);

	if (
		isValidDate(dateEnd, defaultDateFormat) &&
		isValidDate(dateStart, defaultDateFormat)
	) {
		params = {
			dateEnd,
			dateStart,
		};
	}

	return params;
};

const parseDateMoment = (date, format = 'L') => {
	return moment.utc(date, format, 'en');
};

const parseDateItems = isAmPm => items => {
	return items.map(item => {
		const parsedItem = {
			...item,
			dateEnd: item.dateEnd,
			dateStart: item.dateStart,
			key: item.key,
		};

		if (parsedItem.key !== 'custom') {
			parsedItem.description = formatTimeRange(item, isAmPm);
		}

		return parsedItem;
	});
};

export {
	convertQueryDate,
	formatDateTime,
	formatDescriptionDate,
	formatTimeRange,
	getCustomTimeRange,
	getTimeRangeParams,
	isValidDate,
	parseDateMoment,
	parseDateItems,
};

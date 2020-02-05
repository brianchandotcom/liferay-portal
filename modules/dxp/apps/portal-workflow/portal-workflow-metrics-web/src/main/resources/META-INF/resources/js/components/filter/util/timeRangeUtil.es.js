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

import {buildFallbackItems} from '../../../shared/components/filter/util/filterEvents.es';
import moment from '../../../shared/util/moment.es';

const buildFallbackTimeRange = (fallbackKeys, queryDateEnd, queryDateStart) => {
	const fallbackItems = buildFallbackItems(fallbackKeys);

	if (fallbackItems && fallbackItems.length) {
		return {
			...fallbackItems[0],
			dateEnd: parseQueryDate(queryDateEnd, true),
			dateStart: parseQueryDate(queryDateStart)
		};
	}

	return null;
};

const formatDate = (date, locale) => {
	if (locale) {
		return moment.utc(date, null, locale).format('L');
	}

	return moment.utc(date).format('L');
};

const formatDateEnLocale = date => formatDate(date, 'en');

const formatDescriptionDate = date => moment.utc(date).format('ll');

const formatQueryDate = date => parseDateMoment(date).format('YYYY-MM-DD');

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
		dateEnd: parseQueryDate(dateEnd, true),
		dateStart: parseQueryDate(dateStart),
		dividerAfter: true,
		key: 'custom',
		name: Liferay.Language.get('custom-range')
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
		dateStartPattern
	};
};

const isValidDate = date => date && moment(date).isValid();

const parseDate = (date, format, isEndDate, locale) => {
	let utcDate = parseDateMoment(date, format, locale);

	if (isEndDate) {
		utcDate = utcDate
			.hours(23)
			.minutes(59)
			.seconds(59);
	}
	else {
		utcDate = utcDate.hours(0);
	}

	return utcDate.toDate();
};

const parseDateMoment = (date, format = 'L', locale) =>
	moment.utc(date, format, locale, true);

const parseDateItems = isAmPm => items =>
	items.map(item => {
		const parsedItem = {
			...item,
			dateEnd: new Date(item.dateEnd),
			dateStart: new Date(item.dateStart),
			key: item.key
		};

		if (parsedItem.key !== 'custom') {
			parsedItem.description = formatTimeRange(item, isAmPm);
		}

		return parsedItem;
	});

const parseDateMomentEnLocale = (date, format = 'L') =>
	parseDateMoment(date, format, 'en');

const parseDateEnLocale = (date, isEndDate, format = 'L') =>
	parseDate(date, format, isEndDate, 'en');

const parseQueryDate = (date, isEndDate) =>
	parseDate(date, 'YYYY-MM-DD', isEndDate, 'en');

export {
	buildFallbackTimeRange,
	formatDate,
	formatDateEnLocale,
	formatDescriptionDate,
	formatQueryDate,
	formatTimeRange,
	getCustomTimeRange,
	isValidDate,
	parseDate,
	parseDateMoment,
	parseDateItems,
	parseDateMomentEnLocale,
	parseDateEnLocale,
	parseQueryDate
};

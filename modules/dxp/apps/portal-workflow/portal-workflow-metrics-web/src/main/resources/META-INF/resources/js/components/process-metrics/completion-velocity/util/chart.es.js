/* eslint-disable radix */
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

import moment from '../../../../shared/util/moment.es';
import {
	DAYS,
	HOURS,
	LAST_7_DAYS,
	LAST_30_DAYS,
	LAST_90_DAYS,
	LAST_180_DAYS,
	LAST_YEAR,
	MONTHS,
	TODAY,
	WEEKS,
	YEARS,
	YESTERDAY
} from './chartConstants.es';

export const formatMonthDate = (date, timeRange) => {
	const currentDate = moment.utc(date);
	const dateEnd = moment.utc(timeRange.dateEnd);
	const dateStart = moment.utc(timeRange.dateStart);

	let firstDayOfMonth = currentDate.clone().startOf('month');
	let lastDayOfMonth = currentDate.clone().endOf('month');

	if (currentDate.isSame(dateStart, 'month')) {
		firstDayOfMonth = currentDate.clone();
	}
	else if (currentDate.isSame(dateEnd, 'month')) {
		lastDayOfMonth = dateEnd.clone();
	}

	if (firstDayOfMonth.isSame(lastDayOfMonth, 'day')) {
		return firstDayOfMonth.format(Liferay.Language.get('mmm-dd-yyyy'));
	}
	return `${firstDayOfMonth.format(
		Liferay.Language.get('mmm-dd')
	)}-${lastDayOfMonth.format(Liferay.Language.get('dd-yyyy'))}`;
};

export const formatWeekDate = (date, timeRange) => {
	const currentDate = moment.utc(date);
	const dateEnd = moment.utc(timeRange.dateEnd);
	const dateStart = moment.utc(timeRange.dateStart);

	let firstDayOfWeek = currentDate.clone().startOf('week');
	let lastDayOfWeek = currentDate.clone().endOf('week');

	if (currentDate.isSame(dateStart, 'week')) {
		firstDayOfWeek = currentDate.clone();
	}
	else if (currentDate.isSame(dateEnd, 'week')) {
		lastDayOfWeek = dateEnd.clone();
	}
	const firstMonth = firstDayOfWeek.format('MMM');
	const lastMonth = lastDayOfWeek.format('MMM');

	if (firstDayOfWeek.isSame(lastDayOfWeek, 'day')) {
		return firstDayOfWeek.format(Liferay.Language.get('mmm-dd'));
	}
	else if (firstMonth === lastMonth) {
		return `${firstDayOfWeek.format(
			Liferay.Language.get('mmm-dd')
		)}-${lastDayOfWeek.format('DD')}`;
	}
	return `${firstDayOfWeek.format(
		Liferay.Language.get('mmm-dd')
	)}-${lastDayOfWeek.format(Liferay.Language.get('mmm-dd'))}`;
};

export const formatWeekDateWithYear = (date, timeRange) => {
	const currentDate = moment.utc(date);
	const dateEnd = moment.utc(timeRange.dateEnd);
	const dateStart = moment.utc(timeRange.dateStart);

	let firstDayOfWeek = currentDate.clone().startOf('week');
	let lastDayOfWeek = currentDate.clone().endOf('week');

	if (currentDate.isSame(dateStart, 'week')) {
		firstDayOfWeek = currentDate.clone();
	}
	else if (currentDate.isSame(dateEnd, 'week')) {
		lastDayOfWeek = dateEnd.clone();
	}
	const firstMonth = firstDayOfWeek.format('MMM');
	const lastMonth = lastDayOfWeek.format('MMM');

	const firstYear = firstDayOfWeek.format('YYYY');
	const lastYear = lastDayOfWeek.format('YYYY');

	if (firstDayOfWeek.isSame(lastDayOfWeek, 'day')) {
		return firstDayOfWeek.format(Liferay.Language.get('mmm-dd-yyyy'));
	}
	else if (firstYear !== lastYear) {
		return `${firstDayOfWeek.format(
			Liferay.Language.get('mmm-dd-yyyy')
		)} - ${lastDayOfWeek.format(Liferay.Language.get('mmm-dd-yyyy'))}`;
	}
	else if (firstMonth !== lastMonth) {
		return `${firstDayOfWeek.format(
			Liferay.Language.get('mmm-dd')
		)} - ${lastDayOfWeek.format(Liferay.Language.get('mmm-dd-yyyy'))}`;
	}
	return `${firstDayOfWeek.format(
		Liferay.Language.get('mmm-dd')
	)} - ${lastDayOfWeek.format(Liferay.Language.get('dd-yyyy'))}`;
};

export const formatXAxisDate = (date, isAmPm, timeRangeKey, timeRange) => {
	const currentDate = moment.utc(date);
	const rangeUnit = getRangeKey(timeRange);

	if (timeRangeKey === HOURS) {
		let datetPattern = Liferay.Language.get('hh-mm');

		if (isAmPm) {
			datetPattern = Liferay.Language.get('hh-mm-a');
		}

		return currentDate.format(datetPattern);
	}
	else if (timeRangeKey === YEARS) {
		return currentDate.format('YYYY');
	}
	else if (
		[LAST_YEAR, LAST_180_DAYS].includes(rangeUnit) &&
		MONTHS === timeRangeKey
	) {
		return currentDate.format(Liferay.Language.get('mmm-yyyy'));
	}
	else if (timeRangeKey === MONTHS) {
		return currentDate.format('MMM');
	}
	else if (timeRangeKey === WEEKS) {
		return formatWeekDate(date, timeRange);
	}
	return currentDate.format(Liferay.Language.get('mmm-dd'));
};

export const formatYearDate = (date, timeRange) => {
	const currentDate = moment.utc(date);
	const dateEnd = moment.utc(timeRange.dateEnd);
	const dateStart = moment.utc(timeRange.dateStart);

	let firstDayOfYear = currentDate.clone().startOf('year');
	let lastDayOfYear = currentDate.clone().endOf('year');

	if (currentDate.isSame(dateStart, 'year')) {
		firstDayOfYear = currentDate.clone();
	}
	else if (currentDate.isSame(dateEnd, 'year')) {
		lastDayOfYear = dateEnd.clone();
	}

	if (firstDayOfYear.isSame(lastDayOfYear, 'day')) {
		return firstDayOfYear.format(Liferay.Language.get('mmm-dd-yyyy'));
	}
	return `${firstDayOfYear.format(
		Liferay.Language.get('mmm-dd')
	)}-${lastDayOfYear.format(Liferay.Language.get('mmm-dd-yyyy'))}`;
};

export const getAxisMeasures = value => {
	const numChars = Math.floor(value).toString().length;
	const decOrder = Math.pow(10, numChars - 1);
	let maxValue = decOrder * Math.floor(value / decOrder) + decOrder;
	let firstDic = maxValue / decOrder;

	if ([3, 7, 9].indexOf(firstDic) > -1) {
		firstDic += 1;
	}
	maxValue = firstDic * decOrder;
	let intervalCount = 4;

	if ([1, 5, 10].indexOf(firstDic) > -1) {
		intervalCount = 5;
	}
	const intervalValue = maxValue / intervalCount;

	for (let i = 0; i < intervalCount; i++) {
		const tempMaxValue = intervalValue * (i + 1);
		if (tempMaxValue > value) {
			maxValue = tempMaxValue;
			intervalCount = i + 1;
			break;
		}
	}
	const intervals = [];
	intervals.push(0);

	for (let i = 0; i < intervalCount; i++) {
		intervals.push(intervalValue * (i + 1));
	}
	return {
		intervalCount,
		intervalValue,
		intervals,
		maxValue
	};
};

export const getAxisMeasuresFromData = data =>
	getAxisMeasures(
		Math.max(
			...data
				.reduce((prev, next) => prev.concat(next), [])
				.filter(value => typeof value === 'number')
		)
	);

export const getRangeKey = timeRange => {
	const endDate = moment.utc(timeRange.dateEnd);
	const startDate = moment.utc(timeRange.dateStart);

	const diff = parseInt(moment.duration(endDate.diff(startDate)).asDays());

	const diffList = [
		TODAY,
		YESTERDAY,
		LAST_7_DAYS,
		LAST_30_DAYS,
		LAST_90_DAYS,
		LAST_180_DAYS,
		LAST_YEAR
	];

	const diffItem = diffList.find(key => key >= diff);
	if (typeof diffItem !== 'undefined') {
		return diffItem;
	}

	return LAST_YEAR;
};

export const getXAxisIntervals = (timeRange, keys, type) => {
	const endDate = moment.utc(timeRange.dateEnd);
	const lengthKeys = keys.length;
	const secondDate = moment.utc(keys[1]);
	const startDate = moment.utc(timeRange.dateStart);

	const diffLeftDays = parseInt(
		moment.duration(secondDate.diff(startDate)).asDays()
	);

	const diffLeftMonths = parseInt(
		moment.duration(secondDate.diff(startDate)).asMonths()
	);

	const nextToLastDay = moment.utc(keys[lengthKeys - 2]);

	const diffRightDays = parseInt(
		moment.duration(endDate.diff(nextToLastDay)).asDays()
	);

	const diffMap = {
		[TODAY]: () => {
			return {
				offset: 4,
				padLeft: 0,
				padRight: 0
			};
		},
		[YESTERDAY]: () => {
			return {
				offset: 6,
				padLeft: 0,
				padRight: 0
			};
		},
		// eslint-disable-next-line sort-keys
		[LAST_7_DAYS]: () => {
			return {
				offset: 1,
				padLeft: 0,
				padRight: 0
			};
		},
		[LAST_30_DAYS]: type => {
			if (type === DAYS) {
				return {
					offset: 6,
					padLeft: 0,
					padRight: diffRightDays < 2 ? diffRightDays + 2 : 0
				};
			}
			return {
				offset: 1,
				padLeft: 0,
				padRight: 0
			};
		},
		[LAST_90_DAYS]: type => {
			if (type === DAYS) {
				return {
					offset: 11,
					padLeft: 0,
					padRight: 3
				};
			}
			else if (type === WEEKS) {
				return {
					offset: 2,
					padLeft: 0,
					padRight: 0
				};
			}
			return {
				offset: 1,
				padLeft: 0,
				padRight: 0
			};
		},
		[LAST_180_DAYS]: type => {
			if (type === WEEKS) {
				return {
					offset: 4,
					padLeft: 0,
					padRight: 3
				};
			}
			return {
				offset: 1,
				padLeft: diffLeftDays < 14 ? 1 : 0,
				padRight: 0
			};
		},
		[LAST_YEAR]: type => {
			if (type === WEEKS) {
				const lengthWeek = lengthKeys === 52 ? 5 : 6;
				return {
					offset: parseInt(lengthKeys / lengthWeek),
					padLeft: 0,
					padRight: 6
				};
			}
			else if (type === YEARS) {
				return {
					offset: lengthKeys > 12 ? parseInt(lengthKeys / 6) : 1,
					padLeft:
						(lengthKeys > 12 && diffLeftMonths < 7) ||
						diffLeftMonths < 2
							? 1
							: 0,
					padRight: 0
				};
			}
			return {
				offset: parseInt(lengthKeys / 5),
				padLeft: 0,
				padRight: 0
			};
		}
	};
	const diffMapKey = getRangeKey(timeRange);

	const diffIndex = diffMap[diffMapKey](type);

	return keys.filter(
		(key, index) =>
			index === 0 ||
			index === lengthKeys - 1 ||
			(index % diffIndex.offset === 0 &&
				index > diffIndex.padLeft &&
				index < lengthKeys - diffIndex.padRight + 1)
	);
};

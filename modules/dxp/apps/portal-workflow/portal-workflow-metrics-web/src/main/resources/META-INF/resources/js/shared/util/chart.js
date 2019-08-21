import moment from '../../shared/util/moment';
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
} from './chart-constants';

export const formatMonthDate = (date, timeRange) => {
	const currentDate = moment.utc(date);
	const dateEnd = moment.utc(timeRange.dateEnd);
	const dateStart = moment.utc(timeRange.dateStart);

	let firstDayOfMonth = currentDate.clone().startOf('month');
	let lastDayOfMonth = currentDate.clone().endOf('month');

	if (currentDate.isSame(dateStart, 'month')) {
		firstDayOfMonth = currentDate.clone();
	} else if (currentDate.isSame(dateEnd, 'month')) {
		lastDayOfMonth = dateEnd.clone();
	}

	if (firstDayOfMonth.isSame(lastDayOfMonth, 'day')) {
		return firstDayOfMonth.format('MMM D, YYYY');
	}
	return `${firstDayOfMonth.format('MMM D')}-${lastDayOfMonth.format(
		'D, YYYY'
	)}`;
};

export const formatWeekDate = (date, timeRange) => {
	const currentDate = moment.utc(date);
	const dateEnd = moment.utc(timeRange.dateEnd);
	const dateStart = moment.utc(timeRange.dateStart);

	let firstDayOfWeek = currentDate.clone().startOf('week');
	let lastDayOfWeek = currentDate.clone().endOf('week');

	if (currentDate.isSame(dateStart, 'week')) {
		firstDayOfWeek = currentDate.clone();
	} else if (currentDate.isSame(dateEnd, 'week')) {
		lastDayOfWeek = dateEnd.clone();
	}
	const firstMonth = firstDayOfWeek.format('MMM');
	const lastMonth = lastDayOfWeek.format('MMM');

	if (firstDayOfWeek.isSame(lastDayOfWeek, 'day')) {
		return firstDayOfWeek.format('MMM D');
	} else if (firstMonth === lastMonth) {
		return `${firstDayOfWeek.format('MMM D')}-${lastDayOfWeek.format('D')}`;
	}
	return `${firstDayOfWeek.format('MMM D')}-${lastDayOfWeek.format('MMM D')}`;
};

export const formatWeekDateWithYear = (date, timeRange) => {
	const currentDate = moment.utc(date);
	const dateEnd = moment.utc(timeRange.dateEnd);
	const dateStart = moment.utc(timeRange.dateStart);

	let firstDayOfWeek = currentDate.clone().startOf('week');
	let lastDayOfWeek = currentDate.clone().endOf('week');

	if (currentDate.isSame(dateStart, 'week')) {
		firstDayOfWeek = currentDate.clone();
	} else if (currentDate.isSame(dateEnd, 'week')) {
		lastDayOfWeek = dateEnd.clone();
	}
	const firstMonth = firstDayOfWeek.format('MMM');
	const lastMonth = lastDayOfWeek.format('MMM');

	const firstYear = firstDayOfWeek.format('YYYY');
	const lastYear = lastDayOfWeek.format('YYYY');

	if (firstDayOfWeek.isSame(lastDayOfWeek, 'day')) {
		return firstDayOfWeek.format('MMM D, YYYY');
	} else if (firstYear !== lastYear) {
		return `${firstDayOfWeek.format(
			'MMM D, YYYY'
		)} - ${lastDayOfWeek.format('MMM D, YYYY')}`;
	} else if (firstMonth !== lastMonth) {
		return `${firstDayOfWeek.format('MMM D')} - ${lastDayOfWeek.format(
			'MMM D, YYYY'
		)}`;
	}
	return `${firstDayOfWeek.format('MMM D')} - ${lastDayOfWeek.format(
		'D, YYYY'
	)}`;
};

export const formatXAxisDate = (date, timeRangeKey, timeRange) => {
	const currentDate = moment.utc(date);
	const rangeUnit = getRangeKey(timeRange);

	if (timeRangeKey === HOURS) {
		return currentDate.format('h A');
	} else if (timeRangeKey === YEARS) {
		return currentDate.format('YYYY');
	} else if (
		[LAST_YEAR, LAST_180_DAYS].includes(rangeUnit) &&
		MONTHS === timeRangeKey
	) {
		return currentDate.format('MMM YYYY');
	} else if (timeRangeKey === MONTHS) {
		return currentDate.format('MMM');
	} else if (timeRangeKey === WEEKS) {
		return formatWeekDate(date, timeRange);
	}
	return currentDate.format('MMM D');
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
		intervals,
		intervalValue,
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
	const startDate = moment.utc(timeRange.dateStart);
	const endDate = moment.utc(timeRange.dateEnd);

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
	const lengthKeys = keys.length;

	const diffMap = {
		[TODAY]: () => {
			return {
				index: 4,
				pad: lengthKeys
			};
		},
		[YESTERDAY]: () => {
			return {
				index: 6,
				pad: lengthKeys
			};
		},
		[LAST_7_DAYS]: () => {
			return {
				index: 1,
				pad: lengthKeys
			};
		},
		[LAST_30_DAYS]: type => {
			if (type === DAYS) {
				return {
					index: 6,
					pad: lengthKeys
				};
			}
			return {
				index: 1,
				pad: lengthKeys
			};
		},
		[LAST_90_DAYS]: type => {
			if (type === DAYS) {
				return {
					index: 11,
					pad: lengthKeys - 3
				};
			} else if (type === WEEKS) {
				return {
					index: 2,
					pad: lengthKeys
				};
			}
			return {
				index: 1,
				pad: lengthKeys
			};
		},
		[LAST_180_DAYS]: type => {
			if (type === WEEKS) {
				return {
					index: 4,
					pad: lengthKeys - 3
				};
			}
			return {
				index: 1,
				pad: lengthKeys
			};
		},
		[LAST_YEAR]: type => {
			if (type === WEEKS) {
				const lengthWeek = lengthKeys === 52 ? 5 : 6;
				return {
					index: parseInt(lengthKeys / lengthWeek),
					pad: lengthKeys - 5
				};
			}
			return {
				index: parseInt(lengthKeys / 5),
				pad: lengthKeys
			};
		}
	};
	const diffMapKey = getRangeKey(timeRange);

	const diffIndex = diffMap[diffMapKey](type);

	return keys.filter(
		(key, index) =>
			index === 0 ||
			index === lengthKeys - 1 ||
			(index % diffIndex.index === 0 && index < diffIndex.pad)
	);
};

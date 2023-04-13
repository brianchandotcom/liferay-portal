import moment from 'moment';
import {Interval, RangeSelectors} from 'shared/types';
import {INTERVAL_KEY_MAP} from 'shared/util/time';
import {Map} from 'immutable';
import {RangeKeyTimeRanges} from 'shared/util/constants';

export const createDateKeysIMap = (
	interval: Interval,
	history: Array<any>,
	dateKey: string = 'intervalInitDate'
) => {
	const parseHistory = item => {
		const dateStart = item[dateKey];
		const dateEnd =
			interval === 'W'
				? moment.utc(dateStart, 'x').add('6', 'days').valueOf()
				: null;

		return [dateStart, [dateStart, dateEnd]];
	};

	return Map<Date, [Date, Date?]>(history.map(parseHistory));
};

export const getIntervalHandle = (
	rangeKey: RangeSelectors['rangeKey'],
	duration: number,
	timeInterval: Interval
) => {
	const intervalMapsByRangeKey = getIntervalsFromMap(duration)[timeInterval];

	return intervalMapsByRangeKey && intervalMapsByRangeKey[rangeKey];
};

export const getIntervalsFromMap = (duration: number) => ({
	[INTERVAL_KEY_MAP.day]: getDayIntervalsMap(duration),
	[INTERVAL_KEY_MAP.week]: getWeekIntervalsMap(duration)
});

export const getDayIntervalsMap = (duration: number) => ({
	[RangeKeyTimeRanges.CustomRange]: getByCustomRangeKey(
		duration,
		INTERVAL_KEY_MAP.day
	),
	[RangeKeyTimeRanges.Last180Days]: getFirstAndFifteenthsDays,
	[RangeKeyTimeRanges.Last24Hours]: getByIndexesMultipleOfSix,
	[RangeKeyTimeRanges.Last28Days]: getSundays,
	[RangeKeyTimeRanges.Last30Days]: getSundays,
	[RangeKeyTimeRanges.Last90Days]: getFirstAndFifteenthsDays,
	[RangeKeyTimeRanges.LastYear]: getFirstDays,
	[RangeKeyTimeRanges.Yesterday]: getByIndexesMultipleOfSix
});

export const getWeekIntervalsMap = (duration: number) => ({
	[RangeKeyTimeRanges.CustomRange]: getByCustomRangeKey(
		duration,
		INTERVAL_KEY_MAP.week
	),
	[RangeKeyTimeRanges.Last180Days]: getByEvenOrOddIndexes,
	[RangeKeyTimeRanges.Last90Days]: getByEvenOrOddIndexes,
	[RangeKeyTimeRanges.LastYear]: getByIndexesMultipleOfFour
});

export const handleDayInterval = (
	handleFn: (date: number) => number,
	firstTick: number,
	lastDate: number
): number[] => {
	const intervals = [firstTick];
	let lastTick = firstTick;

	while (lastTick < lastDate) {
		lastTick = handleFn(lastTick);

		if (lastTick <= lastDate) {
			intervals.push(lastTick);
		}
	}

	return intervals;
};

export const getByEvenOrOddIndexes = (arr: number[]): number[] =>
	arr.length % 2 === 0
		? [arr[0], ...arr.filter((_, index) => index % 2 !== 0)]
		: arr.filter((_, index) => index % 2 === 0);

export const getByIndexesMultipleOfFour = (arr: number[]): number[] =>
	arr.filter((_, index) => index % 4 === 0);

export const getByIndexesMultipleOfSix = (arr: number[]): number[] => [
	...arr.filter((_, index) => index % 6 === 0),
	arr[arr.length - 1]
];

export const getSundays = (arr: number[]): number[] => {
	const firstDate = arr[0];
	const lastDate = arr[arr.length - 1];

	const firstTick =
		moment.utc(firstDate).get('day') === 0
			? firstDate
			: getNextSunday(firstDate);

	return handleDayInterval(getNextSunday, firstTick, lastDate);
};

export const getFirstAndFifteenthsDays = (arr: number[]): number[] => {
	const firstDate = arr[0];
	const lastDate = arr[arr.length - 1];

	const firstDayDate = moment.utc(firstDate).get('date');

	const firstTick =
		firstDayDate === 1 || firstDayDate === 15
			? firstDate
			: getNextFirstOrFifteenth(firstDate);

	return handleDayInterval(getNextFirstOrFifteenth, firstTick, lastDate);
};

export const getFirstDays = (arr: number[]): number[] => {
	const firstDate = arr[0];
	const lastDate = arr[arr.length - 1];

	const firstTick =
		moment.utc(firstDate).get('day') === 1
			? firstDate
			: getNextFirst(firstDate);

	return handleDayInterval(getNextFirst, firstTick, lastDate);
};

export const getByCustomRangeKey = (
	duration: number,
	timeInterval: Interval
) => {
	if (timeInterval === INTERVAL_KEY_MAP.day) {
		if (duration >= 14 && duration <= 30) {
			return getSundays;
		} else if (duration > 30 && duration <= 180) {
			return getFirstAndFifteenthsDays;
		} else if (duration > 180) {
			return getFirstDays;
		}
	} else if (timeInterval === INTERVAL_KEY_MAP.week) {
		if (duration > 90 && duration <= 180) {
			return getByEvenOrOddIndexes;
		} else if (duration > 180) {
			return getByIndexesMultipleOfFour;
		}
	}
};

export const getNextSunday = (date: number): number =>
	// TIMEZONE
	moment.utc(date).day(7).startOf('day').valueOf();

export const getNextFirstOrFifteenth = (date: number): number => {
	if (moment.utc(date).get('date') >= 15) {
		return getNextFirst(date);
	}

	return (
		moment
			// TIMEZONE
			.utc(date)
			.date(15)
			.startOf('day')
			.valueOf()
	);
};

export const getNextFirst = (date: number): number =>
	moment.utc(date).endOf('month').add(1).valueOf();

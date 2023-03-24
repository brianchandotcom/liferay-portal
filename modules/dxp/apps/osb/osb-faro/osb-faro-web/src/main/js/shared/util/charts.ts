import * as d3 from 'd3';
import moment from 'moment';
import {BAR_COLORS} from 'shared/util/recharts';
import {getIntervalHandle} from './intervals';
import {Interval, RangeSelectors} from 'shared/types';
import {INTERVAL_KEY_MAP, isMonthlyRangeKey} from 'shared/util/time';
import {isNumber} from 'lodash';
import {Map} from 'immutable';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {toDuration, toRounded, toThousands} from 'shared/util/numbers';

export type DataTooltip = {
	id: string;
	index: number;
	name: string;
	value: number;
	x: number | string | Date;
};

export enum MetricValueType {
	Number = 'number',
	Percentage = 'percentage',
	Time = 'time',
	Ratings = 'ratings'
}

export const Colors = {
	gray: '#AEB0BB',
	mapBluePallete: [
		'#B1D4FF',
		'#95C5FF',
		'#7EB7FF',
		'#64A9FF',
		'#4B9BFF',
		'#318DFF',
		'#187FFF',
		'#0071FD',
		'#0065E4'
	],
	mapEmpty: '#E1E1E1',
	mapSelected: '#4B9BFF',
	negative: '#DA1414',
	neutral: '#AEB0BB',
	pallete: [
		'#4B9BFF',
		'#FFB46E',
		'#FF5F5F',
		'#50D2A0',
		'#FF73C3',
		'#9CE268',
		'#B077FF',
		'#FFD76E',
		'#5FC8FF'
	],
	positive: '#287D3C',
	primary: '#4B9BFF',
	secondary: '#CCCCCC'
};

export const dateRangeFormatter = (
	dateStart: Date,
	dateEnd: Date,
	withYear: boolean = false
): string => {
	// TODO: Add timezone param
	const dayFormat = d3.utcFormat('%-d');
	const dayMonthFormat = d3.utcFormat('%b %-d');
	const dayMonthYearFormat = d3.utcFormat('%Y %b %-d');

	return `${
		withYear ? dayMonthYearFormat(dateStart) : dayMonthFormat(dateStart)
	} - ${
		moment(dateStart).get('month') !== moment(dateEnd).get('month')
			? withYear
				? dayMonthYearFormat(dateEnd)
				: dayMonthFormat(dateEnd)
			: dayFormat(dateEnd)
	}`;
};

/**
 * Format Tooltip Date
 * @param {date} date
 * @param {string} rangeKey
 */
export const formatTooltipDate = (date, rangeKey) => {
	if (
		rangeKey === RangeKeyTimeRanges.Last24Hours ||
		rangeKey === RangeKeyTimeRanges.Yesterday
	) {
		// display hours for Last 24 hours and yesterday
		return moment.utc(date).format('MMM D, h A');
	}

	return moment.utc(date).format('YYYY MMM D');
};

export const formatXAxisDate = (
	dateKey: Date,
	rangeKey: string,
	interval: Interval,
	dateKeysIMap: Map<Date, [Date, Date?]>
) => {
	// display date and month
	let formatter = d3.utcFormat('%b %-d');
	const monthFormat = d3.utcFormat('%b');

	const [dateStart, dateEnd] = dateKeysIMap.get(dateKey);

	switch (rangeKey) {
		case RangeKeyTimeRanges.CustomRange:
		case RangeKeyTimeRanges.Last7Days:
		case RangeKeyTimeRanges.Last28Days:
		case RangeKeyTimeRanges.Last30Days:
		case RangeKeyTimeRanges.Last90Days:
		case RangeKeyTimeRanges.Last180Days:
		case RangeKeyTimeRanges.LastYear:
			if (interval === INTERVAL_KEY_MAP.week) {
				// display date range

				// TODO: Add timezone param
				return dateRangeFormatter(dateStart, dateEnd, false);
			}
			if (interval === INTERVAL_KEY_MAP.month) {
				// display month
				return monthFormat(dateStart);
			}
			break;
		case RangeKeyTimeRanges.Last24Hours:
		case RangeKeyTimeRanges.Yesterday:
			// display hours
			formatter = d3.utcFormat('%-I %p');
			break;
		default:
			break;
	}

	return formatter(dateStart);
};

/**
 * Return the formatted numbers to display on charts.
 * Per design requets these numbers shouldn't have decimal
 * precision.
 * @param {string} type
 */
export const getAxisFormatter = type => {
	if (type === 'percentage') {
		return value => `${toRounded(value * 100)}%`;
	} else if (type === 'time') {
		return value => {
			const displayMilliseconds =
				value < 2e3 && value !== 1000 ? 'S[ms]' : '';

			const format = `DD[days] hh[h] mm[m] ss[s] ${displayMilliseconds}`;

			return toDuration(value, format);
		};
	} else if (type == 'ratings') {
		return value => `${(value * 10).toFixed(2)}`;
	}

	return getMetricFormatter(type);
};

/**
 * Return the chart axis measures from a max value
 * @param {number} value
 */
export const getAxisMeasures = (value: number) => {
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

	// avoid extra intervals
	for (let i = 0; i < intervalCount; i++) {
		let tempMaxValue = intervalValue * (i + 1);
		if (tempMaxValue % 1 !== 0) {
			tempMaxValue = parseFloat(tempMaxValue.toFixed(1));
		}
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

/**
 * Return the chart max value from composite data.
 * @param {Array}
 * @returns {Object}
 */
export const getAxisMeasuresFromCompositeData = ([
	data1,
	data2,
	dataPrevious
]: number[][]) => {
	const maxStackedData = d3.max(data1) + d3.max(data2);

	return getAxisMeasures(
		Math.max(
			dataPrevious
				? Math.max(maxStackedData, d3.max(dataPrevious))
				: maxStackedData
		)
	);
};

/**
 * Return the chart max value from a data
 * @param {Array} data
 */
export const getAxisMeasuresFromData = data =>
	getAxisMeasures(
		Math.max(
			...data
				.reduce((prev, next) => prev.concat(next), [])
				.filter(value => typeof value === 'number')
		)
	);

export const getBarColor = (
	currentBarIndex: number,
	hoverIndex: number,
	selectedPoint?: number,
	color = 'blue'
): string => {
	if (selectedPoint === currentBarIndex) {
		return BAR_COLORS[color].selected;
	} else if (currentBarIndex === hoverIndex) {
		return BAR_COLORS[color].hover;
	} else if (isNumber(selectedPoint)) {
		return BAR_COLORS[color].notSelected;
	}

	return BAR_COLORS[color].default;
};

/**
 * Return the formatted array to display on charts.
 * @param {string} type
 */
export const getDataFormatter = type => {
	if (type === 'time') {
		return arr => arr.map(value => Math.round(value / 1e3) * 1e3);
	}

	return arr => arr;
};

/**
 * Get Date Title
 * @param {array} dates
 * @param {string} rangeKey
 */
export const getDateTitle = (
	dates: [Date, Date?],
	rangeKey: RangeKeyTimeRanges,
	interval: Interval
): string => {
	if (!dates) {
		return '';
	}

	const [startDate, endDate] = dates;

	if (isMonthlyRangeKey(rangeKey) && interval === INTERVAL_KEY_MAP.week) {
		return dateRangeFormatter(startDate, endDate, true);
	} else if (interval === INTERVAL_KEY_MAP.month) {
		return moment.utc(startDate).format('YYYY MMM');
	}

	return formatTooltipDate(startDate, rangeKey);
};

/**
 * Get Intervals
 * @param {string} rangeKey
 * @param {array} arr
 */
export const getIntervals = (
	rangeKey: RangeSelectors['rangeKey'],
	arr: number[],
	timeInterval: Interval,
	dateKeysIMap: any
): number[] => {
	if (arr.length) {
		const firstDate = moment(arr[0]);
		const [lastPeriodStart, lastPeriodEnd] = dateKeysIMap.get(
			arr[arr.length - 1]
		);
		const lastDate = lastPeriodEnd
			? moment(lastPeriodEnd)
			: moment(lastPeriodStart);
		const duration = lastDate.diff(firstDate, 'days') + 1;

		const validTimeInterval = [
			RangeKeyTimeRanges.Last24Hours,
			RangeKeyTimeRanges.Yesterday
		].includes(rangeKey)
			? INTERVAL_KEY_MAP.day
			: timeInterval;

		const intervalHandle = getIntervalHandle(
			rangeKey,
			duration,
			validTimeInterval
		);

		return intervalHandle ? intervalHandle(arr) : arr;
	}

	return arr;
};

/**
 * Return the Locations data
 */
export const getLocationsData = (metrics, location = 'Any') => {
	let total = 0;

	metrics.forEach(({value}) => {
		total += value;
	});

	const data = metrics.map(({value, valueKey}) => ({
		group: valueKey,
		id: valueKey,
		name: valueKey,
		total: value,
		value: `${toRounded((value / total) * 100)}`
	}));

	let othersLabel;

	if (location === 'Any') {
		othersLabel = Liferay.Language.get('other-countries');
	} else {
		othersLabel = Liferay.Language.get('other-regions');
	}

	const others = metrics.filter((value, index) => index >= 5);

	if (others.length > 0) {
		let totalOthers = 0;
		others.forEach(({value}) => {
			totalOthers += value;
		});

		data.push({
			color: '#CCCCCC',
			group: othersLabel,
			id: 'others',
			name: othersLabel,
			total: totalOthers,
			value: `${toRounded((totalOthers / total) * 100)}`
		});
	}

	return data;
};

/**
 * Return the metric formatter
 * @param {string} type
 */
export const getMetricFormatter = type => {
	if (type === 'number') {
		return value => `${toThousands(value)}`;
	} else if (type === 'percentage') {
		return value => `${toRounded(value * 100)}%`;
	} else if (type === 'time') {
		return value => toDuration(value);
	} else if (type == 'ratings') {
		return value => `${(value * 10).toFixed(2)}/10`;
	} else {
		return value => value;
	}
};

export const getLegendLineDashed = color =>
	`<div class="legend-icon line line-dashed" style="background-image: linear-gradient(90deg, ${color} 28.3%, transparent 28.3% 38.3%, ${color} 38.3% 61.6%, transparent 61.6% 71.6%, ${color} 71.6% 100%);"></div>`;

export const getLegendCircle = (color: string): string =>
	`<div class="legend-icon circle" style="background-color: ${color};"></div>`;

/**
 * Return a svg line icon
 * @param {color} string
 * @returns {string} svg HTML element
 */
export const getLegendLine = color =>
	`<div class="legend-icon line" style="background-color: ${color};"></div>`;

/**
 * is Empty Data
 * @param {array} data
 * @returns {boolean}
 */
export function isEmptyData(data) {
	return d3.sum(d3.merge(data)) === 0;
}

/**
 * Return the color based on index
 */
export const nextColor = d3.scaleOrdinal().range(Colors.pallete);

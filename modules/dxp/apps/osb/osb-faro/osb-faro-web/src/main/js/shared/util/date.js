import moment from 'moment';
import momentTimezone from 'moment-timezone';
import {flow, get, head, last, rangeRight} from 'lodash/fp';
import {INTERVAL_KEY_MAP} from 'shared/util/time';

export const DEFAULT_DATE_FORMAT = 'YYYY-MM-DD';

export const CUSTOM_DATE_FORMAT = 'MMM DD, YYYY';

export const DATE_MASK = [
	/\d/,
	/\d/,
	/\d/,
	/\d/,
	'-',
	/\d/,
	/\d/,
	'-',
	/\d/,
	/\d/
];

export const DATE_TIME_MASK = [
	/\d/,
	/\d/,
	/\d/,
	/\d/,
	'-',
	/\d/,
	/\d/,
	'-',
	/\d/,
	/\d/,
	' ',
	/\d/,
	/\d/,
	':',
	/\d/,
	/\d/
];

export const WEEKDAYS = [
	Liferay.Language.get('sunday'),
	Liferay.Language.get('monday'),
	Liferay.Language.get('tuesday'),
	Liferay.Language.get('wednesday'),
	Liferay.Language.get('thursday'),
	Liferay.Language.get('friday'),
	Liferay.Language.get('saturday')
];

export const DEFAULT_TIMEZONE_ID = 'UTC';

export const DEFAULT_FORMAT = 'LL';

/**
 * Formats unix timestamp to specified moment format
 * @param {number|string|Date} date
 * @param {string|moment.MomentBuiltinFormat} format
 * @param {string|moment.MomentBuiltinFormat} [inputFormatter]
 * @return {string} formatted date
 */
export function formatUTCDate(date, format = DEFAULT_FORMAT, inputFormatter) {
	return moment.utc(date, inputFormatter).format(format);
}

export const formatUTCDateFromUnix = (date, format = DEFAULT_FORMAT) =>
	formatUTCDate(date, format, 'x');

export const formatDateToTimeZone = (
	date,
	format = DEFAULT_FORMAT,
	timeZoneId = DEFAULT_TIMEZONE_ID
) => applyTimeZone(date, timeZoneId).format(format);

export const applyTimeZone = (date, timeZoneId = DEFAULT_TIMEZONE_ID) =>
	momentTimezone.utc(date).tz(timeZoneId);

export function generateDateRange(period = 30, interval = 'days') {
	return rangeRight(0, period).map(cur =>
		moment.utc().startOf(interval).subtract(cur, interval).valueOf()
	);
}

/**
 * Get Date
 * @param {string | number} [date]
 */
export const getDate = date => moment.utc(date).toDate();

/**
 * Get ISO Date
 * @param {string} date
 */
export const getISODate = date => moment.utc(date).toISOString();

/**
 * Get Date now.
 * @returns {Moment} Date at time of calling.
 */
export const getDateNow = () => moment.utc();

export function getDateRangeLabel(dates, interval, key) {
	const firstDate = flow(head, get(key), formatUTCDate)(dates);
	const lastDate = formatUTCDate(getLastDate(dates, interval, key));

	return `${firstDate} - ${lastDate}`;
}

export function getDateRangeLabelFromDate(date, interval) {
	const firstDate = formatUTCDateFromUnix(date);

	if (interval === INTERVAL_KEY_MAP.day) {
		return `${firstDate}`;
	}

	const lastDate = formatUTCDate(getEndDate(date, interval));

	return `${firstDate} - ${lastDate}`;
}

export function getEndDate(date, interval) {
	if (interval === INTERVAL_KEY_MAP.week) {
		return moment.utc(date).add('6', 'days');
	} else if (interval === INTERVAL_KEY_MAP.month) {
		return moment.utc(date).endOf('month');
	}

	return date;
}

/**
 *  Gets the first date of the array.
 *  @param {Array.<Aggregation>} aggregations - Array of objects.
 *  @returns {number} Date in unix time.
 */
export function getFirstDate(dates, key) {
	return flow(head, get(key))(dates);
}

/**
 *  Gets the last date of the array.
 *  @param {Array.<Aggregation>} aggregations - Array of objects.
 *  @returns {number} Date in unix time.
 */
export function getLastDate(dates, interval, key) {
	const date = flow(last, get(key))(dates);

	return getEndDate(date, interval);
}

/**
 * Get total days to date
 * @param {object} date
 */
export const getTotalDaysToDate = createDate => {
	const duration = moment.duration({
		from: moment(createDate).clone(),
		to: new Date()
	});

	return Math.floor(duration.asDays());
};

export function toUnix(stringOrMoment) {
	return moment.utc(stringOrMoment).valueOf() || null;
}

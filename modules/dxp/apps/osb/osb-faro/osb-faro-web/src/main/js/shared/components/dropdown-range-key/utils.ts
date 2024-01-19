import moment, {Moment} from 'moment';
import momentTimezone from 'moment-timezone';
import {getDate} from 'shared/util/date';
import {RangeKeyTimeRanges} from 'shared/util/constants';
import {TIME_RANGE_LABELS} from 'shared/util/constants';
import {utcFormat} from 'd3';

type TimeRange = {
	description: string;
	value: RangeKeyTimeRanges;
	label: string;
};

export function formatDateWithTimezone(
	timeZoneId: string,
	date = momentTimezone.utc()
): Moment {
	const currentDateWithTimeZone = momentTimezone.tz(date, timeZoneId);
	const hours = currentDateWithTimeZone.utcOffset() / 60;
	const signal = hours < 0 ? 'subtract' : 'add';

	return date.clone()[signal](Math.abs(hours), 'hours');
}

export function formatDateRange(date, rangeKey) {
	if (
		`${rangeKey}` === RangeKeyTimeRanges.Last24Hours ||
		`${rangeKey}` === RangeKeyTimeRanges.Yesterday
	) {
		return utcFormat('%d %b, %I %p')(date);
	}

	return utcFormat('%d %b')(date);
}

export function formatTimeRange(timeRange) {
	return timeRange
		.map(({endDate, rangeKey, startDate}) => {
			const start = formatDateRange(getDate(startDate), rangeKey);
			const end = formatDateRange(getDate(endDate), rangeKey);
			const label = `${start} - ${end}`;

			return {
				description: startDate && endDate && label,
				label: TIME_RANGE_LABELS[rangeKey],
				value: `${rangeKey}` as RangeKeyTimeRanges
			};
		})
		.sort((a, b) => parseInt(a.value) - parseInt(b.value));
}

const filterItemsByRetention = (
	timeRange: Array<TimeRange>,
	retentionPeriod: number
): Array<TimeRange> =>
	timeRange.filter(
		item =>
			!(
				retentionPeriod === 7 &&
				item.value === RangeKeyTimeRanges.LastYear
			)
	);

const filterLegacyItems = (
	timeRange: Array<TimeRange>,
	retentionPeriod: number
): Array<TimeRange> =>
	filterItemsByRetention(
		timeRange.filter(({value}) =>
			[
				RangeKeyTimeRanges.Last24Hours,
				RangeKeyTimeRanges.Yesterday,
				RangeKeyTimeRanges.Last7Days,
				RangeKeyTimeRanges.Last28Days,
				RangeKeyTimeRanges.Last30Days,
				RangeKeyTimeRanges.Last90Days
			].includes(value)
		),
		retentionPeriod
	);

const filterMoreItems = (
	timeRange: Array<TimeRange>,
	rangeKeys: Array<RangeKeyTimeRanges>,
	retentionPeriod: number
): Array<TimeRange> =>
	filterItemsByRetention(
		timeRange.filter(
			item =>
				!rangeKeys
					.filter(value => !rangeKeys.includes(value))
					.includes(item.value)
		),
		retentionPeriod
	);

const filterItems = (
	timeRange: Array<TimeRange>,
	rangeKey: RangeKeyTimeRanges,
	rangeKeys: Array<RangeKeyTimeRanges>,
	retentionPeriod: number
) =>
	filterItemsByRetention(
		timeRange.filter(
			({value}) => value === rangeKey || rangeKeys.includes(value)
		),
		retentionPeriod
	);

type GetFilteredItems = (props: {
	legacy: boolean;
	rangeKey: RangeKeyTimeRanges;
	rangeKeys?: Array<RangeKeyTimeRanges>;
	retentionPeriod: number;
	seeMore: boolean;
	timeRange: Array<TimeRange>;
}) => Array<TimeRange>;

export const getFilteredItems: GetFilteredItems = ({
	legacy,
	rangeKey,
	rangeKeys = [
		RangeKeyTimeRanges.Last24Hours,
		RangeKeyTimeRanges.Last7Days,
		RangeKeyTimeRanges.Last30Days,
		RangeKeyTimeRanges.Last90Days
	],
	retentionPeriod,
	seeMore,
	timeRange
}) => {
	if (legacy) {
		return filterLegacyItems(timeRange, retentionPeriod);
	}

	if (seeMore) {
		return filterMoreItems(timeRange, rangeKeys, retentionPeriod);
	}

	return filterItems(timeRange, rangeKey, rangeKeys, retentionPeriod);
};

export function getSelectedItem({rangeEnd, rangeKey, rangeStart, timeRange}) {
	if (rangeKey === 'CUSTOM') {
		return {
			label: `${moment(rangeStart).format('ll')} - ${moment(
				rangeEnd
			).format('ll')}`,
			value: 'CUSTOM'
		};
	}

	return timeRange.find(({value}) => value === rangeKey) || timeRange[0];
}

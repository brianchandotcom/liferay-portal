import {getDate} from 'shared/util/date';
import {RangeKeyTimeRanges, TIME_RANGE_LABELS} from 'shared/util/constants';
import {safeResultToProps} from 'shared/util/mappers';
import {utcFormat} from 'd3';

/**
 * Formatter Date
 * @param {date} date
 * @param {string} rangeKey
 */
const formatterDate = (date, rangeKey) => {
	let formatter;

	if (
		`${rangeKey}` === RangeKeyTimeRanges.Last24Hours ||
		`${rangeKey}` === RangeKeyTimeRanges.Yesterday
	) {
		formatter = utcFormat('%d %b, %I %p');
	} else {
		formatter = utcFormat('%d %b');
	}

	return formatter(date);
};

/**
 * Get Label Date
 * @param {date} start
 * @param {date} end
 * @param {string} rangeKey
 */
const getLabelDate = (start, end, rangeKey) => {
	const startDate = formatterDate(getDate(start), rangeKey);
	const endDate = formatterDate(getDate(end), rangeKey);

	return `${startDate} - ${endDate}`;
};

const mapResultToProps = safeResultToProps(({timeRange}) => {
	const items = timeRange
		.map(({endDate, rangeKey, startDate}) => {
			const description =
				startDate && endDate
					? getLabelDate(startDate, endDate, rangeKey)
					: null;

			return {
				description,
				label: TIME_RANGE_LABELS[rangeKey],
				value: `${rangeKey}`
			};
		})
		.sort((a, b) => parseInt(a.value) - parseInt(b.value));

	if (timeRange.length === 0) {
		return {empty: true};
	}

	return {
		items
	};
});

export {mapResultToProps};

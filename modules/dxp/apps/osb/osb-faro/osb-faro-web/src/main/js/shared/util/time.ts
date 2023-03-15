import {Interval} from 'shared/types';
import {RangeKeyTimeRanges, TimeIntervals} from 'shared/util/constants';

export const INTERVAL_KEY_MAP: {[s: string]: Interval} = {
	[TimeIntervals.Day]: 'D',
	[TimeIntervals.Month]: 'M',
	[TimeIntervals.Week]: 'W'
};

export const UNIT_LABELS: string[] = [
	Liferay.Language.get('seconds'),
	Liferay.Language.get('minutes'),
	Liferay.Language.get('hours')
];

export enum TimeUnits {
	Hours = 2,
	Minutes = 1,
	Seconds = 0
}

export function formatDuration(milliseconds: number, unit: number): number {
	switch (unit) {
		case TimeUnits.Hours:
			return milliseconds / (Math.pow(60, 2) * 1000);
		case TimeUnits.Minutes:
			return milliseconds / (60 * 1000);
		case TimeUnits.Seconds:
		default:
			return milliseconds / 1000;
	}
}

export function getRemainder(milliseconds: number, unit: number): number {
	switch (unit) {
		case TimeUnits.Hours:
			return milliseconds % (Math.pow(60, 2) * 1000);
		case TimeUnits.Minutes:
			return milliseconds % (60 * 1000);
		case TimeUnits.Seconds:
		default:
			return milliseconds % 1000;
	}
}

export function hasRemainder(milliseconds: number, unit: number): boolean {
	return !!getRemainder(milliseconds, unit);
}

export function getMilliseconds(value: number, unit: number): number {
	switch (unit) {
		case TimeUnits.Hours:
			return value * Math.pow(60, 2) * 1000;
		case TimeUnits.Minutes:
			return value * 60 * 1000;
		case TimeUnits.Seconds:
		default:
			return value * 1000;
	}
}

export function getMillisecondsFromTime(time: string): number {
	const [hours, minutes, seconds] = time.split(':');

	return (
		getMilliseconds(Number(hours), TimeUnits.Hours) +
		getMilliseconds(Number(minutes), TimeUnits.Minutes) +
		getMilliseconds(Number(seconds), TimeUnits.Seconds)
	);
}

export function getLargestNaturalUnit(
	milliseconds: number,
	unit: number = TimeUnits.Hours
) {
	if (hasRemainder(milliseconds, unit) && unit !== 0) {
		return getLargestNaturalUnit(milliseconds, unit - 1);
	}

	return unit;
}

export function getUnitLabel(unit: number): string {
	return UNIT_LABELS[unit];
}

export function isHourlyRangeKey(rangeKey: RangeKeyTimeRanges): boolean {
	return [
		RangeKeyTimeRanges.Last24Hours,
		RangeKeyTimeRanges.Yesterday
	].includes(rangeKey);
}

export function isMonthlyRangeKey(rangeKey: RangeKeyTimeRanges): boolean {
	return [
		RangeKeyTimeRanges.CustomRange,
		RangeKeyTimeRanges.Last28Days,
		RangeKeyTimeRanges.Last30Days,
		RangeKeyTimeRanges.Last90Days,
		RangeKeyTimeRanges.Last180Days,
		RangeKeyTimeRanges.LastYear
	].includes(rangeKey);
}

/**
 * Take milliseconds and converts it to duration time value.
 * @returns {string} Time in HH:MM:SS format.
 */
export function formatTime(milliseconds: number): string {
	const timeArray = [
		TimeUnits.Hours,
		TimeUnits.Minutes,
		TimeUnits.Seconds
	].map(unit => {
		const remainingTime =
			unit === TimeUnits.Hours
				? milliseconds
				: getRemainder(milliseconds, unit + 1);

		let formattedTime = formatDuration(remainingTime, unit);

		if (unit === TimeUnits.Seconds) {
			formattedTime = Math.round(formattedTime);
		} else {
			formattedTime = Math.trunc(formattedTime);
		}

		return String(formattedTime).padStart(2, '0');
	});

	return timeArray.join(':');
}

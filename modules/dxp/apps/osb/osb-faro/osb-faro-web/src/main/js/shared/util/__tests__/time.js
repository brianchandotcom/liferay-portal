import * as time from '../time';
import {RangeKeyTimeRanges} from 'shared/util/constants';

describe('time', () => {
	describe('formatDuration', () => {
		it.each`
			milliseconds | unit                      | retVal
			${3600000}   | ${time.TimeUnits.Seconds} | ${3600}
			${3600000}   | ${time.TimeUnits.Minutes} | ${60}
			${3600000}   | ${time.TimeUnits.Hours}   | ${1}
		`('format $milliseconds to $unit', ({milliseconds, retVal, unit}) => {
			expect(time.formatDuration(milliseconds, unit)).toBe(retVal);
		});
	});

	describe('hasRemainder', () => {
		it.each`
			milliseconds | unit                      | retVal
			${3600000}   | ${time.TimeUnits.Seconds} | ${false}
			${3600001}   | ${time.TimeUnits.Seconds} | ${true}
			${3600000}   | ${time.TimeUnits.Minutes} | ${false}
			${3600001}   | ${time.TimeUnits.Minutes} | ${true}
			${3600000}   | ${time.TimeUnits.Hours}   | ${false}
			${3600001}   | ${time.TimeUnits.Hours}   | ${true}
		`(
			'return $retVal when checking for remainders in $unit unit',
			({milliseconds, retVal, unit}) => {
				expect(time.hasRemainder(milliseconds, unit)).toBe(retVal);
			}
		);
	});

	describe('isHourlyRangeKey', () => {
		it('should return true if the rangeKey is hourly', () => {
			expect(time.isHourlyRangeKey(RangeKeyTimeRanges.Yesterday)).toBe(
				true
			);
			expect(time.isHourlyRangeKey(RangeKeyTimeRanges.Last24Hours)).toBe(
				true
			);
		});

		it('should return false if the rangeKey is not hourly', () => {
			expect(time.isHourlyRangeKey(RangeKeyTimeRanges.Last30Days)).toBe(
				false
			);
		});
	});

	describe('isMonthlyRangeKey', () => {
		it('should return true if the rangeKey is monthly', () => {
			expect(time.isMonthlyRangeKey(RangeKeyTimeRanges.Last28Days)).toBe(
				true
			);
			expect(time.isMonthlyRangeKey(RangeKeyTimeRanges.Last30Days)).toBe(
				true
			);
			expect(time.isMonthlyRangeKey(RangeKeyTimeRanges.Last90Days)).toBe(
				true
			);
		});

		it('should return false if the rangeKey is not monthly', () => {
			expect(time.isMonthlyRangeKey(RangeKeyTimeRanges.Last24Hours)).toBe(
				false
			);
			expect(time.isMonthlyRangeKey(RangeKeyTimeRanges.Last7Days)).toBe(
				false
			);
		});
	});

	describe('getMilliseconds', () => {
		it.each`
			duration | unit                      | retVal
			${3600}  | ${time.TimeUnits.Seconds} | ${3600000}
			${60}    | ${time.TimeUnits.Minutes} | ${3600000}
			${1}     | ${time.TimeUnits.Hours}   | ${3600000}
		`('format $unit to milliseconds', ({duration, retVal, unit}) => {
			expect(time.getMilliseconds(duration, unit)).toBe(retVal);
		});
	});

	describe('getMillisecondsFromTime', () => {
		it.each`
			value         | retVal
			${'01:00:00'} | ${3600000}
			${'00:01:00'} | ${60000}
			${'00:00:01'} | ${1000}
		`('format $time to milliseconds', ({retVal, value}) => {
			expect(time.getMillisecondsFromTime(value)).toBe(retVal);
		});
	});

	describe('getLargestNaturalUnit', () => {
		it.each`
			milliseconds | unit                      | retVal
			${16000}     | ${time.TimeUnits.Hours}   | ${time.TimeUnits.Seconds}
			${16000}     | ${time.TimeUnits.Minutes} | ${time.TimeUnits.Seconds}
			${16000}     | ${time.TimeUnits.Seconds} | ${time.TimeUnits.Seconds}
			${1800000}   | ${time.TimeUnits.Hours}   | ${time.TimeUnits.Minutes}
			${1800000}   | ${time.TimeUnits.Minutes} | ${time.TimeUnits.Minutes}
			${1800000}   | ${time.TimeUnits.Seconds} | ${time.TimeUnits.Seconds}
			${3600000}   | ${time.TimeUnits.Hours}   | ${time.TimeUnits.Hours}
			${1800000}   | ${time.TimeUnits.Minutes} | ${time.TimeUnits.Minutes}
			${1800000}   | ${time.TimeUnits.Seconds} | ${time.TimeUnits.Seconds}
		`(
			'return the largest natural unit start at $unit for $milliseconds milliseconds',
			({milliseconds, retVal, unit}) => {
				expect(time.getLargestNaturalUnit(milliseconds, unit)).toBe(
					retVal
				);
			}
		);
	});

	describe('getUnitLabel', () => {
		it.each`
			unit                      | label
			${time.TimeUnits.Seconds} | ${time.UNIT_LABELS[0]}
			${time.TimeUnits.Minutes} | ${time.UNIT_LABELS[1]}
			${time.TimeUnits.Hours}   | ${time.UNIT_LABELS[2]}
		`('return $label for $unit', ({label, unit}) => {
			expect(time.getUnitLabel(unit)).toBe(label);
		});
	});

	describe('getRemainder', () => {
		it.each`
			milliseconds | unit                      | retVal
			${3600000}   | ${time.TimeUnits.Seconds} | ${0}
			${3600001}   | ${time.TimeUnits.Seconds} | ${1}
			${3600000}   | ${time.TimeUnits.Minutes} | ${0}
			${3600001}   | ${time.TimeUnits.Minutes} | ${1}
			${3600000}   | ${time.TimeUnits.Hours}   | ${0}
			${3600001}   | ${time.TimeUnits.Hours}   | ${1}
		`(
			'return $retVal when checking for remainders in $unit unit',
			({milliseconds, retVal, unit}) => {
				expect(time.getRemainder(milliseconds, unit)).toBe(retVal);
			}
		);
	});

	describe('formatTime', () => {
		it.each`
			milliseconds  | retVal
			${3600000}    | ${'01:00:00'}
			${3600}       | ${'00:00:04'}
			${1800000}    | ${'00:30:00'}
			${1801000}    | ${'00:30:01'}
			${1231231230} | ${'342:00:31'}
		`(
			'return $retVal when formatting $milliseconds to duration time',
			({milliseconds, retVal}) => {
				expect(time.formatTime(milliseconds)).toBe(retVal);
			}
		);
	});
});

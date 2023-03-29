import * as utils from '../utils';
import {CHART_COLOR_NAMES} from 'shared/components/Chart';

const {
	martell,
	martellD2,
	martellL2,
	martellL4,
	mormont,
	mormontD2,
	mormontL2,
	mormontL4,
	stark,
	starkD2,
	starkL2,
	starkL4
} = CHART_COLOR_NAMES;

describe('utils', () => {
	describe('formatDate', () => {
		it.each`
			date            | interval       | abbreviated | formattedDate
			${'2019-12-12'} | ${utils.DAY}   | ${false}    | ${'December 12'}
			${'2019-12-12'} | ${utils.DAY}   | ${true}     | ${'Dec 12'}
			${'2019-12-12'} | ${utils.WEEK}  | ${false}    | ${'Dec 12 - Dec 19'}
			${'2019-12-12'} | ${utils.WEEK}  | ${true}     | ${'Dec 12 - Dec 19'}
			${'2019-12-12'} | ${utils.MONTH} | ${false}    | ${'December'}
			${'2019-12-12'} | ${utils.MONTH} | ${true}     | ${'Dec'}
		`(
			'returns $formattedDate when date $date with $interval and abbreviated is $abbreviated',
			({abbreviated, date, formattedDate, interval}) => {
				expect(utils.formatDate(date, interval, abbreviated)).toBe(
					formattedDate
				);
			}
		);
	});

	describe('getPeriodLabel', () => {
		it.each`
			period | interval       | label
			${0}   | ${utils.DAY}   | ${'Day 0'}
			${0}   | ${utils.MONTH} | ${'Month 0'}
			${0}   | ${utils.WEEK}  | ${'Week 0'}
		`(
			'returns $label when interval is $interval and period is $period',
			({interval, label, period}) => {
				expect(utils.getPeriodLabel(period, interval)).toBe(label);
			}
		);
	});

	describe('getColorHex', () => {
		it.each`
			retention | visitorsType                | hexColor
			${13}     | ${utils.VISITORS}           | ${martellL4}
			${13}     | ${utils.ANONYMOUS_VISITORS} | ${mormontL4}
			${13}     | ${utils.KNOWN_VISITORS}     | ${starkL4}
			${30}     | ${utils.VISITORS}           | ${martellL2}
			${30}     | ${utils.ANONYMOUS_VISITORS} | ${mormontL2}
			${30}     | ${utils.KNOWN_VISITORS}     | ${starkL2}
			${60}     | ${utils.VISITORS}           | ${martell}
			${60}     | ${utils.ANONYMOUS_VISITORS} | ${mormont}
			${60}     | ${utils.KNOWN_VISITORS}     | ${stark}
			${80}     | ${utils.VISITORS}           | ${martellD2}
			${80}     | ${utils.ANONYMOUS_VISITORS} | ${mormontD2}
			${80}     | ${utils.KNOWN_VISITORS}     | ${starkD2}
		`(
			'returns $hexColor when retention is $retention and visitorsType is $visitorsTyp',
			({hexColor, retention, visitorsType}) => {
				expect(utils.getColorHex(retention, visitorsType)).toBe(
					hexColor
				);
			}
		);
	});
});

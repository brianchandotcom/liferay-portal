import {
	formatChange,
	getFinitePercentChange,
	getNetChange,
	getSafeChange
} from '../change';

describe('change', () => {
	describe('formatChange', () => {
		it.each`
			change  | expected
			${0}    | ${0}
			${1230} | ${'+1,230'}
			${-987} | ${'-987'}
		`('should format $change to $expected', ({change, expected}) => {
			expect(formatChange(change)).toBe(expected);
		});
	});

	describe('getFinitePercentChange', () => {
		it.each`
			prevVal | curVal | expected
			${0}    | ${0}   | ${null}
			${0}    | ${10}  | ${null}
			${10}   | ${0}   | ${'-100.0'}
			${15}   | ${5}   | ${'-66.7'}
			${5}    | ${15}  | ${'200.0'}
		`(
			'should calculate the percent change from $prevVal to $curVal and return $expected',
			({curVal, expected, prevVal}) => {
				expect(getFinitePercentChange(curVal, prevVal)).toBe(expected);
			}
		);
	});

	describe('getNetChange', () => {
		it.each`
			prev    | cur     | expected
			${0}    | ${100}  | ${['+100', 100]}
			${null} | ${100}  | ${['+100', 100]}
			${0}    | ${null} | ${undefined}
			${80}   | ${100}  | ${['+20', 25]}
			${80}   | ${40}   | ${['-40', 50]}
		`(
			'should calculate the net change of $prev to $cur and return $expected',
			({cur, expected, prev}) => {
				expect(getNetChange(prev, cur)).toEqual(expected);
			}
		);
	});

	describe('getSafeChange', () => {
		it.each`
			change       | expected
			${NaN}       | ${0}
			${null}      | ${0}
			${undefined} | ${0}
			${0.2}       | ${0.2}
			${Infinity}  | ${Infinity}
		`(
			'should return a safe value, $expected, for $change',
			({change, expected}) => {
				expect(getSafeChange(change, expected)).toBe(expected);
			}
		);
	});
});

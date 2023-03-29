import dom from 'metal-dom';
import {
	formatStringToLowercase,
	getAlignPosition,
	getPercentage,
	getRangeSelectorsFromQuery,
	getSafeDisplayValue,
	getSafeRangeSelectors,
	isBlank,
	isEllipisActive,
	normalizeRangeSelectors,
	truncateText
} from '../util';

describe('util', () => {
	describe('formatStringToLowercase', () => {
		it('should format a string to lowercase', () => {
			const text = '   THIS IS A NOT LOWERCASE TEXT   ';
			const lowercaseText = formatStringToLowercase(text);

			expect(lowercaseText).toEqual('this is a not lowercase text');
		});
	});

	describe('getAlignPosition', () => {
		let source;
		let target;

		afterEach(function () {
			dom.exitDocument(source);
			dom.exitDocument(target);
		});

		beforeEach(function () {
			dom.enterDocument(
				`<div id="source" class="popover clay-popover-top hide analytics-popover no-content">
				<div class="arrow"></div>
				<div class="popover-header">popover header</div>
			</div>`
			);

			dom.enterDocument(
				`<a id="target" data-title="https://www.liferay.com/products/" data-touchpoint="https://www.liferay.com/products/" href="/project/35317/pages/overview/https%3A%2F%2Fwww.liferay.com%2Fproducts" class="table-title">
				<h5 class="mb-1 text-truncate" ref="title">
					https://www.liferay.com/products/
				</h5>
			</a>`
			);

			source = dom.toElement('#source');
			target = dom.toElement('#target');
		});

		it('should return an align position top when it dont have a suggested position', () => {
			expect(getAlignPosition(source, target)).toEqual('top');
		});

		it('should return an align position bottom when it have a suggested position', () => {
			expect(getAlignPosition(source, target, 'bottom')).toEqual(
				'bottom'
			);
		});
	});

	describe('getPercentage', () => {
		it('should convert number to percent passing current number and total number', () => {
			const number1 = 50;
			const number2 = 1000;
			const percent = getPercentage(number1, number2);

			expect(percent).toEqual(5);
		});

		it('should return number 0 if number is invalid, passing current number and total number', () => {
			const number1 = 0;
			const number2 = 0;
			const percent = getPercentage(number1, number2);

			expect(percent).toEqual(0);
		});
	});

	describe('getSafeDisplayValue', () => {
		it.each`
			value        | expected
			${0}         | ${0}
			${123}       | ${123}
			${undefined} | ${'-'}
			${null}      | ${'-'}
			${''}        | ${'-'}
			${'test'}    | ${'test'}
		`(
			'should return $expected if the value is $value',
			({expected, value}) => {
				expect(getSafeDisplayValue(value, '-')).toBe(expected);
			}
		);
	});

	describe('isBlank', () => {
		it.each`
			value        | expected
			${0}         | ${false}
			${123}       | ${false}
			${undefined} | ${true}
			${null}      | ${true}
			${''}        | ${true}
			${'test'}    | ${false}
		`(
			'should return $expected if the value is $value',
			({expected, value}) => {
				expect(isBlank(value)).toBe(expected);
			}
		);
	});

	describe('isEllipisActive', () => {
		it('should return true if is an ellipsis', () => {
			const event = {
				target: {
					offsetWidth: 100,
					scrollWidth: 200
				}
			};

			expect(isEllipisActive(event)).toBeTruthy();
		});
	});

	describe('truncateText', () => {
		it('should truncate the text', () => {
			const text = 'this is a text that should be truncate';
			const truncatedText = truncateText(text, 25);

			expect(truncatedText).toEqual('this is a text that sh...');
		});

		it('should truncate the text by adding a dot at the end of the text', () => {
			const text = 'this is a text that should be truncate';
			const truncatedText = truncateText(text, 25, '.');

			expect(truncatedText).toEqual('this is a text that shou.');
		});

		it('should truncate the text when it reaches 100 letters', () => {
			const text =
				'this is a text that should be truncate, this is a text that should be truncate, this is a text that should be truncate';
			const truncatedText = truncateText(text);

			expect(truncatedText).toEqual(
				'this is a text that should be truncate, this is a text that should be truncate, this is a text th...'
			);
		});

		it('should not truncate text', () => {
			const text = 'this is a not truncate text';
			const truncatedText = truncateText(text, 30);

			expect(truncatedText).toEqual('this is a not truncate text');
		});
	});

	describe('getRangeSelectorsFromQuery', () => {
		it.each`
			rangeEnd        | rangeKey    | rangeStart      | results
			${''}           | ${'30'}     | ${''}           | ${{rangeEnd: '', rangeKey: '30', rangeStart: ''}}
			${'null'}       | ${'90'}     | ${'null'}       | ${{rangeEnd: null, rangeKey: '90', rangeStart: null}}
			${'2020-04-04'} | ${'CUSTOM'} | ${'2020-04-01'} | ${{rangeEnd: '2020-04-04', rangeKey: 'CUSTOM', rangeStart: '2020-04-01'}}
		`(
			'should convert $rangeEnd, $rangeKey, & $rangeStart to $results',
			({rangeEnd, rangeKey, rangeStart, results}) => {
				expect(
					getRangeSelectorsFromQuery({rangeEnd, rangeKey, rangeStart})
				).toMatchObject(results);
			}
		);
	});

	describe('getSafeRangeSelectors', () => {
		it.each`
			rangeEnd        | rangeKey    | rangeStart      | results
			${''}           | ${'30'}     | ${''}           | ${{rangeEnd: null, rangeKey: 30, rangeStart: null}}
			${null}         | ${'90'}     | ${null}         | ${{rangeEnd: null, rangeKey: 90, rangeStart: null}}
			${'2020-04-04'} | ${'CUSTOM'} | ${'2020-04-01'} | ${{rangeEnd: '2020-04-04', rangeKey: null, rangeStart: '2020-04-01'}}
		`(
			'should convert $rangeEnd, $rangeKey, & $rangeStart to $results',
			({rangeEnd, rangeKey, rangeStart, results}) => {
				expect(
					getSafeRangeSelectors({rangeEnd, rangeKey, rangeStart})
				).toMatchObject(results);
			}
		);
	});

	describe('normalizeRangeSelectors', () => {
		it.each`
			rangeEnd        | rangeKey | rangeStart      | results
			${null}         | ${30}    | ${null}         | ${{rangeEnd: '', rangeKey: '30', rangeStart: ''}}
			${'2020-04-04'} | ${null}  | ${'2020-04-01'} | ${{rangeEnd: '2020-04-04', rangeKey: 'CUSTOM', rangeStart: '2020-04-01'}}
		`(
			'should convert $rangeEnd, $rangeKey, & $rangeStart to $results',
			({rangeEnd, rangeKey, rangeStart, results}) => {
				expect(
					normalizeRangeSelectors({rangeEnd, rangeKey, rangeStart})
				).toMatchObject(results);
			}
		);
	});
});

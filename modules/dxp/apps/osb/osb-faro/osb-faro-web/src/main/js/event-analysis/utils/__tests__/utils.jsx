import * as utils from '../utils';
import {
	AttributeOwnerTypes,
	DataTypes,
	DateGroupings,
	Operators
} from '../types';

describe('utils', () => {
	describe('formatDateName', () => {
		it.each`
			name            | dateGrouping           | result
			${'2020-11-22'} | ${DateGroupings.Day}   | ${'Nov 22, 2020'}
			${'2020-11'}    | ${DateGroupings.Month} | ${'Nov 2020'}
			${'2020'}       | ${DateGroupings.Year}  | ${'2020'}
		`(
			'returns $result for $name & $dateGrouping',
			({dateGrouping, name, result}) => {
				expect(utils.formatDateName(name, dateGrouping)).toEqual(
					result
				);
			}
		);
	});

	describe('formatDurationName', () => {
		it.each`
			name                       | result
			${'6000 -12000'}           | ${'00:00:06 - 00:00:12'}
			${'123123 - 321321'}       | ${'00:02:03 - 00:05:21'}
			${'123123123 - 321123123'} | ${'34:12:03 - 89:12:03'}
		`('returns $result for $name', ({name, result}) => {
			expect(utils.formatDurationName(name)).toEqual(result);
		});
	});

	describe('formatBreakdownNameByDataType', () => {
		it.each`
			name              | breakdown                                                      | result
			${'2020-11-22'}   | ${{dataType: DataTypes.Date, dateGrouping: DateGroupings.Day}} | ${'Nov 22, 2020'}
			${'6000 - 12000'} | ${{dataType: DataTypes.Duration}}                              | ${'00:00:06 - 00:00:12'}
			${123}            | ${{dataType: DataTypes.Number}}                                | ${123}
			${true}           | ${{dataType: DataTypes.Boolean}}                               | ${true}
			${'undefined'}    | ${{dataType: DataTypes.Duration}}                              | ${'undefined'}
			${'undefined'}    | ${{dataType: DataTypes.Boolean}}                               | ${'undefined'}
			${'undefined'}    | ${{dataType: DataTypes.Date}}                                  | ${'undefined'}
			${'undefined'}    | ${{dataType: DataTypes.Number}}                                | ${'undefined'}
			${'undefined'}    | ${{dataType: DataTypes.String}}                                | ${'undefined'}
		`('returns $result for $name', ({breakdown, name, result}) => {
			expect(
				utils.formatBreakdownNameByDataType(name, breakdown)
			).toEqual(result);
		});
	});

	describe('getFilterDisplay', () => {
		it.each`
			dataType              | attributeType                     | operator                 | values                          | result
			${DataTypes.Boolean}  | ${AttributeOwnerTypes.Account}    | ${Operators.EQ}          | ${[true]}                       | ${['Account | Test', 'True']}
			${DataTypes.Boolean}  | ${AttributeOwnerTypes.Account}    | ${Operators.EQ}          | ${[false]}                      | ${['Account | Test', 'False']}
			${DataTypes.Date}     | ${AttributeOwnerTypes.Event}      | ${Operators.Between}     | ${['2021-01-20', '2021-01-24']} | ${['Event | Test', 'Jan 20, 2021 - Jan 24, 2021']}
			${DataTypes.Date}     | ${AttributeOwnerTypes.Event}      | ${Operators.Between}     | ${['2021-01-20', '2021-01-24']} | ${['Event | Test', 'Jan 20, 2021 - Jan 24, 2021']}
			${DataTypes.Date}     | ${AttributeOwnerTypes.Event}      | ${Operators.EQ}          | ${['2021-01-20']}               | ${['Event | Test', '= Jan 20, 2021']}
			${DataTypes.Date}     | ${AttributeOwnerTypes.Event}      | ${Operators.GT}          | ${['2021-01-20']}               | ${['Event | Test', 'after Jan 20, 2021']}
			${DataTypes.Date}     | ${AttributeOwnerTypes.Event}      | ${Operators.LT}          | ${['2021-01-20']}               | ${['Event | Test', 'before Jan 20, 2021']}
			${DataTypes.Duration} | ${AttributeOwnerTypes.Session}    | ${Operators.GT}          | ${[123123]}                     | ${['Session | Test', '> 00:02:03']}
			${DataTypes.Duration} | ${AttributeOwnerTypes.Session}    | ${Operators.LT}          | ${[123123123]}                  | ${['Session | Test', '< 34:12:03']}
			${DataTypes.Number}   | ${AttributeOwnerTypes.Individual} | ${Operators.Between}     | ${[120, 200]}                   | ${['Individual | Test', '120 - 200']}
			${DataTypes.Number}   | ${AttributeOwnerTypes.Individual} | ${Operators.GT}          | ${[120]}                        | ${['Individual | Test', '> 120']}
			${DataTypes.Number}   | ${AttributeOwnerTypes.Individual} | ${Operators.LT}          | ${[120]}                        | ${['Individual | Test', '< 120']}
			${DataTypes.String}   | ${AttributeOwnerTypes.Event}      | ${Operators.Contains}    | ${['Hello World']}              | ${['Event | Test', 'contains "Hello World"']}
			${DataTypes.String}   | ${AttributeOwnerTypes.Event}      | ${Operators.NotContains} | ${['Hello World']}              | ${['Event | Test', 'not contains "Hello World"']}
			${DataTypes.String}   | ${AttributeOwnerTypes.Event}      | ${Operators.EQ}          | ${['Hello World']}              | ${['Event | Test', 'is "Hello World"']}
			${DataTypes.String}   | ${AttributeOwnerTypes.Event}      | ${Operators.NE}          | ${['Hello World']}              | ${['Event | Test', 'is not "Hello World"']}
		`(
			'returns $result for $dataType, $type, $operator, $value',
			({attributeType, dataType, operator, result, values}) => {
				expect(
					utils.getFilterDisplay(
						{
							displayName: 'Test'
						},
						{attributeType, dataType, operator, values}
					)
				).toEqual(result);
			}
		);
	});

	describe('getBreakdownDisplay', () => {
		it.each`
			type                              | result
			${AttributeOwnerTypes.Account}    | ${['Account', 'Test']}
			${AttributeOwnerTypes.Event}      | ${['Event', 'Test']}
			${AttributeOwnerTypes.Session}    | ${['Session', 'Test']}
			${AttributeOwnerTypes.Individual} | ${['Individual', 'Test']}
		`('returns $result for attributeOwnerType $type', ({result, type}) => {
			expect(
				utils.getBreakdownDisplay(
					{
						displayName: 'Test'
					},
					type
				)
			).toEqual(result);
		});
	});

	describe('isAttribute', () => {
		it('returns true when item is an attribute', () => {
			expect(
				utils.isAttribute({
					dataType: 'STRING',
					name: 'Test'
				})
			).toBeTruthy();
		});

		it('returns false when item is not an attribute', () => {
			expect(
				utils.isAttribute({
					name: 'Test'
				})
			).toBeFalse();
		});
	});

	describe('createBooleanBreakdown', () => {
		it('returns a boolean breakdown', () => {
			const attributeId = '123';
			const attributeType = AttributeOwnerTypes.Event;

			expect(
				utils.createBooleanBreakdown({
					attributeId,
					attributeType
				})
			).toEqual({
				attributeId,
				attributeType,
				binSize: null,
				dataType: DataTypes.Boolean,
				dateGrouping: null,
				sortType: 'DESC'
			});
		});
	});

	describe('createDateBreakdown', () => {
		it('returns a date breakdown', () => {
			const attributeId = '123';
			const attributeType = AttributeOwnerTypes.Event;

			expect(
				utils.createDateBreakdown({
					attributeId,
					attributeType
				})
			).toEqual({
				attributeId,
				attributeType,
				binSize: null,
				dataType: DataTypes.Date,
				dateGrouping: DateGroupings.Month,
				sortType: 'DESC'
			});
		});
	});

	describe('createDurationBreakdown', () => {
		it('returns a duration breakdown', () => {
			const attributeId = '123';
			const attributeType = AttributeOwnerTypes.Event;

			expect(
				utils.createDurationBreakdown({
					attributeId,
					attributeType
				})
			).toEqual({
				attributeId,
				attributeType,
				binSize: 60000,
				dataType: DataTypes.Duration,
				dateGrouping: null,
				sortType: 'DESC'
			});
		});
	});

	describe('createNumberBreakdown', () => {
		it('returns a number breakdown', () => {
			const attributeId = '123';
			const attributeType = AttributeOwnerTypes.Event;

			expect(
				utils.createNumberBreakdown({
					attributeId,
					attributeType
				})
			).toEqual({
				attributeId,
				attributeType,
				binSize: 10,
				dataType: DataTypes.Number,
				dateGrouping: null,
				sortType: 'DESC'
			});
		});
	});

	describe('createStringBreakdown', () => {
		it('returns a string breakdown', () => {
			const attributeId = '123';
			const attributeType = AttributeOwnerTypes.Event;

			expect(
				utils.createStringBreakdown({
					attributeId,
					attributeType
				})
			).toEqual({
				attributeId,
				attributeType,
				binSize: null,
				dataType: DataTypes.String,
				dateGrouping: null,
				sortType: 'DESC'
			});
		});
	});
});

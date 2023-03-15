import * as data from 'test/data';
import * as ODataUtil from '../odata';
import * as Utils from '../utils';
import {CustomValue} from 'shared/util/records';
import {List, Map} from 'immutable';

function testConversionToAndFrom(testQuery, queryConjunction) {
	const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

	const translatedString = ODataUtil.buildQueryString(
		[translatedMap],
		queryConjunction
	);

	expect(translatedString).toEqual(testQuery);
}

describe('odata', () => {
	beforeAll(() => {
		Utils.generateGroupId = jest.fn(() => 'group_01');
		Utils.generateRowId = jest.fn(() => 'row_01');
	});

	describe('convertBetweenToSubstring', () => {
		it('should convert between to substring', () => {
			expect(
				ODataUtil.convertBetweenToSubstring(
					"between(date,'2020-12-12','2020-12-16')"
				)
			).toEqual("substring(date,'2020-12-12','2020-12-16')");
		});

		it('should not convert between to substring if missing params', () => {
			expect(
				ODataUtil.convertBetweenToSubstring('between(date)')
			).toEqual('between(date)');
		});
	});

	describe('trimSpacesBeforeParams', () => {
		it('should trims spaces before params', () => {
			expect(
				ODataUtil.trimSpacesBeforeParams(
					"accounts.filterByCount(filter='activityKey eq ''Page#pageViewed#12341234''', operator='lt', value=2)"
				)
			).toEqual(
				"accounts.filterByCount(filter='activityKey eq ''Page#pageViewed#12341234''',operator='lt',value=2)"
			);
		});

		it('should return the oData string as is if no spaces exist before params', () => {
			const queryString =
				"accounts.filterByCount(filter='activityKey eq ''Page#pageViewed#12341234''',operator='lt',value=2)";

			expect(ODataUtil.trimSpacesBeforeParams(queryString)).toEqual(
				queryString
			);
		});
	});

	describe('buildQueryString', () => {
		it('should build a query string from a flat criteria map', () => {
			expect(
				ODataUtil.buildQueryString([data.mockNewCriteria(1)])
			).toEqual("(firstName eq 'test')");
			expect(
				ODataUtil.buildQueryString([data.mockNewCriteria(3)])
			).toEqual(
				"(firstName eq 'test' and firstName eq 'test' and firstName eq 'test')"
			);
		});

		it('should build a query string from a criteria map with nested items', () => {
			expect(
				ODataUtil.buildQueryString([data.mockNewCriteriaNested()])
			).toEqual(
				"((((firstName eq 'test' or firstName eq 'test') and firstName eq 'test') or firstName eq 'test') and firstName eq 'test')"
			);
		});

		it('should build a query string and only wrap strings in single quotes', () => {
			expect(
				ODataUtil.buildQueryString([data.mockNewCriteria(1)])
			).toEqual("(firstName eq 'test')");

			expect(
				ODataUtil.buildQueryString([
					data.mockNewCriteria(1, {value: null})
				])
			).toEqual('(firstName eq null)');

			expect(
				ODataUtil.buildQueryString([
					data.mockNewCriteria(1, {value: 123})
				])
			).toEqual('(firstName eq 123)');
		});
	});

	describe('escapeSingleQuotes', () => {
		it('should escape all single quotes in a given string', () => {
			expect(ODataUtil.escapeSingleQuotes("o'high o'hara")).toEqual(
				"o''high o''hara"
			);
		});
	});

	describe('toCriteria', () => {
		it('should return a parsed criteria', () => {});
	});

	describe('translateQueryToCriteria', () => {
		it('should translate a query string into a criteria map', () => {
			expect(
				ODataUtil.translateQueryToCriteria("(firstName eq 'test')")
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: 'test'
					}
				]
			});
		});

		it('should handle a query string with a null value', () => {
			expect(
				ODataUtil.translateQueryToCriteria('(firstName eq null)')
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: null
					}
				]
			});
		});

		it('should handle a query string with a number value', () => {
			expect(
				ODataUtil.translateQueryToCriteria('(firstName eq 123)')
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: 123
					}
				]
			});
		});

		it('should handle a query string with empty groups', () => {
			expect(
				ODataUtil.translateQueryToCriteria("(((firstName eq 'test')))")
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: 'test'
					}
				]
			});
		});

		it('should handle a query string with "ne" operator', () => {
			expect(
				ODataUtil.translateQueryToCriteria("firstName ne 'test'")
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'ne',
						propertyName: 'firstName',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: 'test'
					}
				]
			});
		});

		it('should handle a query string with "contains" operator', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"contains(firstName, 'test')"
				)
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'contains',
						propertyName: 'firstName',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: 'test'
					}
				]
			});
		});

		it('should return null if the query is empty or invalid', () => {
			expect(ODataUtil.translateQueryToCriteria()).toEqual(null);
			expect(ODataUtil.translateQueryToCriteria('()')).toEqual(null);
			expect(
				ODataUtil.translateQueryToCriteria(
					"(firstName eq 'test' eq 'test')"
				)
			).toEqual(null);
			expect(
				ODataUtil.translateQueryToCriteria("(firstName = 'test')")
			).toEqual(null);
		});

		it('should handle a query string with an activities-filter-by-count custom function', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"activities.filterByCount(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')',operator='lt',value=2)"
				)
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'activities-filter-by-count',
						propertyName: 'activityKey',
						rowId: 'row_01',
						touched: {asset: false, occurenceCount: false},
						valid: {asset: true, occurenceCount: true},
						value: new CustomValue({
							criterionGroup: new Map({
								conjunctionName: 'and',
								criteriaGroupId: 'group_01',
								items: new List([
									new Map({
										operatorName: 'eq',
										propertyName: 'activityKey',
										rowId: 'row_01',
										touched: false,
										valid: true,
										value:
											'Page#pageViewed#348853654381438580'
									})
								])
							}),
							operator: 'lt',
							value: 2
						})
					}
				]
			});
		});

		it('should handle a query string with a not-activities-filter-by-count custom function', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"not activities.filterByCount(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')',operator='lt',value=2)"
				)
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'not-activities-filter-by-count',
						propertyName: 'activityKey',
						rowId: 'row_01',
						touched: {asset: false, occurenceCount: false},
						valid: {asset: true, occurenceCount: true},
						value: new CustomValue({
							criterionGroup: new Map({
								conjunctionName: 'and',
								criteriaGroupId: 'group_01',
								items: new List([
									new Map({
										operatorName: 'eq',
										propertyName: 'activityKey',
										rowId: 'row_01',
										touched: false,
										valid: true,
										value:
											'Page#pageViewed#348853654381438580'
									})
								])
							}),
							operator: 'lt',
							value: 2
						})
					}
				]
			});
		});

		it('should handle a query string with an accounts-filter-by-count custom function', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"accounts.filterByCount(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')',operator='lt',value=2)"
				)
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'accounts-filter-by-count',
						propertyName: 'activityKey',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: new CustomValue({
							criterionGroup: new Map({
								conjunctionName: 'and',
								criteriaGroupId: 'group_01',
								items: new List([
									new Map({
										operatorName: 'eq',
										propertyName: 'activityKey',
										rowId: 'row_01',
										touched: false,
										valid: true,
										value:
											'Page#pageViewed#348853654381438580'
									})
								])
							}),
							operator: 'lt',
							value: 2
						})
					}
				]
			});
		});

		it('should handle a query string with a not-accounts-filter-by-count custom function', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"not accounts.filterByCount(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')',operator='lt',value=2)"
				)
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'not-accounts-filter-by-count',
						propertyName: 'activityKey',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: new CustomValue({
							criterionGroup: new Map({
								conjunctionName: 'and',
								criteriaGroupId: 'group_01',
								items: new List([
									new Map({
										operatorName: 'eq',
										propertyName: 'activityKey',
										rowId: 'row_01',
										touched: false,
										valid: true,
										value:
											'Page#pageViewed#348853654381438580'
									})
								])
							}),
							operator: 'lt',
							value: 2
						})
					}
				]
			});
		});

		it('should handle a query string with an accounts-filter custom function', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"accounts.filter(filter='(id eq ''48853654381438580'')')"
				)
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'accounts-filter',
						propertyName: 'id',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: new CustomValue({
							criterionGroup: new Map({
								conjunctionName: 'and',
								criteriaGroupId: 'group_01',
								items: new List([
									new Map({
										operatorName: 'eq',
										propertyName: 'id',
										rowId: 'row_01',
										touched: false,
										valid: true,
										value: '48853654381438580'
									})
								])
							})
						})
					}
				]
			});
		});

		it('should handle a query string with a not-accounts-filter custom function', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"not (accounts.filter(filter='(id eq ''48853654381438580'')'))"
				)
			).toEqual({
				conjunctionName: 'and',
				criteriaGroupId: 'group_01',
				items: [
					{
						operatorName: 'not-accounts-filter',
						propertyName: 'id',
						rowId: 'row_01',
						touched: false,
						valid: true,
						value: new CustomValue({
							criterionGroup: new Map({
								conjunctionName: 'and',
								criteriaGroupId: 'group_01',
								items: new List([
									new Map({
										operatorName: 'eq',
										propertyName: 'id',
										rowId: 'row_01',
										touched: false,
										valid: true,
										value: '48853654381438580'
									})
								])
							})
						})
					}
				]
			});
		});
	});

	describe('conversion to and from', () => {
		it('should be able to translate a query string to map and back to string', () => {
			const testQuery = "(firstName eq 'test')";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a complex query string to map and back to string', () => {
			const testQuery =
				"((firstName eq 'test' or firstName eq 'test') and firstName eq 'test')";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "not" to map and back to string', () => {
			const testQuery = "((not contains(firstName, 'test')))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a complex query string with "not" to map and back to string', () => {
			const testQuery =
				"(firstName eq 'test' and ((not contains(lastName, 'foo')) or (not contains(lastName, 'bar'))))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "contains" to map and back to string', () => {
			const testQuery = "(contains(firstName, 'test'))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "not contains" to map and back to string', () => {
			const testQuery = "((not contains(firstName, 'test')))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "accounts.filterByCount" to map and back to string', () => {
			const testQuery =
				"(accounts.filterByCount(filter='(id eq ''48853654381438580'')',operator='lt',value='2'))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "not accounts.filterByCount" to map and back to string', () => {
			const testQuery =
				"((not accounts.filterByCount(filter='(id eq ''48853654381438580'')',operator='lt',value='2')))";

			testConversionToAndFrom(testQuery);
		});

		fit('should be able to translate a query string with "accounts.filter" to map and back to string', () => {
			const testQuery =
				"(accounts.filter(filter='(id eq ''48853654381438580'')'))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "not accounts.filter" to map and back to string', () => {
			const testQuery =
				"((not accounts.filter(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')')))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "activities.filter" to map and back to string', () => {
			const testQuery =
				"(activities.filter(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')'))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "not activities.filter" to map and back to string', () => {
			const testQuery =
				"((not activities.filter(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')')))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "activities.filterByCount" to map and back to string', () => {
			const testQuery =
				"(activities.filterByCount(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')',operator='lt',value='2'))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "not activities.filterByCount" to map and back to string', () => {
			const testQuery =
				"((not activities.filterByCount(filter='(activityKey eq ''Page#pageViewed#348853654381438580'')',operator='lt',value='2')))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with "between" to a map and back to a string', () => {
			const testQuery = "(between(date,'2020-2-2','2020-2-3'))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with a nested "between" to a map and back to a string', () => {
			const testQuery =
				"(accounts.filterByCount(filter='(activityKey eq ''Page#pageViewed#348853654381438580'' and between(date,''2020-1-4'',''2020-1-6''))',operator='lt',value=2))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string that contains a single quote', () => {
			const testQuery = "(firstName eq 'o''hara')";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a nested query string that contains a single quote', () => {
			const testQuery =
				"(accounts.filter(filter='(organization/accountName/value ne ''o''''hara'')'))";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a query string with special characters', () => {
			const testQuery = "(firstName eq 'test+/?%#&')";

			testConversionToAndFrom(testQuery);
		});

		it('should be able to translate a nested query string with special characters', () => {
			const testQuery =
				"(accounts.filter(filter='(organization/description/value ne ''test+/?%#&'')'))";

			testConversionToAndFrom(testQuery);
		});

		xit('should be able to translate a collection type query string with "contains" to map and back to string', () => {
			const testQuery =
				"(cookies/any(c:contains(c, 'keyTest=valueTest')))";

			testConversionToAndFrom(testQuery);
		});

		xit('should be able to translate a collection type query string with "not contains" to map and back to string', () => {
			const testQuery =
				"((not (cookies/any(c:contains(c, 'keyTest=valueTest')))))";

			testConversionToAndFrom(testQuery);
		});

		xit('should be able to translate a collection type query string with "eq" to map and back to string', () => {
			const testQuery = "(cookies/any(c:c eq 'keyTest=valueTest'))";

			testConversionToAndFrom(testQuery);
		});

		xit('should be able to translate a collection type query string with "not" to map and back to string', () => {
			const testQuery =
				"((not (cookies/any(c:c eq 'keyTest=valueTest'))))";

			testConversionToAndFrom(testQuery);
		});

		xit('should be able to translate a nested and complex collection type query string to map and back to string', () => {
			const testQuery =
				"((not (cookies/any(c:c eq 'keyTest1=valueTest1'))) and ((not (cookies/any(c:c eq 'keyTest2=valueTest2'))) or (cookies/any(c:c eq 'keyTest3=valueTest3') and cookies/any(c:c eq 'keyTest4=valueTest4'))) and name eq 'test')";

			testConversionToAndFrom(testQuery);
		});
	});
});

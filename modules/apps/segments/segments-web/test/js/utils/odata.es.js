/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import * as ODataUtil from '../../../src/main/resources/META-INF/resources/js/utils/odata';
import * as Utils from '../../../src/main/resources/META-INF/resources/js/utils/utils';
import {mockCriteria, mockCriteriaNested} from '../mockData';

const properties = [
	{
		label: 'Cookies',
		name: 'cookies',
		type: 'collection',
	},
];

function testConversionToQueryString(
	translatedMap,
	testQuery,
	{properties, queryConjunction}
) {
	const translatedString = ODataUtil.buildQueryString(
		[translatedMap],
		queryConjunction,
		properties
	);

	expect(translatedString).toEqual(testQuery);
}

describe('odata-util', () => {
	beforeAll(() => {
		jest.spyOn(Utils, 'generateGroupId').mockImplementation(
			() => 'group_01'
		);
	});

	describe('buildQueryString', () => {
		it('returns null if the query is empty or invalid', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [],
			};

			const testQuery = '()';

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('builds a query string from a flat criteria map', () => {
			expect(ODataUtil.buildQueryString([mockCriteria(1)])).toEqual(
				"(firstName eq 'test')"
			);
			expect(ODataUtil.buildQueryString([mockCriteria(3)])).toEqual(
				"(firstName eq 'test' and firstName eq 'test' and firstName eq 'test')"
			);
		});

		it('builds a query string from a criteria map with nested items', () => {
			expect(ODataUtil.buildQueryString([mockCriteriaNested()])).toEqual(
				"((((firstName eq 'test' or firstName eq 'test') and firstName eq 'test') or firstName eq 'test') and firstName eq 'test')"
			);
		});

		it('translate a query string to map and back to string', () => {
			const testQuery = "(firstName eq 'test')";

			testConversionToQueryString(mockCriteria(1), testQuery, {
				properties,
			});
		});

		it('translate a query string with apostrophes, escaping them', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: "Peep''o's",
					},
				],
			};

			const testQuery = "(firstName eq 'Peep''''o''s')";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a query string with special characters to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'test+/?%#&',
					},
				],
			};

			const testQuery = "(firstName eq 'test+/?%#&')";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a query string with special characters and spaces to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'test +/?%#&',
					},
				],
			};

			const testQuery = "(firstName eq 'test +/?%#&')";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a complex query string to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						conjunctionName: 'or',
						groupId: 'group_02',
						items: [
							{
								operatorName: 'eq',
								propertyName: 'firstName',
								value: 'test1',
							},
							{
								operatorName: 'eq',
								propertyName: 'firstName',
								value: 'test2',
							},
						],
					},
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'test3',
					},
				],
			};

			const testQuery =
				"((firstName eq 'test1' or firstName eq 'test2') and firstName eq 'test3')";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a query string with "not" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'not-eq',
						propertyName: 'firstName',
						value: 'test',
					},
				],
			};

			const testQuery = "((not (firstName eq 'test')))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a complex query string with "not" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'test',
					},
					{
						conjunctionName: 'or',
						groupId: 'group_02',
						items: [
							{
								operatorName: 'not-eq',
								propertyName: 'lastName',
								value: 'foo',
							},
							{
								operatorName: 'not-eq',
								propertyName: 'lastName',
								value: 'bar',
							},
						],
					},
				],
			};

			const testQuery =
				"(firstName eq 'test' and ((not (lastName eq 'foo')) or (not (lastName eq 'bar'))))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a query string with "contains" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'contains',
						propertyName: 'firstName',
						value: 'test',
					},
				],
			};

			const testQuery = "(contains(firstName, 'test'))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a query string with "contains" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'test',
					},
					{
						conjunctionName: 'or',
						groupId: 'group_02',
						items: [
							{
								operatorName: 'contains',
								propertyName: 'lastName',
								value: 'foo',
							},
							{
								operatorName: 'contains',
								propertyName: 'lastName',
								value: 'bar',
							},
						],
					},
				],
			};

			const testQuery =
				"(firstName eq 'test' and (contains(lastName, 'foo') or contains(lastName, 'bar')))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a query string with "not contains" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'not-contains',
						propertyName: 'firstName',
						value: 'test',
					},
				],
			};

			const testQuery = "((not (contains(firstName, 'test'))))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a collection type query string with "contains" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'contains',
						propertyName: 'cookies',
						value: 'keyTest=valueTest',
					},
				],
			};

			const testQuery =
				"(cookies/any(c:contains(c, 'keyTest=valueTest')))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a collection type query string with "not contains" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'not-contains',
						propertyName: 'cookies',
						value: 'keyTest=valueTest',
					},
				],
			};

			const testQuery =
				"((not (cookies/any(c:contains(c, 'keyTest=valueTest')))))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a collection type query string with "eq" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'cookies',
						value: 'keyTest=valueTest',
					},
				],
			};

			const testQuery = "(cookies/any(c:c eq 'keyTest=valueTest'))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a collection type query string with "not" to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'not-eq',
						propertyName: 'cookies',
						value: 'keyTest=valueTest',
					},
				],
			};
			const testQuery =
				"((not (cookies/any(c:c eq 'keyTest=valueTest'))))";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});

		it('translate a nested and complex collection type query string to map and back to string', () => {
			const translatedMap = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'not-eq',
						propertyName: 'cookies',
						value: 'keyTest1=valueTest1',
					},
					{
						conjunctionName: 'or',
						groupId: 'group_02',
						items: [
							{
								operatorName: 'not-eq',
								propertyName: 'cookies',
								value: 'keyTest2=valueTest2',
							},
							{
								conjunctionName: 'and',
								groupId: 'group_03',
								items: [
									{
										operatorName: 'eq',
										propertyName: 'cookies',
										value: 'keyTest3=valueTest3',
									},
									{
										operatorName: 'eq',
										propertyName: 'cookies',
										value: 'keyTest4=valueTest4',
									},
								],
							},
						],
					},
					{
						operatorName: 'eq',
						propertyName: 'name',
						value: 'test',
					},
				],
			};

			const testQuery =
				"((not (cookies/any(c:c eq 'keyTest1=valueTest1'))) and ((not (cookies/any(c:c eq 'keyTest2=valueTest2'))) or (cookies/any(c:c eq 'keyTest3=valueTest3') and cookies/any(c:c eq 'keyTest4=valueTest4'))) and name eq 'test')";

			testConversionToQueryString(translatedMap, testQuery, {properties});
		});
	});
});

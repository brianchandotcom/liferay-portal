/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import * as ODataUtil from '../../../src/main/resources/META-INF/resources/js/utils/odata.es';
import * as Utils from '../../../src/main/resources/META-INF/resources/js/utils/utils.es';
import {mockCriteria, mockCriteriaNested} from '../data';

const properties = [
	{
		label: 'Cookies',
		name: 'cookies',
		type: 'collection'
	}
];

function testConversionToAndFrom(testQuery, {properties, queryConjunction}) {
	const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

	const translatedString = ODataUtil.buildQueryString(
		[translatedMap],
		queryConjunction,
		properties
	);

	expect(translatedString).toEqual(testQuery);
}

describe('odata-util', () => {
	beforeAll(() => {
		Utils.generateGroupId = jest.fn(() => 'group_01');
	});

	describe('buildQueryString', () => {
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
	});

	describe('translateQueryToCriteria', () => {
		it('translates a query string into a criteria map', () => {
			expect(
				ODataUtil.translateQueryToCriteria("(firstName eq 'test')")
			).toEqual({
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'test'
					}
				]
			});
		});

		it('handles a query string with empty groups', () => {
			expect(
				ODataUtil.translateQueryToCriteria("(((firstName eq 'test')))")
			).toEqual({
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'eq',
						propertyName: 'firstName',
						value: 'test'
					}
				]
			});
		});

		it('handles a query string with "not" operator', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"(not (firstName eq 'test'))"
				)
			).toEqual({
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'not-eq',
						propertyName: 'firstName',
						value: 'test'
					}
				]
			});
		});

		it('handles a query string with "contains" operator', () => {
			expect(
				ODataUtil.translateQueryToCriteria(
					"contains(firstName, 'test')"
				)
			).toEqual({
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						operatorName: 'contains',
						propertyName: 'firstName',
						value: 'test'
					}
				]
			});
		});

		it('returns null if the query is empty or invalid', () => {
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
	});

	describe('conversion to and from', () => {
		it('is able to translate a query string to map and back to string', () => {
			const testQuery = "(firstName eq 'test')";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a complex query string to map and back to string', () => {
			const testQuery =
				"((firstName eq 'test' or firstName eq 'test') and firstName eq 'test')";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a query string with "not" to map and back to string', () => {
			const testQuery = "((not (firstName eq 'test')))";

			const translatedMap = ODataUtil.translateQueryToCriteria(testQuery);

			const translatedString = ODataUtil.buildQueryString([
				translatedMap
			]);

			expect(translatedString).toEqual(testQuery);
		});

		it('is able to translate a complex query string with "not" to map and back to string', () => {
			const testQuery =
				"(firstName eq 'test' and ((not (lastName eq 'foo')) or (not (lastName eq 'bar'))))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a query string with "contains" to map and back to string', () => {
			const testQuery = "(contains(firstName, 'test'))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a query string with "contains" to map and back to string', () => {
			const testQuery =
				"(firstName eq 'test' and (contains(lastName, 'foo') or contains(lastName, 'bar')))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a query string with "not contains" to map and back to string', () => {
			const testQuery = "((not (contains(firstName, 'test'))))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a collection type query string with "contains" to map and back to string', () => {
			const testQuery =
				"(cookies/any(c:contains(c, 'keyTest=valueTest')))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a collection type query string with "not contains" to map and back to string', () => {
			const testQuery =
				"((not (cookies/any(c:contains(c, 'keyTest=valueTest')))))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a collection type query string with "eq" to map and back to string', () => {
			const testQuery = "(cookies/any(c:c eq 'keyTest=valueTest'))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a collection type query string with "not" to map and back to string', () => {
			const testQuery =
				"((not (cookies/any(c:c eq 'keyTest=valueTest'))))";

			testConversionToAndFrom(testQuery, {properties});
		});

		it('is able to translate a nested and complex collection type query string to map and back to string', () => {
			const testQuery =
				"((not (cookies/any(c:c eq 'keyTest1=valueTest1'))) and ((not (cookies/any(c:c eq 'keyTest2=valueTest2'))) or (cookies/any(c:c eq 'keyTest3=valueTest3') and cookies/any(c:c eq 'keyTest4=valueTest4'))) and name eq 'test')";

			testConversionToAndFrom(testQuery, {properties});
		});
	});
});

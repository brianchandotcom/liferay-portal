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

import * as ACGrammarUtil from '../../../src/main/resources/META-INF/resources/js/utils/ac-grammar.es';

function testConversionToQueryString(criterion, testQuery) {
	const translatedString = ACGrammarUtil.buildEventQueryString(criterion);

	expect(translatedString).toEqual(testQuery);
}

describe('ac-grammar-util', () => {
	describe('buildQueryString', () => {
		it('translate a query string', () => {
			const criterion = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						assetId: '603744226255659006',
						operatorName: 'ge',
						propertyName: 'documentDownloaded',
						value: '2',
					},
				],
			};

			const testQuery =
				"(activities.filterByCount(filter='(activityKey eq ''Document#documentDownloaded#603744226255659006'')',operator='ge',value=2))";

			testConversionToQueryString(criterion, testQuery);
		});

		it('translate a query string with not operator', () => {
			const criterion = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						assetId: '603744226255659006',
						operatorName: 'ge',
						operatorNot: true,
						propertyName: 'documentDownloaded',
						value: '2',
					},
				],
			};

			const testQuery =
				"((not activities.filterByCount(filter='(activityKey eq ''Document#documentDownloaded#603744226255659006'')',operator='ge',value=2)))";

			testConversionToQueryString(criterion, testQuery);
		});

		it('translate a query string with since date modifier', () => {
			const criterion = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						assetId: '603744226255659006',
						day: {operatorName: 'gt', value: 'last28Days'},
						operatorName: 'ge',
						propertyName: 'documentDownloaded',
						value: '2',
					},
				],
			};

			const testQuery =
				"(activities.filterByCount(filter='(activityKey eq ''Document#documentDownloaded#603744226255659006'' and day gt ''last28Days'')',operator='ge',value=2))";

			testConversionToQueryString(criterion, testQuery);
		});

		it('translate a query string with on date modifier', () => {
			const criterion = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						assetId: '603744226255659006',
						day: {operatorName: 'eq', value: '2023-02-23'},
						operatorName: 'ge',
						propertyName: 'documentDownloaded',
						value: '2',
					},
				],
			};

			const testQuery =
				"(activities.filterByCount(filter='(activityKey eq ''Document#documentDownloaded#603744226255659006'' and day eq ''2023-02-23'')',operator='ge',value=2))";

			testConversionToQueryString(criterion, testQuery);
		});

		it('translate a query string with after date modifier', () => {
			const criterion = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						assetId: '603744226255659006',
						day: {operatorName: 'gt', value: '2023-02-23'},
						operatorName: 'ge',
						propertyName: 'documentDownloaded',
						value: '2',
					},
				],
			};

			const testQuery =
				"(activities.filterByCount(filter='(activityKey eq ''Document#documentDownloaded#603744226255659006'' and day gt ''2023-02-23'')',operator='ge',value=2))";

			testConversionToQueryString(criterion, testQuery);
		});

		it('translate a query string with before date modifier', () => {
			const criterion = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						assetId: '603744226255659006',
						day: {operatorName: 'lt', value: '2023-02-23'},
						operatorName: 'ge',
						propertyName: 'documentDownloaded',
						value: '2',
					},
				],
			};

			const testQuery =
				"(activities.filterByCount(filter='(activityKey eq ''Document#documentDownloaded#603744226255659006'' and day lt ''2023-02-23'')',operator='ge',value=2))";

			testConversionToQueryString(criterion, testQuery);
		});

		it('translate a query string with between date modifier', () => {
			const criterion = {
				conjunctionName: 'and',
				groupId: 'group_01',
				items: [
					{
						assetId: '603744226255659006',
						day: {
							operatorName: 'between',
							value: {end: '2023-02-23', start: '2023-02-11'},
						},
						operatorName: 'ge',
						propertyName: 'documentDownloaded',
						value: '2',
					},
				],
			};

			const testQuery =
				"(activities.filterByCount(filter='(activityKey eq ''Document#documentDownloaded#603744226255659006'' and between(day,''2023-02-11'',''2023-02-23''))',operator='ge',value=2))";

			testConversionToQueryString(criterion, testQuery);
		});
	});
});

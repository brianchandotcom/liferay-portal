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

package com.liferay.json.storage.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.json.storage.model.JSONStorageEntryTable;
import com.liferay.json.storage.service.JSONStorageEntryLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class JSONStorageEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() {
		_jsonStorageEntryLocalService.deleteJSONStorageEntries(
			_CLASS_NAME_ID, _CLASS_PK_1);
		_jsonStorageEntryLocalService.deleteJSONStorageEntries(
			_CLASS_NAME_ID, _CLASS_PK_2);
		_jsonStorageEntryLocalService.deleteJSONStorageEntries(
			_CLASS_NAME_ID, _CLASS_PK_3);
	}

	@Test
	public void testEmptyArray() {
		String json = "[]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);

		json = "[[[\"value\"]]]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		_assertJSONStoreEntryCount(_CLASS_PK_1, 3);

		json = "[]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);
	}

	@Test
	public void testEmptyObject() {
		String json = "{}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);

		json = "{\"root\": {\"key\": \"value\"}}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		_assertJSONStoreEntryCount(_CLASS_PK_1, 2);

		json = "{}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);
	}

	@Test
	public void testGetClassPKsCount() {
		_jsonStorageEntryLocalService.addJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1,
			"{\"object\": {\"array\": [1, 2],\"key 1\": \"value 1 a\", \"key " +
				"2\": \"value 2\"}}");
		_jsonStorageEntryLocalService.addJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_2, "[2,3,4]");
		_jsonStorageEntryLocalService.addJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_3,
			"{\"object\": {\"array\": [], \"key 1\": \"value 1 b\", \"key " +
				"2\": \"value 2\"}}");

		_assertClassPKs(
			Collections.emptyList(), new Object[] {"object"}, "value 1 a");
		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_1),
			new Object[] {"object", "key 1"}, "value 1 a");
		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_1), new Object[] {null, null},
			"value 1 a");
		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_1),
			new Object[] {"object", null}, "value 1 a");
		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_1),
			new Object[] {null, "key 1"}, "value 1 a");
		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_1),
			new Object[] {"array", null}, 2);
		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_2), new Object[] {0}, 2);
		_assertClassPKs(Arrays.asList(_CLASS_PK_1, _CLASS_PK_2), null, 2);
		_assertClassPKs(
			Arrays.asList(_CLASS_PK_1, _CLASS_PK_3),
			new Object[] {"object", "key 2"}, "value 2");

		Assert.assertEquals(
			Arrays.asList(_CLASS_PK_1, _CLASS_PK_2, _CLASS_PK_3),
			_jsonStorageEntryLocalService.getClassPKs(
				_COMPANY_ID, _CLASS_NAME_ID, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS));
		Assert.assertEquals(
			3,
			_jsonStorageEntryLocalService.getClassPKsCount(
				_COMPANY_ID, _CLASS_NAME_ID));
	}

	@Test
	public void testGetMissing() {
		Assert.assertNull(
			_jsonStorageEntryLocalService.getJSONArray(
				_CLASS_NAME_ID, _CLASS_PK_1));
		Assert.assertNull(
			_jsonStorageEntryLocalService.getJSONObject(
				_CLASS_NAME_ID, _CLASS_PK_1));
		Assert.assertNull(
			_jsonStorageEntryLocalService.getJSONSerializable(
				_CLASS_NAME_ID, _CLASS_PK_1));
		Assert.assertNull(
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1));
	}

	@Test
	public void testInvalidJSON() {
		try {
			_jsonStorageEntryLocalService.addJSONStorageEntries(
				_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, "null");

			Assert.fail();
		}
		catch (IllegalArgumentException illegalArgumentException) {
			Assert.assertEquals(
				"Invalid JSON: null", illegalArgumentException.getMessage());
		}
	}

	@Test
	public void testNullValue() {
		String json = "{\"key\": null}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_1), new Object[] {"key"}, null);

		JSONObject jsonObject = _jsonStorageEntryLocalService.getJSONObject(
			_CLASS_NAME_ID, _CLASS_PK_1);

		Assert.assertNull(jsonObject.get("key"));

		json = "[null]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		JSONArray jsonArray = _jsonStorageEntryLocalService.getJSONArray(
			_CLASS_NAME_ID, _CLASS_PK_1);

		Assert.assertEquals(1, jsonArray.length());
		Assert.assertNull(jsonArray.get(0));
	}

	@Test
	public void testReplaceArraysWithObjects() {
		String json = "[[], [1, 2]]";

		_jsonStorageEntryLocalService.addJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);
		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "{\"a\": {}, \"b\": {\"c\": 1}}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);
		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);
	}

	@Test
	public void testReplaceObjectsWithArrays() {
		String json = "{\"a\": {}, \"b\": {\"c\": 1}}";

		_jsonStorageEntryLocalService.addJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);
		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "[[], [1, 2]]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);
		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);
	}

	@Test
	public void testUpdateArray() {
		String json = "[1, 2]";

		_jsonStorageEntryLocalService.addJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "[\"a\", \"b\", \"c\"]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "[true, false]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "[3.14]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);
	}

	@Test
	public void testUpdateArrayObjects() {
		String json = "[[{\"a\": \"1\", \"b\": \"2\"}, {\"c\": \"3\"}]]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);
		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "[[{\"a\": \"one\"}, {\"b\": \"two\", \"c\": \"three\"}]]";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);
	}

	@Test
	public void testUpdateObject() {
		String json = "{\"a\": 1, \"b\": 2}";

		_jsonStorageEntryLocalService.addJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "{\"b\": true, \"c\": false}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);

		json = "{\"pi\": 3.14}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);
	}

	@Test
	public void testUpdateObjectArrays() {
		String json = "{\"a\": {\"b\": [1, 2]}}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);
		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);

		json = "{\"a\": {\"b\": [2, 3]}}";

		_jsonStorageEntryLocalService.updateJSONStorageEntries(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONAssert.assertEquals(
			json,
			String.valueOf(
				_jsonStorageEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)),
			true);
		JSONAssert.assertEquals(
			json,
			_jsonStorageEntryLocalService.getJSON(_CLASS_NAME_ID, _CLASS_PK_1),
			true);
	}

	private void _assertClassPKs(
		List<Long> expectedValues, Object[] pathParts, Object value) {

		Assert.assertEquals(
			expectedValues,
			_jsonStorageEntryLocalService.getClassPKs(
				_COMPANY_ID, _CLASS_NAME_ID, pathParts, value,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));
		Assert.assertEquals(
			expectedValues.size(),
			_jsonStorageEntryLocalService.getClassPKsCount(
				_COMPANY_ID, _CLASS_NAME_ID, pathParts, value));
	}

	private void _assertJSONStoreEntryCount(long classPK, int expectedCount) {
		long count = _jsonStorageEntryLocalService.dslQuery(
			DSLQueryFactoryUtil.count(
			).from(
				JSONStorageEntryTable.INSTANCE
			).where(
				JSONStorageEntryTable.INSTANCE.classNameId.eq(
					_CLASS_NAME_ID
				).and(
					JSONStorageEntryTable.INSTANCE.classPK.eq(classPK)
				)
			));

		Assert.assertEquals(expectedCount, count);
	}

	private static final long _CLASS_NAME_ID = 0;

	private static final long _CLASS_PK_1 = 1;

	private static final long _CLASS_PK_2 = 2;

	private static final long _CLASS_PK_3 = 3;

	private static final long _COMPANY_ID = 0;

	@Inject
	private static JSONStorageEntryLocalService _jsonStorageEntryLocalService;

}
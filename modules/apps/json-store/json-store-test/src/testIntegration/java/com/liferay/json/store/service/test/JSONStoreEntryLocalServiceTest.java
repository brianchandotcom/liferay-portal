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

package com.liferay.json.store.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.json.store.model.JSONStoreEntryTable;
import com.liferay.json.store.service.JSONStoreEntryLocalService;
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

/**
 * @author Preston Crary
 */
@RunWith(Arquillian.class)
public class JSONStoreEntryLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@After
	public void tearDown() {
		_jsonStoreEntryLocalService.deleteJSON(_CLASS_NAME_ID, _CLASS_PK_1);

		_jsonStoreEntryLocalService.deleteJSON(_CLASS_NAME_ID, _CLASS_PK_2);

		_jsonStoreEntryLocalService.deleteJSON(_CLASS_NAME_ID, _CLASS_PK_3);
	}

	@Test
	public void testEmptyArray() {
		String json = "[]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);

		json = "[[[\"value\"]]]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		_assertJSONStoreEntryCount(_CLASS_PK_1, 3);

		json = "[]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);
	}

	@Test
	public void testEmptyObject() {
		String json = "{}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);

		json = "{\"root\":{\"key\":\"value\"}}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		_assertJSONStoreEntryCount(_CLASS_PK_1, 2);

		json = "{}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		_assertJSONStoreEntryCount(_CLASS_PK_1, 1);
	}

	@Test
	public void testGetClassPKsCount() {
		_jsonStoreEntryLocalService.addJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1,
			"{\"object\":{\"array\":[1,2],\"key 1\":\"value 1 a\",\"key 2\":" +
				"\"value 2\"}}");

		_jsonStoreEntryLocalService.addJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_2, "[2,3,4]");

		_jsonStoreEntryLocalService.addJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_3,
			"{\"object\":{\"array\":[],\"key 1\":\"value 1 b\",\"key 2\":" +
				"\"value 2\"}}");

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
			Arrays.asList(_CLASS_PK_1), new Object[] {"array", null}, 2);
		_assertClassPKs(
			Collections.singletonList(_CLASS_PK_2), new Object[] {0}, 2);

		_assertClassPKs(Arrays.asList(_CLASS_PK_1, _CLASS_PK_2), null, 2);

		_assertClassPKs(
			Arrays.asList(_CLASS_PK_1, _CLASS_PK_3),
			new Object[] {"object", "key 2"}, "value 2");
	}

	@Test
	public void testGetMissing() {
		Assert.assertNull(
			_jsonStoreEntryLocalService.getJSONArray(
				_CLASS_NAME_ID, _CLASS_PK_1));
		Assert.assertNull(
			_jsonStoreEntryLocalService.getJSONObject(
				_CLASS_NAME_ID, _CLASS_PK_1));
		Assert.assertNull(
			_jsonStoreEntryLocalService.getJSONSerializable(
				_CLASS_NAME_ID, _CLASS_PK_1));
		Assert.assertNull(
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));
	}

	@Test
	public void testInvalidJSON() {
		try {
			_jsonStoreEntryLocalService.addJSON(
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
		String json = "{\"key\":null}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		JSONObject jsonObject = _jsonStoreEntryLocalService.getJSONObject(
			_CLASS_NAME_ID, _CLASS_PK_1);

		Assert.assertNull(jsonObject.get("key"));

		json = "[null]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		JSONArray jsonArray = _jsonStoreEntryLocalService.getJSONArray(
			_CLASS_NAME_ID, _CLASS_PK_1);

		Assert.assertEquals(1, jsonArray.length());

		Assert.assertNull(jsonArray.get(0));
	}

	@Test
	public void testReplaceArraysWithObjects() {
		String json = "[[],[1,2]]";

		_jsonStoreEntryLocalService.addJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)));
		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "{\"a\":{},\"b\":{\"c\":1}}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)));
		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));
	}

	@Test
	public void testReplaceObjectsWithArrays() {
		String json = "{\"a\":{},\"b\":{\"c\":1}}";

		_jsonStoreEntryLocalService.addJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)));
		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "[[],[1,2]]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)));
		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));
	}

	@Test
	public void testUpdateArray() {
		String json = "[1,2]";

		_jsonStoreEntryLocalService.addJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "[\"a\",\"b\",\"c\"]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "[true,false]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "[3.14]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));
	}

	@Test
	public void testUpdateArrayObjects() {
		String json = "[[{\"a\":\"1\",\"b\":\"2\"},{\"c\":\"3\"}]]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)));

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "[[{\"a\":\"one\"},{\"b\":\"two\",\"c\":\"three\"}]]";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONArray(
					_CLASS_NAME_ID, _CLASS_PK_1)));

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));
	}

	@Test
	public void testUpdateObject() {
		String json = "{\"a\":1,\"b\":2}";

		_jsonStoreEntryLocalService.addJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "{\"b\":true,\"c\":false}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		json = "{\"pi\":3.14}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));
	}

	@Test
	public void testUpdateObjectArrays() {
		String json = "{\"a\":{\"b\":[1,2]}}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)));

		json = "{\"a\":{\"b\":[2,3]}}";

		_jsonStoreEntryLocalService.updateJSON(
			_COMPANY_ID, _CLASS_NAME_ID, _CLASS_PK_1, json);

		Assert.assertEquals(
			json,
			String.valueOf(
				_jsonStoreEntryLocalService.getJSONObject(
					_CLASS_NAME_ID, _CLASS_PK_1)));

		Assert.assertEquals(
			json,
			_jsonStoreEntryLocalService.getJSONString(
				_CLASS_NAME_ID, _CLASS_PK_1));
	}

	private void _assertClassPKs(
		List<Long> expectedValues, Object[] pathParts, Object value) {

		Assert.assertEquals(
			expectedValues,
			_jsonStoreEntryLocalService.getClassPKs(
				_COMPANY_ID, _CLASS_NAME_ID, pathParts, value,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS));

		Assert.assertEquals(
			expectedValues.size(),
			_jsonStoreEntryLocalService.getClassPKsCount(
				_COMPANY_ID, _CLASS_NAME_ID, pathParts, value));
	}

	private void _assertJSONStoreEntryCount(long classPK, int expectedCount) {
		long count = _jsonStoreEntryLocalService.dslQuery(
			DSLQueryFactoryUtil.count(
			).from(
				JSONStoreEntryTable.INSTANCE
			).where(
				JSONStoreEntryTable.INSTANCE.classNameId.eq(
					_CLASS_NAME_ID
				).and(
					JSONStoreEntryTable.INSTANCE.classPK.eq(classPK)
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
	private static JSONStoreEntryLocalService _jsonStoreEntryLocalService;

}
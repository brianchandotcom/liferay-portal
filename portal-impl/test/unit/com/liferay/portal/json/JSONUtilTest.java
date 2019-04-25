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

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Tardín
 */
public class JSONUtilTest {

	@Before
	public void setUp() throws Exception {
		JSONInit.init();

		JSONFactoryImpl jsonFactoryImpl = new JSONFactoryImpl();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(jsonFactoryImpl);
	}

	@Test
	public void testAddToStringCollection() {
		List<String> values = new ArrayList<String>() {
			{
				add("alpha");
				add("beta");
			}
		};

		JSONUtil.addToStringCollection(values, JSONUtil.put("gamma"));

		Assert.assertEquals(values.toString(), 3, values.size());
		Assert.assertTrue(values.contains("gamma"));
	}

	@Test
	public void testAddToStringCollectionWithKey() {
		List<String> values = new ArrayList<String>() {
			{
				add("1");
				add("2");
			}
		};

		JSONUtil.addToStringCollection(
			values, JSONUtil.put(JSONUtil.put("alpha", "3")), "alpha");

		Assert.assertEquals(values.toString(), 3, values.size());
		Assert.assertTrue(values.contains("3"));
	}

	@Test
	public void testGetValue() {
		JSONObject jsonObject = JSONUtil.put(
			"alpha", JSONUtil.put("beta", JSONUtil.put("gamma")));

		Assert.assertEquals(
			"gamma",
			JSONUtil.getValue(
				jsonObject, "JSONObject/alpha", "JSONArray/beta", "Object/0"));
	}

	@Test
	public void testHasValue() {
		Assert.assertTrue(
			JSONUtil.hasValue(
				JSONUtil.putAll("alpha", "beta", "gamma"), "gamma"));

		Assert.assertFalse(
			JSONUtil.hasValue(JSONUtil.putAll("alpha", "beta", "gamma"), "1"));
	}

	@Test
	public void testMerge() throws Exception {
		JSONObject jsonObject1 = JSONUtil.put("alpha", "1");
		JSONObject jsonObject2 = JSONUtil.put(
			"beta", "2"
		).put(
			"gamma", "3"
		);

		JSONObject jsonObject3 = JSONUtil.merge(jsonObject1, jsonObject2);

		Assert.assertEquals(3, jsonObject3.length());
		Assert.assertTrue(jsonObject3.has("alpha"));
		Assert.assertTrue(jsonObject3.has("beta"));
		Assert.assertTrue(jsonObject3.has("gamma"));
	}

	@Test
	public void testPutKeyValue() {
		JSONObject jsonObject1 = _createJSONObject();

		jsonObject1.put("alpha", "beta");

		JSONObject jsonObject2 = JSONUtil.put("alpha", "beta");

		Assert.assertEquals(jsonObject1.get("alpha"), jsonObject2.get("alpha"));
	}

	@Test
	public void testPutValue() {
		JSONArray jsonArray1 = _createJSONArray();

		jsonArray1.put("alpha");

		JSONArray jsonArray2 = JSONUtil.put("alpha");

		Assert.assertEquals(jsonArray1.length(), jsonArray2.length());
		Assert.assertEquals(jsonArray1.get(0), jsonArray2.get(0));
	}

	@Test
	public void testPutValues() {
		JSONArray jsonArray1 = _createJSONArray();

		jsonArray1.put(
			"alpha"
		).put(
			"beta"
		).put(
			"gamma"
		);

		JSONArray jsonArray2 = JSONUtil.putAll("alpha", "beta", "gamma");

		for (int i = 0; i < jsonArray1.length(); i++) {
			Assert.assertEquals(jsonArray1.get(i), jsonArray2.get(i));
		}
	}

	@Test
	public void testReplace() {
		JSONArray jsonArray = JSONUtil.put(
			JSONUtil.put(
				"alpha", "1"
			).put(
				"beta", "2"
			));

		JSONObject jsonObject = JSONUtil.put(
			"alpha", "1"
		).put(
			"beta", "3"
		).put(
			"gamma", "4"
		);

		jsonArray = JSONUtil.replace(jsonArray, "alpha", jsonObject);

		jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("3", jsonObject.get("beta"));
		Assert.assertEquals("4", jsonObject.get("gamma"));
	}

	@Test
	public void testSerializeArraysWithObjects() {
		JSONArray jsonArray = JSONUtil.put(_createJSONObject());

		Assert.assertEquals("[{}]", jsonArray.toString());
	}

	@Test
	public void testToLongArray() {
		Assert.assertArrayEquals(
			new long[] {1, 2}, JSONUtil.toLongArray(JSONUtil.putAll(1, 2)));
	}

	@Test
	public void testToLongArrayWithKey() {
		Assert.assertArrayEquals(
			new long[] {1, 2},
			JSONUtil.toLongArray(
				JSONUtil.putAll(
					JSONUtil.put("alpha", 1), JSONUtil.put("alpha", 2),
					JSONUtil.put("beta", 3)),
				"alpha"));
	}

	@Test
	public void testToLongList() {
		Assert.assertEquals(
			new ArrayList<Long>() {
				{
					add(1L);
					add(2L);
					add(3L);
				}
			},
			JSONUtil.toLongList(JSONUtil.putAll(1, 2, 3)));
	}

	@Test
	public void testToLongListWithKey() {
		Assert.assertEquals(
			new ArrayList<Long>() {
				{
					add(1L);
					add(2L);
					add(3L);
				}
			},
			JSONUtil.toLongList(
				JSONUtil.putAll(
					JSONUtil.put("alpha", 1), JSONUtil.put("alpha", 2),
					JSONUtil.put("alpha", 3)),
				"alpha"));
	}

	@Test
	public void testToLongSet() {
		Assert.assertEquals(
			new HashSet<Long>() {
				{
					add(1L);
					add(2L);
					add(3L);
				}
			},
			JSONUtil.toLongSet(JSONUtil.putAll(1, 2, 3)));
	}

	@Test
	public void testToLongSetWithKey() {
		Assert.assertEquals(
			new HashSet<Long>() {
				{
					add(1L);
					add(2L);
				}
			},
			JSONUtil.toLongSet(
				JSONUtil.putAll(
					JSONUtil.put("alpha", 1), JSONUtil.put("alpha", 2),
					JSONUtil.put("beta", 3)),
				"alpha"));
	}

	@Test
	public void testToObjectArray() {
		Assert.assertArrayEquals(
			new Object[] {1, "beta", true},
			JSONUtil.toObjectArray(JSONUtil.putAll(1, "beta", true)));
	}

	@Test
	public void testToObjectArrayWithKey() {
		Assert.assertArrayEquals(
			new Object[] {1, true},
			JSONUtil.toObjectArray(
				JSONUtil.putAll(
					JSONUtil.put("alpha", 1), JSONUtil.put("alpha", true),
					JSONUtil.put("beta", 3)),
				"alpha"));
	}

	@Test
	public void testToObjectList() {
		Assert.assertEquals(
			new ArrayList<Object>() {
				{
					add(1);
					add("beta");
					add(true);
				}
			},
			JSONUtil.toObjectList(JSONUtil.putAll(1, "beta", true)));
	}

	@Test
	public void testToObjectListWithKey() {
		Assert.assertEquals(
			new ArrayList<Object>() {
				{
					add(1);
					add("beta");
				}
			},
			JSONUtil.toObjectList(
				JSONUtil.putAll(
					JSONUtil.put("alpha", 1), JSONUtil.put("alpha", "beta"),
					JSONUtil.put("beta", true)),
				"alpha"));
	}

	@Test
	public void testToObjectSet() {
		Assert.assertEquals(
			new HashSet<Object>() {
				{
					add(1);
					add("beta");
					add(true);
				}
			},
			JSONUtil.toObjectSet(JSONUtil.putAll(1, "beta", true)));
	}

	@Test
	public void testToObjectSetWithKey() {
		Assert.assertEquals(
			new HashSet<Object>() {
				{
					add(1);
					add("beta");
				}
			},
			JSONUtil.toObjectSet(
				JSONUtil.putAll(
					JSONUtil.put("alpha", 1), JSONUtil.put("alpha", "beta"),
					JSONUtil.put("beta", true)),
				"alpha"));
	}

	@Test
	public void testToStringArray() {
		Assert.assertArrayEquals(
			new String[] {"alpha", "beta", "gamma"},
			JSONUtil.toStringArray(JSONUtil.putAll("alpha", "beta", "gamma")));
	}

	@Test
	public void testToStringArrayWithKey() {
		Assert.assertArrayEquals(
			new String[] {"alpha", "beta"},
			JSONUtil.toStringArray(
				JSONUtil.putAll(
					JSONUtil.put("alpha", "alpha"),
					JSONUtil.put("alpha", "beta"),
					JSONUtil.put("beta", "gamma")),
				"alpha"));
	}

	@Test
	public void testToStringList() {
		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("alpha");
					add("beta");
					add("gamma");
				}
			},
			JSONUtil.toStringList(JSONUtil.putAll("alpha", "beta", "gamma")));
	}

	@Test
	public void testToStringListWithKey() {
		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("alpha");
					add("beta");
				}
			},
			JSONUtil.toStringList(
				JSONUtil.putAll(
					JSONUtil.put("alpha", "alpha"),
					JSONUtil.put("alpha", "beta"),
					JSONUtil.put("beta", "gamma")),
				"alpha"));
	}

	@Test
	public void testToStringSet() {
		Assert.assertEquals(
			new HashSet<String>() {
				{
					add("alpha");
					add("beta");
					add("gamma");
				}
			},
			JSONUtil.toStringSet(JSONUtil.putAll("alpha", "beta", "gamma")));
	}

	@Test
	public void testToStringSetWithKey() {
		Assert.assertEquals(
			new HashSet<String>() {
				{
					add("alpha");
					add("beta");
				}
			},
			JSONUtil.toStringSet(
				JSONUtil.putAll(
					JSONUtil.put("alpha", "alpha"),
					JSONUtil.put("alpha", "beta"),
					JSONUtil.put("beta", "gamma")),
				"alpha"));
	}

	private JSONArray _createJSONArray() {
		return JSONFactoryUtil.createJSONArray();
	}

	private JSONObject _createJSONObject() {
		return JSONFactoryUtil.createJSONObject();
	}

}
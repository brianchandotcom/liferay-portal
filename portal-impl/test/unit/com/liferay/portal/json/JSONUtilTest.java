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
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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

		JSONUtil.addToStringCollection(values, null);

		Assert.assertEquals(values.toString(), 3, values.size());
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

		JSONUtil.addToStringCollection(values, null, "alpha");

		Assert.assertEquals(values.toString(), 3, values.size());
	}

	@Test
	public void testCreateCollector() {
		List<String> strings = Arrays.asList("foo", "bar", "baz");

		Stream<String> stringStream = strings.stream();

		JSONArray jsonArray = stringStream.map(
			String::toUpperCase
		).collect(
			JSONUtil.createCollector()
		);

		Assert.assertTrue(
			JSONUtil.equals(
				JSONUtil.concat(JSONUtil.putAll("FOO", "BAR", "BAZ")),
				jsonArray));
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
		Assert.assertFalse(
			JSONUtil.hasValue(JSONUtil.putAll("alpha", "beta", "gamma"), "1"));
		Assert.assertTrue(
			JSONUtil.hasValue(
				JSONUtil.putAll("alpha", "beta", "gamma"), "gamma"));
	}

	@Test
	public void testJSONArrayEqualsWithStrictChecking() {
		Assert.assertTrue(
			JSONUtil.equals(
				JSONUtil.putAll("a", "1"), JSONUtil.putAll("a", "1")));

		Assert.assertFalse(
			JSONUtil.equals(
				JSONUtil.putAll("a", "1"), JSONUtil.putAll("1", "a")));
	}

	@Test
	public void testJSONArrayObjectConcat() {
		Assert.assertTrue(
			JSONUtil.equals(
				JSONUtil.concat(
					JSONUtil.put(JSONUtil.put("foo", "bar")),
					JSONUtil.put(JSONUtil.put("bar", "baz"))),
				JSONUtil.putAll(
					JSONUtil.put("foo", "bar"), JSONUtil.put("bar", "baz"))));
	}

	@Test
	public void testJSONArrayStringConcat() {
		Assert.assertTrue(
			JSONUtil.equals(
				JSONUtil.concat(
					JSONUtil.putAll("foo", "bar", "baz"),
					JSONUtil.putAll("abc", "foo", "xyz")),
				JSONUtil.putAll("foo", "bar", "baz", "abc", "foo", "xyz")));
	}

	@Test
	public void testJSONObjectEquals() {
		Assert.assertTrue(
			JSONUtil.equals(
				JSONUtil.put(
					"double", 0.532049
				).put(
					"foo", "bar"
				),
				JSONUtil.put(
					"double", 0.532049
				).put(
					"foo", "bar"
				)));

		Assert.assertTrue(
			JSONUtil.equals(
				JSONUtil.put(
					"double", 0.532049
				).put(
					"foo", "bar"
				),
				JSONUtil.put(
					"foo", "bar"
				).put(
					"double", 0.532049
				)));

		Assert.assertFalse(
			JSONUtil.equals(
				JSONUtil.put(
					"double", 0.532049
				).put(
					"foo", "bar"
				),
				JSONUtil.put(
					"double", 0.532049
				).put(
					"foo", "bar"
				).put(
					"integer", 5
				)));
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

		JSONObject jsonObject4 = JSONUtil.merge(null, jsonObject2);

		Assert.assertEquals(2, jsonObject4.length());
		Assert.assertTrue(jsonObject4.has("beta"));
		Assert.assertTrue(jsonObject4.has("gamma"));
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
		JSONObject jsonObject = JSONUtil.put(
			"alpha", "1"
		).put(
			"beta", "3"
		).put(
			"gamma", "4"
		);

		Assert.assertNull(JSONUtil.replace(null, "alpha", jsonObject));

		JSONArray jsonArray = JSONUtil.put(
			JSONUtil.put(
				"alpha", "1"
			).put(
				"beta", "2"
			)
		).put(
			JSONUtil.put("alpha", "1")
		);

		jsonArray = JSONUtil.replace(jsonArray, "alpha", jsonObject);

		jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("3", jsonObject.get("beta"));
		Assert.assertEquals("4", jsonObject.get("gamma"));

		jsonObject = jsonArray.getJSONObject(1);

		Assert.assertEquals("3", jsonObject.get("beta"));
		Assert.assertEquals("4", jsonObject.get("gamma"));
	}

	@Test
	public void testSerializeArraysWithObjects() {
		JSONArray jsonArray = JSONUtil.put(_createJSONObject());

		Assert.assertEquals("[{}]", jsonArray.toString());
	}

	@Test
	public void testToArray() throws Exception {
		Assert.assertArrayEquals(
			new String[] {"1", "2"},
			JSONUtil.toArray(
				JSONUtil.putAll(
					JSONUtil.put("foo", 1)
				).put(
					JSONUtil.put("foo", 2)
				),
				jsonObject -> String.valueOf(jsonObject.getInt("foo")),
				String.class));
	}

	@Test
	public void testToJSONArrayWithArray() throws Exception {
		JSONArray expectedJSONArray1 = _createJSONArray();

		JSONArray actualJSONArray1 = JSONUtil.toJSONArray(
			(String[])null, s -> JSONUtil.put("foo", Integer.valueOf(s)));

		Assert.assertEquals(
			expectedJSONArray1.toString(), actualJSONArray1.toString());

		JSONArray expectedJSONArray2 = JSONUtil.put(
			JSONUtil.put("foo", 1)
		).put(
			JSONUtil.put("foo", 2)
		);

		JSONArray actualJSONArray2 = JSONUtil.toJSONArray(
			new String[] {"1", "2"},
			s -> JSONUtil.put("foo", Integer.valueOf(s)));

		Assert.assertEquals(
			expectedJSONArray2.toString(), actualJSONArray2.toString());
	}

	@Test
	public void testToJSONArrayWithList() throws Exception {
		JSONArray expectedJSONArray1 = _createJSONArray();

		JSONArray actualJSONArray1 = JSONUtil.toJSONArray(
			(String[])null, s -> JSONUtil.put("foo", Integer.valueOf(s)));

		Assert.assertEquals(
			expectedJSONArray1.toString(), actualJSONArray1.toString());

		JSONArray expectedJSONArray2 = JSONUtil.put(
			JSONUtil.put("foo", 1)
		).put(
			JSONUtil.put("foo", 2)
		);

		JSONArray actualJSONArray2 = JSONUtil.toJSONArray(
			new ArrayList<String>() {
				{
					add("1");
					add("2");
				}
			},
			s -> JSONUtil.put("foo", Integer.valueOf(s)));

		Assert.assertEquals(
			expectedJSONArray2.toString(), actualJSONArray2.toString());
	}

	@Test
	public void testToJSONObjectMap() {
		Assert.assertEquals(
			Collections.emptyMap(),
			JSONUtil.toJSONObjectMap(_createJSONArray(), null));

		Map<String, JSONObject> expectedJSONObjects =
			HashMapBuilder.<String, JSONObject>put(
				"1",
				JSONUtil.put(
					"alpha", 1
				).put(
					"key", "1"
				)
			).put(
				"2",
				JSONUtil.put(
					"beta", 1
				).put(
					"key", "2"
				)
			).put(
				"3",
				JSONUtil.put(
					"gamma", 1
				).put(
					"key", "3"
				)
			).build();

		Map<String, JSONObject> actualJSONObjects = JSONUtil.toJSONObjectMap(
			JSONUtil.putAll(
				JSONUtil.put(
					"alpha", 1
				).put(
					"key", "1"
				),
				JSONUtil.put(
					"beta", 1
				).put(
					"key", "2"
				),
				JSONUtil.put(
					"gamma", 1
				).put(
					"key", "3"
				)),
			"key");

		Assert.assertEquals(
			expectedJSONObjects.toString(), actualJSONObjects.toString());
	}

	@Test
	public void testToList() throws Exception {
		Assert.assertEquals(
			new ArrayList<Integer>(),
			JSONUtil.toList(null, jsonObject -> jsonObject.getInt("foo")));
		Assert.assertEquals(
			new ArrayList<Integer>() {
				{
					add(1);
					add(2);
				}
			},
			JSONUtil.toList(
				JSONUtil.put(
					JSONUtil.put("foo", "1")
				).put(
					JSONUtil.put("foo", "2")
				),
				jsonObject -> jsonObject.getInt("foo")));
	}

	@Test
	public void testToLongArray() {
		Assert.assertArrayEquals(new long[0], JSONUtil.toLongArray(null));
		Assert.assertArrayEquals(
			new long[] {1, 2}, JSONUtil.toLongArray(JSONUtil.putAll(1, 2)));
	}

	@Test
	public void testToLongArrayWithKey() {
		Assert.assertArrayEquals(
			new long[0], JSONUtil.toLongArray(null, "alpha"));
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
		Assert.assertEquals(Collections.emptyList(), JSONUtil.toLongList(null));
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
			Collections.emptyList(), JSONUtil.toLongList(null, "alpha"));
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
		Assert.assertEquals(Collections.emptySet(), JSONUtil.toLongSet(null));
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
			Collections.emptySet(), JSONUtil.toLongSet(null, "alpha"));
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
		Assert.assertArrayEquals(new Object[0], JSONUtil.toObjectArray(null));
		Assert.assertArrayEquals(
			new Object[] {1, "beta", true},
			JSONUtil.toObjectArray(JSONUtil.putAll(1, "beta", true)));
	}

	@Test
	public void testToObjectArrayWithKey() {
		Assert.assertArrayEquals(
			new Object[0], JSONUtil.toObjectArray(null, "alpha"));
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
			Collections.emptyList(), JSONUtil.toObjectList(null));
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
			Collections.emptyList(), JSONUtil.toObjectList(null, "alpha"));
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
		Assert.assertEquals(Collections.emptySet(), JSONUtil.toObjectSet(null));
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
			Collections.emptySet(), JSONUtil.toObjectSet(null, "alpha"));
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
		Assert.assertArrayEquals(new String[0], JSONUtil.toStringArray(null));
		Assert.assertArrayEquals(
			new String[] {"alpha", "beta", "gamma"},
			JSONUtil.toStringArray(JSONUtil.putAll("alpha", "beta", "gamma")));
	}

	@Test
	public void testToStringArrayWithKey() {
		Assert.assertArrayEquals(
			new String[0], JSONUtil.toStringArray(null, "alpha"));
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
			Collections.emptyList(), JSONUtil.toStringList(null));
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
			Collections.emptyList(), JSONUtil.toStringList(null, "alpha"));
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
		Assert.assertEquals(Collections.emptySet(), JSONUtil.toStringSet(null));
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
			Collections.emptySet(), JSONUtil.toStringSet(null, "alpha"));
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
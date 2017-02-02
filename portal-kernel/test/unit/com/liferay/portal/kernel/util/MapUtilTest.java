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

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Sampsa Sohlman
 * @author Manuel de la Peña
 * @author Peter Borkuti
 */
@RunWith(Enclosed.class)
public class MapUtilTest {

	public static class WhenCreatingALinkedHashMapFromArray {

		@Test
		public void shouldReturnEmptyMapWithParamsInvalid() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one"});

			Assert.assertTrue(map.isEmpty());

			map = MapUtil.toLinkedHashMap(new String[] {"one:two:three:four"});

			Assert.assertTrue(map.isEmpty());
		}

		@Test
		public void shouldReturnEmptyMapWithParamsNull() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(null);

			Assert.assertTrue(map.isEmpty());
		}

		@Test
		public void shouldReturnEmptyMapWithParamsTypeObject() {
			try (CaptureHandler captureHandler =
					JDKLoggerTestUtil.configureJDKLogger(
						MapUtil.class.getName(), Level.SEVERE)) {

				Map<String, Object> map = MapUtil.toLinkedHashMap(
					new String[] {"one:1:" + Object.class.getName()});

				Assert.assertTrue(map.isEmpty());

				List<LogRecord> logRecords = captureHandler.getLogRecords();

				Assert.assertEquals(
					logRecords.toString(), 1, logRecords.size());

				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					"java.lang.Object.<init>(java.lang.String)",
					logRecord.getMessage());

				Throwable throwable = logRecord.getThrown();

				Assert.assertSame(
					NoSuchMethodException.class, throwable.getClass());
			}
		}

		@Test
		public void shouldReturnEmptyMapWithParamsZeroLength() {
			Map<String, String> map = MapUtil.toLinkedHashMap(new String[0]);

			Assert.assertTrue(map.isEmpty());
		}

		@Test
		public void shouldReturnMapWithDelimiterCustom() {
			Map<String, String> map = MapUtil.toLinkedHashMap(
				new String[0], ",");

			Assert.assertTrue(map.isEmpty());

			map = MapUtil.toLinkedHashMap(new String[] {"one,1"}, ",");

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue("1"));

			map = MapUtil.toLinkedHashMap(new String[] {"one,1", "two,2"}, ",");

			Assert.assertEquals(map.toString(), 2, map.size());
		}

		@Test
		public void shouldReturnMapWithDelimiterDefault() {
			Map<String, String> map = MapUtil.toLinkedHashMap(new String[0]);

			Assert.assertTrue(map.isEmpty());

			map = MapUtil.toLinkedHashMap(new String[] {"one:1"});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue("1"));

			map = MapUtil.toLinkedHashMap(new String[] {"one:1", "two:2"});

			Assert.assertEquals(map.toString(), 2, map.size());
		}

	}

	public static class
		WhenCreatingALinkedHashMapFromArrayWithInvalidParamsTypeValue {

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithCompositeDouble() {
			MapUtil.toLinkedHashMap(
				new String[] {"one:foo:" + Double.class.getName()});
		}

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithCompositeInteger() {
			MapUtil.toLinkedHashMap(
				new String[] {"one:foo:" + Integer.class.getName()});
		}

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithCompositeLong() {
			MapUtil.toLinkedHashMap(
				new String[] {"one:foo:" + Long.class.getName()});
		}

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithCompositeShort() {
			MapUtil.toLinkedHashMap(
				new String[] {"one:foo:" + Short.class.getName()});
		}

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithDouble() {
			MapUtil.toLinkedHashMap(new String[] {"one:foo:double"});
		}

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithInteger() {
			MapUtil.toLinkedHashMap(new String[] {"one:foo:int"});
		}

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithLong() {
			MapUtil.toLinkedHashMap(new String[] {"one:foo:long"});
		}

		@Test(expected = NumberFormatException.class)
		public void shouldFailWithShort() {
			MapUtil.toLinkedHashMap(new String[] {"one:foo:short"});
		}

		@Test
		public void shouldReturnMapWithBoolean() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:foo:boolean"});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(false));
			Assert.assertTrue(map.get("one") instanceof Boolean);

			map = MapUtil.toLinkedHashMap(
				new String[] {"one:foo:" + Boolean.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(false));
			Assert.assertTrue(map.get("one") instanceof Boolean);
		}

	}

	public static class WhenCreatingALinkedHashMapFromArrayWithParamsType {

		@Test
		public void shouldReturnMapWithBoolean() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:true:boolean"});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(true));
			Assert.assertTrue(map.get("one") instanceof Boolean);

			map = MapUtil.toLinkedHashMap(
				new String[] {"one:true:" + Boolean.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(true));
			Assert.assertTrue(map.get("one") instanceof Boolean);
		}

		@Test
		public void shouldReturnMapWithComposite() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:" + Byte.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue((byte)1));
			Assert.assertTrue(map.get("one") instanceof Byte);

			map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:" + Float.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue((float)1));
			Assert.assertTrue(map.get("one") instanceof Float);
		}

		@Test
		public void shouldReturnMapWithDouble() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:1.0:double"});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(1.0d));
			Assert.assertTrue(map.get("one") instanceof Double);

			map = MapUtil.toLinkedHashMap(
				new String[] {"one:1.0:" + Double.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(1.0d));
			Assert.assertTrue(map.get("one") instanceof Double);
		}

		@Test
		public void shouldReturnMapWithInteger() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:int"});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(1));
			Assert.assertTrue(map.get("one") instanceof Integer);

			map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:" + Integer.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(1));
			Assert.assertTrue(map.get("one") instanceof Integer);
		}

		@Test
		public void shouldReturnMapWithLong() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:long"});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(1L));
			Assert.assertTrue(map.get("one") instanceof Long);

			map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:" + Long.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue(1L));
			Assert.assertTrue(map.get("one") instanceof Long);
		}

		@Test
		public void shouldReturnMapWithShort() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:short"});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue((short)1));
			Assert.assertTrue(map.get("one") instanceof Short);

			map = MapUtil.toLinkedHashMap(
				new String[] {"one:1:" + Short.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue((short)1));
			Assert.assertTrue(map.get("one") instanceof Short);
		}

		@Test
		public void shouldReturnMapWithString() {
			Map<String, Object> map = MapUtil.toLinkedHashMap(
				new String[] {"one:X:" + String.class.getName()});

			Assert.assertEquals(map.toString(), 1, map.size());
			Assert.assertTrue(map.containsKey("one"));
			Assert.assertTrue(map.containsValue("X"));
			Assert.assertTrue(map.get("one") instanceof String);
		}

	}

	public static class WhenCreatingAMapFromArray {

		@Test
		public void shouldFailWithOddLength() {
			try {
				MapUtil.fromArray(new String[] {"one", "two", "three"});

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Assert.assertEquals(
					"Array length is not an even number", iae.getMessage());
			}
		}

		@Test
		public void shouldReturnEmptyMapWithZeroLength() {
			Map<String, String> map = MapUtil.fromArray(new String[0]);

			Assert.assertTrue(map.isEmpty());
		}

		@Test
		public void shouldSucceedWithEvenLength() {
			String[] array = new String[] {
				PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS,
				PropsKeys.ADMIN_EMAIL_FROM_ADDRESS,
				PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME,
				PropsKeys.ADMIN_EMAIL_FROM_NAME, "allowAnonymousPosting",
				PropsKeys.MESSAGE_BOARDS_ANONYMOUS_POSTING_ENABLED,
				"emailFromAddress", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_ADDRESS,
				"emailFromName", PropsKeys.MESSAGE_BOARDS_EMAIL_FROM_NAME,
				"emailHtmlFormat", PropsKeys.MESSAGE_BOARDS_EMAIL_HTML_FORMAT,
				"emailMessageAddedEnabled",
				PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_ADDED_ENABLED,
				"emailMessageUpdatedEnabled",
				PropsKeys.MESSAGE_BOARDS_EMAIL_MESSAGE_UPDATED_ENABLED,
				"enableFlags", PropsKeys.MESSAGE_BOARDS_FLAGS_ENABLED,
				"enableRatings", PropsKeys.MESSAGE_BOARDS_RATINGS_ENABLED,
				"enableRss", PropsKeys.MESSAGE_BOARDS_RSS_ENABLED,
				"messageFormat",
				PropsKeys.MESSAGE_BOARDS_MESSAGE_FORMATS_DEFAULT, "priorities",
				PropsKeys.MESSAGE_BOARDS_THREAD_PRIORITIES, "ranks",
				PropsKeys.MESSAGE_BOARDS_USER_RANKS, "recentPostsDateOffset",
				PropsKeys.MESSAGE_BOARDS_RECENT_POSTS_DATE_OFFSET, "rssDelta",
				PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA,
				"rssDisplayStyle", PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT,
				"rssFeedType", PropsKeys.RSS_FEED_TYPE_DEFAULT,
				"subscribeByDefault",
				PropsKeys.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT
			};

			Map<String, String> map = MapUtil.fromArray(array);

			Assert.assertNotNull(map);

			for (int i = 0; i < array.length; i += 2) {
				Assert.assertEquals(array[i + 1], map.get(array[i]));
			}
		}

	}

	public static class WhenFilteringByPredicateFilter {

		@Test
		public void shouldAllowFilterBySuperType() {
			Map<String, Integer> inputMap = new HashMap<>();

			inputMap.put("1", 1);
			inputMap.put("2", 2);
			inputMap.put("3", 3);
			inputMap.put("4", 4);
			inputMap.put("5", 5);

			Map<String, Integer> outputMap = MapUtil.filterByValues(
				inputMap,
				new PredicateFilter<Number>() {

					@Override
					public boolean filter(Number number) {
						if ((number.intValue() % 2) == 0) {
							return true;
						}

						return false;
					}

				});

			Assert.assertEquals(outputMap.toString(), 2, outputMap.size());
			Assert.assertEquals((Integer)2, outputMap.get("2"));
			Assert.assertEquals((Integer)4, outputMap.get("4"));
		}

		@Test
		public void shouldAllowFilterBySuperTypeAndOutputToSupertype() {
			Map<String, Integer> inputMap = new HashMap<>();

			inputMap.put("1", 1);
			inputMap.put("2", 2);
			inputMap.put("3", 3);
			inputMap.put("4", 4);
			inputMap.put("5", 5);

			HashMap<String, Number> outputMap = new HashMap<>();

			MapUtil.filter(
				inputMap, outputMap,
				new PredicateFilter<Map.Entry<?, Number>>() {

					@Override
					public boolean filter(Map.Entry<?, Number> entry) {
						if ((entry.getValue().intValue() % 2) == 0) {
							return true;
						}

						return false;
					}

				});

			Assert.assertEquals(outputMap.toString(), 2, outputMap.size());
			Assert.assertEquals(2, outputMap.get("2"));
			Assert.assertEquals(4, outputMap.get("4"));
		}

		@Test
		public void shouldReturnMapFilteredByEven() {
			Map<String, String> inputMap = new HashMap<>();

			inputMap.put("1", "one");
			inputMap.put("2", "two");
			inputMap.put("3", "three");
			inputMap.put("4", "four");
			inputMap.put("5", "five");

			Map<String, String> outputMap = MapUtil.filter(
				inputMap,
				new PredicateFilter<Map.Entry<String, ?>>() {

					@Override
					public boolean filter(Map.Entry<String, ?> entry) {
						int value = GetterUtil.getInteger(entry.getKey());

						if ((value % 2) == 0) {
							return true;
						}

						return false;
					}

				});

			Assert.assertEquals(outputMap.toString(), 2, outputMap.size());
			Assert.assertEquals("two", outputMap.get("2"));
			Assert.assertEquals("four", outputMap.get("4"));
		}

		@Test
		public void shouldReturnMapFilteredByPrefix() {
			Map<String, String> inputMap = new HashMap<>();

			inputMap.put("2", "two");
			inputMap.put("4", "four");
			inputMap.put("x1", "one");
			inputMap.put("x3", "three");
			inputMap.put("x5", "five");

			Map<String, String> outputMap = MapUtil.filterByKeys(
				inputMap, new PrefixPredicateFilter("x"));

			Assert.assertEquals(outputMap.toString(), 2, outputMap.size());
			Assert.assertEquals("two", outputMap.get("2"));
			Assert.assertEquals("four", outputMap.get("4"));
		}

	}

}
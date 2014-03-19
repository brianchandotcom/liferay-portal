/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 */
public class MapUtilTest {

	@Test
	public void testFromArray() {
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
			"enableRss", PropsKeys.MESSAGE_BOARDS_RSS_ENABLED, "messageFormat",
			PropsKeys.MESSAGE_BOARDS_MESSAGE_FORMATS_DEFAULT, "priorities",
			PropsKeys.MESSAGE_BOARDS_THREAD_PRIORITIES, "ranks",
			PropsKeys.MESSAGE_BOARDS_USER_RANKS, "recentPostsDateOffset",
			PropsKeys.MESSAGE_BOARDS_RECENT_POSTS_DATE_OFFSET, "rssDelta",
			PropsKeys.SEARCH_CONTAINER_PAGE_DEFAULT_DELTA, "rssDisplayStyle",
			PropsKeys.RSS_FEED_DISPLAY_STYLE_DEFAULT, "rssFeedType",
			PropsKeys.RSS_FEED_TYPE_DEFAULT, "subscribeByDefault",
			PropsKeys.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT};

		Map<String, String> map = MapUtil.fromArray(array);

		Assert.assertNotNull(map);

		for (int i = 0; i < array.length; i += 2) {
			Assert.assertEquals(array[i + 1], map.get(array[i]));
		}
	}

	@Test
	public void testFromArrayWithOddLength() {
		try {
			MapUtil.fromArray(new String[] {"one", "two", "three"});

			Assert.fail("An IllegalArgument exception should have been thrown");
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Array length is not an even number", iae.getMessage());
		}
	}

	@Test
	public void testFromArrayWithZeroLength() {
		Map<String, String> map = MapUtil.fromArray(new String[] {});

		Assert.assertTrue(map.isEmpty());
	}

	@Test
	public void testPredicateFilter() throws Exception {
		Map<String, String> inputMap = new HashMap<String, String>();

		inputMap.put("1", "one");
		inputMap.put("2", "two");
		inputMap.put("3", "three");
		inputMap.put("4", "four");
		inputMap.put("5", "five");

		Map<String, String> outputMap = MapUtil.filter(
			inputMap, new HashMap<String, String>(),
			new PredicateFilter<String>() {

				@Override
				public boolean filter(String string) {
					int value = GetterUtil.getInteger(string);

					if ((value % 2) == 0) {
						return true;
					}

					return false;
				}

			});

		Assert.assertEquals(2, outputMap.size());
		Assert.assertEquals("two", outputMap.get("2"));
		Assert.assertEquals("four", outputMap.get("4"));
	}

	@Test
	public void testPrefixPredicateFilter() throws Exception {
		Map<String, String> inputMap = new HashMap<String, String>();

		inputMap.put("x1", "one");
		inputMap.put("2", "two");
		inputMap.put("x3", "three");
		inputMap.put("4", "four");
		inputMap.put("x5", "five");

		Map<String, String> outputMap = MapUtil.filter(
			inputMap, new HashMap<String, String>(),
			new PrefixPredicateFilter("x"));

		Assert.assertEquals(2, outputMap.size());
		Assert.assertEquals("two", outputMap.get("2"));
		Assert.assertEquals("four", outputMap.get("4"));
	}

}
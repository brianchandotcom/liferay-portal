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

package com.liferay.portal.kernel.map;

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PropsKeys;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Sampsa Sohlman
 * @author Manuel de la Peña
 * @author Péter Borkuti
 */
public class MapUtilWhenCreatingAMapFromArrayTest {

	@Test
	public void testShouldFailWithOddLength() {
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
	public void testShouldReturnEmptyMapWithZeroLength() {
		Map<String, String> map = MapUtil.fromArray(new String[0]);

		Assert.assertTrue(map.toString(), map.isEmpty());
	}

	@Test
	public void testShouldSucceedWithEvenLength() {
		String[] array = {
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
			PropsKeys.MESSAGE_BOARDS_SUBSCRIBE_BY_DEFAULT
		};

		Map<String, String> map = MapUtil.fromArray(array);

		Assert.assertNotNull(map);

		for (int i = 0; i < array.length; i += 2) {
			Assert.assertEquals(array[i + 1], map.get(array[i]));
		}
	}

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author David Arques
 */
public class TrafficSourceTest {

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testToJSONObject() {
		TrafficSource trafficSource = new TrafficSource(
			RandomTestUtil.randomString(), null, RandomTestUtil.randomInt(),
			RandomTestUtil.randomDouble());

		String helpMessage = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", helpMessage
			).put(
				"keywords", JSONFactoryUtil.createJSONArray()
			).put(
				"name", trafficSource.getName()
			).put(
				"share", trafficSource.getTrafficShare()
			).put(
				"title", title
			).put(
				"value", trafficSource.getTrafficAmount()
			).toString(),
			String.valueOf(trafficSource.toJSONObject(helpMessage, title)));
	}

	@Test
	public void testToJSONObjectWithSearchKeywords() {
		TrafficSource trafficSource = new TrafficSource(
			RandomTestUtil.randomString(),
			Arrays.asList(
				new SearchKeyword("liferay", 1, 3600, 2882),
				new SearchKeyword("liferay portal", 1, 556, 850),
				new SearchKeyword("liferay inc", 1, 755, 855),
				new SearchKeyword("what is liferay", 1, 390, 312),
				new SearchKeyword("liferay development services", 1, 390, 310),
				new SearchKeyword("com liferay portal", 1, 50, 40),
				new SearchKeyword("liferay portal server", 1, 50, 40),
				new SearchKeyword("liferay india", 1, 390, 312)),
			RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		String helpMessage = RandomTestUtil.randomString();
		String title = RandomTestUtil.randomString();

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", helpMessage
			).put(
				"keywords",
				JSONUtil.putAll(
					JSONUtil.put(
						"keyword", "liferay"
					).put(
						"position", 1
					).put(
						"searchVolume", 3600
					).put(
						"traffic", 2882
					),
					JSONUtil.put(
						"keyword", "liferay inc"
					).put(
						"position", 1
					).put(
						"searchVolume", 755
					).put(
						"traffic", 855
					),
					JSONUtil.put(
						"keyword", "liferay portal"
					).put(
						"position", 1
					).put(
						"searchVolume", 556
					).put(
						"traffic", 850
					),
					JSONUtil.put(
						"keyword", "what is liferay"
					).put(
						"position", 1
					).put(
						"searchVolume", 390
					).put(
						"traffic", 312
					),
					JSONUtil.put(
						"keyword", "liferay india"
					).put(
						"position", 1
					).put(
						"searchVolume", 390
					).put(
						"traffic", 312
					))
			).put(
				"name", trafficSource.getName()
			).put(
				"share", trafficSource.getTrafficShare()
			).put(
				"title", title
			).put(
				"value", trafficSource.getTrafficAmount()
			).toString(),
			String.valueOf(trafficSource.toJSONObject(helpMessage, title)));
	}

}
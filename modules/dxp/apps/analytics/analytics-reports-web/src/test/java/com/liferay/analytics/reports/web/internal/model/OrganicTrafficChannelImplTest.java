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

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class OrganicTrafficChannelImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		OrganicTrafficChannelImpl organicTrafficChannelImpl =
			new OrganicTrafficChannelImpl(
				Collections.singletonList(
					new CountrySearchKeywords(
						"us",
						Arrays.asList(
							new SearchKeyword("liferay", 1, 3600, 2880L),
							new SearchKeyword(
								"liferay portal", 1, 390, 312L)))),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"countryKeywords",
				JSONUtil.putAll(
					JSONUtil.put(
						"countryCode", "us"
					).put(
						"countryName", "United States"
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
								"traffic", 2880
							),
							JSONUtil.put(
								"keyword", "liferay portal"
							).put(
								"position", 1
							).put(
								"searchVolume", 390
							).put(
								"traffic", 312
							))
					))
			).put(
				"helpMessage", organicTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", organicTrafficChannelImpl.getName()
			).put(
				"share",
				String.format(
					"%.1f", organicTrafficChannelImpl.getTrafficShare())
			).put(
				"title", organicTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(organicTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				organicTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(organicTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithError() {
		OrganicTrafficChannelImpl organicTrafficChannelImpl =
			new OrganicTrafficChannelImpl(true);

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", organicTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", organicTrafficChannelImpl.getName()
			).put(
				"title", organicTrafficChannelImpl.getName()
			).toString(),
			String.valueOf(
				organicTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(organicTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithoutCountrySearchKeywords() {
		OrganicTrafficChannelImpl organicTrafficChannelImpl =
			new OrganicTrafficChannelImpl(
				null, RandomTestUtil.randomInt(),
				RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", organicTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", organicTrafficChannelImpl.getName()
			).put(
				"share",
				String.format(
					"%.1f", organicTrafficChannelImpl.getTrafficShare())
			).put(
				"title", organicTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(organicTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				organicTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(organicTrafficChannelImpl))));
	}

	private ResourceBundle _getResourceBundle(TrafficChannel trafficChannel) {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Arrays.asList(
						trafficChannel.getName(),
						trafficChannel.getHelpMessageKey()));
			}

			@Override
			protected Object handleGetObject(String key) {
				return key;
			}

		};
	}

}
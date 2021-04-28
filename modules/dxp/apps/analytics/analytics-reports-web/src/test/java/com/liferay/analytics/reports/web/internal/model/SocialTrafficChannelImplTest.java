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

import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Objects;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class SocialTrafficChannelImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		SocialTrafficChannelImpl socialTrafficChannelImpl =
			new SocialTrafficChannelImpl(
				Arrays.asList(
					new ReferringSocialMedia("twitter", 98),
					new ReferringSocialMedia("other", 76)),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		JSONObject jsonObject = socialTrafficChannelImpl.toJSONObject(
			LocaleUtil.US, _getResourceBundle(socialTrafficChannelImpl));

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"name", "twitter"
				).put(
					"title", "Twitter"
				).put(
					"trafficAmount", 98
				),
				JSONUtil.put(
					"name", "other"
				).put(
					"title", "Other"
				).put(
					"trafficAmount", 76
				)
			).toString(),
			String.valueOf(jsonObject.get("referringSocialMedia")));
	}

	@Test
	public void testToJSONObjectWithEmptyTrafficAmountReferringSocialMedia() {
		SocialTrafficChannelImpl socialTrafficChannelImpl =
			new SocialTrafficChannelImpl(
				Arrays.asList(
					new ReferringSocialMedia("twitter", 98),
					new ReferringSocialMedia("other", 76),
					new ReferringSocialMedia(RandomTestUtil.randomString(), 0)),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		JSONObject jsonObject = socialTrafficChannelImpl.toJSONObject(
			LocaleUtil.US, _getResourceBundle(socialTrafficChannelImpl));

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"name", "twitter"
				).put(
					"title", "Twitter"
				).put(
					"trafficAmount", 98
				),
				JSONUtil.put(
					"name", "other"
				).put(
					"title", "Other"
				).put(
					"trafficAmount", 76
				)
			).toString(),
			String.valueOf(jsonObject.get("referringSocialMedia")));
	}

	@Test
	public void testToJSONObjectWithError() {
		SocialTrafficChannelImpl socialTrafficChannelImpl =
			new SocialTrafficChannelImpl(true);

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", socialTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", socialTrafficChannelImpl.getName()
			).put(
				"title", socialTrafficChannelImpl.getName()
			).toString(),
			String.valueOf(
				socialTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(socialTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithoutReferringSocialMedia() {
		SocialTrafficChannelImpl socialTrafficChannelImpl =
			new SocialTrafficChannelImpl(
				Collections.emptyList(), RandomTestUtil.randomInt(),
				RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", socialTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", socialTrafficChannelImpl.getName()
			).put(
				"share",
				String.format(
					"%.1f", socialTrafficChannelImpl.getTrafficShare())
			).put(
				"title", socialTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(socialTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				socialTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(socialTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithUnsortedReferringSocialMedia() {
		SocialTrafficChannelImpl socialTrafficChannelImpl =
			new SocialTrafficChannelImpl(
				Arrays.asList(
					new ReferringSocialMedia("other", 76),
					new ReferringSocialMedia("twitter", 98)),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		JSONObject jsonObject = socialTrafficChannelImpl.toJSONObject(
			LocaleUtil.US, _getResourceBundle(socialTrafficChannelImpl));

		Assert.assertEquals(
			JSONUtil.putAll(
				JSONUtil.put(
					"name", "twitter"
				).put(
					"title", "Twitter"
				).put(
					"trafficAmount", 98
				),
				JSONUtil.put(
					"name", "other"
				).put(
					"title", "Other"
				).put(
					"trafficAmount", 76
				)
			).toString(),
			String.valueOf(jsonObject.get("referringSocialMedia")));
	}

	private ResourceBundle _getResourceBundle(TrafficChannel trafficChannel) {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Arrays.asList(
						trafficChannel.getName(),
						trafficChannel.getHelpMessageKey(), "other"));
			}

			@Override
			protected Object handleGetObject(String key) {
				if (Objects.equals("other", key)) {
					return "Other";
				}

				return key;
			}

		};
	}

}
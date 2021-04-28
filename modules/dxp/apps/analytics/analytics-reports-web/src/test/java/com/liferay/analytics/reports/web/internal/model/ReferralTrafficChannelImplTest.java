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
public class ReferralTrafficChannelImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		ReferralTrafficChannelImpl referralTrafficChannelImpl =
			new ReferralTrafficChannelImpl(
				Collections.singletonList(
					new ReferringURL(45, "https://www.liferay.com/")),
				Arrays.asList(
					new ReferringURL(15, "https://www.liferay.com/page1"),
					new ReferringURL(4, "https://www.liferay.com/page2")),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", referralTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", referralTrafficChannelImpl.getName()
			).put(
				"referringDomains",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 45
					).put(
						"url", "https://www.liferay.com/"
					))
			).put(
				"referringPages",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 15
					).put(
						"url", "https://www.liferay.com/page1"
					),
					JSONUtil.put(
						"trafficAmount", 4
					).put(
						"url", "https://www.liferay.com/page2"
					))
			).put(
				"share",
				String.format(
					"%.1f", referralTrafficChannelImpl.getTrafficShare())
			).put(
				"title", referralTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(referralTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				referralTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(referralTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithError() {
		ReferralTrafficChannelImpl referralTrafficChannelImpl =
			new ReferralTrafficChannelImpl(true);

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", referralTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", referralTrafficChannelImpl.getName()
			).put(
				"title", referralTrafficChannelImpl.getName()
			).toString(),
			String.valueOf(
				referralTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(referralTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithMoreThanTenReferringDomains() {
		ReferralTrafficChannelImpl referralTrafficChannelImpl =
			new ReferralTrafficChannelImpl(
				Arrays.asList(
					new ReferringURL(99, "https://www.domain1.com/"),
					new ReferringURL(98, "https://www.domain2.com/"),
					new ReferringURL(97, "https://www.domain3.com/"),
					new ReferringURL(96, "https://www.domain4.com/"),
					new ReferringURL(95, "https://www.domain5.com/"),
					new ReferringURL(94, "https://www.domain6.com/"),
					new ReferringURL(93, "https://www.domain7.com/"),
					new ReferringURL(92, "https://www.domain8.com/"),
					new ReferringURL(91, "https://www.domain9.com/"),
					new ReferringURL(90, "https://www.domain10.com/"),
					new ReferringURL(89, "https://www.domain11.com/"),
					new ReferringURL(88, "https://www.domain12.com/")),
				Arrays.asList(
					new ReferringURL(15, "https://www.liferay.com/page1"),
					new ReferringURL(4, "https://www.liferay.com/page2")),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", referralTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", referralTrafficChannelImpl.getName()
			).put(
				"referringDomains",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 99
					).put(
						"url", "https://www.domain1.com/"
					),
					JSONUtil.put(
						"trafficAmount", 98
					).put(
						"url", "https://www.domain2.com/"
					),
					JSONUtil.put(
						"trafficAmount", 97
					).put(
						"url", "https://www.domain3.com/"
					),
					JSONUtil.put(
						"trafficAmount", 96
					).put(
						"url", "https://www.domain4.com/"
					),
					JSONUtil.put(
						"trafficAmount", 95
					).put(
						"url", "https://www.domain5.com/"
					),
					JSONUtil.put(
						"trafficAmount", 94
					).put(
						"url", "https://www.domain6.com/"
					),
					JSONUtil.put(
						"trafficAmount", 93
					).put(
						"url", "https://www.domain7.com/"
					),
					JSONUtil.put(
						"trafficAmount", 92
					).put(
						"url", "https://www.domain8.com/"
					),
					JSONUtil.put(
						"trafficAmount", 91
					).put(
						"url", "https://www.domain9.com/"
					),
					JSONUtil.put(
						"trafficAmount", 90
					).put(
						"url", "https://www.domain10.com/"
					))
			).put(
				"referringPages",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 15
					).put(
						"url", "https://www.liferay.com/page1"
					),
					JSONUtil.put(
						"trafficAmount", 4
					).put(
						"url", "https://www.liferay.com/page2"
					))
			).put(
				"share",
				String.format(
					"%.1f", referralTrafficChannelImpl.getTrafficShare())
			).put(
				"title", referralTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(referralTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				referralTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(referralTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithMoreThanTenReferringPages() {
		ReferralTrafficChannelImpl referralTrafficChannelImpl =
			new ReferralTrafficChannelImpl(
				Collections.singletonList(
					new ReferringURL(45, "https://www.liferay.com/")),
				Arrays.asList(
					new ReferringURL(99, "https://www.liferay.com/page1"),
					new ReferringURL(98, "https://www.liferay.com/page2"),
					new ReferringURL(97, "https://www.liferay.com/page3"),
					new ReferringURL(96, "https://www.liferay.com/page4"),
					new ReferringURL(95, "https://www.liferay.com/page5"),
					new ReferringURL(94, "https://www.liferay.com/page6"),
					new ReferringURL(93, "https://www.liferay.com/page7"),
					new ReferringURL(92, "https://www.liferay.com/page8"),
					new ReferringURL(91, "https://www.liferay.com/page9"),
					new ReferringURL(90, "https://www.liferay.com/page10"),
					new ReferringURL(89, "https://www.liferay.com/page11"),
					new ReferringURL(88, "https://www.liferay.com/page12")),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", referralTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", referralTrafficChannelImpl.getName()
			).put(
				"referringDomains",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 45
					).put(
						"url", "https://www.liferay.com/"
					))
			).put(
				"referringPages",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 99
					).put(
						"url", "https://www.liferay.com/page1"
					),
					JSONUtil.put(
						"trafficAmount", 98
					).put(
						"url", "https://www.liferay.com/page2"
					),
					JSONUtil.put(
						"trafficAmount", 97
					).put(
						"url", "https://www.liferay.com/page3"
					),
					JSONUtil.put(
						"trafficAmount", 96
					).put(
						"url", "https://www.liferay.com/page4"
					),
					JSONUtil.put(
						"trafficAmount", 95
					).put(
						"url", "https://www.liferay.com/page5"
					),
					JSONUtil.put(
						"trafficAmount", 94
					).put(
						"url", "https://www.liferay.com/page6"
					),
					JSONUtil.put(
						"trafficAmount", 93
					).put(
						"url", "https://www.liferay.com/page7"
					),
					JSONUtil.put(
						"trafficAmount", 92
					).put(
						"url", "https://www.liferay.com/page8"
					),
					JSONUtil.put(
						"trafficAmount", 91
					).put(
						"url", "https://www.liferay.com/page9"
					),
					JSONUtil.put(
						"trafficAmount", 90
					).put(
						"url", "https://www.liferay.com/page10"
					))
			).put(
				"share",
				String.format(
					"%.1f", referralTrafficChannelImpl.getTrafficShare())
			).put(
				"title", referralTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(referralTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				referralTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(referralTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithoutReferringDomainsAndWithoutReferringPages() {
		ReferralTrafficChannelImpl referralTrafficChannelImpl =
			new ReferralTrafficChannelImpl(
				null, null, RandomTestUtil.randomInt(),
				RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", referralTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", referralTrafficChannelImpl.getName()
			).put(
				"share",
				String.format(
					"%.1f", referralTrafficChannelImpl.getTrafficShare())
			).put(
				"title", referralTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(referralTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				referralTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(referralTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithUnsortedReferringDomains() {
		ReferralTrafficChannelImpl referralTrafficChannelImpl =
			new ReferralTrafficChannelImpl(
				Arrays.asList(
					new ReferringURL(98, "https://www.domain1.com/"),
					new ReferringURL(99, "https://www.domain2.com/")),
				Collections.singletonList(
					new ReferringURL(15, "https://www.liferay.com/page1")),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", referralTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", referralTrafficChannelImpl.getName()
			).put(
				"referringDomains",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 99
					).put(
						"url", "https://www.domain2.com/"
					),
					JSONUtil.put(
						"trafficAmount", 98
					).put(
						"url", "https://www.domain1.com/"
					))
			).put(
				"referringPages",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 15
					).put(
						"url", "https://www.liferay.com/page1"
					))
			).put(
				"share",
				String.format(
					"%.1f", referralTrafficChannelImpl.getTrafficShare())
			).put(
				"title", referralTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(referralTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				referralTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(referralTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithUnsortedReferringPages() {
		ReferralTrafficChannelImpl referralTrafficChannelImpl =
			new ReferralTrafficChannelImpl(
				Collections.singletonList(
					new ReferringURL(45, "https://www.liferay.com/")),
				Arrays.asList(
					new ReferringURL(98, "https://www.liferay.com/page1"),
					new ReferringURL(99, "https://www.liferay.com/page2")),
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", referralTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", referralTrafficChannelImpl.getName()
			).put(
				"referringDomains",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 45
					).put(
						"url", "https://www.liferay.com/"
					))
			).put(
				"referringPages",
				JSONUtil.putAll(
					JSONUtil.put(
						"trafficAmount", 99
					).put(
						"url", "https://www.liferay.com/page2"
					),
					JSONUtil.put(
						"trafficAmount", 98
					).put(
						"url", "https://www.liferay.com/page1"
					))
			).put(
				"share",
				String.format(
					"%.1f", referralTrafficChannelImpl.getTrafficShare())
			).put(
				"title", referralTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(referralTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				referralTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(referralTrafficChannelImpl))));
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
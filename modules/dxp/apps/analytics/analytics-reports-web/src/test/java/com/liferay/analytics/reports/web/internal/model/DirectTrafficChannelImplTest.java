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
public class DirectTrafficChannelImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		DirectTrafficChannelImpl directTrafficChannelImpl =
			new DirectTrafficChannelImpl(
				RandomTestUtil.randomInt(), RandomTestUtil.randomDouble());

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", directTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", directTrafficChannelImpl.getName()
			).put(
				"share",
				String.format(
					"%.1f", directTrafficChannelImpl.getTrafficShare())
			).put(
				"title", directTrafficChannelImpl.getName()
			).put(
				"value",
				Math.toIntExact(directTrafficChannelImpl.getTrafficAmount())
			).toString(),
			String.valueOf(
				directTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(directTrafficChannelImpl))));
	}

	@Test
	public void testToJSONObjectWithError() {
		DirectTrafficChannelImpl directTrafficChannelImpl =
			new DirectTrafficChannelImpl(true);

		Assert.assertEquals(
			JSONUtil.put(
				"helpMessage", directTrafficChannelImpl.getHelpMessageKey()
			).put(
				"name", directTrafficChannelImpl.getName()
			).put(
				"title", directTrafficChannelImpl.getName()
			).toString(),
			String.valueOf(
				directTrafficChannelImpl.toJSONObject(
					LocaleUtil.US,
					_getResourceBundle(directTrafficChannelImpl))));
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
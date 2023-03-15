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

package com.liferay.osb.faro.model.impl;

import com.liferay.osb.faro.model.FaroProject;
import com.liferay.portal.configuration.ConfigurationFactoryImpl;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.configuration.ConfigurationFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shinn Lok
 */
public class FaroProjectImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		ConfigurationFactoryUtil.setConfigurationFactory(
			new ConfigurationFactoryImpl());

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@Test
	public void testIsAllowedIPAddress() throws Exception {
		FaroProject faroProject = new FaroProjectImpl();

		faroProject.setIpAddresses(
			JSONUtil.put(
				"192.168.0.159/0"
			).toString());

		Assert.assertTrue(faroProject.isAllowedIPAddress("1.2.3.4"));
		Assert.assertTrue(faroProject.isAllowedIPAddress("192.168.0.159"));

		faroProject.setIpAddresses(
			JSONUtil.put(
				"192.168.1.0/24"
			).toString());

		Assert.assertTrue(faroProject.isAllowedIPAddress("192.168.1.104"));
		Assert.assertFalse(faroProject.isAllowedIPAddress("192.168.0.104"));

		faroProject.setIpAddresses(
			JSONUtil.putAll(
				"192.168.1.159", "192.168.1.161"
			).toString());

		Assert.assertTrue(faroProject.isAllowedIPAddress("192.168.1.159"));
		Assert.assertFalse(faroProject.isAllowedIPAddress("192.168.1.160"));
		Assert.assertTrue(faroProject.isAllowedIPAddress("192.168.1.161"));

		faroProject.setIpAddresses(
			JSONUtil.putAll(
				"192.168.0.159", "192.168.1.0/24"
			).toString());

		Assert.assertTrue(faroProject.isAllowedIPAddress("192.168.0.159"));
		Assert.assertFalse(faroProject.isAllowedIPAddress("192.168.0.160"));
		Assert.assertTrue(faroProject.isAllowedIPAddress("192.168.1.99"));
	}

}
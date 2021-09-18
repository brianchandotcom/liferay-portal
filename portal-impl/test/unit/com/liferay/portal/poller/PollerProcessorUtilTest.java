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

package com.liferay.portal.poller;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Leon Chi
 */
public class PollerProcessorUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetPollerProcessor() {
		PollerProcessor pollerProcessor = ProxyFactory.newDummyInstance(
			PollerProcessor.class);

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceRegistration<?> serviceRegistration =
			bundleContext.registerService(
				PollerProcessor.class, pollerProcessor,
				MapUtil.singletonDictionary(
					"javax.portlet.name", _TEST_PORTLET_ID));

		try {
			Assert.assertSame(
				pollerProcessor,
				PollerProcessorUtil.getPollerProcessor(_TEST_PORTLET_ID));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _TEST_PORTLET_ID = "TEST_PORTLET_ID";

}
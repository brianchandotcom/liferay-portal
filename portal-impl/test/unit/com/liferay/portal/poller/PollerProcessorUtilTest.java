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

import com.liferay.portal.kernel.poller.PollerProcessor;
import com.liferay.portal.kernel.resiliency.spi.SPIRegistryUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.resiliency.spi.SPIRegistryImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leon Chi
 */
public class PollerProcessorUtilTest {

	@Test
	public void testGetPollerProcessor() {
		PollerProcessor pollerProcessor = ProxyFactory.newDummyInstance(
			PollerProcessor.class);

		SPIRegistryUtil spiRegistryUtil = new SPIRegistryUtil();

		spiRegistryUtil.setSPIRegistry(new SPIRegistryImpl());

		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		ServiceRegistration<PollerProcessor> serviceRegistration =
			registry.registerService(
				PollerProcessor.class, pollerProcessor,
				Collections.singletonMap(
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
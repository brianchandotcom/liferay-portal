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

package com.liferay.portal.test.function;

import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.cm.ManagedService;

/**
 * @author Raymond Augé
 */
public class AwaitingConfigurationHolder extends ConfigurationHolder {

	public AwaitingConfigurationHolder(
			BundleContext bundleContext, ConfigurationAdmin configurationAdmin,
			String pid, long timeout, TimeUnit timeUnit)
		throws Exception {

		super(
			() -> {
				CountDownLatch countDownLatch = new CountDownLatch(2);

				ServiceRegistration<ManagedService> serviceRegistration =
					bundleContext.registerService(
						ManagedService.class,
						properties -> countDownLatch.countDown(),
						HashMapDictionaryBuilder.<String, Object>put(
							Constants.SERVICE_PID, pid
						).build());

				try {
					countDownLatch.await(timeout, timeUnit);
				}
				finally {
					serviceRegistration.unregister();
				}

				Configuration[] configurations =
					configurationAdmin.listConfigurations(
						"(service.pid=".concat(
							pid
						).concat(
							")"
						));

				Assert.assertNotNull(configurations);

				return configurations[0];
			});
	}

}
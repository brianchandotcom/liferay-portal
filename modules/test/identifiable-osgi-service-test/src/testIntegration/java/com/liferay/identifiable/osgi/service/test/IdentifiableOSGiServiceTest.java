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

package com.liferay.identifiable.osgi.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Tina Tian
 */
@RunWith(Arquillian.class)
public class IdentifiableOSGiServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testIdentifiableOSGiService() {
		Bundle bundle = FrameworkUtil.getBundle(
			IdentifiableOSGiServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String testOSGiServiceIdentifier = "testIdentifiableOSGiService";

		IdentifiableOSGiService testIdentifiableOSGiService =
			new IdentifiableOSGiService() {

				@Override
				public String getOSGiServiceIdentifier() {
					return testOSGiServiceIdentifier;
				}

			};

		ServiceRegistration<IdentifiableOSGiService> serviceRegistration =
			bundleContext.registerService(
				IdentifiableOSGiService.class, testIdentifiableOSGiService,
				null);

		Assert.assertSame(
			testIdentifiableOSGiService,
			IdentifiableOSGiServiceUtil.getIdentifiableOSGiService(
				testOSGiServiceIdentifier));

		serviceRegistration.unregister();
	}

}
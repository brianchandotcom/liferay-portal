/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
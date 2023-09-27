/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.deploy.hot.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.service.EmailAddressLocalService;
import com.liferay.portal.kernel.service.EmailAddressLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.test.rule.Inject;
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
 * @author Leon Chi
 */
@RunWith(Arquillian.class)
public class ServiceWrapperRegistryTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testInvokeOverrideMethod() throws PortalException {
		Bundle bundle = FrameworkUtil.getBundle(
			ServiceWrapperRegistryTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		EmailAddress emailAddress =
			_emailAddressLocalService.createEmailAddress(
				_TEST_EMAIL_ADDRESS_ID);

		ServiceRegistration<ServiceWrapper<?>> serviceRegistration =
			bundleContext.registerService(
				(Class<ServiceWrapper<?>>)(Class<?>)ServiceWrapper.class,
				new EmailAddressLocalServiceWrapper() {

					@Override
					public EmailAddress getEmailAddress(long emailAddressId) {
						return emailAddress;
					}

				},
				null);

		try {
			Assert.assertSame(
				emailAddress,
				_emailAddressLocalService.getEmailAddress(
					_TEST_EMAIL_ADDRESS_ID));
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final long _TEST_EMAIL_ADDRESS_ID = 1;

	@Inject
	private EmailAddressLocalService _emailAddressLocalService;

}
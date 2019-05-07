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

		ServiceRegistration<ServiceWrapper> serviceRegistration =
			bundleContext.registerService(
				ServiceWrapper.class, new TestEmailLocalServiceWrapper(), null);

		try {
			EmailAddress emailAddress =
				_emailAddressLocalService.getEmailAddress(1);

			Assert.assertEquals("email@liferay.com", emailAddress.getAddress());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Inject
	private EmailAddressLocalService _emailAddressLocalService;

	private static class TestEmailLocalServiceWrapper
		extends EmailAddressLocalServiceWrapper {

		public TestEmailLocalServiceWrapper() {
			super(null);
		}

		public TestEmailLocalServiceWrapper(
			EmailAddressLocalService emailAddressService) {

			super(emailAddressService);
		}

		@Override
		public EmailAddress getEmailAddress(long emailAddressId) {
			EmailAddress emailAddress = createEmailAddress(1);

			emailAddress.setAddress("email@liferay.com");

			return emailAddress;
		}

	}

}
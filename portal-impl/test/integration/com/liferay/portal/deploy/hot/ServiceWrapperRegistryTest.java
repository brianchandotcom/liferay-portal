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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.model.EmailAddress;
import com.liferay.portal.service.EmailAddressLocalService;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SyntheticBundleRule;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Manuel de la Peña
 * @author Miguel Pastor
 */
public class ServiceWrapperRegistryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new SyntheticBundleRule("bundle.servicewrapperregistry"));

	@BeforeClass
	public static void setUpClass() throws Exception {
		_serviceWrapperRegistry = new ServiceWrapperRegistry();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_serviceWrapperRegistry.close();
	}

	@Test
	public void testInvokeOverrideMethod() throws PortalException {
		EmailAddressLocalService emailAddressLocalService =
			(EmailAddressLocalService)PortalBeanLocatorUtil.locate(
				EmailAddressLocalService.class.getName());

		EmailAddress emailAddress = emailAddressLocalService.getEmailAddress(1);

		Assert.assertEquals("email@liferay.com", emailAddress.getAddress());
	}

	private static ServiceWrapperRegistry _serviceWrapperRegistry;

}
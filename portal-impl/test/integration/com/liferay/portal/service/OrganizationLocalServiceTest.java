/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ListTypeConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.OrganizationConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="OrganizationLocalServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class OrganizationLocalServiceTest {

	@Test
	@Transactional
	public void testCreateOrganizationAndNotBecomingAUser() throws Exception {
		long userId = TestPropsValues.getUserId();

		Organization organization =
			OrganizationLocalServiceUtil.addOrganization(
				userId, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				"testCreateOrganizationAndNotBecomingAUser",
				OrganizationConstants.TYPE_REGULAR_ORGANIZATION, false, 0, 0,
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
				false, null);

		Assert.assertFalse(
			OrganizationLocalServiceUtil.hasUserOrganization(
				userId, organization.getOrganizationId()));
	}

	@Test
	public void testRetrieveOwnedOrganizations() throws Exception {
		User user = ServiceTestUtil.addUser("test1", false, null);
		long userId = user.getUserId();

		Organization organization =
			OrganizationLocalServiceUtil.addOrganization(
				userId, OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID,
				"testRetrieveOwnedOrganizations",
				OrganizationConstants.TYPE_REGULAR_ORGANIZATION, false, 0, 0,
				ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
				false, null);

		try {
			List<Organization> organizations = user.getOrganizations(true);

			boolean found = false;

			for (Organization curOrganization : organizations) {
				if (curOrganization.getOrganizationId() ==
						organization.getOrganizationId()) {

					found = true;
				}
			}

			Assert.assertTrue(
				"The organization created and owned by the user has not been " +
				"returned as one of his organizations", found);
		}
		finally {
			OrganizationLocalServiceUtil.deleteOrganization(
				organization.getOrganizationId());
		}
	}

}
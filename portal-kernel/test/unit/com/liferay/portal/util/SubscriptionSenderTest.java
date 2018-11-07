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

package com.liferay.portal.util;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CompanyWrapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupWrapper;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.CompanyLocalServiceWrapper;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Mika Koivisto
 */
public class SubscriptionSenderTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		ReflectionTestUtil.setFieldValue(
			CompanyLocalServiceUtil.class, "_service",
			new CompanyLocalServiceWrapper(null) {

				@Override
				public Company getCompany(long companyId) {
					return new CompanyWrapper(null) {

						@Override
						public long getCompanyId() {
							return 0L;
						}

						@Override
						public String getMx() {
							return null;
						}

						@Override
						public String getName() {
							return null;
						}

						@Override
						public String getPortalURL(long groupId) {
							if (groupId == 0L) {
								return "http://www.portal.com";
							}
							else if (groupId == 100L) {
								return "http://www.virtual.com";
							}

							return null;
						}

					};
				}

			});

		ReflectionTestUtil.setFieldValue(
			GroupLocalServiceUtil.class, "_service",
			new GroupLocalServiceWrapper(null) {

				@Override
				public Group getGroup(long groupId) {
					if (groupId == 100L) {
						return new GroupWrapper(null) {

							@Override
							public String getDescriptiveName() {
								return null;
							}

							@Override
							public boolean isLayout() {
								return false;
							}

						};
					}

					return null;
				}

			});

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		portalUUIDUtil.setPortalUUID(
			ProxyFactory.newDummyInstance(PortalUUID.class));

		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(ProxyFactory.newDummyInstance(Portal.class));
	}

	@Test
	public void testGetPortalURLWithGroupId() throws Exception {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setGroupId(100);
		subscriptionSender.setMailId("test-mail-id");

		subscriptionSender.initialize();

		String portalURL = String.valueOf(
			subscriptionSender.getContextAttribute("[$PORTAL_URL$]"));

		Assert.assertEquals("http://www.virtual.com", portalURL);
	}

	@Test
	public void testGetPortalURLWithoutGroupId() throws Exception {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setMailId("test-mail-id");

		subscriptionSender.initialize();

		String portalURL = String.valueOf(
			subscriptionSender.getContextAttribute("[$PORTAL_URL$]"));

		Assert.assertEquals("http://www.portal.com", portalURL);
	}

	@Test
	public void testGetPortalURLWithServiceContext() throws Exception {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setMailId("test-mail-id");

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(100L);

		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.initialize();

		String portalURL = String.valueOf(
			subscriptionSender.getContextAttribute("[$PORTAL_URL$]"));

		Assert.assertEquals("http://www.virtual.com", portalURL);
	}

}
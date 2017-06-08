/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Mika Koivisto
 */
@RunWith(PowerMockRunner.class)
public class SubscriptionSenderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		Props props = mock(Props.class);

		PropsUtil.setProps(props);

		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		portalUtil.setPortal(portal);

		portalBeanLocator = mock(BeanLocator.class);

		PortalBeanLocatorUtil.setBeanLocator(portalBeanLocator);

		GroupLocalService groupLocalService = getMockService(
			GroupLocalServiceUtil.class, GroupLocalService.class);

		Group group = mock(Group.class);

		when(groupLocalService.getGroup(Mockito.eq(100l))).thenReturn(group);

		when(group.isLayout()).thenReturn(false);

		CompanyLocalService companyLocalService = getMockService(
			CompanyLocalServiceUtil.class, CompanyLocalService.class);

		Company company = mock(Company.class);

		when(
			companyLocalService.getCompany(Mockito.anyLong())
		).thenReturn(
			company
		);

		when(
			company.getPortalURL(Mockito.eq(0l))
		).thenReturn(
			"http://www.portal.com"
		);

		when(
			company.getPortalURL(Mockito.eq(100l))
		).thenReturn(
			"http://www.virtual.com"
		);
	}

	@After
	public void tearDown() {
		for (Class<?> serviceUtilClass : serviceUtilClasses) {
			try {
				Field field = serviceUtilClass.getDeclaredField("_service");

				field.setAccessible(true);

				field.set(serviceUtilClass, null);
			}
			catch (Exception e) {
			}
		}

		PortalBeanLocatorUtil.reset();
	}

	@Test
	public void testWithGroupId() throws Exception {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setGroupId(100);

		subscriptionSender.initialize();

		String portalUrl = subscriptionSender.getContextAttribute(
			"[$PORTAL_URL$]").toString();

		Assert.assertEquals("http://www.virtual.com", portalUrl);
	}

	@Test
	public void testWithoutGroupId() throws Exception {
		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.initialize();

		String portalUrl = subscriptionSender.getContextAttribute(
			"[$PORTAL_URL$]").toString();

		Assert.assertEquals("http://www.portal.com", portalUrl);
	}

	@Test
	public void testWithServiceContext() throws Exception {
		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(100l);

		SubscriptionSender subscriptionSender = new SubscriptionSender();

		subscriptionSender.setServiceContext(serviceContext);

		subscriptionSender.initialize();

		String portalUrl = subscriptionSender.getContextAttribute(
			"[$PORTAL_URL$]").toString();

		Assert.assertEquals("http://www.virtual.com", portalUrl);
	}

	protected <T> T getMockService(
		Class<?> serviceUtilClass, Class<T> serviceClass) {

		serviceUtilClasses.add(serviceUtilClass);

		T service = mock(serviceClass);

		when(
			portalBeanLocator.locate(
				Mockito.eq(serviceClass.getName()))
		).thenReturn(
			service
		);

		return service;
	}

	protected BeanLocator portalBeanLocator;
	protected List<Class<?>> serviceUtilClasses = new ArrayList<Class<?>>();

}
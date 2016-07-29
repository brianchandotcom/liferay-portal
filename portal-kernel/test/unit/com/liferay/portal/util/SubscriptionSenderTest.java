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

import com.liferay.portal.kernel.bean.BeanLocator;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.SubscriptionSender;
import com.liferay.portal.kernel.uuid.PortalUUID;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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
		CompanyLocalService companyLocalService = getMockService(
			CompanyLocalServiceUtil.class, CompanyLocalService.class);

		Company company = mock(Company.class);

		when(
			companyLocalService.getCompany(Mockito.anyLong())
		).thenReturn(
			company
		);

		when(
			company.getPortalURL(Mockito.eq(0L))
		).thenReturn(
			"http://www.portal.com"
		);

		when(
			company.getPortalURL(Mockito.eq(100L))
		).thenReturn(
			"http://www.virtual.com"
		);

		GroupLocalService groupLocalService = getMockService(
			GroupLocalServiceUtil.class, GroupLocalService.class);

		Group group = mock(Group.class);

		when(
			groupLocalService.getGroup(Mockito.eq(100L))
		).thenReturn(
			group
		);

		when(
			group.isLayout()
		).thenReturn(
			false
		);

		PortalBeanLocatorUtil.setBeanLocator(_beanLocator);

		PortalUUIDUtil portalUUIDUtil = new PortalUUIDUtil();

		PortalUUID portalUUID = mock(PortalUUID.class);

		portalUUIDUtil.setPortalUUID(portalUUID);

		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		portalUtil.setPortal(portal);

		Props props = mock(Props.class);

		PropsUtil.setProps(props);
	}

	@After
	public void tearDown() {
		for (Class<?> serviceUtilClass : _serviceUtilClasses) {
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

	protected <T> T getMockService(
		Class<?> serviceUtilClass, Class<T> serviceClass) {

		_serviceUtilClasses.add(serviceUtilClass);

		T service = mock(serviceClass);

		when(
			_beanLocator.locate(Mockito.eq(serviceClass.getName()))
		).thenReturn(
			service
		);

		return service;
	}

	private final BeanLocator _beanLocator = mock(BeanLocator.class);
	private final List<Class<?>> _serviceUtilClasses = new ArrayList<>();

}
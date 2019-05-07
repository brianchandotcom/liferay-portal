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

package com.liferay.portlet;

import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.util.ProxyFactory;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portlet.internal.FriendlyURLMapperTrackerImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Leon Chi
 */
public class FriendlyURLMapperTrackerImplTest {

	@Test
	public void testGetFriendlyURLMapper() throws Exception {
		RegistryUtil.setRegistry(new BasicRegistryImpl());

		Registry registry = RegistryUtil.getRegistry();

		Portlet portlet = new PortletImpl();

		portlet.setPortletClass(MVCPortlet.class.getName());
		portlet.setPortletId(_PORTLET_NAME);

		FriendlyURLMapperTracker friendlyURLMapperTracker =
			new FriendlyURLMapperTrackerImpl(portlet);

		FriendlyURLMapper friendlyURLMapper = ProxyFactory.newDummyInstance(
			FriendlyURLMapper.class);

		ServiceRegistration<FriendlyURLMapper> serviceRegistration =
			registry.registerService(
				FriendlyURLMapper.class, friendlyURLMapper,
				Collections.singletonMap("javax.portlet.name", _PORTLET_NAME));

		try {
			Assert.assertSame(
				friendlyURLMapper,
				friendlyURLMapperTracker.getFriendlyURLMapper());
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	private static final String _PORTLET_NAME =
		"FriendlyURLMapperTrackerImplTest";

}
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

package com.liferay.portal.vulcan.internal.jaxrs.container.request.filter.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.FileNotFoundException;

import java.net.URL;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Application;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class SiteValidatorContainerRequestFilterTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("liferay.auth.verifier", true);
		properties.put("liferay.oauth2", false);
		properties.put("osgi.jaxrs.application.base", "/test-vulcan");
		properties.put(
			"osgi.jaxrs.extension.select", "(osgi.jaxrs.name=Liferay.Vulcan)");

		_serviceRegistration = registry.registerService(
			Application.class,
			new SiteValidatorContainerRequestFilterTest.TestApplication(),
			properties);
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test(expected = FileNotFoundException.class)
	public void testInValidGroup() throws Exception {
		URL url = new URL("http://localhost:8080/o/test-vulcan/0/name");

		StringUtil.read(url.openStream());
	}

	@Test
	public void testValidGroup() throws Exception {
		long defaultCompanyId = _portal.getDefaultCompanyId();

		User user = UserTestUtil.getAdminUser(defaultCompanyId);

		Group group = GroupTestUtil.addGroup(
			defaultCompanyId, user.getUserId(),
			GroupConstants.DEFAULT_PARENT_GROUP_ID);

		URL url = new URL(
			"http://localhost:8080/o/test-vulcan/" + group.getGroupId() +
				"/name");

		String groupName = StringUtil.read(url.openStream());

		Assert.assertEquals(group.getName(LocaleUtil.getDefault()), groupName);
	}

	public static class TestApplication extends Application {

		@Override
		public Set<Object> getSingletons() {
			return Collections.singleton(this);
		}

		@GET
		@Path("/{siteId}/name")
		public String testClass(@PathParam("siteId") Long siteId)
			throws Exception {

			Group group = GroupLocalServiceUtil.getGroup(siteId);

			return group.getName(LocaleUtil.getDefault());
		}

	}

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject
	private Portal _portal;

	private ServiceRegistration<Application> _serviceRegistration;

}
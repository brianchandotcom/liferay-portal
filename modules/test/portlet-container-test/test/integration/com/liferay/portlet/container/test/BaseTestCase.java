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

package com.liferay.portlet.container.test;

import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.test.LayoutTestUtil;

import java.io.IOException;
import java.io.InputStream;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Raymond Augé
 */
public class BaseTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		layout = LayoutTestUtil.addLayout(group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	@After
	public void tearDown() throws Exception {
		PermissionThreadLocal.setPermissionChecker(null);

		if (serviceTracker != null) {
			serviceTracker.close();
		}

		for (ServiceRegistration<?> serviceRegistration :
				serviceRegistrations) {

			serviceRegistration.unregister();
		}

		serviceRegistrations.clear();
	}

	protected String drain(InputStream stream) throws IOException {
		if (stream == null) {
			return "";
		}

		byte[] bytes = new byte[100];
		StringBuffer buffer = new StringBuffer(500);
		int length = 0;

		while ((length = stream.read(bytes)) != -1) {
			String chunk = new String(bytes, 0, length);
			buffer.append(chunk);
		}

		return buffer.toString();
	}

	protected BundleContext getBundleContext() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		return bundle.getBundleContext();
	}

	protected MockHttpServletRequest getRequest() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		themeDisplay.setCompany(company);
		themeDisplay.setLayout(layout);
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setPortalURL(TestPropsValues.PORTAL_URL);
		themeDisplay.setRequest(request);
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		request.setAttribute(WebKeys.LAYOUT, layout);
		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return request;
	}

	protected Map<String, List<String>> request(String urlSpec)
		throws IOException {

		return request(urlSpec, null);
	}

	protected Map<String, List<String>> request(
			String urlSpec, Map<String, List<String>> headers)
		throws IOException {

		URL url = new URL(urlSpec);

		HttpURLConnection connection = (HttpURLConnection)url.openConnection();

		connection.setInstanceFollowRedirects(true);
		connection.setConnectTimeout(1500 * 1000);
		connection.setReadTimeout(1500 * 1000);

		if (headers != null) {
			for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
				String key = entry.getKey();

				for (String entryValue : entry.getValue()) {
					if (key.equals("Cookie")) {
						connection.addRequestProperty(
							key, entryValue.split(";", 2)[0]);
					}
					else {
						connection.setRequestProperty(key, entryValue);
					}
				}
			}
		}

		InputStream stream = null;

		try {
			stream = connection.getInputStream();
		}
		catch (IOException ioe) {
			stream = connection.getErrorStream();
		}

		try {
			Map<String, List<String>> map = new HashMap<>(
				connection.getHeaderFields());

			String responseCode = String.valueOf(connection.getResponseCode());

			map.put("responseCode", Arrays.asList(responseCode));
			map.put("responseBody", Arrays.asList(drain(stream)));

			return map;
		}
		finally {
			if (stream != null) {
				stream.close();
			}
		}
	}

	protected void setUpPortlet(
			javax.portlet.Portlet portlet,
			Dictionary<String, Object> properties, String portletName)
		throws Exception {

		setUpPortlet(portlet, properties, portletName, true);
	}

	protected void setUpPortlet(
			javax.portlet.Portlet portlet,
			Dictionary<String, Object> properties, String portletName,
			boolean addToPage)
		throws Exception {

		Assert.assertNotNull(properties);

		BundleContext bundleContext = getBundleContext();

		properties.put("javax.portlet.name", portletName);

		serviceRegistration = bundleContext.registerService(
			new String[] {
				Object.class.getName(), javax.portlet.Portlet.class.getName()
			},
			portlet, properties);

		serviceRegistrations.add(serviceRegistration);

		if (addToPage) {
			LayoutTestUtil.addPortletToLayout(
				TestPropsValues.getUserId(), layout, portletName, "column-1",
				new HashMap<String, String[]>());
		}
	}

	@DeleteAfterTestRun
	protected Group group;

	protected Layout layout;
	protected ServiceRegistration<?> serviceRegistration;
	protected List<ServiceRegistration<?>> serviceRegistrations =
		new CopyOnWriteArrayList<>();
	protected ServiceTracker<Portlet, Portlet> serviceTracker;

}
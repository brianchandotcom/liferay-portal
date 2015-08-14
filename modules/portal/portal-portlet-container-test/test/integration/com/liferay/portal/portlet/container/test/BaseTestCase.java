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

package com.liferay.portal.portlet.container.test;

import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.util.test.LayoutTestUtil;
import javax.portlet.Portlet;
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

import javax.servlet.http.HttpServletRequest;

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

	protected String read(InputStream inputStream) throws IOException {
		if (inputStream == null) {
			return "";
		}

		return StringUtil.read(inputStream);
	}

	protected BundleContext getBundleContext() {
		Bundle bundle = FrameworkUtil.getBundle(getClass());

		return bundle.getBundleContext();
	}

	protected HttpServletRequest getHttpServletRequest() throws Exception {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLayout(layout);
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setPortalURL(TestPropsValues.PORTAL_URL);
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		return httpServletRequest;
	}

	protected Map<String, List<String>> request(String url) throws IOException {
		return request(url, null);
	}

	protected Map<String, List<String>> request(
			String url, Map<String, List<String>> headers)
		throws IOException {

		URL urlObject = new URL(url);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)urlObject.openConnection();

		httpURLConnection.setInstanceFollowRedirects(true);
		httpURLConnection.setConnectTimeout(1500 * 1000);
		httpURLConnection.setReadTimeout(1500 * 1000);

		if (headers != null) {
			for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
				String key = entry.getKey();

				for (String value : entry.getValue()) {
					if (key.equals("Cookie")) {
						httpURLConnection.addRequestProperty(
							key, value.split(";", 2)[0]);
					}
					else {
						httpURLConnection.setRequestProperty(key, value);
					}
				}
			}
		}

		InputStream inputStream = null;

		try {
			inputStream = httpURLConnection.getInputStream();
		}
		catch (IOException ioe) {
			inputStream = httpURLConnection.getErrorStream();
		}

		try {
			Map<String, List<String>> map = new HashMap<>(
				httpURLConnection.getHeaderFields());

			String responseCode =
				String.valueOf(httpURLConnection.getResponseCode());

			map.put("responseCode", Arrays.asList(responseCode));
			map.put("responseBody", Arrays.asList(read(inputStream)));

			return map;
		}
		finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	protected void setUpPortlet(
			Portlet portlet,
			Dictionary<String, Object> properties, String portletName)
		throws Exception {

		setUpPortlet(portlet, properties, portletName, true);
	}

	protected void setUpPortlet(
			Portlet portlet,
			Dictionary<String, Object> properties, String portletName,
			boolean addToLayout)
		throws Exception {

		Assert.assertNotNull(properties);

		BundleContext bundleContext = getBundleContext();

		properties.put("javax.portlet.name", portletName);

		serviceRegistration = bundleContext.registerService(
			new String[] {
				Object.class.getName(), Portlet.class.getName()
			},
			portlet, properties);

		serviceRegistrations.add(serviceRegistration);

		if (addToLayout) {
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
	protected ServiceTracker
		<com.liferay.portal.model.Portlet, com.liferay.portal.model.Portlet>
			serviceTracker;

}
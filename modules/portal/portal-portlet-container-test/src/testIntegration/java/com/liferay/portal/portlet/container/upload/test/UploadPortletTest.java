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

package com.liferay.portal.portlet.container.upload.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.portlet.container.test.BasePortletContainerTestCase;
import com.liferay.portal.service.CompanyLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.theme.ThemeDisplayFactory;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.util.test.PortletContainerTestUtil;
import com.liferay.portal.util.test.PortletContainerTestUtil.Response;
import com.liferay.portlet.PortletURLFactoryUtil;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.Portlet;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockMultipartHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class UploadPortletTest extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_testUploadPortlet = new TestUploadPortlet() {

			@Override
			public void serveResource(
					ResourceRequest resourceRequest,
					ResourceResponse resourceResponse)
				throws IOException {

				PrintWriter printWriter = resourceResponse.getWriter();

				PortletURL portletURL = resourceResponse.createActionURL();

				String queryString = HttpUtil.getQueryString(
					portletURL.toString());

				Map<String, String[]> parameterMap = HttpUtil.getParameterMap(
					queryString);

				String portalAuthenticationToken = MapUtil.getString(
					parameterMap, "p_auth");

				printWriter.write(portalAuthenticationToken);
			}

		};

		registerMVCActionCommand(
			new TestUploadMVCActionCommand(_testUploadPortlet));
		registerMVCPortlet(_testUploadPortlet);
	}

	@Test
	public void testUploadFile() throws Exception {
		String content = "Enterprise. Open Source. For Life.";

		Response response = testUpload(content.getBytes());

		Assert.assertEquals(200, response.getCode());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			response.getBody());

		Assert.assertTrue(jsonObject.getBoolean("success"));

		String key =
			group.getGroupId() + "_0_" + TestUploadPortlet.PARAMETER_NAME;

		TestFileEntry actualTestFileEntry = _testUploadPortlet.get(key);

		Assert.assertNotNull(actualTestFileEntry);
		Assert.assertEquals(
			content, StringUtil.read(actualTestFileEntry.getInputStream()));
	}

	@Test
	public void testUploadZeroBytesFile() throws Exception {
		Response response = testUpload(new byte[0]);

		Assert.assertEquals(200, response.getCode());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			response.getBody());

		Assert.assertTrue(jsonObject.getBoolean("success"));

		String key =
			group.getGroupId() + "_0_" + TestUploadPortlet.PARAMETER_NAME;

		TestFileEntry actualTestFileEntry = _testUploadPortlet.get(key);

		Assert.assertNull(actualTestFileEntry.getInputStream());
	}

	protected void registerMVCActionCommand(MVCActionCommand mvcActionCommand)
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("javax.portlet.name", TestUploadPortlet.PORTLET_NAME);
		properties.put("mvc.command.name", TestUploadPortlet.MVC_COMMAND_NAME);

		ServiceRegistration<MVCActionCommand> serviceRegistration =
			bundleContext.registerService(
				MVCActionCommand.class, mvcActionCommand, properties);

		serviceRegistrations.add(serviceRegistration);
	}

	protected void registerMVCPortlet(Portlet portlet) throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put(
			"com.liferay.portlet.private-request-attributes",
			Boolean.FALSE.toString());
		properties.put(
			"com.liferay.portlet.private-session-attributes",
			Boolean.FALSE.toString());
		properties.put(
			"com.liferay.portlet.scopeable", Boolean.TRUE.toString());
		properties.put(
			"com.liferay.portlet.struts-path", TestUploadPortlet.MVC_PATH);
		properties.put(
			"com.liferay.portlet.use-default-template",
			Boolean.TRUE.toString());
		properties.put(
			"com.liferay.portlet.webdav-storage-token",
			TestUploadPortlet.MVC_PATH);
		properties.put("javax.portlet.display-name", "Test Upload Portlet");
		properties.put("javax.portlet.expiration-cache", "0");
		properties.put(
			"javax.portlet.init-param.single-page-application-cacheable",
			Boolean.FALSE.toString());
		properties.put("javax.portlet.init-param.template-path", "/");
		properties.put(
			"javax.portlet.init-param.view-template",
			"/" + TestUploadPortlet.PORTLET_NAME + "/view.jsp");
		properties.put("javax.portlet.name", TestUploadPortlet.PORTLET_NAME);
		properties.put("javax.portlet.resource-bundle", "content.Language");
		properties.put(
			"javax.portlet.security-role-ref", "guest,power-user,user");
		properties.put("javax.portlet.supports.mime-type", "text/html");

		setUpPortlet(portlet, properties, TestUploadPortlet.PORTLET_NAME);
	}

	protected void setUp(
			LiferayServletRequest liferayServletRequest, Layout layout)
		throws Exception {

		if (liferayServletRequest == null) {
			throw new IllegalArgumentException(
				"Liferay servlet request is null");
		}

		if (layout == null) {
			throw new IllegalArgumentException("Layout is null");
		}

		HttpServletRequest httpServletRequest =
			(HttpServletRequest)liferayServletRequest.getRequest();

		httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = ThemeDisplayFactory.create();

		Company company = CompanyLocalServiceUtil.getCompany(
			layout.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLayout(layout);
		themeDisplay.setPlid(layout.getPlid());

		themeDisplay.setPortalURL(TestPropsValues.PORTAL_URL);
		themeDisplay.setRequest(httpServletRequest);

		Group group = layout.getGroup();

		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setSiteGroupId(group.getGroupId());

		themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);
	}

	protected Response testUpload(byte[] bytes) throws Exception {
		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				TestUploadPortlet.PARAMETER_NAME, bytes);

		setUp(liferayServletRequest, layout);

		ServletRequest servletRequest = liferayServletRequest.getRequest();

		MockMultipartHttpServletRequest mockServletRequest =
			(MockMultipartHttpServletRequest)servletRequest;

		Response response = PortletContainerTestUtil.getPortalAuthentication(
			mockServletRequest, layout, TestUploadPortlet.PORTLET_NAME);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			mockServletRequest, TestUploadPortlet.PORTLET_NAME,
			layout.getPlid(), PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, TestUploadPortlet.MVC_COMMAND_NAME);
		portletURL.setParameter("randomId", RandomTestUtil.randomString());

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_auth", response.getBody());

		List<String> cookies = response.getCookies();

		mockServletRequest.addParameter(
			"Cookie", cookies.toArray(new String[cookies.size()]));

		return PortletContainerTestUtil.postMultipart(
			url, mockServletRequest, TestUploadPortlet.PARAMETER_NAME);
	}

	private TestUploadPortlet _testUploadPortlet;

}
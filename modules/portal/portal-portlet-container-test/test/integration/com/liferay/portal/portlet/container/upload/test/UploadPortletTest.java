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

		registerMVCPortlet(_testUploadPortlet);
	}

	@Test
	public void testUploadFile() throws Exception {
		registerMVCActionCommand(new TestUploadMVCActionCommand());

		String content = "Enterprise. Open Source. For Life.";

		Response response = testUpload(content.getBytes());

		Assert.assertEquals(200, response.getCode());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			response.getBody());

		Assert.assertTrue(jsonObject.getBoolean("success"));

		String key =
			group.getGroupId() + "_0_" +
				TestUploadPortlet.TEST_UPLOAD_FILE_NAME_PARAMETER;

		// verify the file was upload to the TestUploadPortlet store

		TestFileEntry actualTestFileEntry = TestUploadPortlet.get(key);

		Assert.assertNotNull(actualTestFileEntry);
		Assert.assertEquals(
			content, StringUtil.read(actualTestFileEntry.getInputStream()));
	}

	@Test
	public void testUploadZeroBitsFile() throws Exception {
		registerMVCActionCommand(new TestUploadMVCActionCommand());

		Response response = testUpload(new byte[0]);

		Assert.assertEquals(200, response.getCode());

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			response.getBody());

		Assert.assertTrue(jsonObject.getBoolean("success"));

		// verify the file was upload to the TestUploadPortlet store, but empty

		String key =
			group.getGroupId() + "_0_" +
				TestUploadPortlet.TEST_UPLOAD_FILE_NAME_PARAMETER;

		TestFileEntry actualTestFileEntry = TestUploadPortlet.get(key);

		Assert.assertNull(actualTestFileEntry.getInputStream());
	}

	protected void registerMVCActionCommand(MVCActionCommand mvcActionCommand)
		throws Exception {

		Dictionary<String, Object> mvcActionCommandProperties =
			new HashMapDictionary<>();

		mvcActionCommandProperties.put(
			"javax.portlet.name", TestUploadPortlet.TEST_UPLOAD_PORTLET);
		mvcActionCommandProperties.put(
			"mvc.command.name", TestUploadPortlet.TEST_MVC_COMMAND_NAME);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<MVCActionCommand> serviceRegistration =
			bundleContext.registerService(
				MVCActionCommand.class, mvcActionCommand,
				mvcActionCommandProperties);

		serviceRegistrations.add(serviceRegistration);
	}

	protected void registerMVCPortlet(Portlet portlet) throws Exception {
		Dictionary<String, Object> portletProperties =
			new HashMapDictionary<>();

		portletProperties.put(
			"com.liferay.portlet.private-request-attributes",
			Boolean.FALSE.toString());
		portletProperties.put(
			"com.liferay.portlet.private-session-attributes",
			Boolean.FALSE.toString());
		portletProperties.put(
			"com.liferay.portlet.scopeable", Boolean.TRUE.toString());
		portletProperties.put(
			"com.liferay.portlet.struts-path",
			TestUploadPortlet.TEST_UPLOAD_STRUTS_PATH);
		portletProperties.put(
			"com.liferay.portlet.use-default-template",
			Boolean.TRUE.toString());
		portletProperties.put(
			"com.liferay.portlet.webdav-storage-token",
			TestUploadPortlet.TEST_UPLOAD_STRUTS_PATH);
		portletProperties.put(
			"javax.portlet.display-name", "Test Upload Portlet");
		portletProperties.put("javax.portlet.expiration-cache", "0");
		portletProperties.put(
			"javax.portlet.init-param.single-page-application-cacheable",
			Boolean.FALSE.toString());
		portletProperties.put("javax.portlet.init-param.template-path", "/");
		portletProperties.put(
			"javax.portlet.init-param.view-template",
			"/" + TestUploadPortlet.TEST_UPLOAD_PORTLET + "/view.jsp");
		portletProperties.put(
			"javax.portlet.name", TestUploadPortlet.TEST_UPLOAD_PORTLET);
		portletProperties.put(
			"javax.portlet.resource-bundle", "content.Language");
		portletProperties.put(
			"javax.portlet.security-role-ref", "guest,power-user,user");
		portletProperties.put("javax.portlet.supports.mime-type", "text/html");

		setUpPortlet(
			portlet, portletProperties, TestUploadPortlet.TEST_UPLOAD_PORTLET);
	}

	protected Response testUpload(byte[] bytes) throws Exception {
		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				TestUploadPortlet.TEST_UPLOAD_FILE_NAME_PARAMETER, bytes);

		_addGroupAndLayoutToServletRequest(liferayServletRequest, layout);

		ServletRequest servletRequest = liferayServletRequest.getRequest();

		MockMultipartHttpServletRequest mockServletRequest =
			(MockMultipartHttpServletRequest)servletRequest;

		Response response = PortletContainerTestUtil.getPortalAuthentication(
			mockServletRequest, layout, TestUploadPortlet.TEST_UPLOAD_PORTLET);

		PortletURL portletURL = PortletURLFactoryUtil.create(
			mockServletRequest, TestUploadPortlet.TEST_UPLOAD_PORTLET,
			layout.getPlid(), PortletRequest.ACTION_PHASE);

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, TestUploadPortlet.TEST_MVC_COMMAND_NAME);
		portletURL.setParameter("randomId", RandomTestUtil.randomString());

		String url = portletURL.toString();

		url = HttpUtil.setParameter(url, "p_auth", response.getBody());

		List<String> cookies = response.getCookies();

		mockServletRequest.addParameter(
			"Cookie", cookies.toArray(new String[cookies.size()]));

		return PortletContainerTestUtil.postMultipart(
			url, mockServletRequest,
			TestUploadPortlet.TEST_UPLOAD_FILE_NAME_PARAMETER);
	}

	private void _addGroupAndLayoutToServletRequest(
			LiferayServletRequest liferayServletRequest, Layout layout)
		throws Exception {

		if ((liferayServletRequest == null) || (layout == null)) {
			throw new IllegalArgumentException("Arguments cannot be null.");
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

	private TestUploadPortlet _testUploadPortlet;

}
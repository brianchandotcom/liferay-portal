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

package com.liferay.layout.reports.web.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletURL;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.test.MockLiferayPortletContext;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.portlet.Portlet;

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
public class LayoutReportsDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup(
			TestPropsValues.getCompanyId(), TestPropsValues.getUserId(), 0);
	}

	@Test
	public void testGetData() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsPageSpeedConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put("apiKey", RandomTestUtil.randomString());
							put("enabled", true);
						}
					})) {

			Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

			GroupTestUtil.updateDisplaySettings(
				_group.getGroupId(),
				Arrays.asList(LocaleUtil.BRAZIL, LocaleUtil.SPAIN),
				LocaleUtil.SPAIN);

			Map<String, Object> data = ReflectionTestUtil.invoke(
				_getLayoutReportsDisplayContext(layout), "getData",
				new Class<?>[0]);

			Map<String, Object> context = (Map<String, Object>)data.get(
				"context");

			String assetsPath = (String)context.get("assetsPath");

			Assert.assertTrue(assetsPath.contains("assets"));

			List<Map<String, Object>> canonicalURLs =
				(List<Map<String, Object>>)context.get("canonicalURLs");

			Assert.assertEquals(
				String.valueOf(canonicalURLs), 2, canonicalURLs.size());

			Map<String, Object> canonicalURL1 = canonicalURLs.get(0);

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.SPAIN),
				canonicalURL1.get("languageId"));

			Map<String, Object> canonicalURL2 = canonicalURLs.get(1);

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.BRAZIL),
				canonicalURL2.get("languageId"));

			String configurePageSpeedURL = (String)context.get(
				"configurePageSpeedURL");

			Assert.assertTrue(
				configurePageSpeedURL.contains("configuration_admin"));

			Assert.assertEquals(
				LocaleUtil.toW3cLanguageId(LocaleUtil.SPAIN),
				context.get("defaultLanguageId"));

			Assert.assertTrue((Boolean)context.get("validConnection"));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
	}

	@Test
	public void testGetDataWithApiKeyInSiteConfiguration() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsPageSpeedConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put("apiKey", StringPool.BLANK);
							put("enabled", true);
						}
					})) {

			UnicodeProperties unicodeProperties =
				_group.getTypeSettingsProperties();

			unicodeProperties.setProperty(
				"pageSpeedApiKey", RandomTestUtil.randomString());
			unicodeProperties.setProperty(
				"pageSpeedEnabled", Boolean.TRUE.toString());

			_groupLocalService.updateGroup(_group);

			Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

			Map<String, Object> data = ReflectionTestUtil.invoke(
				_getLayoutReportsDisplayContext(layout), "getData",
				new Class<?>[0]);

			Map<String, Object> context = (Map<String, Object>)data.get(
				"context");

			Assert.assertTrue((Boolean)context.get("validConnection"));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
	}

	@Test
	public void testGetDataWithoutApiKey() throws Exception {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.layout.reports.web.internal.configuration." +
						"LayoutReportsPageSpeedConfiguration",
					new HashMapDictionary<String, Object>() {
						{
							put("apiKey", StringPool.BLANK);
							put("enabled", true);
						}
					})) {

			Layout layout = LayoutTestUtil.addLayout(_group.getGroupId());

			Map<String, Object> data = ReflectionTestUtil.invoke(
				_getLayoutReportsDisplayContext(layout), "getData",
				new Class<?>[0]);

			Map<String, Object> context = (Map<String, Object>)data.get(
				"context");

			Assert.assertFalse((Boolean)context.get("validConnection"));
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();

			ServiceContextThreadLocal.pushServiceContext(serviceContext);
		}
	}

	private Object _getLayoutReportsDisplayContext(Layout layout)
		throws Exception {

		MVCPortlet mvcPortlet = (MVCPortlet)_portlet;

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			_getMockLiferayPortletRenderRequest(layout);

		mvcPortlet.render(
			mockLiferayPortletRenderRequest,
			new MockLiferayPortletRenderResponse());

		return mockLiferayPortletRenderRequest.getAttribute(
			"LAYOUT_REPORTS_DISPLAY_CONTEXT");
	}

	private MockLiferayPortletRenderRequest _getMockLiferayPortletRenderRequest(
			Layout layout)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		serviceContext.setRequest(
			mockLiferayPortletRenderRequest.getHttpServletRequest());

		mockLiferayPortletRenderRequest.setAttribute(
			StringBundler.concat(
				mockLiferayPortletRenderRequest.getPortletName(), "-",
				WebKeys.CURRENT_PORTLET_URL),
			new MockLiferayPortletURL());

		String path = "/view.jsp";

		mockLiferayPortletRenderRequest.setParameter("mvcPath", path);

		mockLiferayPortletRenderRequest.setAttribute(
			MVCRenderConstants.
				PORTLET_CONTEXT_OVERRIDE_REQUEST_ATTIBUTE_NAME_PREFIX + path,
			new MockLiferayPortletContext(path));

		mockLiferayPortletRenderRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.fetchCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setPlid(layout.getPlid());
		themeDisplay.setSiteGroupId(layout.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockLiferayPortletRenderRequest;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private GroupLocalService _groupLocalService;

	@Inject(
		filter = "component.name=com.liferay.layout.reports.web.internal.portlet.LayoutReportsPortlet"
	)
	private Portlet _portlet;

}
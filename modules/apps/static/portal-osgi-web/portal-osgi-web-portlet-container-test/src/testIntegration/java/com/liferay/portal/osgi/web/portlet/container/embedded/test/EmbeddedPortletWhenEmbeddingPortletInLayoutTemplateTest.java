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

package com.liferay.portal.osgi.web.portlet.container.embedded.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.layoutconfiguration.util.RuntimePageUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTemplateConstants;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.osgi.web.portlet.container.test.BasePortletContainerTestCase;
import com.liferay.portal.osgi.web.portlet.container.test.TestPortlet;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Jorge Díaz
 */
@RunWith(Arquillian.class)
public class EmbeddedPortletWhenEmbeddingPortletInLayoutTemplateTest
	extends BasePortletContainerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testRenderEmbeddedPortlet() throws Exception {
		Dictionary<String, Object> properties = new HashMapDictionary<>();

		setUpPortlet(testPortlet, properties, TEST_PORTLET_ID, false);

		String templateId =
			"themeId" + LayoutTemplateConstants.CUSTOM_SEPARATOR + "testId";
		String templateContent =
			"${processor.processPortlet(\"" + TEST_PORTLET_ID + "\")}";

		_processLayoutTemplate(templateId, templateContent);

		Assert.assertTrue(testPortlet.isCalledRender());
	}

	@Test
	public void testRenderEmbeddedPortletFollowsRenderWeights()
		throws Exception {

		List<String> renderedPortlets = new ArrayList<>();

		_setUpPortletWithRenderWeight(
			renderedPortlets, "TEST_EMBEDDED_PORTLET_1", 1, false);
		_setUpPortletWithRenderWeight(
			renderedPortlets, "TEST_EMBEDDED_PORTLET_2", 2, false);
		_setUpPortletWithRenderWeight(
			renderedPortlets, "TEST_EMBEDDED_PORTLET_3", 3, false);
		_setUpPortletWithRenderWeight(
			renderedPortlets, "TEST_COLUMN-1_PORTLET_1", 4, true);
		_setUpPortletWithRenderWeight(
			renderedPortlets, "TEST_COLUMN-1_PORTLET_2", 5, true);

		String templateId =
			"themeId" + LayoutTemplateConstants.CUSTOM_SEPARATOR + "testId";
		String templateContent = StringBundler.concat(
			"${processor.processPortlet(\"TEST_EMBEDDED_PORTLET_3\")}",
			"${processor.processPortlet(\"TEST_EMBEDDED_PORTLET_1\")}",
			"${processor.processPortlet(\"TEST_EMBEDDED_PORTLET_2\")}",
			"${processor.processColumn(\"column-1\"",
			"\"portlet-column-content portlet-column-content-first\")}");

		_processLayoutTemplate(templateId, templateContent);

		Assert.assertEquals(
			Arrays.asList(
				"TEST_COLUMN-1_PORTLET_2", "TEST_COLUMN-1_PORTLET_1",
				"TEST_EMBEDDED_PORTLET_3", "TEST_EMBEDDED_PORTLET_2",
				"TEST_EMBEDDED_PORTLET_1"),
			renderedPortlets);
	}

	private HttpServletRequest _getHttpServletRequest(
			HttpServletResponse httpServletResponse)
		throws Exception {

		MockHttpServletRequest httpServletRequest =
			new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletRenderResponse());
		httpServletRequest.setAttribute(
			WebKeys.CTX, httpServletRequest.getServletContext());
		httpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, RandomTestUtil.randomString());
		httpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		ThemeDisplay themeDisplay = new ThemeDisplay();

		Company company = _companyLocalService.getCompany(
			layout.getCompanyId());

		themeDisplay.setCompany(company);

		themeDisplay.setLanguageId(group.getDefaultLanguageId());

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());

		LayoutSet layoutSet = group.getPublicLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLocale(
			LocaleUtil.fromLanguageId(group.getDefaultLanguageId()));
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());
		themeDisplay.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));
		themeDisplay.setPortalDomain(company.getVirtualHostname());
		themeDisplay.setPortalURL(company.getPortalURL(group.getGroupId()));
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setResponse(httpServletResponse);
		themeDisplay.setScopeGroupId(group.getGroupId());
		themeDisplay.setServerPort(8080);
		themeDisplay.setSignedIn(true);
		themeDisplay.setSiteGroupId(group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		httpServletRequest.setMethod(HttpMethods.GET);

		return httpServletRequest;
	}

	private void _processLayoutTemplate(
			String templateId, String templateContent)
		throws Exception {

		HttpServletResponse httpServletResponse = new MockHttpServletResponse();

		HttpServletRequest httpServletRequest = _getHttpServletRequest(
			httpServletResponse);

		RuntimePageUtil.processTemplate(
			httpServletRequest, httpServletResponse, null,
			new StringTemplateResource(templateId, templateContent),
			TemplateConstants.LANG_TYPE_FTL);
	}

	private void _setUpPortletWithRenderWeight(
			List<String> renderedPortlets, String portletName, int renderWeight,
			boolean addToLayout)
		throws Exception {

		TestPortlet testPortlet = new TestPortlet() {

			@Override
			public void render(
					RenderRequest renderRequest, RenderResponse renderResponse)
				throws IOException, PortletException {

				super.render(renderRequest, renderResponse);

				renderedPortlets.add(portletName);
			}

		};

		setUpPortlet(
			testPortlet,
			HashMapDictionaryBuilder.<String, Object>put(
				"com.liferay.portlet.render-weight", renderWeight
			).build(),
			portletName, addToLayout);
	}

	@Inject
	private CompanyLocalService _companyLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.action.RenderPortletAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import jakarta.portlet.HeaderRequest;
import jakarta.portlet.HeaderResponse;
import jakarta.portlet.Portlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.PrintWriter;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Eric Yan
 */
@RunWith(Arquillian.class)
public class RenderPortletActionTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testRenderHeaders() throws Exception {
		Bundle bundle = FrameworkUtil.getBundle(RenderPortletActionTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String header =
			"<link rel=\"stylesheet\" href=\"" + RandomTestUtil.randomString() +
				".css\">";
		String portletId = RandomTestUtil.randomString();

		ServiceRegistration<Portlet> serviceRegistration =
			bundleContext.registerService(
				Portlet.class,
				new MVCPortlet() {

					@Override
					public void renderHeaders(
							HeaderRequest headerRequest,
							HeaderResponse headerResponse)
						throws IOException {

						PrintWriter printWriter = headerResponse.getWriter();

						printWriter.write(header);
					}

				},
				HashMapDictionaryBuilder.put(
					"com.liferay.portlet.deploy.parallel", "false"
				).put(
					"com.liferay.portlet.use-default-template", "false"
				).put(
					"jakarta.portlet.init-param.valid-paths", "/"
				).put(
					"jakarta.portlet.init-param.view-template", "/"
				).put(
					"jakarta.portlet.name", portletId
				).put(
					"jakarta.portlet.version", "3.0"
				).build());

		RenderPortletAction renderPortletAction = new RenderPortletAction();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest(HttpMethods.GET, StringPool.BLANK) {

				@Override
				public RequestDispatcher getRequestDispatcher(String path) {
					ServletContext servletContext = ServletContextPool.get(
						StringPool.BLANK);

					return servletContext.getRequestDispatcher(path);
				}

			};

		mockHttpServletRequest.setAttribute(
			WebKeys.CURRENT_URL, "http://localhost:8080");

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypePortletLayout(group.getGroupId());

		PermissionThreadLocal.setPermissionChecker(
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()));

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);
		mockHttpServletRequest.setAttribute(
			WebKeys.RENDER_PORTLET,
			_portletLocalService.getPortletById(
				TestPropsValues.getCompanyId(), portletId));

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLayout(layout);

		LayoutSet layoutSet = group.getPublicLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);

		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setParameter(
			"p_p_id",
			LayoutTestUtil.addPortletToLayout(layout, portletId));

		renderPortletAction.execute(
			null, mockHttpServletRequest, new MockHttpServletResponse());

		serviceRegistration.unregister();

		OutputData outputData = (OutputData)mockHttpServletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		Assert.assertEquals(
			StringPool.NEW_LINE + header,
			String.valueOf(outputData.getMergedDataSB(WebKeys.PAGE_TOP)));
	}

	@Inject
	private static PortletLocalService _portletLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private LayoutLocalService _layoutLocalService;

}
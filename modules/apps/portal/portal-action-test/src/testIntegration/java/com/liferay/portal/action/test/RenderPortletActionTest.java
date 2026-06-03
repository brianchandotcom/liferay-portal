/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.action.RenderPortletAction;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.PortletLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.taglib.util.OutputData;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.portlet.HeaderRequest;
import jakarta.portlet.HeaderResponse;
import jakarta.portlet.Portlet;
import jakarta.portlet.RenderRequest;
import jakarta.portlet.RenderResponse;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Arrays;

import org.junit.After;
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
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@After
	public void tearDown() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Test
	public void testRender() throws Exception {
		String portletId = _registerPortlet(
			new MVCPortlet() {

				@Override
				public void render(
						RenderRequest renderRequest,
						RenderResponse renderResponse)
					throws IOException {

					PrintWriter printWriter = renderResponse.getWriter();

					printWriter.write(
						String.valueOf(
							Arrays.asList(
								renderRequest.getAttribute(
									WebKeys.PORTLET_DECORATE),
								renderRequest.getAttribute(WebKeys.RENDER_PATH),
								renderRequest.getAttribute(
									WebKeys.RENDER_PORTLET_BOUNDARY),
								renderRequest.getAttribute(
									WebKeys.RENDER_PORTLET_COLUMN_COUNT),
								renderRequest.getAttribute(
									WebKeys.RENDER_PORTLET_COLUMN_ID),
								renderRequest.getAttribute(
									WebKeys.RENDER_PORTLET_COLUMN_POS))));
				}

				@Override
				public void renderHeaders(
						HeaderRequest headerRequest,
						HeaderResponse headerResponse)
					throws IOException {

					PrintWriter printWriter = headerResponse.getWriter();

					printWriter.write(
						StringBundler.concat(
							"<link rel=\"stylesheet\" href=\"",
							Arrays.asList(
								headerRequest.getAttribute(
									WebKeys.PORTLET_DECORATE),
								headerRequest.getAttribute(WebKeys.RENDER_PATH),
								headerRequest.getAttribute(
									WebKeys.RENDER_PORTLET_BOUNDARY),
								headerRequest.getAttribute(
									WebKeys.RENDER_PORTLET_COLUMN_COUNT),
								headerRequest.getAttribute(
									WebKeys.RENDER_PORTLET_COLUMN_ID),
								headerRequest.getAttribute(
									WebKeys.RENDER_PORTLET_COLUMN_POS)),
							".css\">"));
				}

			});

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
			WebKeys.CURRENT_URL,
			"http://localhost:" + PortalUtil.getPortalServerPort(false));

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypePortletLayout(group.getGroupId());

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		mockHttpServletRequest.setAttribute(
			WebKeys.RENDER_PORTLET,
			_portletLocalService.getPortletById(
				TestPropsValues.getCompanyId(), portletId));
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout, group));
		mockHttpServletRequest.setParameter(
			"p_p_id", LayoutTestUtil.addPortletToLayout(layout, portletId));

		String columnId = RandomTestUtil.randomString();

		mockHttpServletRequest.setParameter("p_p_col_id", columnId);

		String columnPos = String.valueOf(RandomTestUtil.randomInt());

		mockHttpServletRequest.setParameter("p_p_col_pos", columnPos);

		String columnCount = String.valueOf(RandomTestUtil.randomInt());

		mockHttpServletRequest.setParameter("p_p_col_count", columnCount);

		String renderPortletBoundary = String.valueOf(
			RandomTestUtil.randomBoolean());

		mockHttpServletRequest.setParameter(
			"p_p_boundary", renderPortletBoundary);

		String portletDecorate = String.valueOf(RandomTestUtil.randomBoolean());

		mockHttpServletRequest.setParameter("p_p_decorate", portletDecorate);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		renderPortletAction.execute(
			null, mockHttpServletRequest, mockHttpServletResponse);

		OutputData outputData = (OutputData)mockHttpServletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		Assert.assertEquals(
			StringBundler.concat(
				"\n<link rel=\"stylesheet\" href=\"",
				Arrays.asList(
					portletDecorate, null, renderPortletBoundary, columnCount,
					columnId, columnPos),
				".css\">"),
			String.valueOf(outputData.getMergedDataSB(WebKeys.PAGE_TOP)));

		String content = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(
			content,
			content.contains(
				String.valueOf(
					Arrays.asList(
						null, null, renderPortletBoundary, columnCount,
						columnId, columnPos))));
	}

	@Test
	public void testRenderHeaders() throws Exception {
		String html =
			"<link rel=\"stylesheet\" href=\"" + RandomTestUtil.randomString() +
				".css\">";

		String portletId = _registerPortlet(
			new MVCPortlet() {

				@Override
				public void renderHeaders(
						HeaderRequest headerRequest,
						HeaderResponse headerResponse)
					throws IOException {

					PrintWriter printWriter = headerResponse.getWriter();

					printWriter.write(html);
				}

			});

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
			WebKeys.CURRENT_URL,
			"http://localhost:" + PortalUtil.getPortalServerPort(false));

		Group group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addTypePortletLayout(group.getGroupId());

		mockHttpServletRequest.setAttribute(WebKeys.LAYOUT, layout);

		mockHttpServletRequest.setAttribute(
			WebKeys.RENDER_PORTLET,
			_portletLocalService.getPortletById(
				TestPropsValues.getCompanyId(), portletId));

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(layout, group));

		mockHttpServletRequest.setParameter(
			"p_p_id", LayoutTestUtil.addPortletToLayout(layout, portletId));

		renderPortletAction.execute(
			null, mockHttpServletRequest, new MockHttpServletResponse());

		OutputData outputData = (OutputData)mockHttpServletRequest.getAttribute(
			WebKeys.OUTPUT_DATA);

		Assert.assertEquals(
			StringPool.NEW_LINE + html,
			String.valueOf(outputData.getMergedDataSB(WebKeys.PAGE_TOP)));
	}

	private ThemeDisplay _getThemeDisplay(Layout layout, Group group)
		throws Exception {

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

		return themeDisplay;
	}

	private String _registerPortlet(Portlet portlet) {
		Bundle bundle = FrameworkUtil.getBundle(RenderPortletActionTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		String portletId = RandomTestUtil.randomString();

		_serviceRegistration = bundleContext.registerService(
			Portlet.class, portlet,
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

		return portletId;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private PortletLocalService _portletLocalService;

	private ServiceRegistration<?> _serviceRegistration;

}
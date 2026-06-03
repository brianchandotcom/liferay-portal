/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.login.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Debora Buriti
 */
@RunWith(Arquillian.class)
public class UpdateEmailAddressActionTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testRenderRefererURL() throws Exception {
		_testRenderRefererURL(
			"http://localhost:" + PortalUtil.getPortalServerPort(false),
			"http://localhost:" + PortalUtil.getPortalServerPort(false));
		_testRenderRefererURL(
			StringPool.BLANK,
			"http://" + RandomTestUtil.randomString() + ".com");
	}

	private void _testRenderRefererURL(
			String expectedRenderedRefererURL, String referURL)
		throws Exception {

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

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setLocale(LocaleUtil.getDefault());
		themeDisplay.setLookAndFeel(
			_themeLocalService.getTheme(
				TestPropsValues.getCompanyId(),
				PropsValues.DEFAULT_REGULAR_THEME_ID),
			null);
		themeDisplay.setPathMain("/c");
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(mockHttpServletRequest);
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockHttpServletRequest.setParameter(WebKeys.REFERER, referURL);

		RequestDispatcher requestDispatcher =
			mockHttpServletRequest.getRequestDispatcher(
				"/html/portal/update_email_address.jsp");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setPortalURL(
			"http://localhost:" + PortalUtil.getPortalServerPort(false));

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			requestDispatcher.include(
				mockHttpServletRequest, mockHttpServletResponse);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}

		String content = mockHttpServletResponse.getContentAsString();

		Matcher matcher = _pattern.matcher(content);

		Assert.assertTrue(content, matcher.find());

		Assert.assertEquals(expectedRenderedRefererURL, matcher.group(1));
	}

	private static final Pattern _pattern = Pattern.compile(
		"id=\"referer\"[^>]*value=\"([^\"]*)\"");

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private ThemeLocalService _themeLocalService;

}
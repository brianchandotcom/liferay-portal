/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.configuration.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class CaptchaConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testCompanyCaptchaConfiguration() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					CaptchaConfiguration.class.getName(),
					new HashMapDictionaryBuilder(
					).<String, Object>put(
						"createAccountCaptchaEnabled", false
					).build());
			CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CaptchaConfiguration.class.getName(),
						new HashMapDictionaryBuilder(
						).<String, Object>put(
							"createAccountCaptchaEnabled", true
						).build())) {

			String portletURL = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					_getMockHttpServletRequest(), PortletKeys.LOGIN,
					_layoutLocalService.fetchLayout(TestPropsValues.getPlid()),
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/login/create_account"
			).setParameter(
				"saveLastPath", false
			).setPortletMode(
				PortletMode.VIEW
			).setWindowState(
				WindowState.MAXIMIZED
			).buildString();

			HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(
				portletURL
			).openConnection();

			Assert.assertEquals(
				HttpURLConnection.HTTP_OK, httpURLConnection.getResponseCode());

			boolean captchaRendered = false;

			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(
						httpURLConnection.getInputStream()))) {

				String line;

				while ((line = reader.readLine()) != null) {
					if (line.contains("CAPTCHA")) {
						captchaRendered = true;

						break;
					}
				}
			}

			Assert.assertTrue(captchaRendered);
		}
	}

	@Test
	public void testSystemCaptchaConfiguration() throws Exception {
		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					CaptchaConfiguration.class.getName(),
					new HashMapDictionaryBuilder(
					).<String, Object>put(
						"createAccountCaptchaEnabled", false
					).build())) {

			String portletURL = PortletURLBuilder.create(
				PortletURLFactoryUtil.create(
					_getMockHttpServletRequest(), PortletKeys.LOGIN,
					_layoutLocalService.fetchLayout(TestPropsValues.getPlid()),
					PortletRequest.RENDER_PHASE)
			).setMVCRenderCommandName(
				"/login/create_account"
			).setParameter(
				"saveLastPath", false
			).setPortletMode(
				PortletMode.VIEW
			).setWindowState(
				WindowState.MAXIMIZED
			).buildString();

			HttpURLConnection httpURLConnection = (HttpURLConnection)new URL(
				portletURL
			).openConnection();

			Assert.assertEquals(
				HttpURLConnection.HTTP_OK, httpURLConnection.getResponseCode());

			boolean captchaRendered = false;

			try (BufferedReader reader = new BufferedReader(
					new InputStreamReader(
						httpURLConnection.getInputStream()))) {

				String line;

				while ((line = reader.readLine()) != null) {
					if (line.contains("CAPTCHA")) {
						captchaRendered = true;

						break;
					}
				}
			}

			Assert.assertFalse(captchaRendered);
		}
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setLayout(
			_layoutLocalService.fetchLayout(TestPropsValues.getPlid()));
		themeDisplay.setPlid(TestPropsValues.getPlid());
		themeDisplay.setPortalURL("http://localhost:8080");
		themeDisplay.setScopeGroupId(TestPropsValues.getGroupId());
		themeDisplay.setSiteGroupId(TestPropsValues.getGroupId());

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	@Inject
	private LayoutLocalService _layoutLocalService;

}
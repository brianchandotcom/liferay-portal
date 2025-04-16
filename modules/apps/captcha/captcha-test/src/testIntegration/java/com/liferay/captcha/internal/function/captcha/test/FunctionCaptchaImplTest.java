/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.captcha.internal.function.captcha.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.captcha.configuration.CaptchaConfiguration;
import com.liferay.client.extension.type.CustomElementCET;
import com.liferay.client.extension.type.configuration.CETConfiguration;
import com.liferay.client.extension.type.manager.CETManager;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.WindowState;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Pedro Victor Silvestre
 */
@RunWith(Arquillian.class)
public class FunctionCaptchaImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_properties = HashMapBuilder.<String, Object>put(
			"baseURL", "${portalURL}/o/test_" + _VIRTUAL_HOSTNAME
		).put(
			"buildTimestamp", System.currentTimeMillis()
		).put(
			"description", ""
		).put(
			"dxp.lxc.liferay.com.virtualInstanceId", _VIRTUAL_HOSTNAME
		).put(
			"name", "Test " + _VIRTUAL_HOSTNAME
		).put(
			"projectId", "test"
		).put(
			"projectName", "test"
		).put(
			"properties", new String[] {""}
		).put(
			"sourceCodeURL", ""
		).put(
			"type", "customElement"
		).put(
			"typeSettings",
			new String[] {
				"friendlyURLMapping=test", "htmlElementName=test",
				"instanceable=false",
				"portletCategoryName=category.client-extensions",
				"urls=index.js", "useESM=false"
			}
		).put(
			"webContextPath", "/test_" + _VIRTUAL_HOSTNAME
		).build();

		_cet = (CustomElementCET)_cetManager.addCET(
			ConfigurableUtil.createConfigurable(
				CETConfiguration.class, _properties),
			TestPropsValues.getCompanyId(), "LXC:test");

		_pid = ConfigurationTestUtil.createFactoryConfiguration(
			"com.liferay.captcha.internal.configuration." +
				"FunctionCaptchaImplConfiguration",
			HashMapDictionaryBuilder.<String, Object>put(
				"captchaName", "ClientExtensionCaptcha"
			).put(
				"customElementExternalReferenceCode", "LXC:test"
			).build());
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		_cetManager.deleteCET(_cet);

		ConfigurationTestUtil.deleteConfiguration(_pid);
	}

	@Test
	public void test() throws Exception {
		String servicePid = StringUtil.extractLast(_pid, StringPool.TILDE);

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						CaptchaConfiguration.class.getName(),
						new HashMapDictionaryBuilder(
						).<String, Object>put(
							"captchaEngine",
							"com.liferay.captcha.internal.function.captcha." +
								"FunctionCaptchaImpl#" + servicePid
						).build())) {

			Assert.assertTrue(_isCaptchaRendered());
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

	private boolean _isCaptchaRendered() throws Exception {
		URL url = new URL(
			PortletURLBuilder.create(
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
			).buildString());

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		Assert.assertEquals(
			HttpURLConnection.HTTP_OK, httpURLConnection.getResponseCode());

		try (BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(httpURLConnection.getInputStream()))) {

			String line;

			while ((line = bufferedReader.readLine()) != null) {
				if (line.contains(
						StringPool.LESS_THAN + _cet.getHTMLElementName())) {

					return true;
				}
			}
		}

		return false;
	}

	private static final String _VIRTUAL_HOSTNAME =
		RandomTestUtil.randomString() + ".localtest.me";

	private static volatile CustomElementCET _cet;

	@Inject
	private static CETManager _cetManager;

	private static String _pid;
	private static Map<String, Object> _properties;

	@Inject
	private LayoutLocalService _layoutLocalService;

}
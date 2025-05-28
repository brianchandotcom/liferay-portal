/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public class LanguageRequestHelperTest {

	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testConfigurationSendNoCache() throws Exception {

		// must-revalidate

		LanguageRequestHelper languageRequestHelper = new LanguageRequestHelper(
			_mockConfigurationProvider(
				_mockFrontendCachingConfiguration(1234L, false)),
			new JSONFactoryImpl(), _mockLanguage(), _mockPortal(),
			_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		languageRequestHelper.process(
			_mockHttpServletRequest(
				"/o/js/language/en_US/frontend-js-web/all.js"),
			mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("must-revalidate"));

		// no-cache

		languageRequestHelper = new LanguageRequestHelper(
			_mockConfigurationProvider(
				_mockFrontendCachingConfiguration(1234L, true)),
			new JSONFactoryImpl(), _mockLanguage(), _mockPortal(),
			_mockServiceTrackerMap(_mockServletContext()));

		mockHttpServletResponse = new MockHttpServletResponse();

		languageRequestHelper.process(
			_mockHttpServletRequest(
				"/o/js/language/en_US/frontend-js-web/all.js"),
			mockHttpServletResponse);

		cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("no-cache"));
	}

	@Test
	public void testInvalidRequest() throws Exception {
		LanguageRequestHelper languageRequestHelper = new LanguageRequestHelper(
			_mockConfigurationProvider(
				_mockFrontendCachingConfiguration(3600L, false)),
			new JSONFactoryImpl(), _mockLanguage(), _mockPortal(),
			_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		languageRequestHelper.process(
			_mockHttpServletRequest(
				"/o/js/language/en_US/___INVALID___/all.js"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			mockHttpServletResponse.getStatus());
	}

	@Test
	public void testIsAcceptableRequest() throws Exception {
		LanguageRequestHelper languageRequestHelper = new LanguageRequestHelper(
			_mockConfigurationProvider(
				_mockFrontendCachingConfiguration(3600L, false)),
			new JSONFactoryImpl(), _mockLanguage(), _mockPortal(),
			_mockServiceTrackerMap(_mockServletContext()));

		Assert.assertTrue(
			languageRequestHelper.isAcceptableRequest(
				_mockHttpServletRequest(
					"/o/js/language/en_US/frontend-js-web/all.js")));

		Assert.assertFalse(
			languageRequestHelper.isAcceptableRequest(
				_mockHttpServletRequest("/nonsense/request/all.js")));
	}

	@Test
	public void testProcess() throws Exception {
		LanguageRequestHelper languageRequestHelper = new LanguageRequestHelper(
			_mockConfigurationProvider(
				_mockFrontendCachingConfiguration(1234L, true)),
			new JSONFactoryImpl(), _mockLanguage(), _mockPortal(),
			_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		languageRequestHelper.process(
			_mockHttpServletRequest(
				"/o/js/language/en_US/frontend-js-web/all.js"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("no-cache"));

		Assert.assertTrue(cacheControl.contains("max-age=1234"));

		String contentType = mockHttpServletResponse.getHeader(
			HttpHeaders.CONTENT_TYPE);

		Assert.assertTrue(contentType.contains(ContentTypes.TEXT_JAVASCRIPT));

		Assert.assertTrue(contentType.contains("charset=UTF-8"));

		Assert.assertNull(mockHttpServletResponse.getHeader(HttpHeaders.ETAG));

		String contentAsString = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(contentAsString.contains("export default labels;"));
	}

	@Test
	public void testReasonableConfigurationDefaults() throws Exception {
		LanguageRequestHelper languageRequestHelper = new LanguageRequestHelper(
			_mockConfigurationProvider(null), new JSONFactoryImpl(),
			_mockLanguage(), _mockPortal(),
			_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		languageRequestHelper.process(
			_mockHttpServletRequest(
				"/o/js/language/en_US/frontend-js-web/all.js"),
			mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("must-revalidate"));
		Assert.assertTrue(cacheControl.contains("max-age=3600"));
	}

	private ConfigurationProvider _mockConfigurationProvider(
			FrontendCachingConfiguration frontendCachingConfiguration)
		throws Exception {

		ConfigurationProvider configurationProvider = Mockito.mock(
			ConfigurationProvider.class);

		if (frontendCachingConfiguration == null) {
			Mockito.when(
				configurationProvider.getCompanyConfiguration(
					FrontendCachingConfiguration.class, _COMPANY_ID)
			).thenThrow(
				new ConfigurationException()
			);
		}
		else {
			Mockito.when(
				configurationProvider.getCompanyConfiguration(
					FrontendCachingConfiguration.class, _COMPANY_ID)
			).thenReturn(
				frontendCachingConfiguration
			);
		}

		return configurationProvider;
	}

	private FrontendCachingConfiguration _mockFrontendCachingConfiguration(
		long labelsModulesMaxAge, boolean sendNoCacheForLabelsModules) {

		FrontendCachingConfiguration frontendCachingConfiguration =
			Mockito.mock(FrontendCachingConfiguration.class);

		Mockito.when(
			frontendCachingConfiguration.labelsModulesMaxAge()
		).thenReturn(
			labelsModulesMaxAge
		);

		Mockito.when(
			frontendCachingConfiguration.sendNoCacheForLabelsModules()
		).thenReturn(
			sendNoCacheForLabelsModules
		);

		return frontendCachingConfiguration;
	}

	private MockHttpServletRequest _mockHttpServletRequest(String requestURI) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setRequestURI(requestURI);

		return mockHttpServletRequest;
	}

	private Language _mockLanguage() {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.isAvailableLocale(Mockito.any(Locale.class))
		).thenReturn(
			true
		);

		new LanguageUtil(
		).setLanguage(
			language
		);

		return language;
	}

	private Portal _mockPortal() {
		Portal portal = Mockito.mock(Portal.class);

		Mockito.when(
			portal.getCompanyId(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			_COMPANY_ID
		);

		return portal;
	}

	private ServiceTrackerMap<String, ServletContext> _mockServiceTrackerMap(
		ServletContext servletContext) {

		ServiceTrackerMap<String, ServletContext> serviceTrackerMap =
			Mockito.mock(ServiceTrackerMap.class);

		Mockito.when(
			serviceTrackerMap.getService("/o/frontend-js-web")
		).thenReturn(
			servletContext
		);

		return serviceTrackerMap;
	}

	private ServletContext _mockServletContext() throws Exception {
		ServletContext servletContext = Mockito.mock(ServletContext.class);

		URL url = Mockito.mock(URL.class);

		Mockito.when(
			url.openStream()
		).thenReturn(
			new ByteArrayInputStream(
				"{keys:[]}".getBytes(StandardCharsets.UTF_8))
		);

		Mockito.when(
			servletContext.getResource("/language.json")
		).thenReturn(
			url
		);

		return servletContext;
	}

	private static final long _COMPANY_ID = 1L;

}
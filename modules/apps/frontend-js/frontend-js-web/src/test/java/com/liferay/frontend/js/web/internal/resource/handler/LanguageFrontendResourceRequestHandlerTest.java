/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Locale;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
public class LanguageFrontendResourceRequestHandlerTest {

	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCanHandleRequest() throws Exception {
		LanguageFrontendResourceRequestHandler
			languageFrontendResourceRequestHandler =
				new LanguageFrontendResourceRequestHandler(
					_mockConfigurationProvider(1234L, false),
					_mockHashedFilesRegistry(), new JSONFactoryImpl(),
					_mockLanguage(), _mockPortal());

		Assert.assertTrue(
			languageFrontendResourceRequestHandler.canHandleRequest(
				_mockHttpServletRequest(
					"/o/js/language/en_US/frontend-js-web/all.js")));

		Assert.assertFalse(
			languageFrontendResourceRequestHandler.canHandleRequest(
				_mockHttpServletRequest("/nonsense/request/index.js")));
	}

	@Test
	public void testConfiguration() throws Exception {
		LanguageFrontendResourceRequestHandler
			languageFrontendResourceRequestHandler =
				new LanguageFrontendResourceRequestHandler(
					_mockConfigurationProvider(4321L, true),
					_mockHashedFilesRegistry(), new JSONFactoryImpl(),
					_mockLanguage(), _mockPortal());

		FrontendResource frontendResource =
			languageFrontendResourceRequestHandler.handleRequest(
				_mockHttpServletRequest(
					"/o/js/language/en_US/frontend-js-web/all.js"));

		Assert.assertNotNull(frontendResource);

		Assert.assertEquals(4321L, frontendResource.getMaxAge());

		Assert.assertTrue(frontendResource.isSendNoCache());
	}

	@Test
	public void testHandleRequest() throws Exception {
		LanguageFrontendResourceRequestHandler
			languageFrontendResourceRequestHandler =
				new LanguageFrontendResourceRequestHandler(
					_mockConfigurationProvider(1234L, false),
					_mockHashedFilesRegistry(), new JSONFactoryImpl(),
					_mockLanguage(), _mockPortal());

		FrontendResource frontendResource =
			languageFrontendResourceRequestHandler.handleRequest(
				_mockHttpServletRequest(
					"/o/js/language/en_US/frontend-js-web/all.js"));

		Assert.assertNotNull(frontendResource);

		Assert.assertEquals(
			ContentTypes.TEXT_JAVASCRIPT, frontendResource.getContentType());

		Assert.assertNull(frontendResource.getETag());

		Assert.assertEquals(1234L, frontendResource.getMaxAge());

		Assert.assertFalse(frontendResource.isImmutable());

		Assert.assertFalse(frontendResource.isSendNoCache());

		String content = StreamUtil.toString(frontendResource.getInputStream());

		Assert.assertTrue(content.contains("'a-key':'a-key',"));
	}

	private ConfigurationProvider _mockConfigurationProvider(
			long maxAge, boolean sendNoCache)
		throws Exception {

		ConfigurationProvider configurationProvider = Mockito.mock(
			ConfigurationProvider.class);

		FrontendCachingConfiguration frontendCachingConfiguration =
			Mockito.mock(FrontendCachingConfiguration.class);

		Mockito.when(
			frontendCachingConfiguration.labelsModulesMaxAge()
		).thenReturn(
			maxAge
		);

		Mockito.when(
			frontendCachingConfiguration.sendNoCacheForLabelsModules()
		).thenReturn(
			sendNoCache
		);

		Mockito.when(
			configurationProvider.getCompanyConfiguration(
				FrontendCachingConfiguration.class, _COMPANY_ID)
		).thenReturn(
			frontendCachingConfiguration
		);

		return configurationProvider;
	}

	private HashedFilesRegistry _mockHashedFilesRegistry() throws Exception {
		HashedFilesRegistry hashedFilesRegistry = Mockito.mock(
			HashedFilesRegistry.class);

		URL url = Mockito.mock(URL.class);

		Mockito.when(
			url.openStream()
		).thenReturn(
			new ByteArrayInputStream(
				"{keys:['a-key']}".getBytes(StandardCharsets.UTF_8))
		);

		Mockito.when(
			hashedFilesRegistry.getResource(Mockito.anyString())
		).thenReturn(
			url
		);

		return hashedFilesRegistry;
	}

	private HttpServletRequest _mockHttpServletRequest(String requestURI) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setRequestURI(requestURI);

		return mockHttpServletRequest;
	}

	private Language _mockLanguage() {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.isAvailableLocale(LocaleUtil.ENGLISH)
		).thenReturn(
			true
		);

		Mockito.when(
			language.get(Mockito.any(Locale.class), Mockito.anyString())
		).thenAnswer(
			(Answer<String>)invocationOnMock -> invocationOnMock.getArgument(
				1, String.class)
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

	private static final long _COMPANY_ID = 1L;

}
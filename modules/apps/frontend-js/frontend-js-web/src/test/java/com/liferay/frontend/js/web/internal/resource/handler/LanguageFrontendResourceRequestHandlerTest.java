/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
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

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class LanguageFrontendResourceRequestHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "{0}: deployLanguageJSON={1}, requestURL={2}"
	)
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, false, _INVALID_URL}, {1, false, _VALID_URL},
				{2, true, _VALID_URL}
			});
	}

	@Test
	public void test() throws Exception {
		LanguageFrontendResourceRequestHandler
			languageFrontendResourceRequestHandler =
				new LanguageFrontendResourceRequestHandler(
					_mockConfigurationProvider(), _mockHashedFilesRegistry(),
					new JSONFactoryImpl(), _mockLanguage(), _mockPortal());

		HttpServletRequest httpServletRequest = _mockHttpServletRequest(
			requestURL);

		Assert.assertEquals(
			_EXPECTED_CAN_HANDLE_REQUEST[index],
			languageFrontendResourceRequestHandler.canHandleRequest(
				httpServletRequest));

		FrontendResource frontendResource =
			languageFrontendResourceRequestHandler.handleRequest(
				httpServletRequest);

		if (!_EXPECTED_FRONTEND_RESOURCE[index]) {
			Assert.assertNull(frontendResource);

			return;
		}

		Assert.assertNotNull(frontendResource);
		Assert.assertEquals(
			ContentTypes.APPLICATION_JAVASCRIPT,
			frontendResource.getContentType());
		Assert.assertNull(frontendResource.getETag());
		Assert.assertFalse(frontendResource.isImmutable());
		Assert.assertEquals(
			_LABELS_MODULES_MAX_AGE, frontendResource.getMaxAge());
		Assert.assertFalse(frontendResource.isPrivate());
		Assert.assertEquals(
			_SEND_NO_CACHE_FOR_LABELS_MODULES,
			frontendResource.isSendNoCache());

		String content = StringUtil.read(frontendResource.getInputStream());

		Assert.assertTrue(content.contains(_EXPECTED_CONTENT_SUBSTRING[index]));
	}

	@Parameterized.Parameter(1)
	public boolean deployLanguageJSON;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(2)
	public String requestURL;

	private ConfigurationProvider _mockConfigurationProvider()
		throws Exception {

		ConfigurationProvider configurationProvider = Mockito.mock(
			ConfigurationProvider.class);

		FrontendCachingConfiguration frontendCachingConfiguration =
			Mockito.mock(FrontendCachingConfiguration.class);

		Mockito.when(
			frontendCachingConfiguration.labelsModulesMaxAge()
		).thenReturn(
			_LABELS_MODULES_MAX_AGE
		);

		Mockito.when(
			frontendCachingConfiguration.sendNoCacheForLabelsModules()
		).thenReturn(
			_SEND_NO_CACHE_FOR_LABELS_MODULES
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

		if (deployLanguageJSON) {
			URL url = Mockito.mock(URL.class);

			Mockito.when(
				url.openStream()
			).thenReturn(
				new ByteArrayInputStream(
					"{keys:['a-key']}".getBytes(StandardCharsets.UTF_8))
			);

			Mockito.when(
				hashedFilesRegistry.getResource(
					Mockito.endsWith("/language.json"))
			).thenReturn(
				url
			);
		}

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

		Mockito.when(
			portal.getPathContext()
		).thenReturn(
			StringPool.BLANK
		);

		Mockito.when(
			portal.getPathProxy()
		).thenReturn(
			StringPool.BLANK
		);

		return portal;
	}

	private static final long _COMPANY_ID = 100;

	private static final boolean[] _EXPECTED_CAN_HANDLE_REQUEST = {
		false, true, true
	};

	private static final String[] _EXPECTED_CONTENT_SUBSTRING = {
		null, null, "'a-key':'a-key'"
	};

	private static final boolean[] _EXPECTED_FRONTEND_RESOURCE = {
		false, false, true
	};

	private static final String _INVALID_URL = "/nonsense/request/index.js";

	private static final long _LABELS_MODULES_MAX_AGE = 1000;

	private static final boolean _SEND_NO_CACHE_FOR_LABELS_MODULES = true;

	private static final String _VALID_URL =
		"/o/js/language/en_US/frontend-js-web/all.js";

}
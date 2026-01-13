/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.frontend.js.web.test.util.FrontendJSWebTestUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.PortletConfigFactory;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class JavaScriptFrontendResourceRequestHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(
		name = "{0}: deployHashed={1},  deployLanguageJSON={2}, deployTranslatable={3}, requestURL={4}"
	)
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, false, false, false, _INVALID_EXTENSION_URL},
				{1, false, false, false, _INVALID_URL},
				{2, false, false, false, _VALID_URL},
				{3, false, false, false, _VALID_HASHED_URL},
				{4, false, false, true, _VALID_URL},
				{5, false, false, true, _VALID_HASHED_URL},
				{6, false, true, false, _VALID_URL},
				{7, false, true, false, _VALID_HASHED_URL},
				{8, false, true, true, _VALID_URL},
				{9, false, true, true, _VALID_HASHED_URL},
				{10, true, false, false, _VALID_URL},
				{11, true, false, false, _VALID_HASHED_URL},
				{12, true, false, true, _VALID_URL},
				{13, true, false, true, _VALID_HASHED_URL},
				{14, true, true, false, _VALID_URL},
				{15, true, true, false, _VALID_HASHED_URL},
				{16, true, true, true, _VALID_URL},
				{17, true, true, true, _VALID_HASHED_URL}
			});
	}

	@Before
	public void setUp() {
		_localeUtilMockedStatic = Mockito.mockStatic(LocaleUtil.class);

		_localeUtilMockedStatic.when(
			() -> LocaleUtil.fromLanguageId(Mockito.anyString())
		).thenAnswer(
			(Answer<Locale>)invocationOnMock -> new Locale(
				invocationOnMock.getArgument(0))
		);
	}

	@After
	public void tearDown() {
		if (_localeUtilMockedStatic != null) {
			_localeUtilMockedStatic.close();

			_localeUtilMockedStatic = null;
		}
	}

	@Test
	public void test() throws Exception {
		JavaScriptFrontendResourceRequestHandler
			javaScriptFrontendResourceRequestHandler =
				new JavaScriptFrontendResourceRequestHandler(
					_mockConfigurationProvider(), _mockHashedFilesRegistry(),
					_mockLanguage(), _mockPortal(),
					Mockito.mock(PortletConfigFactory.class));

		HttpServletRequest httpServletRequest = _mockHttpServletRequest();

		Assert.assertEquals(
			_EXPECTED_CAN_HANDLE_REQUEST[index],
			javaScriptFrontendResourceRequestHandler.canHandleRequest(
				httpServletRequest));

		FrontendResource frontendResource =
			javaScriptFrontendResourceRequestHandler.handleRequest(
				httpServletRequest);

		if (!_EXPECTED_FRONTEND_RESOURCE[index]) {
			Assert.assertNull(frontendResource);

			return;
		}

		Assert.assertNotNull(frontendResource);
		Assert.assertEquals(
			ContentTypes.APPLICATION_JAVASCRIPT,
			frontendResource.getContentType());

		if (_EXPECTED_E_TAG[index] == ExpectedETag.HASH) {
			Assert.assertEquals(_HASH, frontendResource.getETag());
		}
		else if (_EXPECTED_E_TAG[index] == ExpectedETag.NULL) {
			Assert.assertNull(frontendResource.getETag());
		}

		Assert.assertEquals(
			_EXPECTED_IMMUTABLE[index], frontendResource.isImmutable());

		if (_EXPECTED_MAX_AGE[index] == ExpectedMaxAge.JS_FILES) {
			Assert.assertEquals(
				_JS_FILES_MAX_AGE, frontendResource.getMaxAge());
		}
		else if (_EXPECTED_MAX_AGE[index] == ExpectedMaxAge.INFINITE) {
			Assert.assertEquals(31536000L, frontendResource.getMaxAge());
		}
		else if (_EXPECTED_MAX_AGE[index] ==
					ExpectedMaxAge.TRANSLATED_JS_FILES) {

			Assert.assertEquals(
				_TRANSLATED_JS_FILES_MAX_AGE, frontendResource.getMaxAge());
		}

		Assert.assertEquals(
			_EXPECTED_PRIVATE[index], frontendResource.isPrivate());

		if (_EXPECTED_SEND_NO_CACHE[index] == ExpectedSendNoCache.JS_FILES) {
			Assert.assertEquals(
				_SEND_NO_CACHE_FOR_JS_FILES, frontendResource.isSendNoCache());
		}
		else if (_EXPECTED_SEND_NO_CACHE[index] == ExpectedSendNoCache.FALSE) {
			Assert.assertFalse(frontendResource.isSendNoCache());
		}
		else if (_EXPECTED_SEND_NO_CACHE[index] ==
					ExpectedSendNoCache.TRANSLATED_JS_FILES) {

			Assert.assertEquals(
				_SEND_NO_CACHE_FOR_TRANSLATED_JS_FILES,
				frontendResource.isSendNoCache());
		}

		if (_EXPECTED_CONTENT[index] == ExpectedContent.JS) {
			Assert.assertEquals(
				_JS_CONTENT,
				StreamUtil.toString(frontendResource.getInputStream()));
		}
		else if (_EXPECTED_CONTENT[index] == ExpectedContent.TRANSLATABLE_JS) {
			Assert.assertEquals(
				_TRANSLATABLE_JS_CONTENT,
				StreamUtil.toString(frontendResource.getInputStream()));
		}
		else if (_EXPECTED_CONTENT[index] == ExpectedContent.TRANSLATED_JS) {
			Assert.assertEquals(
				_TRANSLATED_JS_CONTENT,
				StreamUtil.toString(frontendResource.getInputStream()));
		}
	}

	@Parameterized.Parameter(1)
	public boolean deployHashed;

	@Parameterized.Parameter(2)
	public boolean deployLanguageJSON;

	@Parameterized.Parameter(3)
	public boolean deployTranslatable;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(4)
	public String requestURL;

	private ConfigurationProvider _mockConfigurationProvider()
		throws Exception {

		ConfigurationProvider configurationProvider = Mockito.mock(
			ConfigurationProvider.class);

		FrontendCachingConfiguration frontendCachingConfiguration =
			Mockito.mock(FrontendCachingConfiguration.class);

		Mockito.when(
			frontendCachingConfiguration.jsFilesMaxAge()
		).thenReturn(
			_JS_FILES_MAX_AGE
		);

		Mockito.when(
			frontendCachingConfiguration.translatedJSFilesMaxAge()
		).thenReturn(
			_TRANSLATED_JS_FILES_MAX_AGE
		);

		Mockito.when(
			frontendCachingConfiguration.sendNoCacheForJSFiles()
		).thenReturn(
			_SEND_NO_CACHE_FOR_JS_FILES
		);

		Mockito.when(
			frontendCachingConfiguration.sendNoCacheForTranslatedJSFiles()
		).thenReturn(
			_SEND_NO_CACHE_FOR_TRANSLATED_JS_FILES
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

		Mockito.when(
			hashedFilesRegistry.getHashedFileURI(Mockito.eq(_VALID_URL))
		).thenReturn(
			deployHashed ? _VALID_HASHED_FILE : _VALID_FILE
		);

		URL url = Mockito.mock(URL.class);

		Mockito.when(
			hashedFilesRegistry.getResource(Mockito.eq(_LANGUAGE_JSON_URL))
		).thenReturn(
			deployLanguageJSON ? url : null
		);

		url = Mockito.mock(URL.class);

		Mockito.when(
			url.getFile()
		).thenReturn(
			deployHashed ? _VALID_HASHED_FILE : _VALID_FILE
		);

		String content =
			deployTranslatable ? _TRANSLATABLE_JS_CONTENT : _JS_CONTENT;

		Mockito.when(
			url.openStream()
		).thenAnswer(
			(Answer<InputStream>)invocationOnMock -> new ByteArrayInputStream(
				content.getBytes(StandardCharsets.UTF_8))
		);

		Mockito.when(
			hashedFilesRegistry.getResource(Mockito.eq(_VALID_HASHED_URL))
		).thenReturn(
			deployHashed ? url : null
		);

		Mockito.when(
			hashedFilesRegistry.getResource(Mockito.eq(_VALID_URL))
		).thenReturn(
			url
		);

		return hashedFilesRegistry;
	}

	private HttpServletRequest _mockHttpServletRequest() {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setRequestURI(requestURL);

		return mockHttpServletRequest;
	}

	private Language _mockLanguage() {
		Language language = Mockito.mock(Language.class);

		Mockito.when(
			language.getLanguageId(Mockito.any(HttpServletRequest.class))
		).thenReturn(
			_LANGUAGE_ID
		);

		Mockito.when(
			language.process(
				Mockito.any(), Mockito.eq(new Locale(_LANGUAGE_ID)),
				Mockito.eq(_TRANSLATABLE_JS_CONTENT))
		).thenReturn(
			_TRANSLATED_JS_CONTENT
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
			portal.getPathModule()
		).thenReturn(
			Portal.PATH_MODULE
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
		false, false, true, false, true, false, true, false, true, false, true,
		true, true, true, true, true, true, true
	};

	private static final ExpectedContent[] _EXPECTED_CONTENT = {
		null, null, ExpectedContent.JS, null, ExpectedContent.TRANSLATED_JS,
		null, ExpectedContent.JS, null, ExpectedContent.TRANSLATABLE_JS, null,
		ExpectedContent.JS, ExpectedContent.JS, ExpectedContent.TRANSLATED_JS,
		ExpectedContent.TRANSLATED_JS, ExpectedContent.JS, ExpectedContent.JS,
		ExpectedContent.TRANSLATABLE_JS, ExpectedContent.TRANSLATABLE_JS
	};

	private static final ExpectedETag[] _EXPECTED_E_TAG = {
		null, null, ExpectedETag.NULL, null, ExpectedETag.NULL, null,
		ExpectedETag.NULL, null, ExpectedETag.NULL, null, ExpectedETag.HASH,
		ExpectedETag.HASH, ExpectedETag.NULL, ExpectedETag.NULL,
		ExpectedETag.HASH, ExpectedETag.HASH, ExpectedETag.HASH,
		ExpectedETag.HASH
	};

	private static final boolean[] _EXPECTED_FRONTEND_RESOURCE = {
		false, false, true, false, true, false, true, false, true, false, true,
		true, true, true, true, true, true, true
	};

	private static final boolean[] _EXPECTED_IMMUTABLE = {
		false, false, false, false, false, false, false, false, false, false,
		false, true, false, false, false, true, false, true
	};

	private static final ExpectedMaxAge[] _EXPECTED_MAX_AGE = {
		null, null, ExpectedMaxAge.JS_FILES, null,
		ExpectedMaxAge.TRANSLATED_JS_FILES, null, ExpectedMaxAge.JS_FILES, null,
		ExpectedMaxAge.JS_FILES, null, ExpectedMaxAge.JS_FILES,
		ExpectedMaxAge.INFINITE, ExpectedMaxAge.TRANSLATED_JS_FILES,
		ExpectedMaxAge.TRANSLATED_JS_FILES, ExpectedMaxAge.JS_FILES,
		ExpectedMaxAge.INFINITE, ExpectedMaxAge.JS_FILES,
		ExpectedMaxAge.INFINITE
	};

	private static final boolean[] _EXPECTED_PRIVATE = {
		false, false, false, false, true, false, false, false, false, false,
		false, false, true, true, false, false, false, false
	};

	private static final ExpectedSendNoCache[] _EXPECTED_SEND_NO_CACHE = {
		null, null, ExpectedSendNoCache.JS_FILES, null,
		ExpectedSendNoCache.TRANSLATED_JS_FILES, null,
		ExpectedSendNoCache.JS_FILES, null, ExpectedSendNoCache.JS_FILES, null,
		ExpectedSendNoCache.JS_FILES, ExpectedSendNoCache.FALSE,
		ExpectedSendNoCache.TRANSLATED_JS_FILES,
		ExpectedSendNoCache.TRANSLATED_JS_FILES, ExpectedSendNoCache.JS_FILES,
		ExpectedSendNoCache.FALSE, ExpectedSendNoCache.JS_FILES,
		ExpectedSendNoCache.FALSE
	};

	private static final String _HASH =
		FrontendJSWebTestUtil.randomHashedFileHash();

	private static final String _INVALID_EXTENSION_URL =
		"/o/frontend-js-web/__liferay__/index.nojs";

	private static final String _INVALID_URL = "/nonsense/request/index.js";

	private static final String _JS_CONTENT =
		"export default f() {return 'Hi there!';}";

	private static final long _JS_FILES_MAX_AGE = 1000;

	private static final String _LANGUAGE_ID = "en";

	private static final String _LANGUAGE_JSON_URL =
		"/o/frontend-js-web/language.json";

	private static final boolean _SEND_NO_CACHE_FOR_JS_FILES =
		RandomTestUtil.randomBoolean();

	private static final boolean _SEND_NO_CACHE_FOR_TRANSLATED_JS_FILES =
		RandomTestUtil.randomBoolean();

	private static final String _TRANSLATABLE_JS_CONTENT =
		"export default f() {return Liferay.Language.get('portlet');}";

	private static final String _TRANSLATED_JS_CONTENT =
		"export default f() {return 'Portlet';}";

	private static final long _TRANSLATED_JS_FILES_MAX_AGE = 2000;

	private static final String _VALID_FILE = "index.js";

	private static final String _VALID_HASHED_FILE = "index.(" + _HASH + ").js";

	private static final String _VALID_HASHED_URL =
		"/o/frontend-js-web/__liferay__/" + _VALID_HASHED_FILE;

	private static final String _VALID_URL =
		"/o/frontend-js-web/__liferay__/" + _VALID_FILE;

	private MockedStatic<LocaleUtil> _localeUtilMockedStatic;

	private enum ExpectedContent {

		JS, TRANSLATABLE_JS, TRANSLATED_JS

	}

	private enum ExpectedETag {

		HASH, NULL

	}

	private enum ExpectedMaxAge {

		INFINITE, JS_FILES, TRANSLATED_JS_FILES

	}

	private enum ExpectedSendNoCache {

		FALSE, JS_FILES, TRANSLATED_JS_FILES

	}

}
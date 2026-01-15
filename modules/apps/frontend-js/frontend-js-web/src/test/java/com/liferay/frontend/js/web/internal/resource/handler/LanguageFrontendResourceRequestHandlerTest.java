/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

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

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class LanguageFrontendResourceRequestHandlerTest
	extends BaseFrontendResourceRequestHandlerTestCase {

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
				{0, false, "/nonsense/request/index.js"},
				{1, false, _VALID_URL}, {2, true, _VALID_URL}
			});
	}

	@Test
	public void test() throws Exception {
		HashedFilesRegistry hashedFilesRegistry = Mockito.mock(
			HashedFilesRegistry.class);

		if (deployLanguageJSON) {
			mockDeployedFile(
				hashedFilesRegistry, "/o/frontend-js-web/language.json",
				"{keys:['a-key']}");
		}

		LanguageFrontendResourceRequestHandler
			languageFrontendResourceRequestHandler =
				new LanguageFrontendResourceRequestHandler(
					mockConfigurationProvider(
						HashMapBuilder.<String, Object>put(
							"labelsModulesMaxAge", _LABELS_MODULES_MAX_AGE
						).put(
							"sendNoCacheForLabelsModules",
							_SEND_NO_CACHE_FOR_LABELS_MODULES
						).build()),
					hashedFilesRegistry, new JSONFactoryImpl(), _mockLanguage(),
					mockPortal());

		HttpServletRequest httpServletRequest = mockHttpServletRequest(
			requestURL, null);

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

	private static final boolean[] _EXPECTED_CAN_HANDLE_REQUEST = {
		false, true, true
	};

	private static final String[] _EXPECTED_CONTENT_SUBSTRING = {
		null, null, "'a-key':'a-key'"
	};

	private static final boolean[] _EXPECTED_FRONTEND_RESOURCE = {
		false, false, true
	};

	private static final long _LABELS_MODULES_MAX_AGE = 1000;

	private static final boolean _SEND_NO_CACHE_FOR_LABELS_MODULES = true;

	private static final String _VALID_URL =
		"/o/js/language/en_US/frontend-js-web/all.js";

}
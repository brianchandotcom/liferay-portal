/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.resource.handler;

import com.liferay.frontend.js.web.internal.resource.FrontendResource;
import com.liferay.frontend.js.web.test.util.FrontendJSWebTestUtil;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.frontend.hashed.files.HashedFilesRegistry;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Arrays;
import java.util.Collection;

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

/**
 * @author Iván Zaera Avellón
 */
@RunWith(Parameterized.class)
public class HashedFileFrontendResourceRequestHandlerTest
	extends BaseFrontendResourceRequestHandlerTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "{0}: deployHashed={1}, requestURL={2}")
	public static Collection<Object[]> data() {
		return Arrays.asList(
			new Object[][] {
				{0, false, "/o/frontend-js-web/index.js.nomap"},
				{1, false, "/nonsense/request/index.js.map"},
				{2, false, _VALID_HASHED_URL}, {3, false, _VALID_URL},
				{4, true, _VALID_HASHED_URL}, {5, true, _VALID_URL}
			});
	}

	@Before
	public void setUp() {
		_fallbackKeysSettingsUtilMockedStatic = Mockito.mockStatic(
			FallbackKeysSettingsUtil.class);

		Settings settings = Mockito.mock(Settings.class);

		Mockito.when(
			settings.getValue(Mockito.eq("maxAgeKey"), Mockito.anyString())
		).thenReturn(
			String.valueOf(_MAX_AGE)
		);

		Mockito.when(
			settings.getValue(Mockito.eq("sendNoCacheKey"), Mockito.anyString())
		).thenReturn(
			String.valueOf(_SEND_NO_CACHE)
		);

		_fallbackKeysSettingsUtilMockedStatic.when(
			() -> FallbackKeysSettingsUtil.getSettings(
				Mockito.any(SettingsLocator.class))
		).thenReturn(
			settings
		);
	}

	@After
	public void tearDown() {
		if (_fallbackKeysSettingsUtilMockedStatic != null) {
			_fallbackKeysSettingsUtilMockedStatic.close();

			_fallbackKeysSettingsUtilMockedStatic = null;
		}
	}

	@Test
	public void test() throws Exception {
		HashedFilesRegistry hashedFilesRegistry = Mockito.mock(
			HashedFilesRegistry.class);

		mockDeployedFile(
			hashedFilesRegistry, deployHashed ? _VALID_HASHED_URL : _VALID_URL,
			_CONTENT);

		HashedFileFrontendResourceRequestHandler
			hashedFileFrontendResourceRequestHandler =
				new HashedFileFrontendResourceRequestHandler(
					ContentTypes.APPLICATION_JSON, _MAX_AGE, _SEND_NO_CACHE,
					".map", hashedFilesRegistry, "maxAgeKey",
					Mockito.mock(Portal.class), "sendNoCacheKey");

		HttpServletRequest httpServletRequest = mockHttpServletRequest(
			requestURL, null);

		Assert.assertEquals(
			_EXPECTED_CAN_HANDLE_REQUEST[index],
			hashedFileFrontendResourceRequestHandler.canHandleRequest(
				httpServletRequest));

		FrontendResource frontendResource =
			hashedFileFrontendResourceRequestHandler.handleRequest(
				httpServletRequest);

		if (!_EXPECTED_FRONTEND_RESOURCE[index]) {
			Assert.assertNull(frontendResource);

			return;
		}

		Assert.assertNotNull(frontendResource);
		Assert.assertEquals(
			ContentTypes.APPLICATION_JSON, frontendResource.getContentType());

		if (_EXPECTED_E_TAG[index] == ExpectedETag.HASH) {
			Assert.assertEquals(_HASH, frontendResource.getETag());
		}
		else if (_EXPECTED_E_TAG[index] == ExpectedETag.NULL) {
			Assert.assertNull(frontendResource.getETag());
		}

		Assert.assertEquals(
			_EXPECTED_IMMUTABLE[index], frontendResource.isImmutable());

		if (_EXPECTED_MAX_AGE[index] == ExpectedMaxAge.CONFIGURED) {
			Assert.assertEquals(_MAX_AGE, frontendResource.getMaxAge());
		}
		else if (_EXPECTED_MAX_AGE[index] == ExpectedMaxAge.INFINITE) {
			Assert.assertEquals(31536000L, frontendResource.getMaxAge());
		}

		Assert.assertFalse(frontendResource.isPrivate());

		if (_EXPECTED_SEND_NO_CACHE[index] == ExpectedSendNoCache.CONFIGURED) {
			Assert.assertEquals(
				_SEND_NO_CACHE, frontendResource.isSendNoCache());
		}
		else if (_EXPECTED_SEND_NO_CACHE[index] == ExpectedSendNoCache.FALSE) {
			Assert.assertFalse(frontendResource.isSendNoCache());
		}

		Assert.assertEquals(
			_CONTENT, StreamUtil.toString(frontendResource.getInputStream()));
	}

	@Parameterized.Parameter(1)
	public boolean deployHashed;

	@Parameterized.Parameter
	public int index;

	@Parameterized.Parameter(2)
	public String requestURL;

	private static final String _CONTENT = "{}";

	private static final boolean[] _EXPECTED_CAN_HANDLE_REQUEST = {
		false, false, false, true, true, true
	};

	private static final ExpectedETag[] _EXPECTED_E_TAG = {
		null, null, null, ExpectedETag.NULL, ExpectedETag.HASH,
		ExpectedETag.HASH
	};

	private static final boolean[] _EXPECTED_FRONTEND_RESOURCE = {
		false, false, false, true, true, true
	};

	private static final boolean[] _EXPECTED_IMMUTABLE = {
		false, false, false, false, true, false
	};

	private static final ExpectedMaxAge[] _EXPECTED_MAX_AGE = {
		null, null, null, ExpectedMaxAge.CONFIGURED, ExpectedMaxAge.INFINITE,
		ExpectedMaxAge.CONFIGURED
	};

	private static final ExpectedSendNoCache[] _EXPECTED_SEND_NO_CACHE = {
		null, null, ExpectedSendNoCache.CONFIGURED,
		ExpectedSendNoCache.CONFIGURED, ExpectedSendNoCache.FALSE,
		ExpectedSendNoCache.CONFIGURED
	};

	private static final String _HASH =
		FrontendJSWebTestUtil.randomHashedFileHash();

	private static final long _MAX_AGE = 1000;

	private static final boolean _SEND_NO_CACHE = true;

	private static final String _VALID_HASHED_URL =
		"/o/frontend-js-web/index.js.(" + _HASH + ").map";

	private static final String _VALID_URL = "/o/frontend-js-web/index.js.map";

	private MockedStatic<FallbackKeysSettingsUtil>
		_fallbackKeysSettingsUtilMockedStatic;

	private enum ExpectedETag {

		HASH, NULL,

	}

	private enum ExpectedMaxAge {

		CONFIGURED, INFINITE

	}

	private enum ExpectedSendNoCache {

		CONFIGURED, FALSE

	}

}
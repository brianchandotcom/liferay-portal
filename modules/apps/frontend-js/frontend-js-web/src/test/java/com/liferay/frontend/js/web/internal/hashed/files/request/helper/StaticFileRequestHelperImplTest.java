/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.hashed.files.HashedFileURIsRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.ByteArrayInputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public class StaticFileRequestHelperImplTest {

	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@After
	public void tearDown() {
		if (_fallbackKeysSettingsUtilMockedStatic != null) {
			_fallbackKeysSettingsUtilMockedStatic.close();

			_fallbackKeysSettingsUtilMockedStatic = null;
		}
	}

	@Test
	public void testColdRequestWithoutHash() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"max-age-key", 1234L
			).put(
				"send-no-cache-key", false
			).build());

		StaticFileRequestHelperImpl staticFileRequestHelperImpl =
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
				1234L, "max-age-key", _mockPortal(), false, "send-no-cache-key",
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		staticFileRequestHelperImpl.process(
			_mockHttpServletRequest("/o/frontend-js-web/__liferay__/index.js"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("must-revalidate"));

		Assert.assertTrue(cacheControl.contains("max-age=1234"));

		String contentType = mockHttpServletResponse.getHeader(
			HttpHeaders.CONTENT_TYPE);

		Assert.assertTrue(contentType.contains(ContentTypes.TEXT_JAVASCRIPT));

		Assert.assertTrue(contentType.contains("charset=UTF-8"));

		Assert.assertEquals(
			"CAFEBABE", mockHttpServletResponse.getHeader(HttpHeaders.ETAG));

		String contentAsString = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(contentAsString.contains("export default x;"));
	}

	@Test
	public void testConfigurationSendNoCache() throws Exception {

		// must-revalidate

		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"max-age-key", 1234L
			).put(
				"send-no-cache-key", false
			).build());

		StaticFileRequestHelperImpl staticFileRequestHelperImpl =
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
				1234L, "max-age-key", _mockPortal(), false, "send-no-cache-key",
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		staticFileRequestHelperImpl.process(
			_mockHttpServletRequest("/o/frontend-js-web/__liferay__/index.js"),
			mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(
			cacheControl, cacheControl.contains("must-revalidate"));

		// no-cache

		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"max-age-key", 1234L
			).put(
				"send-no-cache-key", true
			).build());

		staticFileRequestHelperImpl = new StaticFileRequestHelperImpl(
			ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
			1234L, "max-age-key", _mockPortal(), true, "send-no-cache-key",
			_mockServiceTrackerMap(_mockServletContext()));

		mockHttpServletResponse = new MockHttpServletResponse();

		staticFileRequestHelperImpl.process(
			_mockHttpServletRequest("/o/frontend-js-web/__liferay__/index.js"),
			mockHttpServletResponse);

		cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl, cacheControl.contains("no-cache"));
	}

	@Test
	public void testInvalidRequest() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"max-age-key", 1234L
			).put(
				"send-no-cache-key", false
			).build());

		StaticFileRequestHelperImpl staticFileRequestHelperImpl =
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
				1234L, "max-age-key", _mockPortal(), false, "send-no-cache-key",
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		staticFileRequestHelperImpl.process(
			_mockHttpServletRequest(
				"/o/___INVALID___/__liferay__/index.(CAFEBABE).js"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			mockHttpServletResponse.getStatus());
	}

	@Test
	public void testIsAcceptableRequest() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"max-age-key", 1234L
			).put(
				"send-no-cache-key", false
			).build());

		StaticFileRequestHelperImpl staticFileRequestHelperImpl =
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
				1234L, "max-age-key", _mockPortal(), false, "send-no-cache-key",
				_mockServiceTrackerMap(_mockServletContext()));

		Assert.assertTrue(
			staticFileRequestHelperImpl.isAcceptableRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.js")));

		Assert.assertTrue(
			staticFileRequestHelperImpl.isAcceptableRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js")));

		Assert.assertFalse(
			staticFileRequestHelperImpl.isAcceptableRequest(
				_mockHttpServletRequest("/nonsense/request/index.js")));
	}

	@Test
	public void testReasonableConfigurationDefaults() throws Exception {
		_mockFallbackKeysSettingsUtil(null);

		StaticFileRequestHelperImpl staticFileRequestHelperImpl =
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
				4321L, "max-age-key", _mockPortal(), false, "send-no-cache-key",
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		staticFileRequestHelperImpl.process(
			_mockHttpServletRequest("/o/frontend-js-web/__liferay__/index.js"),
			mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("must-revalidate"));
		Assert.assertTrue(cacheControl.contains("max-age=4321"));
	}

	@Test
	public void testRequestWithHash() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"max-age-key", 1234L
			).put(
				"send-no-cache-key", false
			).build());

		StaticFileRequestHelperImpl staticFileRequestHelperImpl =
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
				1234L, "max-age-key", _mockPortal(), false, "send-no-cache-key",
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		staticFileRequestHelperImpl.process(
			_mockHttpServletRequest(
				"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("immutable"));

		Assert.assertTrue(cacheControl.contains("max-age=31536000"));

		String contentType = mockHttpServletResponse.getHeader(
			HttpHeaders.CONTENT_TYPE);

		Assert.assertTrue(contentType.contains(ContentTypes.TEXT_JAVASCRIPT));

		Assert.assertTrue(contentType.contains("charset=UTF-8"));

		Assert.assertEquals(
			"CAFEBABE", mockHttpServletResponse.getHeader(HttpHeaders.ETAG));

		String contentAsString = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(contentAsString.contains("export default x;"));
	}

	@Test
	public void testWarmRequestWithoutHash() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"max-age-key", 1234L
			).put(
				"send-no-cache-key", false
			).build());

		StaticFileRequestHelperImpl staticFileRequestHelperImpl =
			new StaticFileRequestHelperImpl(
				ContentTypes.TEXT_JAVASCRIPT, ".js", _mockHashedFilesRegistry(),
				1234L, "max-age-key", _mockPortal(), false, "send-no-cache-key",
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletRequest mockHttpServletRequest = _mockHttpServletRequest(
			"/o/frontend-js-web/__liferay__/index.js");

		mockHttpServletRequest.addHeader(HttpHeaders.IF_NONE_MATCH, "CAFEBABE");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		staticFileRequestHelperImpl.process(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_MODIFIED,
			mockHttpServletResponse.getStatus());
	}

	private void _mockFallbackKeysSettingsUtil(Map<String, Object> map) {
		if (_fallbackKeysSettingsUtilMockedStatic != null) {
			_fallbackKeysSettingsUtilMockedStatic.close();
		}

		_fallbackKeysSettingsUtilMockedStatic = Mockito.mockStatic(
			FallbackKeysSettingsUtil.class);

		Settings settings = null;

		if (map != null) {
			settings = Mockito.mock(Settings.class);

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				Mockito.when(
					settings.getValue(
						Mockito.eq(entry.getKey()), Mockito.anyString())
				).thenReturn(
					String.valueOf(entry.getValue())
				);
			}
		}

		_fallbackKeysSettingsUtilMockedStatic.when(
			() -> FallbackKeysSettingsUtil.getSettings(
				Mockito.any(SettingsLocator.class))
		).thenReturn(
			settings
		);
	}

	private HashedFileURIsRegistry _mockHashedFilesRegistry() {
		HashedFileURIsRegistry hashedFileURIsRegistry = Mockito.mock(
			HashedFileURIsRegistry.class);

		Mockito.when(
			hashedFileURIsRegistry.get(
				Mockito.eq("/o/frontend-js-web/__liferay__/index.js"))
		).thenReturn(
			"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js"
		);

		return hashedFileURIsRegistry;
	}

	private MockHttpServletRequest _mockHttpServletRequest(String requestURI) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setRequestURI(requestURI);

		return mockHttpServletRequest;
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
				"export default x;".getBytes(StandardCharsets.UTF_8))
		);

		Mockito.when(
			servletContext.getResource("/__liferay__/index.(CAFEBABE).js")
		).thenReturn(
			url
		);

		return servletContext;
	}

	private static final long _COMPANY_ID = 1L;

	private MockedStatic<FallbackKeysSettingsUtil>
		_fallbackKeysSettingsUtilMockedStatic;

}
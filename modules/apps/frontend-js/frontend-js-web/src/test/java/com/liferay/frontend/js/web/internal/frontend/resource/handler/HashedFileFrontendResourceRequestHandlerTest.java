/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.frontend.resource.handler;

import com.liferay.frontend.js.web.internal.frontend.resource.FrontendResource;
import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.petra.io.StreamUtil;
import com.liferay.portal.kernel.settings.FallbackKeysSettingsUtil;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsLocator;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

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

/**
 * @author Iván Zaera Avellón
 */
public class HashedFileFrontendResourceRequestHandlerTest {

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
	public void testCanHandleRequest() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"maxAgeKey", 1234L
			).put(
				"sendNoCacheKey", false
			).build());

		HashedFileFrontendResourceRequestHandler
			hashedFileFrontendResourceRequestHandler =
				new HashedFileFrontendResourceRequestHandler(
					ContentTypes.TEXT_JAVASCRIPT, ".js",
					_mockHashedFilesRegistry(), 1234L, "maxAgeKey",
					_mockPortal(), false, "sendNoCacheKey",
					_mockServiceTrackerMap(
						_mockServletContext(
							"/__liferay__/index.(CAFEBABE).js")));

		Assert.assertTrue(
			hashedFileFrontendResourceRequestHandler.canHandleRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.js")));

		Assert.assertTrue(
			hashedFileFrontendResourceRequestHandler.canHandleRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js")));

		Assert.assertFalse(
			hashedFileFrontendResourceRequestHandler.canHandleRequest(
				_mockHttpServletRequest("/nonsense/request/index.js")));
	}

	@Test
	public void testConfigurationDefaults() throws Exception {
		_mockFallbackKeysSettingsUtil(null);

		HashedFileFrontendResourceRequestHandler
			hashedFileFrontendResourceRequestHandler =
				new HashedFileFrontendResourceRequestHandler(
					ContentTypes.TEXT_JAVASCRIPT, ".js",
					_mockHashedFilesRegistry(), 4321L, "maxAgeKey",
					_mockPortal(), true, "sendNoCacheKey",
					_mockServiceTrackerMap(
						_mockServletContext(
							"/__liferay__/index.(CAFEBABE).js")));

		FrontendResource frontendResource =
			hashedFileFrontendResourceRequestHandler.handleRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.js"));

		Assert.assertEquals(4321L, frontendResource.getMaxAge());
		Assert.assertTrue(frontendResource.isSendNoCache());
	}

	@Test
	public void testRequestWithHash() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"maxAgeKey", 1234L
			).put(
				"sendNoCacheKey", false
			).build());

		HashedFileFrontendResourceRequestHandler
			hashedFileFrontendResourceRequestHandler =
				new HashedFileFrontendResourceRequestHandler(
					ContentTypes.TEXT_JAVASCRIPT, ".js",
					_mockHashedFilesRegistry(), 4321L, "maxAgeKey",
					_mockPortal(), true, "sendNoCacheKey",
					_mockServiceTrackerMap(
						_mockServletContext(
							"/__liferay__/index.(CAFEBABE).js")));

		FrontendResource frontendResource =
			hashedFileFrontendResourceRequestHandler.handleRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js"));

		Assert.assertNotNull(frontendResource);

		Assert.assertEquals(
			ContentTypes.TEXT_JAVASCRIPT, frontendResource.getContentType());

		Assert.assertEquals("CAFEBABE", frontendResource.getETag());

		Assert.assertEquals(31536000L, frontendResource.getMaxAge());

		Assert.assertTrue(frontendResource.isImmutable());

		Assert.assertFalse(frontendResource.isSendNoCache());

		Assert.assertEquals(
			"export default x;",
			StreamUtil.toString(frontendResource.getInputStream()));
	}

	@Test
	public void testRequestWithoutHashForNonregisteredFile() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"maxAgeKey", 1234L
			).put(
				"sendNoCacheKey", false
			).build());

		HashedFileFrontendResourceRequestHandler
			hashedFileFrontendResourceRequestHandler =
				new HashedFileFrontendResourceRequestHandler(
					ContentTypes.TEXT_JAVASCRIPT, ".js",
					Mockito.mock(HashedFilesRegistry.class), 4321L, "maxAgeKey",
					_mockPortal(), true, "sendNoCacheKey",
					_mockServiceTrackerMap(
						_mockServletContext("/__liferay__/index.js")));

		FrontendResource frontendResource =
			hashedFileFrontendResourceRequestHandler.handleRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.js"));

		Assert.assertNotNull(frontendResource);

		Assert.assertEquals(
			ContentTypes.TEXT_JAVASCRIPT, frontendResource.getContentType());

		Assert.assertNull(frontendResource.getETag());

		Assert.assertEquals(1234L, frontendResource.getMaxAge());

		Assert.assertFalse(frontendResource.isImmutable());

		Assert.assertFalse(frontendResource.isSendNoCache());

		Assert.assertEquals(
			"export default x;",
			StreamUtil.toString(frontendResource.getInputStream()));
	}

	@Test
	public void testRequestWithoutHashForRegisteredFile() throws Exception {
		_mockFallbackKeysSettingsUtil(
			HashMapBuilder.<String, Object>put(
				"maxAgeKey", 1234L
			).put(
				"sendNoCacheKey", false
			).build());

		HashedFileFrontendResourceRequestHandler
			hashedFileFrontendResourceRequestHandler =
				new HashedFileFrontendResourceRequestHandler(
					ContentTypes.TEXT_JAVASCRIPT, ".js",
					_mockHashedFilesRegistry(), 4321L, "maxAgeKey",
					_mockPortal(), true, "sendNoCacheKey",
					_mockServiceTrackerMap(
						_mockServletContext(
							"/__liferay__/index.(CAFEBABE).js")));

		FrontendResource frontendResource =
			hashedFileFrontendResourceRequestHandler.handleRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.js"));

		Assert.assertNotNull(frontendResource);

		Assert.assertEquals(
			ContentTypes.TEXT_JAVASCRIPT, frontendResource.getContentType());

		Assert.assertEquals("CAFEBABE", frontendResource.getETag());

		Assert.assertEquals(1234L, frontendResource.getMaxAge());

		Assert.assertFalse(frontendResource.isImmutable());

		Assert.assertFalse(frontendResource.isSendNoCache());

		Assert.assertEquals(
			"export default x;",
			StreamUtil.toString(frontendResource.getInputStream()));
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

	private HashedFilesRegistry _mockHashedFilesRegistry() {
		HashedFilesRegistry hashedFilesRegistry = Mockito.mock(
			HashedFilesRegistry.class);

		Mockito.when(
			hashedFilesRegistry.get(
				Mockito.eq("/o/frontend-js-web/__liferay__/index.js"))
		).thenReturn(
			"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js"
		);

		return hashedFilesRegistry;
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

	private ServletContext _mockServletContext(String resourcePath)
		throws Exception {

		ServletContext servletContext = Mockito.mock(ServletContext.class);

		URL url = Mockito.mock(URL.class);

		Mockito.when(
			url.openStream()
		).thenReturn(
			new ByteArrayInputStream(
				"export default x;".getBytes(StandardCharsets.UTF_8))
		);

		Mockito.when(
			servletContext.getResource(resourcePath)
		).thenReturn(
			url
		);

		return servletContext;
	}

	private static final long _COMPANY_ID = 1L;

	private MockedStatic<FallbackKeysSettingsUtil>
		_fallbackKeysSettingsUtilMockedStatic;

}
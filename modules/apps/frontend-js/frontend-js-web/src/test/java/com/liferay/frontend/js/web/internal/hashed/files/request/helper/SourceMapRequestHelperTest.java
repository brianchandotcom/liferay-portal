/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.js.web.internal.hashed.files.request.helper;

import com.liferay.frontend.js.web.internal.configuration.FrontendCachingConfiguration;
import com.liferay.frontend.js.web.internal.hashed.files.HashedFilesRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.configuration.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayInputStream;

import java.net.URL;

import java.nio.charset.StandardCharsets;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Iván Zaera Avellón
 */
public class SourceMapRequestHelperTest {

	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testColdRequestWithoutHash() throws Exception {
		SourceMapRequestHelper sourceMapRequestHelper =
			new SourceMapRequestHelper(
				_mockConfigurationProvider(
					_mockFrontendCachingConfiguration(1234L, false)),
				_mockHashedFilesRegistry(), _mockPortal(),
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sourceMapRequestHelper.process(
			_mockHttpServletRequest(
				"/o/frontend-js-web/__liferay__/index.js.map"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("must-revalidate"));

		Assert.assertTrue(cacheControl.contains("max-age=1234"));

		String contentType = mockHttpServletResponse.getHeader(
			HttpHeaders.CONTENT_TYPE);

		Assert.assertTrue(contentType.contains(ContentTypes.APPLICATION_JSON));

		Assert.assertTrue(contentType.contains("charset=UTF-8"));

		Assert.assertEquals(
			"CAFEBABE", mockHttpServletResponse.getHeader(HttpHeaders.ETAG));

		String contentAsString = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(contentAsString.contains("{}"));
	}

	@Test
	public void testConfigurationSendNoCache() throws Exception {

		// must-revalidate

		SourceMapRequestHelper sourceMapRequestHelper =
			new SourceMapRequestHelper(
				_mockConfigurationProvider(
					_mockFrontendCachingConfiguration(1234L, false)),
				_mockHashedFilesRegistry(), _mockPortal(),
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sourceMapRequestHelper.process(
			_mockHttpServletRequest(
				"/o/frontend-js-web/__liferay__/index.js.map"),
			mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(
			cacheControl, cacheControl.contains("must-revalidate"));

		// no-cache

		sourceMapRequestHelper = new SourceMapRequestHelper(
			_mockConfigurationProvider(
				_mockFrontendCachingConfiguration(1234L, true)),
			_mockHashedFilesRegistry(), _mockPortal(),
			_mockServiceTrackerMap(_mockServletContext()));

		mockHttpServletResponse = new MockHttpServletResponse();

		sourceMapRequestHelper.process(
			_mockHttpServletRequest(
				"/o/frontend-js-web/__liferay__/index.js.map"),
			mockHttpServletResponse);

		cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl, cacheControl.contains("no-cache"));
	}

	@Test
	public void testInvalidRequest() throws Exception {
		SourceMapRequestHelper sourceMapRequestHelper =
			new SourceMapRequestHelper(
				_mockConfigurationProvider(
					_mockFrontendCachingConfiguration(1234L, false)),
				_mockHashedFilesRegistry(), _mockPortal(),
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sourceMapRequestHelper.process(
			_mockHttpServletRequest(
				"/o/___INVALID___/__liferay__/index.(CAFEBABE).js.map"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_FOUND,
			mockHttpServletResponse.getStatus());
	}

	@Test
	public void testIsAcceptableRequest() throws Exception {
		SourceMapRequestHelper sourceMapRequestHelper =
			new SourceMapRequestHelper(
				_mockConfigurationProvider(
					_mockFrontendCachingConfiguration(1234L, false)),
				_mockHashedFilesRegistry(), _mockPortal(),
				_mockServiceTrackerMap(_mockServletContext()));

		Assert.assertTrue(
			sourceMapRequestHelper.isAcceptableRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.js.map")));

		Assert.assertTrue(
			sourceMapRequestHelper.isAcceptableRequest(
				_mockHttpServletRequest(
					"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js.map")));

		Assert.assertFalse(
			sourceMapRequestHelper.isAcceptableRequest(
				_mockHttpServletRequest("/nonsense/request/index.js.map")));
	}

	@Test
	public void testReasonableConfigurationDefaults() throws Exception {
		SourceMapRequestHelper sourceMapRequestHelper =
			new SourceMapRequestHelper(
				_mockConfigurationProvider(null), _mockHashedFilesRegistry(),
				_mockPortal(), _mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sourceMapRequestHelper.process(
			_mockHttpServletRequest(
				"/o/frontend-js-web/__liferay__/index.js.map"),
			mockHttpServletResponse);

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("must-revalidate"));
		Assert.assertTrue(cacheControl.contains("max-age=86400"));
	}

	@Test
	public void testRequestWithHash() throws Exception {
		SourceMapRequestHelper sourceMapRequestHelper =
			new SourceMapRequestHelper(
				_mockConfigurationProvider(
					_mockFrontendCachingConfiguration(1234L, false)),
				_mockHashedFilesRegistry(), _mockPortal(),
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sourceMapRequestHelper.process(
			_mockHttpServletRequest(
				"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js.map"),
			mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_OK, mockHttpServletResponse.getStatus());

		String cacheControl = mockHttpServletResponse.getHeader(
			HttpHeaders.CACHE_CONTROL);

		Assert.assertTrue(cacheControl.contains("immutable"));

		Assert.assertTrue(cacheControl.contains("max-age=31536000"));

		String contentType = mockHttpServletResponse.getHeader(
			HttpHeaders.CONTENT_TYPE);

		Assert.assertTrue(contentType.contains(ContentTypes.APPLICATION_JSON));

		Assert.assertTrue(contentType.contains("charset=UTF-8"));

		Assert.assertEquals(
			"CAFEBABE", mockHttpServletResponse.getHeader(HttpHeaders.ETAG));

		String contentAsString = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(contentAsString.contains("{}"));
	}

	@Test
	public void testWarmRequestWithoutHash() throws Exception {
		SourceMapRequestHelper sourceMapRequestHelper =
			new SourceMapRequestHelper(
				_mockConfigurationProvider(
					_mockFrontendCachingConfiguration(1234L, false)),
				_mockHashedFilesRegistry(), _mockPortal(),
				_mockServiceTrackerMap(_mockServletContext()));

		MockHttpServletRequest mockHttpServletRequest = _mockHttpServletRequest(
			"/o/frontend-js-web/__liferay__/index.js.map");

		mockHttpServletRequest.addHeader(HttpHeaders.IF_NONE_MATCH, "CAFEBABE");

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		sourceMapRequestHelper.process(
			mockHttpServletRequest, mockHttpServletResponse);

		Assert.assertEquals(
			HttpServletResponse.SC_NOT_MODIFIED,
			mockHttpServletResponse.getStatus());
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
		long esModulesMaxAge, boolean sendNoCacheForESModules) {

		FrontendCachingConfiguration frontendCachingConfiguration =
			Mockito.mock(FrontendCachingConfiguration.class);

		Mockito.when(
			frontendCachingConfiguration.esModulesMaxAge()
		).thenReturn(
			esModulesMaxAge
		);

		Mockito.when(
			frontendCachingConfiguration.sendNoCacheForESModules()
		).thenReturn(
			sendNoCacheForESModules
		);

		return frontendCachingConfiguration;
	}

	private HashedFilesRegistry _mockHashedFilesRegistry() {
		HashedFilesRegistry hashedFilesRegistry = Mockito.mock(
			HashedFilesRegistry.class);

		Mockito.when(
			hashedFilesRegistry.getHashedFile(
				Mockito.eq("/o/frontend-js-web/__liferay__/index.js.map"))
		).thenReturn(
			"/o/frontend-js-web/__liferay__/index.(CAFEBABE).js.map"
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

	private ServletContext _mockServletContext() throws Exception {
		ServletContext servletContext = Mockito.mock(ServletContext.class);

		URL url = Mockito.mock(URL.class);

		Mockito.when(
			url.openStream()
		).thenReturn(
			new ByteArrayInputStream("{}".getBytes(StandardCharsets.UTF_8))
		);

		Mockito.when(
			servletContext.getResource("/__liferay__/index.(CAFEBABE).js.map")
		).thenReturn(
			url
		);

		return servletContext;
	}

	private static final long _COMPANY_ID = 1L;

}
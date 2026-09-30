/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.catapult.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.oauth2.provider.constants.ClientProfile;
import com.liferay.oauth2.provider.constants.GrantType;
import com.liferay.oauth2.provider.model.OAuth2Application;
import com.liferay.oauth2.provider.service.OAuth2ApplicationLocalService;
import com.liferay.portal.catapult.PortalCatapult;
import com.liferay.portal.catapult.PortalCatapultHeaderContributor;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;

import java.util.Collections;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Jorge García Jiménez
 */
@RunWith(Arquillian.class)
public class PortalCatapultTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testLaunchWithContributedHeaders() throws Exception {
		try (ClientExtensionHttpServer clientExtensionHttpServer =
				new ClientExtensionHttpServer()) {

			String value = RandomTestUtil.randomString();

			_launch(
				clientExtensionHttpServer,
				(companyId, homePageURL, location, oAuth2ApplicationFeatures,
				 userId) -> HashMapBuilder.put(
					_HEADER_NAME, value
				).put(
					HttpHeaders.AUTHORIZATION, value
				).put(
					HttpHeaders.CONTENT_TYPE, value
				).put(
					"authorization", value
				).build());

			Assert.assertEquals(
				value, clientExtensionHttpServer._getHeader(_HEADER_NAME));

			String authorization = clientExtensionHttpServer._getHeader(
				HttpHeaders.AUTHORIZATION);

			Assert.assertTrue(
				authorization, authorization.startsWith("Bearer "));

			Assert.assertEquals(
				ContentTypes.APPLICATION_JSON,
				clientExtensionHttpServer._getHeader(HttpHeaders.CONTENT_TYPE));
		}
	}

	@Test
	public void testLaunchWithoutContributedHeaders() throws Exception {
		try (ClientExtensionHttpServer clientExtensionHttpServer =
				new ClientExtensionHttpServer()) {

			_launch(
				clientExtensionHttpServer,
				(companyId, homePageURL, location, oAuth2ApplicationFeatures,
				 userId) -> Collections.emptyMap());

			Assert.assertNotNull(
				clientExtensionHttpServer._getHeader(
					HttpHeaders.AUTHORIZATION));
			Assert.assertNull(
				clientExtensionHttpServer._getHeader(_HEADER_NAME));
		}
	}

	private OAuth2Application _addOAuth2Application(String homePageURL)
		throws Exception {

		User user = UserTestUtil.getAdminUser(TestPropsValues.getCompanyId());

		return _oAuth2ApplicationLocalService.addOrUpdateOAuth2Application(
			RandomTestUtil.randomString(), user.getUserId(), user.getLogin(),
			ListUtil.fromArray(
				GrantType.CLIENT_CREDENTIALS, GrantType.JWT_BEARER),
			"client_secret_post", user.getUserId(),
			RandomTestUtil.randomString(), ClientProfile.HEADLESS_SERVER.id(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ListUtil.fromArray("token.introspection"), homePageURL, 0, null,
			RandomTestUtil.randomString(),
			"http://" + RandomTestUtil.randomString() + ".liferay.com/privacy",
			Collections.emptyList(), false, Collections.emptyList(), true,
			new ServiceContext());
	}

	private void _launch(
			ClientExtensionHttpServer clientExtensionHttpServer,
			PortalCatapultHeaderContributor portalCatapultHeaderContributor)
		throws Exception {

		OAuth2Application oAuth2Application = _addOAuth2Application(
			clientExtensionHttpServer._getURL());

		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		ServiceRegistration<PortalCatapultHeaderContributor>
			serviceRegistration = bundleContext.registerService(
				PortalCatapultHeaderContributor.class,
				portalCatapultHeaderContributor, null);

		try {
			Future<byte[]> future = _portalCatapult.launch(
				oAuth2Application.getCompanyId(), Http.Method.GET,
				oAuth2Application.getExternalReferenceCode(), null, "/resource",
				TestPropsValues.getUserId());

			Assert.assertEquals(
				"{}", new String(future.get(1, TimeUnit.MINUTES)));
		}
		finally {
			serviceRegistration.unregister();

			_oAuth2ApplicationLocalService.deleteOAuth2Application(
				oAuth2Application);
		}
	}

	private static final String _HEADER_NAME = RandomTestUtil.randomString();

	@Inject
	private OAuth2ApplicationLocalService _oAuth2ApplicationLocalService;

	@Inject
	private PortalCatapult _portalCatapult;

	private static class ClientExtensionHttpServer implements AutoCloseable {

		public ClientExtensionHttpServer() throws IOException {
			_httpServer = HttpServer.create(
				new InetSocketAddress("127.0.0.1", 0), 0);

			_httpServer.createContext(
				"/",
				httpExchange -> {
					_headers = httpExchange.getRequestHeaders();

					byte[] bytes = "{}".getBytes(StandardCharsets.UTF_8);

					Headers responseHeaders = httpExchange.getResponseHeaders();

					responseHeaders.set(
						HttpHeaders.CONTENT_TYPE,
						ContentTypes.APPLICATION_JSON);

					httpExchange.sendResponseHeaders(
						HttpURLConnection.HTTP_OK, bytes.length);

					try (OutputStream outputStream =
							httpExchange.getResponseBody()) {

						outputStream.write(bytes);
					}
				});

			_httpServer.start();

			InetSocketAddress inetSocketAddress = _httpServer.getAddress();

			_url = "http://127.0.0.1:" + inetSocketAddress.getPort();
		}

		@Override
		public void close() {
			_httpServer.stop(0);
		}

		private String _getHeader(String name) {
			if (_headers == null) {
				return null;
			}

			return _headers.getFirst(name);
		}

		private String _getURL() {
			return _url;
		}

		private volatile Headers _headers;
		private final HttpServer _httpServer;
		private final String _url;

	}

}
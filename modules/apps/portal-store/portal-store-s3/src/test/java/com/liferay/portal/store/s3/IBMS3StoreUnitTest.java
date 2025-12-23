/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.s3;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.store.s3.configuration.S3StoreConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.netty.handler.codec.http.HttpRequest;

import jakarta.annotation.Generated;

import java.net.InetSocketAddress;

import java.util.Collections;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.littleshoot.proxy.HttpFilters;
import org.littleshoot.proxy.HttpFiltersSourceAdapter;
import org.littleshoot.proxy.HttpProxyServer;
import org.littleshoot.proxy.HttpProxyServerBootstrap;
import org.littleshoot.proxy.ProxyAuthenticator;
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Kevin Lee
 */
@Generated("")
public class IBMS3StoreUnitTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_ibmS3StoreConfiguration = Mockito.mock(S3StoreConfiguration.class);

		Mockito.when(
			_ibmS3StoreConfiguration.accessKey()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_ibmS3StoreConfiguration.bucketName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_ibmS3StoreConfiguration.connectionTimeout()
		).thenReturn(
			1000
		);

		Mockito.when(
			_ibmS3StoreConfiguration.httpClientMaxConnections()
		).thenReturn(
			1
		);

		Mockito.when(
			_ibmS3StoreConfiguration.maxPoolSize()
		).thenReturn(
			1
		);

		Mockito.when(
			_ibmS3StoreConfiguration.s3Region()
		).thenReturn(
			"us-east-1"
		);

		Mockito.when(
			_ibmS3StoreConfiguration.s3StorageClass()
		).thenReturn(
			"STANDARD"
		);

		Mockito.when(
			_ibmS3StoreConfiguration.secretKey()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_configurableUtilMockedStatic.when(
			() -> ConfigurableUtil.createConfigurable(
				Mockito.eq(S3StoreConfiguration.class), Mockito.anyMap())
		).thenReturn(
			_ibmS3StoreConfiguration
		);
	}

	@After
	public void tearDown() {
		_configurableUtilMockedStatic.close();
	}

	@Test
	public void testProxy() throws Exception {
		_mockProxy(null, null);
		_testProxy(true, null, null);
	}

	@Test
	public void testProxyAuthentication() throws Exception {
		String proxyUserName = RandomTestUtil.randomString();
		String proxyPassword = RandomTestUtil.randomString();

		_mockProxy(proxyUserName, proxyPassword);
		_testProxy(true, proxyUserName, proxyPassword);
	}

	@Test
	public void testProxyAuthenticationFailed() throws Exception {
		String proxyUserName = RandomTestUtil.randomString();
		String proxyPassword = RandomTestUtil.randomString();

		_mockProxy(proxyUserName, proxyPassword);
		_testProxy(false, proxyUserName, proxyPassword + "1");
	}

	private void _mockProxy(String proxyUserName, String proxyPassword) {
		Mockito.when(
			_ibmS3StoreConfiguration.proxyHost()
		).thenReturn(
			_INET_SOCKET_ADDRESS.getHostString()
		);

		Mockito.when(
			_ibmS3StoreConfiguration.proxyPort()
		).thenReturn(
			_INET_SOCKET_ADDRESS.getPort()
		);

		if (Validator.isNotNull(proxyUserName)) {
			Mockito.when(
				_ibmS3StoreConfiguration.proxyAuthType()
			).thenReturn(
				"username-password"
			);

			Mockito.when(
				_ibmS3StoreConfiguration.proxyPassword()
			).thenReturn(
				proxyPassword
			);

			Mockito.when(
				_ibmS3StoreConfiguration.proxyUsername()
			).thenReturn(
				proxyUserName
			);
		}
		else {
			Mockito.when(
				_ibmS3StoreConfiguration.proxyAuthType()
			).thenReturn(
				"none"
			);
		}
	}

	private void _testProxy(
			boolean expectedProxyHit, String proxyUserName,
			String proxyPassword)
		throws Exception {

		AtomicBoolean proxyHit = new AtomicBoolean(false);

		HttpProxyServerBootstrap httpProxyServerBootstrap =
			DefaultHttpProxyServer.bootstrap();

		httpProxyServerBootstrap.withAddress(_INET_SOCKET_ADDRESS);
		httpProxyServerBootstrap.withFiltersSource(
			new HttpFiltersSourceAdapter() {

				@Override
				public HttpFilters filterRequest(HttpRequest httpRequest) {
					proxyHit.set(true);

					return super.filterRequest(httpRequest);
				}

			});

		if (Validator.isNotNull(proxyUserName)) {
			httpProxyServerBootstrap.withProxyAuthenticator(
				new ProxyAuthenticator() {

					@Override
					public boolean authenticate(
						String userName, String password) {

						if (Objects.equals(userName, proxyUserName) &&
							Objects.equals(password, proxyPassword)) {

							return true;
						}

						return false;
					}

					@Override
					public String getRealm() {
						return null;
					}

				});
		}

		HttpProxyServer httpProxyServer = httpProxyServerBootstrap.start();

		try {
			IBMS3Store ibmS3Store = new IBMS3Store();

			ibmS3Store.activate(Collections.emptyMap());

			try {
				ibmS3Store.hasFile(
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomString(), Store.VERSION_DEFAULT);

				Assert.fail("The store should fail to connect");
			}
			catch (SystemException systemException) {
				String message = systemException.getMessage();

				Assert.assertTrue(
					message.contains("Could not connect to proxy") ||
					message.contains("Status Code: 403") ||
					message.contains("Status Code: 407"));

				Assert.assertEquals(expectedProxyHit, proxyHit.get());
			}
			finally {
				ibmS3Store.deactivate();
			}
		}
		finally {
			httpProxyServer.stop();
		}
	}

	private static final InetSocketAddress _INET_SOCKET_ADDRESS =
		new InetSocketAddress("localhost", 4250);

	private final MockedStatic<ConfigurableUtil> _configurableUtilMockedStatic =
		Mockito.mockStatic(ConfigurableUtil.class);
	private S3StoreConfiguration _ibmS3StoreConfiguration;

}
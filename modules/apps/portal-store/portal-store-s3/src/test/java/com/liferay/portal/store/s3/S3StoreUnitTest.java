/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.store.s3;

import com.liferay.document.library.kernel.store.Store;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.store.s3.configuration.S3StoreConfiguration;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import io.netty.handler.codec.http.HttpRequest;

import java.net.InetSocketAddress;

import java.util.Collections;
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
import org.littleshoot.proxy.impl.DefaultHttpProxyServer;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Kevin Lee
 */
public class S3StoreUnitTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_s3StoreConfiguration = Mockito.mock(S3StoreConfiguration.class);

		Mockito.when(
			_s3StoreConfiguration.accessKey()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_s3StoreConfiguration.bucketName()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			_s3StoreConfiguration.connectionTimeout()
		).thenReturn(
			1000
		);

		Mockito.when(
			_s3StoreConfiguration.httpClientMaxConnections()
		).thenReturn(
			1
		);

		Mockito.when(
			_s3StoreConfiguration.maxPoolSize()
		).thenReturn(
			1
		);

		Mockito.when(
			_s3StoreConfiguration.s3Region()
		).thenReturn(
			"us-east-1"
		);

		Mockito.when(
			_s3StoreConfiguration.s3StorageClass()
		).thenReturn(
			"STANDARD"
		);

		Mockito.when(
			_s3StoreConfiguration.secretKey()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_configurableUtilMockedStatic.when(
			() -> ConfigurableUtil.createConfigurable(
				Mockito.eq(S3StoreConfiguration.class), Mockito.anyMap())
		).thenReturn(
			_s3StoreConfiguration
		);
	}

	@After
	public void tearDown() {
		_configurableUtilMockedStatic.close();
	}

	@Test
	public void testProxy() throws Exception {
		_mockProxy();
		_testProxy(true);
	}

	private void _mockProxy() {
		Mockito.when(
			_s3StoreConfiguration.proxyHost()
		).thenReturn(
			_INET_SOCKET_ADDRESS.getHostString()
		);

		Mockito.when(
			_s3StoreConfiguration.proxyPort()
		).thenReturn(
			_INET_SOCKET_ADDRESS.getPort()
		);
	}

	private void _testProxy(boolean expectedProxyHit) throws Exception {
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

		HttpProxyServer httpProxyServer = httpProxyServerBootstrap.start();

		try {
			S3Store s3Store = new S3Store();

			s3Store.activate(Collections.emptyMap());

			try {
				s3Store.getFileSize(
					RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
					RandomTestUtil.randomString(), Store.VERSION_DEFAULT);

				Assert.fail();
			}
			catch (SystemException systemException) {
				String message = systemException.getMessage();

				Assert.assertTrue(message.contains("Status Code: 403"));

				Assert.assertEquals(expectedProxyHit, proxyHit.get());
			}
			finally {
				s3Store.deactivate();
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
	private S3StoreConfiguration _s3StoreConfiguration;

}
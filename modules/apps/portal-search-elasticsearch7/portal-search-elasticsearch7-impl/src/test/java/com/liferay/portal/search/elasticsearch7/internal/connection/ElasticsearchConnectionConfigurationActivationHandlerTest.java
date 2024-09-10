/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.search.elasticsearch7.configuration.ElasticsearchConnectionConfiguration;
import com.liferay.portal.search.elasticsearch7.configuration.RESTClientLoggerLevel;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.HashMap;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Almir Ferreira
 */
public class ElasticsearchConnectionConfigurationActivationHandlerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@AfterClass
	public static void tearDownClass() {
		_configurableUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_elasticsearchConnectionConfigurationActivationHandler =
			new ElasticsearchConnectionConfigurationActivationHandler();
		_elasticsearchConnectionManager =
			_createElasticsearchConnectionManager();

		ReflectionTestUtil.setFieldValue(
			_elasticsearchConnectionConfigurationActivationHandler,
			"elasticsearchConnectionManager", _elasticsearchConnectionManager);

		ReflectionTestUtil.setFieldValue(
			_elasticsearchConnectionConfigurationActivationHandler, "http",
			_http);
		_configurableUtilMockedStatic.when(
			() -> ConfigurableUtil.createConfigurable(
				Mockito.eq(ElasticsearchConnectionConfiguration.class),
				Mockito.anyMap())
		).thenReturn(
			_elasticsearchConnectionConfiguration
		);
	}

	@Test
	public void testApplyProxyConfigurationInElasticsearchConnection() {
		String connectionId = RandomTestUtil.randomString();
		String[] networkHostAddresses = RandomTestUtil.randomStrings(10);
		String proxyHost = RandomTestUtil.randomString();
		String proxyPassword = RandomTestUtil.randomString();
		int proxyPort = RandomTestUtil.randomInt();
		String proxyUserName = RandomTestUtil.randomString();

		Mockito.when(
			_elasticsearchConnectionConfiguration.active()
		).thenReturn(
			true
		);
		Mockito.when(
			_elasticsearchConnectionConfiguration.connectionId()
		).thenReturn(
			connectionId
		);
		Mockito.when(
			_elasticsearchConnectionConfiguration.networkHostAddresses()
		).thenReturn(
			networkHostAddresses
		);
		Mockito.when(
			_elasticsearchConnectionConfiguration.proxyHost()
		).thenReturn(
			proxyHost
		);
		Mockito.when(
			_elasticsearchConnectionConfiguration.proxyPassword()
		).thenReturn(
			proxyPassword
		);
		Mockito.when(
			_elasticsearchConnectionConfiguration.proxyPort()
		).thenReturn(
			proxyPort
		);
		Mockito.when(
			_elasticsearchConnectionConfiguration.proxyUserName()
		).thenReturn(
			proxyUserName
		);

		_elasticsearchConnectionConfigurationActivationHandler.activate(
			new HashMap<>());

		ProxyConfig proxyConfig = ReflectionTestUtil.getFieldValue(
			_elasticsearchConnectionManager.getElasticsearchConnection(
				connectionId),
			"_proxyConfig");

		Assert.assertEquals(proxyHost, proxyConfig.getHost());
		Assert.assertEquals(proxyPassword, proxyConfig.getPassword());
		Assert.assertEquals(proxyPort, proxyConfig.getPort());
		Assert.assertEquals(proxyUserName, proxyConfig.getUserName());
	}

	private ElasticsearchConnectionManager
		_createElasticsearchConnectionManager() {

		ElasticsearchConfigurationWrapper configurationWrapper = Mockito.mock(
			ElasticsearchConfigurationWrapper.class);

		Mockito.when(
			configurationWrapper.restClientLoggerLevel()
		).thenReturn(
			RESTClientLoggerLevel.ERROR
		);

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new ElasticsearchConnectionManager() {
				{
					elasticsearchConfigurationWrapper = configurationWrapper;
					http = _http;
				}
			};

		elasticsearchConnectionManager.activate(
			SystemBundleUtil.getBundleContext());

		return elasticsearchConnectionManager;
	}

	private static final MockedStatic<ConfigurableUtil>
		_configurableUtilMockedStatic = Mockito.mockStatic(
			ConfigurableUtil.class);

	private final ElasticsearchConnectionConfiguration
		_elasticsearchConnectionConfiguration = Mockito.mock(
			ElasticsearchConnectionConfiguration.class);
	private ElasticsearchConnectionConfigurationActivationHandler
		_elasticsearchConnectionConfigurationActivationHandler;
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private final Http _http = Mockito.mock(Http.class);

}
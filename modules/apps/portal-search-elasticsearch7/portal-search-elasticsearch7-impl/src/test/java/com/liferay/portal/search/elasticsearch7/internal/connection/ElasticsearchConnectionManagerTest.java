/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.connection;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.search.elasticsearch7.configuration.RESTClientLoggerLevel;
import com.liferay.portal.search.elasticsearch7.internal.configuration.ElasticsearchConfigurationWrapper;
import com.liferay.portal.search.elasticsearch7.internal.connection.constants.ConnectionConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.elasticsearch.client.RestHighLevelClient;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author André de Oliveira
 */
public class ElasticsearchConnectionManagerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_frameworkUtilMockedStatic.when(
			() -> FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);
	}

	@AfterClass
	public static void tearDownClass() {
		_frameworkUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_resetAndSetUpMocks();

		_elasticsearchConnectionManager = _createElasticsearchConnectionManager(
			_sidecarElasticsearchConnection, _remoteElasticsearchConnection1,
			_remoteElasticsearchConnection2, _remoteElasticsearchConnection3);
	}

	@Test
	public void testActivateRemoteModeDisabled() {
		ElasticsearchConnectionManager elasticsearchConnectionManager =
			Mockito.spy(_elasticsearchConnectionManager);

		elasticsearchConnectionManager.activate(
			SystemBundleUtil.getBundleContext());

		Mockito.verify(
			elasticsearchConnectionManager, Mockito.never()
		).addElasticsearchConnection(
			Mockito.any()
		);

		Mockito.verify(
			elasticsearchConnectionManager
		).removeElasticsearchConnection(
			Mockito.any()
		);
	}

	@Test
	public void testActivateRemoteModeEnabledWithConnectionId() {
		Mockito.when(
			_elasticsearchConfigurationWrapper.isProductionModeEnabled()
		).thenReturn(
			true
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.remoteClusterConnectionId()
		).thenReturn(
			"test"
		);

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			Mockito.spy(_elasticsearchConnectionManager);

		elasticsearchConnectionManager.activate(
			SystemBundleUtil.getBundleContext());

		Mockito.verify(
			elasticsearchConnectionManager, Mockito.never()
		).addElasticsearchConnection(
			Mockito.any()
		);

		Mockito.verify(
			elasticsearchConnectionManager, Mockito.never()
		).removeElasticsearchConnection(
			Mockito.any()
		);
	}

	@Test
	public void testActivateRemoteModeEnabledWithoutConnectionId() {
		Mockito.when(
			_elasticsearchConfigurationWrapper.isProductionModeEnabled()
		).thenReturn(
			true
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.remoteClusterConnectionId()
		).thenReturn(
			null
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.networkHostAddresses()
		).thenReturn(
			new String[] {"http://localhost:9200"}
		);

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			Mockito.spy(_elasticsearchConnectionManager);

		elasticsearchConnectionManager.activate(
			SystemBundleUtil.getBundleContext());

		Mockito.verify(
			elasticsearchConnectionManager
		).addElasticsearchConnection(
			Mockito.any()
		);

		Mockito.verify(
			elasticsearchConnectionManager, Mockito.never()
		).removeElasticsearchConnection(
			Mockito.any()
		);
	}

	@Test
	public void testAddConnectionNoConnectionIdAndIsActive() {
		ElasticsearchConnection elasticsearchConnection = Mockito.mock(
			ElasticsearchConnection.class);

		Mockito.when(
			elasticsearchConnection.getConnectionId()
		).thenReturn(
			null
		);

		Mockito.when(
			elasticsearchConnection.isActive()
		).thenReturn(
			true
		);

		_elasticsearchConnectionManager.addElasticsearchConnection(
			elasticsearchConnection);

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).isActive();

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).connect();
	}

	@Test
	public void testAddConnectionNoConnectionIdAndIsNotActive() {
		ElasticsearchConnection elasticsearchConnection = Mockito.mock(
			ElasticsearchConnection.class);

		Mockito.when(
			elasticsearchConnection.getConnectionId()
		).thenReturn(
			null
		);

		Mockito.when(
			elasticsearchConnection.isActive()
		).thenReturn(
			false
		);

		_elasticsearchConnectionManager.addElasticsearchConnection(
			elasticsearchConnection);

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).isActive();

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).connect();
	}

	@Test
	public void testAddConnectionWithConnectionIdAndIsActive() {
		ElasticsearchConnection elasticsearchConnection = Mockito.mock(
			ElasticsearchConnection.class);

		Mockito.when(
			elasticsearchConnection.getConnectionId()
		).thenReturn(
			"test"
		);

		Mockito.when(
			elasticsearchConnection.isActive()
		).thenReturn(
			true
		);

		_elasticsearchConnectionManager.addElasticsearchConnection(
			elasticsearchConnection);

		_elasticsearchConnectionManager.getElasticsearchConnection(
			elasticsearchConnection.getConnectionId());

		Mockito.verify(
			elasticsearchConnection
		).isActive();

		Mockito.verify(
			elasticsearchConnection
		).connect();
	}

	@Test
	public void testAddConnectionWithConnectionIdAndIsNotActive() {
		ElasticsearchConnection elasticsearchConnection = Mockito.mock(
			ElasticsearchConnection.class);

		Mockito.when(
			elasticsearchConnection.getConnectionId()
		).thenReturn(
			"test"
		);

		Mockito.when(
			elasticsearchConnection.isActive()
		).thenReturn(
			false
		);

		_elasticsearchConnectionManager.addElasticsearchConnection(
			elasticsearchConnection);

		Mockito.verify(
			elasticsearchConnection
		).isActive();

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).connect();
	}

	@Test
	public void testApplyProxyConfigurationInElasticsearchConnection() {
		_enableRemoteMode();

		String[] networkHostAddresses = RandomTestUtil.randomStrings(10);

		Mockito.when(
			_elasticsearchConfigurationWrapper.networkHostAddresses()
		).thenReturn(
			networkHostAddresses
		);

		String proxyHost = RandomTestUtil.randomString();

		Mockito.when(
			_elasticsearchConfigurationWrapper.proxyHost()
		).thenReturn(
			proxyHost
		);

		String proxyPassword = RandomTestUtil.randomString();

		Mockito.when(
			_elasticsearchConfigurationWrapper.proxyPassword()
		).thenReturn(
			proxyPassword
		);

		int proxyPort = RandomTestUtil.randomInt();

		Mockito.when(
			_elasticsearchConfigurationWrapper.proxyPort()
		).thenReturn(
			proxyPort
		);

		String proxyUserName = RandomTestUtil.randomString();

		Mockito.when(
			_elasticsearchConfigurationWrapper.proxyUserName()
		).thenReturn(
			proxyUserName
		);

		_elasticsearchConnectionManager.applyConfigurations();

		ProxyConfig proxyConfig = ReflectionTestUtil.getFieldValue(
			_elasticsearchConnectionManager.getElasticsearchConnection(),
			"_proxyConfig");

		Assert.assertEquals(proxyHost, proxyConfig.getHost());
		Assert.assertEquals(proxyPassword, proxyConfig.getPassword());
		Assert.assertEquals(proxyPort, proxyConfig.getPort());
		Assert.assertEquals(proxyUserName, proxyConfig.getUserName());
	}

	@Test
	public void testGetElasticsearchConnectionWithRemoteModeDisabled() {
		Assert.assertEquals(
			_sidecarElasticsearchConnection,
			_elasticsearchConnectionManager.getElasticsearchConnection());
	}

	@Test
	public void testGetElasticsearchConnectionWithRemoteModeEnabled() {
		_enableRemoteMode();

		Assert.assertEquals(
			_defaultRemoteElasticsearchConnection,
			_elasticsearchConnectionManager.getElasticsearchConnection());
	}

	@Test
	public void testGetElasticsearchConnectionWithRemoteModeEnabledAndConnectionId() {
		_enableRemoteMode();
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection1,
			_elasticsearchConnectionManager.getElasticsearchConnection());

		_setRemoteConnectionId(_REMOTE_2_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection2,
			_elasticsearchConnectionManager.getElasticsearchConnection());
	}

	@Test
	public void testGetExplicitElasticsearchConnectionWhenConnectionDoesNotExist() {
		Assert.assertEquals(
			null,
			_elasticsearchConnectionManager.getElasticsearchConnection("none"));
	}

	@Test
	public void testGetExplicitElasticsearchConnectionWhenConnectionIdNull() {
		try {
			_elasticsearchConnectionManager.getElasticsearchConnection(null);

			Assert.fail();
		}
		catch (NullPointerException nullPointerException) {
		}
	}

	@Test
	public void testGetExplicitElasticsearchConnectionWithDifferentConnectionId() {
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection2,
			_elasticsearchConnectionManager.getElasticsearchConnection(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitElasticsearchConnectionWithRemoteModeDisabled() {
		Assert.assertEquals(
			_remoteElasticsearchConnection1,
			_elasticsearchConnectionManager.getElasticsearchConnection(
				_REMOTE_1_CONNECTION_ID));

		Assert.assertEquals(
			_remoteElasticsearchConnection2,
			_elasticsearchConnectionManager.getElasticsearchConnection(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitElasticsearchConnectionWithRemoteModeEnabled() {
		_enableRemoteMode();

		Assert.assertEquals(
			_remoteElasticsearchConnection1,
			_elasticsearchConnectionManager.getElasticsearchConnection(
				_REMOTE_1_CONNECTION_ID));

		Assert.assertEquals(
			_remoteElasticsearchConnection2,
			_elasticsearchConnectionManager.getElasticsearchConnection(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitElasticsearchConnectionWithRemoteModeEnabledAndDifferentConnectionId() {
		_enableRemoteMode();
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection2,
			_elasticsearchConnectionManager.getElasticsearchConnection(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitRestHighLevelClientWhenRestClientNull() {
		try {
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_3_CONNECTION_ID);

			Assert.fail();
		}
		catch (ElasticsearchConnectionNotInitializedException
					elasticsearchConnectionNotInitializedException) {

			String message =
				elasticsearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(
				message.contains("REST high level client not found"));
		}
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeDisabled() {
		Assert.assertEquals(
			_remoteElasticsearchConnection1.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_1_CONNECTION_ID));

		Assert.assertEquals(
			_remoteElasticsearchConnection2.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeDisabledAndConnectionDoesNotExist() {
		try {
			_elasticsearchConnectionManager.getRestHighLevelClient("none");

			Assert.fail();
		}
		catch (ElasticsearchConnectionNotInitializedException
					elasticsearchConnectionNotInitializedException) {

			String message =
				elasticsearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(
				message.contains("Elasticsearch connection not found"));
		}
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeDisabledAndDifferentConnectionId() {
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection2.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeDisabledAndIdNull() {
		Assert.assertEquals(
			_sidecarElasticsearchConnection.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(null));
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeEnabled() {
		_enableRemoteMode();

		Assert.assertEquals(
			_remoteElasticsearchConnection1.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_1_CONNECTION_ID));

		Assert.assertEquals(
			_remoteElasticsearchConnection2.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeEnabledAndConnectionDoesNotExist() {
		_enableRemoteMode();

		try {
			_elasticsearchConnectionManager.getRestHighLevelClient("none");

			Assert.fail();
		}
		catch (ElasticsearchConnectionNotInitializedException
					elasticsearchConnectionNotInitializedException) {

			String message =
				elasticsearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(
				message.contains("Elasticsearch connection not found"));
		}
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeEnabledAndDifferentConnectionId() {
		_enableRemoteMode();
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection2.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitRestHighLevelClientWithRemoteModeEnabledAndIdNull() {
		_enableRemoteMode();

		Assert.assertEquals(
			_defaultRemoteElasticsearchConnection.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient(null));
	}

	@Test
	public void testGetRestHighLevelClientWithRemoteModeDisabled() {
		Assert.assertEquals(
			_sidecarElasticsearchConnection.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());
	}

	@Test
	public void testGetRestHighLevelClientWithRemoteModeDisabledAndConnectionId() {
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_sidecarElasticsearchConnection.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());

		_setRemoteConnectionId(_REMOTE_2_CONNECTION_ID);

		Assert.assertEquals(
			_sidecarElasticsearchConnection.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());
	}

	@Test
	public void testGetRestHighLevelClientWithRemoteModeEnabled() {
		_enableRemoteMode();

		Assert.assertEquals(
			_defaultRemoteElasticsearchConnection.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());
	}

	@Test
	public void testGetRestHighLevelClientWithRemoteModeEnabledAndConnectionId() {
		_enableRemoteMode();
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection1.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());

		_setRemoteConnectionId(_REMOTE_2_CONNECTION_ID);

		Assert.assertEquals(
			_remoteElasticsearchConnection2.getRestHighLevelClient(),
			_elasticsearchConnectionManager.getRestHighLevelClient());
	}

	@Test
	public void testRemoveConnectionThatDoesNotExistWithConnectionId() {
		ElasticsearchConnection elasticsearchConnection = Mockito.mock(
			ElasticsearchConnection.class);

		Mockito.when(
			elasticsearchConnection.getConnectionId()
		).thenReturn(
			"test"
		);

		_elasticsearchConnectionManager.removeElasticsearchConnection(
			elasticsearchConnection.getConnectionId());

		Mockito.verify(
			elasticsearchConnection, Mockito.never()
		).close();
	}

	@Test
	public void testRemoveConnectionThatExistsWithConnectionId() {
		_elasticsearchConnectionManager.removeElasticsearchConnection(
			_remoteElasticsearchConnection1.getConnectionId());

		Mockito.verify(
			_remoteElasticsearchConnection1
		).close();
	}

	@Test
	public void testRemoveConnectionWithNullConnectionId() {
		_elasticsearchConnectionManager.removeElasticsearchConnection(null);
	}

	private ElasticsearchConnectionManager
		_createElasticsearchConnectionManager(
			ElasticsearchConnection remoteElasticsearchConnection1,
			ElasticsearchConnection remoteElasticsearchConnection2,
			ElasticsearchConnection remoteElasticsearchConnection3,
			ElasticsearchConnection sidecarElasticsearchConnection) {

		ElasticsearchConnectionManager elasticsearchConnectionManager =
			new ElasticsearchConnectionManager() {
				{
					elasticsearchConfigurationWrapper =
						_elasticsearchConfigurationWrapper;
					http = _http;
				}
			};

		elasticsearchConnectionManager.addElasticsearchConnection(
			remoteElasticsearchConnection1);
		elasticsearchConnectionManager.addElasticsearchConnection(
			remoteElasticsearchConnection2);
		elasticsearchConnectionManager.addElasticsearchConnection(
			remoteElasticsearchConnection3);
		elasticsearchConnectionManager.addElasticsearchConnection(
			sidecarElasticsearchConnection);

		elasticsearchConnectionManager.activate(
			SystemBundleUtil.getBundleContext());

		return elasticsearchConnectionManager;
	}

	private void _enableRemoteMode() {
		Mockito.when(
			_elasticsearchConfigurationWrapper.isProductionModeEnabled()
		).thenReturn(
			true
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.isDevelopmentModeEnabled()
		).thenReturn(
			false
		);

		_elasticsearchConnectionManager.addElasticsearchConnection(
			_defaultRemoteElasticsearchConnection);
	}

	private void _resetAndSetUpMocks() {
		Mockito.reset(
			_defaultRemoteElasticsearchConnection,
			_elasticsearchConfigurationWrapper, _remoteElasticsearchConnection1,
			_remoteElasticsearchConnection2, _remoteElasticsearchConnection3,
			_sidecarElasticsearchConnection);

		_setUpDefaultConnection();
		_setUpElasticsearchConfigurationWrapper();
		_setUpOperationModeResolver();
		_setUpRemoteConnection1();
		_setUpRemoteConnection2();
		_setUpRemoteConnection3();
		_setUpSidecarConnection();
	}

	private void _setRemoteConnectionId(String connectionId) {
		Mockito.when(
			_elasticsearchConfigurationWrapper.remoteClusterConnectionId()
		).thenReturn(
			connectionId
		);
	}

	private void _setUpDefaultConnection() {
		Mockito.when(
			_defaultRemoteElasticsearchConnection.getConnectionId()
		).thenReturn(
			ConnectionConstants.REMOTE_CONNECTION_ID
		);

		Mockito.when(
			_defaultRemoteElasticsearchConnection.getRestHighLevelClient()
		).thenReturn(
			Mockito.mock(RestHighLevelClient.class)
		);

		Mockito.when(
			_defaultRemoteElasticsearchConnection.isActive()
		).thenReturn(
			true
		);
	}

	private void _setUpElasticsearchConfigurationWrapper() {
		Mockito.when(
			_elasticsearchConfigurationWrapper.restClientLoggerLevel()
		).thenReturn(
			RESTClientLoggerLevel.ERROR
		);
	}

	private void _setUpOperationModeResolver() {
		Mockito.when(
			_elasticsearchConfigurationWrapper.isProductionModeEnabled()
		).thenReturn(
			false
		);

		Mockito.when(
			_elasticsearchConfigurationWrapper.isDevelopmentModeEnabled()
		).thenReturn(
			true
		);
	}

	private void _setUpRemoteConnection1() {
		Mockito.when(
			_remoteElasticsearchConnection1.getConnectionId()
		).thenReturn(
			_REMOTE_1_CONNECTION_ID
		);

		Mockito.when(
			_remoteElasticsearchConnection1.getRestHighLevelClient()
		).thenReturn(
			Mockito.mock(RestHighLevelClient.class)
		);

		Mockito.when(
			_remoteElasticsearchConnection1.isActive()
		).thenReturn(
			true
		);
	}

	private void _setUpRemoteConnection2() {
		Mockito.when(
			_remoteElasticsearchConnection2.getConnectionId()
		).thenReturn(
			_REMOTE_2_CONNECTION_ID
		);

		Mockito.when(
			_remoteElasticsearchConnection2.getRestHighLevelClient()
		).thenReturn(
			Mockito.mock(RestHighLevelClient.class)
		);

		Mockito.when(
			_remoteElasticsearchConnection2.isActive()
		).thenReturn(
			true
		);
	}

	private void _setUpRemoteConnection3() {
		Mockito.when(
			_remoteElasticsearchConnection3.getConnectionId()
		).thenReturn(
			_REMOTE_3_CONNECTION_ID
		);

		Mockito.when(
			_remoteElasticsearchConnection3.getRestHighLevelClient()
		).thenReturn(
			null
		);

		Mockito.when(
			_remoteElasticsearchConnection3.isActive()
		).thenReturn(
			false
		);
	}

	private void _setUpSidecarConnection() {
		Mockito.when(
			_sidecarElasticsearchConnection.getConnectionId()
		).thenReturn(
			ConnectionConstants.SIDECAR_CONNECTION_ID
		);

		Mockito.when(
			_sidecarElasticsearchConnection.getRestHighLevelClient()
		).thenReturn(
			Mockito.mock(RestHighLevelClient.class)
		);

		Mockito.when(
			_sidecarElasticsearchConnection.isActive()
		).thenReturn(
			true
		);
	}

	private static final String _REMOTE_1_CONNECTION_ID = "remote 1";

	private static final String _REMOTE_2_CONNECTION_ID = "remote 2";

	private static final String _REMOTE_3_CONNECTION_ID = "remote 3";

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

	private final ElasticsearchConnection
		_defaultRemoteElasticsearchConnection = Mockito.mock(
			ElasticsearchConnection.class);
	private final ElasticsearchConfigurationWrapper
		_elasticsearchConfigurationWrapper = Mockito.mock(
			ElasticsearchConfigurationWrapper.class);
	private ElasticsearchConnectionManager _elasticsearchConnectionManager;
	private final Http _http = Mockito.mock(Http.class);
	private final ElasticsearchConnection _remoteElasticsearchConnection1 =
		Mockito.mock(ElasticsearchConnection.class);
	private final ElasticsearchConnection _remoteElasticsearchConnection2 =
		Mockito.mock(ElasticsearchConnection.class);
	private final ElasticsearchConnection _remoteElasticsearchConnection3 =
		Mockito.mock(ElasticsearchConnection.class);
	private final ElasticsearchConnection _sidecarElasticsearchConnection =
		Mockito.mock(ElasticsearchConnection.class);

}
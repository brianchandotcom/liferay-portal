/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection;

import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapper;
import com.liferay.portal.search.opensearch2.internal.configuration.OpenSearchConfigurationWrapperImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.hamcrest.CoreMatchers;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.opensearch.client.opensearch.OpenSearchClient;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class OpenSearchConnectionManagerTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

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

		_openSearchConnectionManagerImpl = _createOpenSearchConnectionManager(
			_remoteOpenSearchConnection1, _remoteOpenSearchConnection2,
			_remoteOpenSearchConnection3);
	}

	@Test
	public void testActivateWithConnectionId() {
		Mockito.when(
			_openSearchConfigurationWrapper.remoteClusterConnectionId()
		).thenReturn(
			"test"
		);

		OpenSearchConnectionManagerImpl openSearchConnectionManagerImpl =
			Mockito.spy(_openSearchConnectionManagerImpl);

		openSearchConnectionManagerImpl.activate();

		Mockito.verify(
			openSearchConnectionManagerImpl, Mockito.never()
		).addOpenSearchConnection(
			Mockito.any()
		);

		Mockito.verify(
			openSearchConnectionManagerImpl, Mockito.never()
		).removeOpenSearchConnection(
			Mockito.any()
		);
	}

	@Test
	public void testAddConnectionNoConnectionIdAndIsActive() {
		OpenSearchConnection openSearchConnection = Mockito.mock(
			OpenSearchConnection.class);

		Mockito.when(
			openSearchConnection.getConnectionId()
		).thenReturn(
			null
		);

		Mockito.when(
			openSearchConnection.isActive()
		).thenReturn(
			true
		);

		_openSearchConnectionManagerImpl.addOpenSearchConnection(
			openSearchConnection);

		Mockito.verify(
			openSearchConnection, Mockito.never()
		).isActive();

		Mockito.verify(
			openSearchConnection, Mockito.never()
		).connect();
	}

	@Test
	public void testAddConnectionNoConnectionIdAndIsNotActive() {
		OpenSearchConnection openSearchConnection = Mockito.mock(
			OpenSearchConnection.class);

		Mockito.when(
			openSearchConnection.getConnectionId()
		).thenReturn(
			null
		);

		Mockito.when(
			openSearchConnection.isActive()
		).thenReturn(
			false
		);

		_openSearchConnectionManagerImpl.addOpenSearchConnection(
			openSearchConnection);

		Mockito.verify(
			openSearchConnection, Mockito.never()
		).isActive();

		Mockito.verify(
			openSearchConnection, Mockito.never()
		).connect();
	}

	@Test
	public void testAddConnectionWithConnectionIdAndIsActive() {
		OpenSearchConnection openSearchConnection = Mockito.mock(
			OpenSearchConnection.class);

		Mockito.when(
			openSearchConnection.getConnectionId()
		).thenReturn(
			"test"
		);

		Mockito.when(
			openSearchConnection.isActive()
		).thenReturn(
			true
		);

		_openSearchConnectionManagerImpl.addOpenSearchConnection(
			openSearchConnection);

		Mockito.verify(
			openSearchConnection
		).isActive();

		Mockito.verify(
			openSearchConnection
		).connect();
	}

	@Test
	public void testAddConnectionWithConnectionIdAndIsNotActive() {
		OpenSearchConnection openSearchConnection = Mockito.mock(
			OpenSearchConnection.class);

		Mockito.when(
			openSearchConnection.getConnectionId()
		).thenReturn(
			"test"
		);

		Mockito.when(
			openSearchConnection.isActive()
		).thenReturn(
			false
		);

		_openSearchConnectionManagerImpl.addOpenSearchConnection(
			openSearchConnection);

		Mockito.verify(
			openSearchConnection
		).isActive();

		Mockito.verify(
			openSearchConnection, Mockito.never()
		).connect();
	}

	@Test
	public void testGetExplicitOpenSearchClient() {
		Assert.assertEquals(
			_remoteOpenSearchConnection1.getOpenSearchClient(),
			_openSearchConnectionManagerImpl.getOpenSearchClient(
				_REMOTE_1_CONNECTION_ID));

		Assert.assertEquals(
			_remoteOpenSearchConnection2.getOpenSearchClient(),
			_openSearchConnectionManagerImpl.getOpenSearchClient(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitOpenSearchClientWhenRestClientNull() {
		try {
			_openSearchConnectionManagerImpl.getOpenSearchClient(
				_REMOTE_3_CONNECTION_ID);

			Assert.fail();
		}
		catch (OpenSearchConnectionNotInitializedException
					openSearchConnectionNotInitializedException) {

			String message =
				openSearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(message.contains("OpenSearch client not found"));
		}
	}

	@Test
	public void testGetExplicitOpenSearchClientWithConnectionDoesNotExist() {
		try {
			_openSearchConnectionManagerImpl.getOpenSearchClient("none");

			Assert.fail();
		}
		catch (OpenSearchConnectionNotInitializedException
					openSearchConnectionNotInitializedException) {

			String message =
				openSearchConnectionNotInitializedException.getMessage();

			Assert.assertTrue(
				message.contains("OpenSearch connection not found"));
		}
	}

	@Test
	public void testGetExplicitOpenSearchClientWithDifferentConnectionId() {
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteOpenSearchConnection2.getOpenSearchClient(),
			_openSearchConnectionManagerImpl.getOpenSearchClient(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitOpenSearchConnection() {
		Assert.assertEquals(
			_remoteOpenSearchConnection1,
			_openSearchConnectionManagerImpl.getOpenSearchConnection(
				_REMOTE_1_CONNECTION_ID));

		Assert.assertEquals(
			_remoteOpenSearchConnection2,
			_openSearchConnectionManagerImpl.getOpenSearchConnection(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetExplicitOpenSearchConnectionWhenConnectionDoesNotExist() {
		expectedException.expect(
			OpenSearchConnectionNotInitializedException.class);
		expectedException.expectMessage(
			CoreMatchers.containsString("OpenSearch connection not found"));

		_openSearchConnectionManagerImpl.getOpenSearchConnection("none");
	}

	@Test
	public void testGetExplicitOpenSearchConnectionWhenConnectionIdNull() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			CoreMatchers.containsString(
				"OpenSearch connection not configured"));

		_openSearchConnectionManagerImpl.getOpenSearchConnection(null);
	}

	@Test
	public void testGetExplicitOpenSearchConnectionWithDifferentConnectionId() {
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteOpenSearchConnection2,
			_openSearchConnectionManagerImpl.getOpenSearchConnection(
				_REMOTE_2_CONNECTION_ID));
	}

	@Test
	public void testGetOpenSearchClientWithConnectionId() {
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteOpenSearchConnection1.getOpenSearchClient(),
			_openSearchConnectionManagerImpl.getOpenSearchClient());

		_setRemoteConnectionId(_REMOTE_2_CONNECTION_ID);

		Assert.assertEquals(
			_remoteOpenSearchConnection2.getOpenSearchClient(),
			_openSearchConnectionManagerImpl.getOpenSearchClient());
	}

	@Test
	public void testGetOpenSearchClientWithoutConnectionId() {
		expectedException.expect(RuntimeException.class);
		expectedException.expectMessage(
			CoreMatchers.containsString(
				"OpenSearch connection not configured"));

		_openSearchConnectionManagerImpl.getOpenSearchClient();
	}

	@Test
	public void testGetOpenSearchConnectionWithConnectionId() {
		_setRemoteConnectionId(_REMOTE_1_CONNECTION_ID);

		Assert.assertEquals(
			_remoteOpenSearchConnection1,
			_openSearchConnectionManagerImpl.getOpenSearchConnection());

		_setRemoteConnectionId(_REMOTE_2_CONNECTION_ID);

		Assert.assertEquals(
			_remoteOpenSearchConnection2,
			_openSearchConnectionManagerImpl.getOpenSearchConnection());
	}

	@Test
	public void testRemoveConnectionThatDoesNotExistWithConnectionId() {
		OpenSearchConnection openSearchConnection = Mockito.mock(
			OpenSearchConnection.class);

		Mockito.when(
			openSearchConnection.getConnectionId()
		).thenReturn(
			"test"
		);

		_openSearchConnectionManagerImpl.removeOpenSearchConnection(
			openSearchConnection.getConnectionId());

		Mockito.verify(
			openSearchConnection, Mockito.never()
		).close();
	}

	@Test
	public void testRemoveConnectionThatExistsWithConnectionId() {
		_openSearchConnectionManagerImpl.removeOpenSearchConnection(
			_remoteOpenSearchConnection1.getConnectionId());

		Mockito.verify(
			_remoteOpenSearchConnection1
		).close();
	}

	@Test
	public void testRemoveConnectionWithNullConnectionId() {
		_openSearchConnectionManagerImpl.removeOpenSearchConnection(null);
	}

	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	private OpenSearchConnectionManagerImpl _createOpenSearchConnectionManager(
		OpenSearchConnection remoteOpenSearchConnection1,
		OpenSearchConnection remoteOpenSearchConnection2,
		OpenSearchConnection remoteOpenSearchConnection3) {

		OpenSearchConnectionManagerImpl openSearchConnectionManagerImpl =
			new OpenSearchConnectionManagerImpl() {
				{
					openSearchConfigurationWrapper =
						_openSearchConfigurationWrapper;

					openSearchConnectionsHolder =
						new OpenSearchConnectionsHolderImpl();
				}
			};

		openSearchConnectionManagerImpl.addOpenSearchConnection(
			remoteOpenSearchConnection1);
		openSearchConnectionManagerImpl.addOpenSearchConnection(
			remoteOpenSearchConnection2);
		openSearchConnectionManagerImpl.addOpenSearchConnection(
			remoteOpenSearchConnection3);

		openSearchConnectionManagerImpl.activate();

		return openSearchConnectionManagerImpl;
	}

	private void _resetAndSetUpMocks() {
		Mockito.reset(
			_defaultOpenSearchConnection, _openSearchConfigurationWrapper,
			_remoteOpenSearchConnection1, _remoteOpenSearchConnection2,
			_remoteOpenSearchConnection3);

		_setUpRemoteConnection1();
		_setUpRemoteConnection2();
		_setUpRemoteConnection3();
	}

	private void _setRemoteConnectionId(String connectionId) {
		Mockito.when(
			_openSearchConfigurationWrapper.remoteClusterConnectionId()
		).thenReturn(
			connectionId
		);
	}

	private void _setUpRemoteConnection1() {
		Mockito.when(
			_remoteOpenSearchConnection1.getConnectionId()
		).thenReturn(
			_REMOTE_1_CONNECTION_ID
		);

		Mockito.when(
			_remoteOpenSearchConnection1.getOpenSearchClient()
		).thenReturn(
			Mockito.mock(OpenSearchClient.class)
		);

		Mockito.when(
			_remoteOpenSearchConnection1.isActive()
		).thenReturn(
			true
		);
	}

	private void _setUpRemoteConnection2() {
		Mockito.when(
			_remoteOpenSearchConnection2.getConnectionId()
		).thenReturn(
			_REMOTE_2_CONNECTION_ID
		);

		Mockito.when(
			_remoteOpenSearchConnection2.getOpenSearchClient()
		).thenReturn(
			Mockito.mock(OpenSearchClient.class)
		);

		Mockito.when(
			_remoteOpenSearchConnection2.isActive()
		).thenReturn(
			true
		);
	}

	private void _setUpRemoteConnection3() {
		Mockito.when(
			_remoteOpenSearchConnection3.getConnectionId()
		).thenReturn(
			_REMOTE_3_CONNECTION_ID
		);

		Mockito.when(
			_remoteOpenSearchConnection3.getOpenSearchClient()
		).thenReturn(
			null
		);

		Mockito.when(
			_remoteOpenSearchConnection3.isActive()
		).thenReturn(
			false
		);
	}

	private static final String _REMOTE_1_CONNECTION_ID = "remote 1";

	private static final String _REMOTE_2_CONNECTION_ID = "remote 2";

	private static final String _REMOTE_3_CONNECTION_ID = "remote 3";

	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

	private final OpenSearchConnection _defaultOpenSearchConnection =
		Mockito.mock(OpenSearchConnection.class);
	private final OpenSearchConfigurationWrapper
		_openSearchConfigurationWrapper = Mockito.mock(
			OpenSearchConfigurationWrapperImpl.class);
	private OpenSearchConnectionManagerImpl _openSearchConnectionManagerImpl;
	private final OpenSearchConnection _remoteOpenSearchConnection1 =
		Mockito.mock(OpenSearchConnection.class);
	private final OpenSearchConnection _remoteOpenSearchConnection2 =
		Mockito.mock(OpenSearchConnection.class);
	private final OpenSearchConnection _remoteOpenSearchConnection3 =
		Mockito.mock(OpenSearchConnection.class);

}
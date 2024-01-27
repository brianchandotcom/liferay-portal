/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.connection;

import com.liferay.portal.search.opensearch2.internal.OpenSearchTestRule;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.function.Consumer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 * @author Petteri Karttunen
 */
public class OpenSearchConnectionTest {

	@ClassRule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@ClassRule
	public static final OpenSearchTestRule openSearchTestRule =
		OpenSearchTestRule.INSTANCE;

	@Before
	public void setUp() {
		OpenSearchConnection.Builder openSearchConnectionBuilder =
			new OpenSearchConnection.Builder();

		openSearchConnectionBuilder.networkHostAddresses(
			new String[] {"http://localhost:9200"});

		_postCloseRunnable = Mockito.mock(Runnable.class);

		openSearchConnectionBuilder.postCloseRunnable(_postCloseRunnable);

		_preConnectOpenSearchConnectionConsumer = Mockito.mock(Consumer.class);

		openSearchConnectionBuilder.preConnectOpenSearchConnectionConsumer(
			_preConnectOpenSearchConnectionConsumer);

		_openSearchConnection = openSearchConnectionBuilder.build();
	}

	@Test
	public void testConnectAndClose() throws Exception {
		Assert.assertFalse(_openSearchConnection.isConnected());

		_openSearchConnection.connect();

		Assert.assertTrue(_openSearchConnection.isConnected());

		Mockito.verify(
			_preConnectOpenSearchConnectionConsumer
		).accept(
			Mockito.any()
		);

		_openSearchConnection.close();

		Assert.assertFalse(_openSearchConnection.isConnected());

		Mockito.verify(
			_postCloseRunnable
		).run();
	}

	private OpenSearchConnection _openSearchConnection;
	private Runnable _postCloseRunnable;
	private Consumer<OpenSearchConnection>
		_preConnectOpenSearchConnectionConsumer;

}
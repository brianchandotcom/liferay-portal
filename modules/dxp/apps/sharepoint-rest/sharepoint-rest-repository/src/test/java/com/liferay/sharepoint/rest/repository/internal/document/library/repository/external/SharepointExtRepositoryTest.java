/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.document.library.repository.external;

import com.liferay.document.library.repository.authorization.oauth2.Token;
import com.liferay.document.library.repository.authorization.oauth2.TokenStore;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvoker;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.sharepoint.rest.repository.internal.configuration.SharepointRepositoryConfiguration;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryAuthenticationResult;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryToken;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.SharepointRepositoryTokenBroker;
import com.liferay.sharepoint.rest.repository.internal.document.library.repository.authorization.oauth2.util.SharepointRepositoryTokenBrokerFactoryUtil;

import java.util.concurrent.Callable;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Manuel Rives
 */
public class SharepointExtRepositoryTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		TransactionInvokerUtil transactionInvokerUtil =
			new TransactionInvokerUtil();

		transactionInvokerUtil.setTransactionInvoker(
			new TransactionInvoker() {

				@Override
				public <T> T invoke(
						TransactionConfig transactionConfig,
						Callable<T> callable)
					throws Throwable {

					return callable.call();
				}

			});
	}

	@Before
	public void setUp() {
		PrincipalThreadLocal.setName(_USER_ID);

		_sharepointRepositoryTokenBrokerFactoryUtilMockedStatic =
			Mockito.mockStatic(
				SharepointRepositoryTokenBrokerFactoryUtil.class);
	}

	@After
	public void tearDown() {
		_sharepointRepositoryTokenBrokerFactoryUtilMockedStatic.close();
	}

	@Test
	public void testGetAccessTokenDoesNotSaveWhenTokenUnchanged()
		throws Exception {

		String accessToken = RandomTestUtil.randomString();

		Token brokerToken = SharepointRepositoryToken.newInstance(accessToken);
		Token cachedToken = SharepointRepositoryToken.newInstance(accessToken);

		TokenStore tokenStore = Mockito.mock(TokenStore.class);

		Mockito.when(
			tokenStore.get(_CONFIGURATION_NAME, _USER_ID)
		).thenReturn(
			cachedToken
		);

		SharepointRepositoryConfiguration sharepointRepositoryConfiguration =
			_mockSharepointRepositoryConfiguration();

		_mockBroker(
			brokerToken, cachedToken, sharepointRepositoryConfiguration);

		SharepointExtRepository sharepointExtRepository =
			new SharepointExtRepository(
				tokenStore, sharepointRepositoryConfiguration);

		String result = ReflectionTestUtil.invoke(
			sharepointExtRepository, "_getAccessToken", new Class<?>[0]);

		Assert.assertEquals(_RETURNED_ACCESS_TOKEN, result);

		Mockito.verify(
			tokenStore, Mockito.never()
		).save(
			Mockito.anyString(), Mockito.anyLong(), Mockito.any(Token.class)
		);
	}

	@Test
	public void testGetAccessTokenSavesWhenStoredTokenIsMissing()
		throws Exception {

		Token brokerToken = SharepointRepositoryToken.newInstance(
			RandomTestUtil.randomString());
		Token cachedToken = SharepointRepositoryToken.newInstance(
			RandomTestUtil.randomString());

		TokenStore tokenStore = Mockito.mock(TokenStore.class);

		Mockito.when(
			tokenStore.get(_CONFIGURATION_NAME, _USER_ID)
		).thenReturn(
			cachedToken, (Token)null
		);

		SharepointRepositoryConfiguration sharepointRepositoryConfiguration =
			_mockSharepointRepositoryConfiguration();

		_mockBroker(
			brokerToken, cachedToken, sharepointRepositoryConfiguration);

		SharepointExtRepository sharepointExtRepository =
			new SharepointExtRepository(
				tokenStore, sharepointRepositoryConfiguration);

		ReflectionTestUtil.invoke(
			sharepointExtRepository, "_getAccessToken", new Class<?>[0]);

		Mockito.verify(
			tokenStore
		).save(
			_CONFIGURATION_NAME, _USER_ID, brokerToken
		);
	}

	@Test
	public void testGetAccessTokenSavesWhenTokenChanged() throws Exception {
		Token brokerToken = SharepointRepositoryToken.newInstance(
			RandomTestUtil.randomString());
		Token cachedToken = SharepointRepositoryToken.newInstance(
			RandomTestUtil.randomString());

		TokenStore tokenStore = Mockito.mock(TokenStore.class);

		Mockito.when(
			tokenStore.get(_CONFIGURATION_NAME, _USER_ID)
		).thenReturn(
			cachedToken
		);

		SharepointRepositoryConfiguration sharepointRepositoryConfiguration =
			_mockSharepointRepositoryConfiguration();

		_mockBroker(
			brokerToken, cachedToken, sharepointRepositoryConfiguration);

		SharepointExtRepository sharepointExtRepository =
			new SharepointExtRepository(
				tokenStore, sharepointRepositoryConfiguration);

		String result = ReflectionTestUtil.invoke(
			sharepointExtRepository, "_getAccessToken", new Class<?>[0]);

		Assert.assertEquals(_RETURNED_ACCESS_TOKEN, result);

		Mockito.verify(
			tokenStore
		).save(
			_CONFIGURATION_NAME, _USER_ID, brokerToken
		);
	}

	private void _mockBroker(
			Token brokerToken, Token cachedToken,
			SharepointRepositoryConfiguration sharepointRepositoryConfiguration)
		throws Exception {

		SharepointRepositoryAuthenticationResult
			sharepointRepositoryAuthenticationResult = Mockito.mock(
				SharepointRepositoryAuthenticationResult.class);

		Mockito.when(
			sharepointRepositoryAuthenticationResult.getAccessToken()
		).thenReturn(
			_RETURNED_ACCESS_TOKEN
		);

		Mockito.when(
			sharepointRepositoryAuthenticationResult.getToken()
		).thenReturn(
			brokerToken
		);

		SharepointRepositoryTokenBroker sharepointRepositoryTokenBroker =
			Mockito.mock(SharepointRepositoryTokenBroker.class);

		Mockito.when(
			sharepointRepositoryTokenBroker.requestAccessTokenSilently(
				cachedToken)
		).thenReturn(
			sharepointRepositoryAuthenticationResult
		);

		_sharepointRepositoryTokenBrokerFactoryUtilMockedStatic.when(
			() -> SharepointRepositoryTokenBrokerFactoryUtil.create(
				sharepointRepositoryConfiguration)
		).thenReturn(
			sharepointRepositoryTokenBroker
		);
	}

	private SharepointRepositoryConfiguration
		_mockSharepointRepositoryConfiguration() {

		SharepointRepositoryConfiguration sharepointRepositoryConfiguration =
			Mockito.mock(SharepointRepositoryConfiguration.class);

		Mockito.when(
			sharepointRepositoryConfiguration.name()
		).thenReturn(
			_CONFIGURATION_NAME
		);

		return sharepointRepositoryConfiguration;
	}

	private static final String _CONFIGURATION_NAME =
		RandomTestUtil.randomString();

	private static final String _RETURNED_ACCESS_TOKEN =
		RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private MockedStatic<SharepointRepositoryTokenBrokerFactoryUtil>
		_sharepointRepositoryTokenBrokerFactoryUtilMockedStatic;

}
/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.key.internal.profile;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.security.key.internal.profile.configuration.KeyManagerGlobalConfiguration;
import com.liferay.portal.security.key.spi.profile.KeyManagerProfile;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Christopher Kian
 */
public class KeyManagerProfileOrchestratorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		_keyManagerProfileOrchestratorImpl =
			new KeyManagerProfileOrchestratorImpl();

		_setActiveProfileId(CustomKeyManagerProfile.PROFILE_ID);

		_setServiceTrackerMap(_serviceTrackerMap);
	}

	@Test
	public void testBootstrapInvokedForFallbackCustomProfile()
		throws Exception {

		_setActiveProfileId(null);

		Mockito.when(
			_keyManagerProfile.getProfileId()
		).thenReturn(
			CustomKeyManagerProfile.PROFILE_ID
		);

		Mockito.when(
			_serviceTrackerMap.getService(CustomKeyManagerProfile.PROFILE_ID)
		).thenReturn(
			_keyManagerProfile
		);

		_invokeBootstrap(_keyManagerProfile);

		Mockito.verify(
			_keyManagerProfile, Mockito.times(1)
		).bootstrap();
	}

	@Test
	public void testBootstrapInvokedOnceWhenProfilePresent() throws Exception {
		Mockito.when(
			_keyManagerProfile.getProfileId()
		).thenReturn(
			CustomKeyManagerProfile.PROFILE_ID
		);

		Mockito.when(
			_serviceTrackerMap.getService(CustomKeyManagerProfile.PROFILE_ID)
		).thenReturn(
			_keyManagerProfile
		);

		_invokeBootstrap(_keyManagerProfile);
		_invokeBootstrap(_keyManagerProfile);

		Mockito.verify(
			_keyManagerProfile, Mockito.times(1)
		).bootstrap();
	}

	@Test
	public void testBootstrapSkipsMismatchedProfile() throws Exception {
		Mockito.when(
			_keyManagerProfile.getProfileId()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		_invokeBootstrap(_keyManagerProfile);

		Mockito.verify(
			_keyManagerProfile, Mockito.never()
		).bootstrap();
	}

	@Test
	public void testGetActiveKeyManagerProfileFallsBackToCustom() {
		String activeProfileId = RandomTestUtil.randomString();

		_setActiveProfileId(activeProfileId);

		Mockito.when(
			_serviceTrackerMap.getService(activeProfileId)
		).thenReturn(
			null
		);

		Mockito.when(
			_serviceTrackerMap.getService(CustomKeyManagerProfile.PROFILE_ID)
		).thenReturn(
			_keyManagerProfile
		);

		Assert.assertSame(
			_keyManagerProfile,
			_keyManagerProfileOrchestratorImpl.getActiveKeyManagerProfile());
	}

	@Test
	public void testGetActiveKeyManagerProfileResolvesConfiguredId() {
		Mockito.when(
			_serviceTrackerMap.getService(CustomKeyManagerProfile.PROFILE_ID)
		).thenReturn(
			_keyManagerProfile
		);

		Assert.assertSame(
			_keyManagerProfile,
			_keyManagerProfileOrchestratorImpl.getActiveKeyManagerProfile());
	}

	private void _invokeBootstrap(KeyManagerProfile keyManagerProfile)
		throws Exception {

		Method method =
			KeyManagerProfileOrchestratorImpl.class.getDeclaredMethod(
				"_bootstrap", KeyManagerProfile.class);

		method.setAccessible(true);
		method.invoke(_keyManagerProfileOrchestratorImpl, keyManagerProfile);
	}

	private void _setActiveProfileId(String activeProfileId) {
		KeyManagerGlobalConfiguration keyManagerGlobalConfiguration =
			() -> activeProfileId;

		try {
			Field field =
				KeyManagerProfileOrchestratorImpl.class.getDeclaredField(
					"_keyManagerGlobalConfiguration");

			field.setAccessible(true);
			field.set(
				_keyManagerProfileOrchestratorImpl,
				keyManagerGlobalConfiguration);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _setServiceTrackerMap(
		ServiceTrackerMap<String, KeyManagerProfile> serviceTrackerMap) {

		try {
			Field field =
				KeyManagerProfileOrchestratorImpl.class.getDeclaredField(
					"_serviceTrackerMap");

			field.setAccessible(true);
			field.set(_keyManagerProfileOrchestratorImpl, serviceTrackerMap);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Mock
	private KeyManagerProfile _keyManagerProfile;

	private KeyManagerProfileOrchestratorImpl
		_keyManagerProfileOrchestratorImpl;

	@Mock
	private ServiceTrackerMap<String, KeyManagerProfile> _serviceTrackerMap;

}
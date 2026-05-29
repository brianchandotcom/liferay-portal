/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.keymanager.internal.profile;

import com.liferay.keymanager.internal.profile.configuration.KeyManagerGlobalConfiguration;
import com.liferay.keymanager.spi.profile.KeyManagerProfile;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
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
public class ProfileOrchestratorImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.openMocks(this);

		_profileOrchestratorImpl = new ProfileOrchestratorImpl();

		_setActiveProfileId("custom");

		_setServiceTrackerMap(_serviceTrackerMap);
	}

	@Test
	public void testBootstrapInvokedOnceWhenProfilePresent() throws Exception {
		Mockito.when(
			_keyManagerProfile.getProfileId()
		).thenReturn(
			"custom"
		);

		Mockito.when(
			_serviceTrackerMap.getService("custom")
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
	public void testGetActiveProfileFallsBackToCustom() {
		String activeProfileId = RandomTestUtil.randomString();

		_setActiveProfileId(activeProfileId);

		Mockito.when(
			_serviceTrackerMap.getService(activeProfileId)
		).thenReturn(
			null
		);

		Mockito.when(
			_serviceTrackerMap.getService("custom")
		).thenReturn(
			_keyManagerProfile
		);

		Assert.assertSame(
			_keyManagerProfile, _profileOrchestratorImpl.getActiveProfile());
	}

	@Test
	public void testGetActiveProfileResolvesConfiguredId() {
		Mockito.when(
			_serviceTrackerMap.getService("custom")
		).thenReturn(
			_keyManagerProfile
		);

		Assert.assertSame(
			_keyManagerProfile, _profileOrchestratorImpl.getActiveProfile());
	}

	private void _invokeBootstrap(KeyManagerProfile keyManagerProfile)
		throws Exception {

		Method method = ProfileOrchestratorImpl.class.getDeclaredMethod(
			"_bootstrap", KeyManagerProfile.class);

		method.setAccessible(true);
		method.invoke(_profileOrchestratorImpl, keyManagerProfile);
	}

	private void _setActiveProfileId(String activeProfileId) {
		KeyManagerGlobalConfiguration keyManagerGlobalConfiguration =
			() -> activeProfileId;

		try {
			Field field = ProfileOrchestratorImpl.class.getDeclaredField(
				"_keyManagerGlobalConfiguration");

			field.setAccessible(true);
			field.set(_profileOrchestratorImpl, keyManagerGlobalConfiguration);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _setServiceTrackerMap(
		ServiceTrackerMap<String, KeyManagerProfile> serviceTrackerMap) {

		try {
			Field field = ProfileOrchestratorImpl.class.getDeclaredField(
				"_serviceTrackerMap");

			field.setAccessible(true);
			field.set(_profileOrchestratorImpl, serviceTrackerMap);
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	@Mock
	private KeyManagerProfile _keyManagerProfile;

	private ProfileOrchestratorImpl _profileOrchestratorImpl;

	@Mock
	private ServiceTrackerMap<String, KeyManagerProfile> _serviceTrackerMap;

}
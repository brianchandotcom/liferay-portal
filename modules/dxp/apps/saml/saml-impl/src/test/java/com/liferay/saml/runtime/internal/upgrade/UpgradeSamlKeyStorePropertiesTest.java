/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.saml.runtime.internal.upgrade;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.saml.runtime.internal.upgrade.v1_0_0.UpgradeSamlKeyStoreProperties;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mockito;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Carlos Sierra
 */
@RunWith(PowerMockRunner.class)
public class UpgradeSamlKeyStorePropertiesTest extends PowerMockito {

	@Test
	public void testUpgradeWithEmptyProperty() throws Exception {
		ConfigurationAdmin configurationAdmin = spy(
			mock(ConfigurationAdmin.class));

		PrefsProps prefsProps = mock(PrefsProps.class);

		when(
			prefsProps.getString("saml.keystore.manager.impl")
		).thenReturn(
			""
		);

		UpgradeProcess upgradeProcess = new UpgradeSamlKeyStoreProperties(
			configurationAdmin, prefsProps);

		upgradeProcess.upgrade();

		verifyZeroInteractions(configurationAdmin);
	}

	@Test
	public void testUpgradeWithNullProperty() throws Exception {
		ConfigurationAdmin configurationAdmin = spy(
			mock(ConfigurationAdmin.class));

		PrefsProps prefsProps = mock(PrefsProps.class);

		when(
			prefsProps.getString("saml.keystore.manager.impl")
		).thenReturn(
			null
		);

		UpgradeProcess upgradeProcess = new UpgradeSamlKeyStoreProperties(
			configurationAdmin, prefsProps);

		upgradeProcess.upgrade();

		verifyZeroInteractions(configurationAdmin);
	}

	@Test
	public void testUpgradeWithProperty() throws Exception {
		ConfigurationAdmin configurationAdmin = spy(
			mock(ConfigurationAdmin.class));

		Configuration configuration = spy(mock(Configuration.class));

		when(
			configurationAdmin.getConfiguration(
				Mockito.anyString(), Matchers.eq(StringPool.QUESTION))
		).thenReturn(
			configuration
		);

		PrefsProps prefsProps = mock(PrefsProps.class);

		String samlKeyStoreManagerImpl = RandomTestUtil.randomString();

		when(
			prefsProps.getString("saml.keystore.manager.impl")
		).thenReturn(
			samlKeyStoreManagerImpl
		);

		UpgradeProcess upgradeProcess = new UpgradeSamlKeyStoreProperties(
			configurationAdmin, prefsProps);

		upgradeProcess.upgrade();

		ConfigurationAdmin verifyConfigurationAdmin = Mockito.verify(
			configurationAdmin, Mockito.times(1));

		verifyConfigurationAdmin.getConfiguration(
			Matchers.eq(
				"com.liferay.saml.runtime.configuration." +
					"SamlKeyStoreManagerConfiguration"),
			Matchers.eq(StringPool.QUESTION));

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			"KeyStoreManager.target",
			"(component.name=" + samlKeyStoreManagerImpl + ")");

		Configuration verifyConfiguration = Mockito.verify(
			configuration, Mockito.times(1));

		verifyConfiguration.update(Matchers.eq(properties));
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.display.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;

/**
 * @author Thiago Buarque
 */
@RunWith(Arquillian.class)
public class ConfigurationScreenTest {

	@Test
	public void testConfigurationScreenScope() {
		Bundle bundle = FrameworkUtil.getBundle(ConfigurationScreenTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		try (ServiceTrackerMap<String, ConfigurationScreen> serviceTrackerMap =
				ServiceTrackerMapFactory.openSingleValueMap(
					bundleContext, ConfigurationScreen.class, null,
					(serviceReference, emitter) -> {
						ConfigurationScreen configurationScreen =
							bundleContext.getService(serviceReference);

						emitter.emit(configurationScreen.getKey());
					})) {

			for (ConfigurationScreen configurationScreen :
					serviceTrackerMap.values()) {

				ExtendedObjectClassDefinition.Scope.getScope(
					configurationScreen.getScope());
			}
		}
	}

}
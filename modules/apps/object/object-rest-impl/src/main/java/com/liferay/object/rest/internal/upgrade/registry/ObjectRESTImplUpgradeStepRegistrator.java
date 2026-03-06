/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.upgrade.registry;

import com.liferay.object.rest.internal.upgrade.v1_0_0.SAPEntryAllowedServiceSignaturesUpgradeProcess;
import com.liferay.object.rest.internal.upgrade.v1_0_1.VulcanCompanyConfigurationUpgradeProcess;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = UpgradeStepRegistrator.class)
public class ObjectRESTImplUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			new SAPEntryAllowedServiceSignaturesUpgradeProcess());

		registry.register(
			"1.0.0", "1.0.1",
			new VulcanCompanyConfigurationUpgradeProcess(
				_configurationAdmin, _objectDefinitionLocalService));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

}
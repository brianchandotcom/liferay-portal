/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.user.associated.data.web.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration;
import com.liferay.user.associated.data.web.internal.upgrade.v1_0_0.ResourceActionUpgradeProcess;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tina Tian
 */
@Component(service = UpgradeStepRegistrator.class)
public class UserAssociatedDataWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.0", new ResourceActionUpgradeProcess());

		registry.register(
			"1.0.0", "1.1.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				AnonymousUserConfiguration.class.getName(),
				AnonymousUserConfiguration.class.getName() + ".scoped"));
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}
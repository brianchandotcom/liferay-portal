/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.web.internal.upgrade.registry;

import com.liferay.adaptive.media.web.internal.configuration.AMConfiguration;
import com.liferay.adaptive.media.web.internal.upgrade.v1_0_0.BlogsEntryDataFileEntryIdUpgradeProcess;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(service = UpgradeStepRegistrator.class)
public class AMWebUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"1.0.0", "1.0.1",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.adaptive.media.internal.configuration." +
					"AMConfiguration",
				AMConfiguration.class.getName()));

		registry.register(
			"1.0.1", "1.0.2",
			new BlogsEntryDataFileEntryIdUpgradeProcess(
				_blogsEntryLocalService));
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}
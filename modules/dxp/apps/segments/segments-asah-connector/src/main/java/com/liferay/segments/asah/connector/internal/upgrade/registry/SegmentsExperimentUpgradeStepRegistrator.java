/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.segments.asah.connector.internal.configuration.SegmentsExperimentConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = UpgradeStepRegistrator.class)
public class SegmentsExperimentUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.0", "1.0.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.segments.experiment.web.internal.configuration." +
					"SegmentsExperimentConfiguration",
				SegmentsExperimentConfiguration.class.getName()));
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}
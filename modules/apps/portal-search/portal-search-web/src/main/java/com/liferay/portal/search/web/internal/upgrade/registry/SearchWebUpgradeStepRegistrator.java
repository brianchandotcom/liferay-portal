/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.search.configuration.SemanticSearchConfiguration;
import com.liferay.portal.search.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.search.web.internal.upgrade.v1_0_0.UpgradePortletPreferences;
import com.liferay.portal.search.web.internal.upgrade.v2_0_0.SearchPortletUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 * @author Petteri Karttunen
 */
@Component(service = UpgradeStepRegistrator.class)
public class SearchWebUpgradeStepRegistrator implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0", new UpgradePortletId(),
			new UpgradePortletPreferences());

		registry.register("1.0.0", "2.0.0", new SearchPortletUpgradeProcess());

		registry.register(
			"2.0.0", "2.0.1",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.search.experiences.configuration." +
					"SemanticSearchConfiguration",
				SemanticSearchConfiguration.class.getName()),
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.search.experiences.configuration." +
					"SemanticSearchConfiguration.scoped",
				SemanticSearchConfiguration.class.getName() + ".scoped"));

		registry.register(
			"2.0.1", "2.0.2",
			new com.liferay.portal.search.web.internal.upgrade.v2_0_2.
				SearchPortletUpgradeProcess());
	}

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}
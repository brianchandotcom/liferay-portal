/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.seo.internal.upgrade.registry;

import com.liferay.layout.seo.internal.upgrade.v2_1_0.SchemaUpgradeProcess;
import com.liferay.layout.seo.internal.upgrade.v2_2_0.LayoutSEODynamicRenderingConfigurationUpgradeProcess;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(service = UpgradeStepRegistrator.class)
public class LayoutSEOServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			UpgradeProcessFactory.alterColumnName(
				"LayoutSEOEntry", "enabled", "canonicalURLEnabled BOOLEAN"),
			UpgradeProcessFactory.addColumns(
				"LayoutSEOEntry", "openGraphTitleEnabled BOOLEAN",
				"openGraphTitle STRING null",
				"openGraphDescriptionEnabled BOOLEAN",
				"openGraphDescription STRING null",
				"openGraphImageFileEntryId LONG"));

		registry.register("2.0.0", "2.1.0", new SchemaUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			new CTModelUpgradeProcess("LayoutSEOEntry", "LayoutSEOSite"));

		registry.register(
			"2.2.0", "2.3.0",
			new LayoutSEODynamicRenderingConfigurationUpgradeProcess(
				_configurationAdmin));
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}
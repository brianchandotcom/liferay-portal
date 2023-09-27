/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch.monitoring.web.internal.upgrade.registry;

import com.liferay.portal.configuration.persistence.upgrade.ConfigurationUpgradeStepFactory;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.search.elasticsearch.monitoring.web.internal.constants.MonitoringPortletKeys;
import com.liferay.portal.search.elasticsearch.monitoring.web.internal.upgrade.v1_0_0.MonitoringConfigurationUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Bryan Engler
 */
@Component(enabled = false, service = UpgradeStepRegistrator.class)
public class MonitoringWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register(
			"0.0.1", "1.0.0",
			_configurationUpgradeStepFactory.createUpgradeStep(
				"com.liferay.portal.search.elasticsearch6.xpack.monitoring." +
					"web.internal.configuration.XPackMonitoringConfiguration",
				"com.liferay.portal.search.elasticsearch.monitoring.web." +
					"internal.configuration.MonitoringConfiguration"));

		registry.register(
			"1.0.0", "2.0.0",
			new MonitoringConfigurationUpgradeProcess(_configurationAdmin));

		registry.register(
			"2.0.0", "3.0.0",
			new BasePortletIdUpgradeProcess() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						{
							"com_liferay_portal_search_elasticsearch6_xpack_" +
								"monitoring_portlet_XPackMonitoringPortlet",
							MonitoringPortletKeys.MONITORING
						}
					};
				}

			});
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private ConfigurationUpgradeStepFactory _configurationUpgradeStepFactory;

}
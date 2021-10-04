/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.search.elasticsearch.monitoring.web.internal.upgrade;

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
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class MonitoringWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
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
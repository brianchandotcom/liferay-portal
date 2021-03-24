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

package com.liferay.change.tracking.web.internal.upgrade;

import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.web.internal.constants.CTPortletKeys;
import com.liferay.portal.kernel.upgrade.BasePortletIdUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Preston Crary
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class PublicationsWebUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register("0.0.0", "1.0.0", new DummyUpgradeStep());

		registry.register(
			"1.0.0", "1.0.1",
			new BasePortletIdUpgradeProcess() {

				@Override
				protected String[][] getRenamePortletIdsArray() {
					return new String[][] {
						{
							"com_liferay_change_tracking_web_portlet_" +
								"ChangeListsPortlet",
							CTPortletKeys.PUBLICATIONS
						},
						{
							"com_liferay_change_tracking_web_portlet_" +
								"ChangeListsConfigurationPortlet",
							CTPortletKeys.PUBLICATIONS_CONFIGURATION
						}
					};
				}

			});
	}

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

}
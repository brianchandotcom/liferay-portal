/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.web.internal.upgrade.registry;

import com.liferay.exportimport.web.internal.upgrade.v1_0_0.UpgradePortletId;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Daniel Kocsis
 */
@Component(service = UpgradeStepRegistrator.class)
public class ExportImportWebUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.registerInitialization();

		registry.register("0.0.1", "1.0.0", new UpgradePortletId());

		registry.register(
			"1.0.0", "1.0.1",
			new com.liferay.exportimport.web.internal.upgrade.v1_0_1.
				StagingConfigurationClassNamesUpgradeProcess(
					_groupLocalService));
	}

	@Reference
	private GroupLocalService _groupLocalService;

}
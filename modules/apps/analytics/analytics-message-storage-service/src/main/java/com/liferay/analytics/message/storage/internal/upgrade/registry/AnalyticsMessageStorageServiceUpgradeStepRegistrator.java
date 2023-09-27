/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.internal.upgrade.registry;

import com.liferay.analytics.message.storage.internal.upgrade.v1_1_0.util.AnalyticsDeleteMessageTable;
import com.liferay.analytics.message.storage.internal.upgrade.v1_2_0.util.AnalyticsAssociationTable;
import com.liferay.portal.kernel.upgrade.CTModelUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(service = UpgradeStepRegistrator.class)
public class AnalyticsMessageStorageServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.1.0", AnalyticsDeleteMessageTable.create());

		registry.register("1.1.0", "1.2.0", AnalyticsAssociationTable.create());

		registry.register(
			"1.2.0", "1.3.0",
			new CTModelUpgradeProcess(
				"AnalyticsAssociation", "AnalyticsDeleteMessage",
				"AnalyticsMessage"));
	}

}
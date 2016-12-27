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

package com.liferay.dynamic.data.lists.internal.upgrade;

import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.UpgradeKernelPackage;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.UpgradeLastPublishDate;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_0.UpgradeSchema;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_1.UpgradeRecordGroup;
import com.liferay.dynamic.data.lists.internal.upgrade.v1_0_2.UpgradeDDLRecordSetSettings;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(
	immediate = true,
	service = {DDLServiceUpgrade.class, UpgradeStepRegistrator.class}
)
public class DDLServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"com.liferay.dynamic.data.lists.service", "0.0.1", "0.0.2",
			new UpgradeSchema());

		registry.register(
			"com.liferay.dynamic.data.lists.service", "0.0.2", "0.0.3",
			new UpgradeKernelPackage());

		registry.register(
			"com.liferay.dynamic.data.lists.service", "0.0.3", "1.0.0",
			new UpgradeLastPublishDate());

		registry.register(
			"com.liferay.dynamic.data.lists.service", "1.0.0", "1.0.1",
			new UpgradeRecordGroup());

		registry.register(
			"com.liferay.dynamic.data.lists.service", "1.0.1", "1.0.2",
			new UpgradeDDLRecordSetSettings(_jsonFactory));
	}

	@Reference(unbind = "-")
	protected void setDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {
	}

	@Reference
	private JSONFactory _jsonFactory;

}
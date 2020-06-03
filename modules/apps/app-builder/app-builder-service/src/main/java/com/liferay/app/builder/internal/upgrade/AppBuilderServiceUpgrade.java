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

package com.liferay.app.builder.internal.upgrade;

import com.liferay.app.builder.internal.upgrade.v2_2_0.UpgradeSchema;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class AppBuilderServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "2.0.0",
			new com.liferay.app.builder.internal.upgrade.v2_0_0.
				UpgradeAppBuilderApp());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.app.builder.internal.upgrade.v2_1_0.
				UpgradeAppBuilderApp());

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.app.builder.internal.upgrade.v2_2_0.
				UpgradeAppBuilderApp(
					_ddlRecordSetLocalService, _ddmStructureLocalService),
			new UpgradeSchema());
	}

	@Reference
	private DDLRecordSetLocalService _ddlRecordSetLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

}
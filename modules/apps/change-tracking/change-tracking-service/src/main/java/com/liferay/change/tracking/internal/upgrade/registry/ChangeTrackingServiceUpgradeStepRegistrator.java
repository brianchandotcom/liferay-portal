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

package com.liferay.change.tracking.internal.upgrade.registry;

import com.liferay.change.tracking.internal.upgrade.v2_3_0.UpgradeCompanyId;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ChangeTrackingServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.0.1",
			UpgradeProcessFactory.alterColumnTypes(
				"CTCollection", "VARCHAR(200) null", "description"));

		registry.register(
			"1.0.1", "2.0.0",
			new com.liferay.change.tracking.internal.upgrade.v2_0_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.change.tracking.internal.upgrade.v2_1_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			UpgradeProcessFactory.addColumns(
				"CTPreferences", "previousCtCollectionId LONG"));

		registry.register("2.2.0", "2.3.0", new UpgradeCompanyId());

		registry.register(
			"2.3.0", "2.4.0",
			UpgradeProcessFactory.addColumns(
				"CTCollection", "schemaVersionId LONG"),
			UpgradeProcessFactory.runSQL(
				"update CTCollection set schemaVersionId = 0"),
			UpgradeProcessFactory.runSQL(
				StringBundler.concat(
					"create table CTSchemaVersion (mvccVersion LONG default 0 ",
					"not null, schemaVersionId LONG not null primary key, ",
					"companyId LONG, schemaContext TEXT null)")),
			UpgradeProcessFactory.runSQL(
				StringBundler.concat(
					"update CTCollection set status = ",
					WorkflowConstants.STATUS_EXPIRED, " where status = ",
					WorkflowConstants.STATUS_DRAFT)),
			UpgradeProcessFactory.runSQL(
				"update CTPreferences set ctCollectionId = 0, " +
					"previousCtCollectionId = 0"));

		registry.register(
			"2.4.0", "2.5.0",
			new com.liferay.change.tracking.internal.upgrade.v2_5_0.
				SchemaUpgradeProcess());

		registry.register("2.5.0", "2.5.1", new DummyUpgradeStep());
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.upgrade.registry;

import com.liferay.change.tracking.internal.upgrade.v2_10_0.CTCollectionUpgradeProcess;
import com.liferay.change.tracking.internal.upgrade.v2_3_0.UpgradeCompanyId;
import com.liferay.change.tracking.internal.upgrade.v2_4_0.CTSchemaVersionUpgradeProcess;
import com.liferay.change.tracking.internal.upgrade.v2_7_0.CTProcessUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseUuidUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Daniel Kocsis
 */
@Component(service = UpgradeStepRegistrator.class)
public class ChangeTrackingServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"1.0.0", "1.0.1",
			UpgradeProcessFactory.alterColumnType(
				"CTCollection", "description", "VARCHAR(200) null"));

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
			"2.3.0", "2.4.0", new CTSchemaVersionUpgradeProcess());

		registry.register(
			"2.4.0", "2.5.0",
			new com.liferay.change.tracking.internal.upgrade.v2_5_0.
				SchemaUpgradeProcess());

		registry.register("2.5.0", "2.5.1", new DummyUpgradeStep());

		registry.register(
			"2.5.1", "2.6.0",
			new com.liferay.change.tracking.internal.upgrade.v2_6_0.
				SchemaUpgradeProcess());

		registry.register("2.6.0", "2.7.0", new CTProcessUpgradeProcess());

		registry.register(
			"2.7.0", "2.8.0",
			new com.liferay.change.tracking.internal.upgrade.v2_8_0.
				SchemaUpgradeProcess());

		registry.register(
			"2.8.0", "2.9.0",
			new BaseUuidUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {{"CTCollection", "ctCollectionId"}};
				}

			});

		registry.register(
			"2.9.0", "2.9.1",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {{"CTCollection", "ctCollectionId"}};
				}

			});

		registry.register("2.9.1", "2.10.0", new CTCollectionUpgradeProcess());

		registry.register(
			"2.10.0", "2.11.0",
			UpgradeProcessFactory.addColumns("CTCollection", "ctRemoteId LONG"),
			UpgradeProcessFactory.addColumns(
				"CTRemote", "clientId VARCHAR(75)",
				"clientSecret VARCHAR(75)"));
	}

}
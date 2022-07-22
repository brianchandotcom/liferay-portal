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

package com.liferay.commerce.inventory.internal.upgrade.registry;

import com.liferay.commerce.inventory.internal.upgrade.v1_1_0.CommerceInventoryWarehouseItemUpgradeProcess;
import com.liferay.commerce.inventory.internal.upgrade.v2_0_0.CommerceInventoryAuditUpgradeProcess;
import com.liferay.commerce.inventory.internal.upgrade.v2_1_0.MVCCUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.BaseUuidUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceInventoryServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce inventory upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0",
			new CommerceInventoryWarehouseItemUpgradeProcess());

		registry.register("1.1.0", "1.2.0", new DummyUpgradeProcess());

		registry.register(
			"1.2.0", "2.0.0", new CommerceInventoryAuditUpgradeProcess());

		registry.register("2.0.0", "2.1.0", new MVCCUpgradeProcess());

		registry.register(
			"2.1.0", "2.2.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"CIAudit", "CIBookedQuantity", "CIReplenishmentItem",
						"CIWarehouse", "CIWarehouseGroupRel", "CIWarehouseItem"
					};
				}

			});

		registry.register(
			"2.2.0", "2.3.0",
			new BaseUuidUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"CIWarehouse", "CIWarehouseId"},
						{"CIWarehouseItem", "CIWarehouseItemId"}
					};
				}

			});

		registry.register(
			"2.3.0", "2.3.1",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"CIWarehouse", "CIWarehouseId"},
						{"CIWarehouseItem", "CIWarehouseItemId"}
					};
				}

			});

		registry.register(
			"2.3.1", "2.4.0",
			new BaseUuidUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"CIReplenishmentItem", "CIReplenishmentItemId"}
					};
				}

			});

		registry.register(
			"2.4.0", "2.4.1",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"CIReplenishmentItem", "CIReplenishmentItemId"}
					};
				}

			});

		registry.register(
			"2.4.1", "2.5.0",
			new com.liferay.commerce.inventory.internal.upgrade.v2_5_0.
				CommerceInventoryWarehouseUpgradeProcess(
					_companyLocalService, _resourceActionLocalService,
					_resourceLocalService));

		if (_log.isInfoEnabled()) {
			_log.info("Commerce inventory upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceInventoryServiceUpgradeStepRegistrator.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

}
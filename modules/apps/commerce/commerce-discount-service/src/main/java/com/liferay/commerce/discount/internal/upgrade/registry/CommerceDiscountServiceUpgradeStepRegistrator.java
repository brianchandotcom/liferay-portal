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

package com.liferay.commerce.discount.internal.upgrade.registry;

import com.liferay.commerce.discount.internal.upgrade.v2_0_0.util.CommerceDiscountCommerceAccountGroupRelTable;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.CommerceDiscountRuleNameUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.util.CommerceDiscountAccountRelTable;
import com.liferay.commerce.discount.internal.upgrade.v2_6_0.util.CommerceDiscountOrderTypeRelTable;
import com.liferay.commerce.discount.model.impl.CommerceDiscountModelImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountRelModelImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountRuleModelImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseExternalReferenceCodeUpgradeProcess;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.kernel.upgrade.UpgradeProcessFactory;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceDiscountServiceUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce discount upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "2.0.0",
			CommerceDiscountCommerceAccountGroupRelTable.create(),
			UpgradeProcessFactory.dropColumns(
				CommerceDiscountRelModelImpl.TABLE_NAME, "groupId"),
			UpgradeProcessFactory.dropColumns(
				CommerceDiscountRuleModelImpl.TABLE_NAME, "groupId"),
			UpgradeProcessFactory.dropColumns(
				CommerceDiscountModelImpl.TABLE_NAME, "groupId"),
			UpgradeProcessFactory.dropColumns(
				CommerceDiscountUsageEntryModelImpl.TABLE_NAME, "groupId"));

		registry.register(
			"2.0.0", "2.1.0",
			UpgradeProcessFactory.addColumns(
				"CommerceDiscount", "externalReferenceCode VARCHAR(75)"));

		registry.register(
			"2.1.0", "2.2.0",
			new com.liferay.commerce.discount.internal.upgrade.v2_2_0.
				CommerceDiscountUpgradeProcess(),
			CommerceDiscountAccountRelTable.create(),
			new CommerceDiscountRuleNameUpgradeProcess(),
			com.liferay.commerce.discount.internal.upgrade.v2_2_0.util.
				CommerceDiscountCommerceAccountGroupRelTable.create());

		registry.register(
			"2.2.0", "2.3.0",
			UpgradeProcessFactory.alterColumnName(
				"CommerceDiscount", "level", "levelType VARCHAR(75)"));

		registry.register(
			"2.3.0", "2.4.0",
			new com.liferay.commerce.discount.internal.upgrade.v2_4_0.
				CommerceDiscountUpgradeProcess());

		registry.register(
			"2.4.0", "2.4.1",
			new com.liferay.commerce.discount.internal.upgrade.v2_4_1.
				CommerceDiscountUpgradeProcess());

		registry.register("2.4.1", "2.4.2", new DummyUpgradeStep());

		registry.register("2.4.2", "2.5.0", new DummyUpgradeStep());

		registry.register(
			"2.5.0", "2.6.0", CommerceDiscountOrderTypeRelTable.create());

		registry.register(
			"2.6.0", "2.7.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"CDiscountCAccountGroupRel", "CommerceDiscount",
						"CommerceDiscountAccountRel",
						"CommerceDiscountOrderTypeRel", "CommerceDiscountRel",
						"CommerceDiscountRule", "CommerceDiscountUsageEntry"
					};
				}

			});

		registry.register(
			"2.7.0", "2.8.0",
			new BaseExternalReferenceCodeUpgradeProcess() {

				@Override
				protected String[][] getTableAndPrimaryKeyColumnNames() {
					return new String[][] {
						{"CommerceDiscount", "commerceDiscountId"}
					};
				}

			});

		if (_log.isInfoEnabled()) {
			_log.info("Commerce discount upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountServiceUpgradeStepRegistrator.class);

}
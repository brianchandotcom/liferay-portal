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

package com.liferay.commerce.discount.internal.upgrade;

import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountRelUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountRuleUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountUsageEntryUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.util.CommerceDiscountCommerceAccountGroupRelTable;
import com.liferay.commerce.discount.internal.upgrade.v2_1_0.CommerceDiscountExternalReferenceCodeUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.CommerceDiscountRuleNameUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.util.CommerceDiscountAccountRelTable;
import com.liferay.commerce.discount.internal.upgrade.v2_6_0.util.CommerceDiscountOrderTypeRelTable;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.DummyUpgradeStep;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommerceDiscountUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce discount upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "2.0.0",
			CommerceDiscountCommerceAccountGroupRelTable.create(),
			new CommerceDiscountRelUpgradeProcess(),
			new CommerceDiscountRuleUpgradeProcess(),
			new CommerceDiscountUpgradeProcess(),
			new CommerceDiscountUsageEntryUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new CommerceDiscountExternalReferenceCodeUpgradeProcess());

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
			new com.liferay.commerce.discount.internal.upgrade.v2_3_0.
				CommerceDiscountUpgradeProcess());

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

		if (_log.isInfoEnabled()) {
			_log.info("Commerce discount upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountUpgradeStepRegistrator.class);

}
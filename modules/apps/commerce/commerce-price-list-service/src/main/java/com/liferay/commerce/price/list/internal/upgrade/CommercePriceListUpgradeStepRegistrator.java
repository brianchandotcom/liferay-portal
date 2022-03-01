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

package com.liferay.commerce.price.list.internal.upgrade;

import com.liferay.commerce.price.list.internal.upgrade.v1_1_0.CommercePriceEntryUpgradeProcess;
import com.liferay.commerce.price.list.internal.upgrade.v1_2_0.util.CommercePriceListAccountRelTable;
import com.liferay.commerce.price.list.internal.upgrade.v2_0_0.CommerceTierPriceEntryUpgradeProcess;
import com.liferay.commerce.price.list.internal.upgrade.v2_0_0.util.CommercePriceListCommerceAccountGroupRelTable;
import com.liferay.commerce.price.list.internal.upgrade.v2_1_0.util.CommercePriceListChannelRelTable;
import com.liferay.commerce.price.list.internal.upgrade.v2_1_0.util.CommercePriceListDiscountRelTable;
import com.liferay.commerce.price.list.internal.upgrade.v2_2_0.util.CommercePriceListOrderTypeRelTable;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourceLocalService;
import com.liferay.portal.kernel.upgrade.DummyUpgradeProcess;
import com.liferay.portal.kernel.upgrade.MVCCVersionUpgradeProcess;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true, service = UpgradeStepRegistrator.class
)
public class CommercePriceListUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("Commerce price list upgrade step registrator started");
		}

		registry.register(
			"1.0.0", "1.1.0",
			new CommercePriceEntryUpgradeProcess(
				_cpDefinitionLocalService, _cpInstanceLocalService));

		registry.register(
			"1.1.0", "1.2.0", CommercePriceListAccountRelTable.create());

		registry.register(
			"1.2.0", "2.0.0",
			new com.liferay.commerce.price.list.internal.upgrade.v2_0_0.
				CommercePriceEntryUpgradeProcess(),
			new com.liferay.commerce.price.list.internal.upgrade.v2_0_0.
				CommercePriceListAccountRelUpgradeProcess(),
			CommercePriceListCommerceAccountGroupRelTable.create(),
			new CommerceTierPriceEntryUpgradeProcess());

		registry.register(
			"2.0.0", "2.1.0",
			new com.liferay.commerce.price.list.internal.upgrade.v2_1_0.
				CommercePriceEntryUpgradeProcess(),
			new com.liferay.commerce.price.list.internal.upgrade.v2_1_0.
				CommercePriceListUpgradeProcess(),
			new com.liferay.commerce.price.list.internal.upgrade.v2_1_0.
				CommerceTierPriceEntryUpgradeProcess(),
			CommercePriceListChannelRelTable.create(),
			CommercePriceListDiscountRelTable.create());

		registry.register(
			"2.1.0", "2.1.1",
			new com.liferay.commerce.price.list.internal.upgrade.v2_1_1.
				CommercePriceListUpgradeProcess());

		registry.register(
			"2.1.1", "2.1.2",
			new com.liferay.commerce.price.list.internal.upgrade.v2_1_2.
				CommercePriceListUpgradeProcess(
					_resourceActionLocalService, _resourceLocalService));

		registry.register("2.1.2", "2.1.3", new DummyUpgradeProcess());

		registry.register(
			"2.1.3", "2.2.0", CommercePriceListOrderTypeRelTable.create());

		registry.register(
			"2.2.0", "2.3.0",
			new MVCCVersionUpgradeProcess() {

				@Override
				protected String[] getModuleTableNames() {
					return new String[] {
						"CPLCommerceGroupAccountRel", "CommercePriceEntry",
						"CommercePriceList", "CommercePriceListAccountRel",
						"CommercePriceListChannelRel",
						"CommercePriceListDiscountRel",
						"CommercePriceListOrderTypeRel",
						"CommerceTierPriceEntry"
					};
				}

			});

		registry.register(
			"2.3.0", "2.4.0",
			new com.liferay.commerce.price.list.internal.upgrade.v2_4_0.
				CommercePriceEntryUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("Commerce price list upgrade step registrator finished");
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListUpgradeStepRegistrator.class);

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourceLocalService _resourceLocalService;

}
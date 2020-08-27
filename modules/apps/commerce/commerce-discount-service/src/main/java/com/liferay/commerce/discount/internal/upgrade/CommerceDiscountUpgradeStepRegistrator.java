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

import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountCommerceAccountGroupRelUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountRelUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountRuleUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_0_0.CommerceDiscountUsageEntryUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_1_0.CommerceDiscountExternalReferenceCodeUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.CommerceDiscountAccountRelUpgradeProcess;
import com.liferay.commerce.discount.internal.upgrade.v2_2_0.CommerceDiscountRuleNameUpgradeProcess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class CommerceDiscountUpgradeStepRegistrator
	implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		if (_log.isInfoEnabled()) {
			_log.info("COMMERCE DISCOUNT UPGRADE STEP REGISTRATOR STARTED");
		}

		registry.register(
			_SCHEMA_VERSION_1_0_0, _SCHEMA_VERSION_2_0_0,
			new CommerceDiscountCommerceAccountGroupRelUpgradeProcess(),
			new CommerceDiscountRelUpgradeProcess(),
			new CommerceDiscountRuleUpgradeProcess(),
			new CommerceDiscountUpgradeProcess(),
			new CommerceDiscountUsageEntryUpgradeProcess());

		registry.register(
			_SCHEMA_VERSION_2_0_0, _SCHEMA_VERSION_2_1_0,
			new CommerceDiscountExternalReferenceCodeUpgradeProcess());

		registry.register(
			_SCHEMA_VERSION_2_1_0, _SCHEMA_VERSION_2_2_0,
			new com.liferay.commerce.discount.internal.upgrade.v2_2_0.
				CommerceDiscountUpgradeProcess(),
			new CommerceDiscountAccountRelUpgradeProcess(),
			new CommerceDiscountRuleNameUpgradeProcess(),
			new com.liferay.commerce.discount.internal.upgrade.v2_2_0.
				CommerceDiscountCommerceAccountGroupRelUpgradeProcess());

		registry.register(
			_SCHEMA_VERSION_2_2_0, _SCHEMA_VERSION_2_3_0,
			new com.liferay.commerce.discount.internal.upgrade.v2_3_0.
				CommerceDiscountUpgradeProcess());

		registry.register(
			_SCHEMA_VERSION_2_3_0, _SCHEMA_VERSION_2_4_0,
			new com.liferay.commerce.discount.internal.upgrade.v2_4_0.
				CommerceDiscountUpgradeProcess());

		registry.register(
			_SCHEMA_VERSION_2_4_0, _SCHEMA_VERSION_2_4_1,
			new com.liferay.commerce.discount.internal.upgrade.v2_4_1.
				CommerceDiscountUpgradeProcess());

		if (_log.isInfoEnabled()) {
			_log.info("COMMERCE DISCOUNT UPGRADE STEP REGISTRATOR FINISHED");
		}
	}

	private static final String _SCHEMA_VERSION_1_0_0 = "1.0.0";

	private static final String _SCHEMA_VERSION_2_0_0 = "2.0.0";

	private static final String _SCHEMA_VERSION_2_1_0 = "2.1.0";

	private static final String _SCHEMA_VERSION_2_2_0 = "2.2.0";

	private static final String _SCHEMA_VERSION_2_3_0 = "2.3.0";

	private static final String _SCHEMA_VERSION_2_4_0 = "2.4.0";

	private static final String _SCHEMA_VERSION_2_4_1 = "2.4.1";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountUpgradeStepRegistrator.class);

}
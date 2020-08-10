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

package com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v1_1_0;

import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionRelImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionRelUpgradeProcess
	extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		if (_log.isInfoEnabled()) {
			_log.info(
				String.format(
					"Renaming column %s to table %s", "commerceWarehouseId",
					CommerceShippingFixedOptionRelImpl.TABLE_NAME));
		}

		if (!hasColumn(
				CommerceShippingFixedOptionRelImpl.TABLE_NAME,
				"commerceInventoryWarehouseId")) {

			alter(
				CommerceShippingFixedOptionRelImpl.class,
				new AlterColumnName(
					"commerceWarehouseId",
					"commerceInventoryWarehouseId LONG"));
		}
		else {
			if (_log.isInfoEnabled()) {
				_log.info(
					String.format(
						"Column %s already exists on table %s",
						"commerceInventoryWarehouseId",
						CommerceShippingFixedOptionRelImpl.TABLE_NAME));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionRelUpgradeProcess.class);

}
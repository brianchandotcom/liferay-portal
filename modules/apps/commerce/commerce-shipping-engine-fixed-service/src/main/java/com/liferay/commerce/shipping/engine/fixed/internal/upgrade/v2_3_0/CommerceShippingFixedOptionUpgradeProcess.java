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

package com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v2_3_0;

import com.liferay.commerce.shipping.engine.fixed.internal.upgrade.v2_3_0.util.CommerceShippingFixedOptionTable;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.CommerceShippingFixedOptionLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.util.List;

/**
 * @author Luca Pellizzon
 */
public class CommerceShippingFixedOptionUpgradeProcess extends UpgradeProcess {

	public CommerceShippingFixedOptionUpgradeProcess(
		CommerceShippingFixedOptionLocalService
			commerceShippingFixedOptionLocalService) {

		_commerceShippingFixedOptionLocalService =
			commerceShippingFixedOptionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		if (!hasColumn(CommerceShippingFixedOptionTable.TABLE_NAME, "key_")) {
			alterTableAddColumn(
				CommerceShippingFixedOptionTable.TABLE_NAME, "key_",
				"VARCHAR(75)");
		}

		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			_commerceShippingFixedOptionLocalService.
				getCommerceShippingFixedOptions(
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CommerceShippingFixedOption commerceShippingFixedOption :
				commerceShippingFixedOptions) {

			_commerceShippingFixedOptionLocalService.
				updateCommerceShippingFixedOption(
					commerceShippingFixedOption.
						getCommerceShippingFixedOptionId(),
					commerceShippingFixedOption.getAmount(),
					commerceShippingFixedOption.getDescriptionMap(),
					commerceShippingFixedOption.getNameMap(),
					commerceShippingFixedOption.getPriority());
		}
	}

	private final CommerceShippingFixedOptionLocalService
		_commerceShippingFixedOptionLocalService;

}
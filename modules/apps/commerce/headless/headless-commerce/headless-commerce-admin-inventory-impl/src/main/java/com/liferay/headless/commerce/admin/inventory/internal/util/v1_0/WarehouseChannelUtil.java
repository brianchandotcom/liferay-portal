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

package com.liferay.headless.commerce.admin.inventory.internal.util.v1_0;

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.product.exception.NoSuchChannelException;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRel;
import com.liferay.commerce.product.service.CommerceChannelRelService;
import com.liferay.commerce.product.service.CommerceChannelService;
import com.liferay.headless.commerce.admin.inventory.dto.v1_0.WarehouseChannel;
import com.liferay.headless.commerce.core.util.ServiceContextHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Crescenzo Rega
 */
public class WarehouseChannelUtil {

	public static CommerceChannelRel addCommerceInventoryWarehouseChannelRel(
			CommerceChannelService commerceChannelService,
			CommerceChannelRelService commerceChannelRelService,
			WarehouseChannel warehouseChannel,
			CommerceInventoryWarehouse commerceInventoryWarehouse,
			ServiceContextHelper serviceContextHelper)
		throws PortalException {

		ServiceContext serviceContext =
			serviceContextHelper.getServiceContext();

		CommerceChannel commerceChannel;

		if (Validator.isNull(
				warehouseChannel.getChannelExternalReferenceCode())) {

			commerceChannel = commerceChannelService.getCommerceChannel(
				warehouseChannel.getChannelId());
		}
		else {
			commerceChannel =
				commerceChannelService.fetchByExternalReferenceCode(
					warehouseChannel.getChannelExternalReferenceCode(),
					serviceContext.getCompanyId());

			if (commerceChannel == null) {
				throw new NoSuchChannelException(
					"Unable to find channel with external reference code " +
						warehouseChannel.getChannelExternalReferenceCode());
			}
		}

		return commerceChannelRelService.addCommerceChannelRel(
			CommerceInventoryWarehouse.class.getName(),
			commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
			commerceChannel.getCommerceChannelId(), serviceContext);
	}

}
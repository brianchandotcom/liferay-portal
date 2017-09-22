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

package com.liferay.commerce.inventory.web.internal.portlet.action;

import com.liferay.commerce.inventory.web.internal.constants.CommerceInventoryWebKeys;
import com.liferay.commerce.model.CommerceInventory;
import com.liferay.commerce.service.CommerceInventoryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommerceInventoryActionHelper.class)
public class CommerceInventoryActionHelper {

	public CommerceInventory getCommerceInventory(RenderRequest renderRequest)
		throws PortalException {

		CommerceInventory commerceInventory =
			(CommerceInventory)renderRequest.getAttribute(
				CommerceInventoryWebKeys.COMMERCE_INVENTORY);

		if (commerceInventory != null) {
			return commerceInventory;
		}

		long cpDefinitionId = ParamUtil.getLong(
			renderRequest, "cpDefinitionId");

		if (cpDefinitionId > 0) {
			commerceInventory =
				_commerceInventoryService.
					fetchCommerceInventoryByCPDefinitionId(cpDefinitionId);
		}

		if (commerceInventory != null) {
			renderRequest.setAttribute(
				CommerceInventoryWebKeys.COMMERCE_INVENTORY, commerceInventory);
		}

		return commerceInventory;
	}

	@Reference
	private CommerceInventoryService _commerceInventoryService;

}
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

package com.liferay.commerce.warehouse.web.internal.display.context;

import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.service.CommerceWarehouseItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceWarehouseItemDisplayContext {

	public CommerceWarehouseItemDisplayContext(
		CommerceWarehouseItemService commerceWarehouseItemService,
		RenderRequest renderRequest) {

		_commerceWarehouseItemService = commerceWarehouseItemService;
		_renderRequest = renderRequest;
	}

	public CommerceWarehouseItem getCommerceWarehouseItem()
		throws PortalException {

		if (_commerceWarehouseItem != null) {
			return _commerceWarehouseItem;
		}

		long commerceWarehouseItemId = ParamUtil.getLong(
			_renderRequest, "commerceWarehouseItemId");

		if (commerceWarehouseItemId > 0) {
			_commerceWarehouseItem =
				_commerceWarehouseItemService.getCommerceWarehouseItem(
					commerceWarehouseItemId);
		}

		return _commerceWarehouseItem;
	}

	private CommerceWarehouseItem _commerceWarehouseItem;
	private final CommerceWarehouseItemService _commerceWarehouseItemService;
	private final RenderRequest _renderRequest;

}
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

import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.product.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrea Di Giorgi
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryWarehouseItemsDisplayContext {

	public CommerceInventoryWarehouseItemsDisplayContext(
		CommerceInventoryWarehouseItemService
			commerceInventoryWarehouseItemService,
		CommerceInventoryWarehouseService commerceInventoryWarehouseService,
		CPInstanceService cpInstanceService,
		HttpServletRequest httpServletRequest, Portal portal) {

		_commerceInventoryWarehouseItemService =
			commerceInventoryWarehouseItemService;
		_commerceInventoryWarehouseService = commerceInventoryWarehouseService;
		_cpInstanceService = cpInstanceService;
		_portal = portal;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);
	}

	public String getBackURL() throws PortalException {
		RenderRequest renderRequest = _cpRequestHelper.getRenderRequest();

		String lifecycle = (String)renderRequest.getAttribute(
			LiferayPortletRequest.LIFECYCLE_PHASE);

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			renderRequest, CPPortletKeys.CP_DEFINITIONS, lifecycle);

		portletURL.setParameter(
			"mvcRenderCommandName", "editProductDefinition");

		CPInstance cpInstance = getCPInstance();

		portletURL.setParameter(
			"cpDefinitionId", String.valueOf(cpInstance.getCPDefinitionId()));

		portletURL.setParameter(
			"screenNavigationCategoryKey",
			CPDefinitionScreenNavigationConstants.CATEGORY_KEY_SKUS);

		return portletURL.toString();
	}

	public CommerceInventoryWarehouseItem getCommerceInventoryWarehouseItem(
			CommerceInventoryWarehouse commerceInventoryWarehouse)
		throws PortalException {

		CPInstance cpInstance = getCPInstance();

		return _commerceInventoryWarehouseItemService.
			fetchCommerceInventoryWarehouseItem(
				commerceInventoryWarehouse.getCommerceInventoryWarehouseId(),
				cpInstance.getSku());
	}

	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses()
		throws PortalException {

		return _getCommerceInventoryWarehouses();
	}

	public CPInstance getCPInstance() throws PortalException {
		if (_cpInstance == null) {
			long cpInstanceId = ParamUtil.getLong(
				_cpRequestHelper.getRenderRequest(), "cpInstanceId");

			_cpInstance = _cpInstanceService.getCPInstance(cpInstanceId);
		}

		return _cpInstance;
	}

	public String getUpdateCommerceInventoryWarehouseItemTaglibOnClick(
		long commerceInventoryWarehouseId,
		long commerceInventoryWarehouseItemId, int index) {

		RenderResponse renderResponse = _cpRequestHelper.getRenderResponse();

		StringBundler sb = new StringBundler(10);

		sb.append(renderResponse.getNamespace());
		sb.append("updateCommerceInventoryWarehouseItem");
		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(commerceInventoryWarehouseId);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(commerceInventoryWarehouseItemId);
		sb.append(StringPool.COMMA_AND_SPACE);
		sb.append(index);
		sb.append(StringPool.CLOSE_PARENTHESIS);
		sb.append(StringPool.SEMICOLON);

		return sb.toString();
	}

	public boolean hasManageCommerceInventoryWarehousePermission() {
		return true;
	}

	private List<CommerceInventoryWarehouse> _getCommerceInventoryWarehouses()
		throws PortalException {

		if (_commerceInventoryWarehouses != null) {
			return _commerceInventoryWarehouses;
		}

		_commerceInventoryWarehouses =
			_commerceInventoryWarehouseService.getCommerceInventoryWarehouses(
				_cpRequestHelper.getCompanyId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		return _commerceInventoryWarehouses;
	}

	private final CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;
	private List<CommerceInventoryWarehouse> _commerceInventoryWarehouses;
	private final CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;
	private CPInstance _cpInstance;
	private final CPInstanceService _cpInstanceService;
	private final CPRequestHelper _cpRequestHelper;
	private final Portal _portal;

}
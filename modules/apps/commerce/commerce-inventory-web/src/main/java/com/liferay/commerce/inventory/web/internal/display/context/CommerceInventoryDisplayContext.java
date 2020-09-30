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

package com.liferay.commerce.inventory.web.internal.display.context;

import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryReplenishmentItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseService;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
public class CommerceInventoryDisplayContext {

	public CommerceInventoryDisplayContext(
		CommerceInventoryReplenishmentItemService
			commerceInventoryReplenishmentItemService,
		CommerceInventoryWarehouseService commerceInventoryWarehouseService,
		CommerceInventoryWarehouseItemService
			commerceInventoryWarehouseItemService,
		ModelResourcePermission<CommerceInventoryWarehouse>
			commerceInventoryWarehouseModelResourcePermission,
		HttpServletRequest httpServletRequest) {

		_commerceInventoryReplenishmentItemService =
			commerceInventoryReplenishmentItemService;
		_commerceInventoryWarehouseService = commerceInventoryWarehouseService;
		_commerceInventoryWarehouseItemService =
			commerceInventoryWarehouseItemService;
		_commerceInventoryWarehouseModelResourcePermission =
			commerceInventoryWarehouseModelResourcePermission;

		_cpRequestHelper = new CPRequestHelper(httpServletRequest);

		_sku = ParamUtil.getString(httpServletRequest, "sku");
	}

	public String getAddQuantityActionURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_inventory/edit_commerce_inventory_warehouse");
		portletURL.setParameter("sku", _sku);

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public CommerceInventoryReplenishmentItem
			getCommerceInventoryReplenishmentItem()
		throws PortalException {

		long commerceInventoryReplenishmentItemId = ParamUtil.getLong(
			_cpRequestHelper.getRequest(),
			"commerceInventoryReplenishmentItemId");

		if (commerceInventoryReplenishmentItemId > 0) {
			return _commerceInventoryReplenishmentItemService.
				getCommerceInventoryReplenishmentItem(
					commerceInventoryReplenishmentItemId);
		}

		return null;
	}

	public long getCommerceInventoryReplenishmentItemId()
		throws PortalException {

		CommerceInventoryReplenishmentItem commerceInventoryReplenishmentItem =
			getCommerceInventoryReplenishmentItem();

		if (commerceInventoryReplenishmentItem == null) {
			return 0;
		}

		return commerceInventoryReplenishmentItem.
			getCommerceInventoryReplenishmentItemId();
	}

	public CommerceInventoryWarehouseItem getCommerceInventoryWarehouseItem()
		throws PortalException {

		long commerceInventoryWarehouseItemId = ParamUtil.getLong(
			_cpRequestHelper.getRequest(), "commerceInventoryWarehouseItemId");

		if (commerceInventoryWarehouseItemId > 0) {
			return _commerceInventoryWarehouseItemService.
				getCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseItemId);
		}

		return null;
	}

	public long getCommerceInventoryWarehouseItemId() throws PortalException {
		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			getCommerceInventoryWarehouseItem();

		if (commerceInventoryWarehouseItem == null) {
			return 0;
		}

		return commerceInventoryWarehouseItem.
			getCommerceInventoryWarehouseItemId();
	}

	public List<CommerceInventoryWarehouse> getCommerceInventoryWarehouses()
		throws PrincipalException {

		return _commerceInventoryWarehouseService.
			getCommerceInventoryWarehouses(
				_cpRequestHelper.getCompanyId(), true, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);
	}

	public String getCreateInventoryItemActionURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_inventory/add_commerce_inventory_item");

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public String getCreateReplenishmentActionURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_inventory/edit_commerce_inventory_replenishment_item");
		portletURL.setParameter("sku", _sku);

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public List<HeaderActionModel> getHeaderActionModels()
		throws PrincipalException {

		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		if (_sku == null) {
			return headerActionModels;
		}

		if (_hasPermission()) {
			RenderResponse renderResponse =
				_cpRequestHelper.getRenderResponse();

			RenderURL cancelURL = renderResponse.createRenderURL();

			headerActionModels.add(
				new HeaderActionModel(
					null, cancelURL.toString(), null, "cancel"));
		}

		return headerActionModels;
	}

	public CreationMenu getInventoryItemCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (_hasPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getCreateInventoryItemActionURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							_cpRequestHelper.getRequest(),
							"add-inventory-item"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public PortletURL getPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		String redirect = ParamUtil.getString(
			_cpRequestHelper.getRequest(), "redirect");

		if (Validator.isNotNull(redirect)) {
			portletURL.setParameter("redirect", redirect);
		}

		if (_sku != null) {
			portletURL.setParameter("sku", _sku);
		}

		return portletURL;
	}

	public CreationMenu getReplenishmentCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (_hasPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getCreateReplenishmentActionURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							_cpRequestHelper.getRequest(), "add-income"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	public String getSku() {
		return _sku;
	}

	public String getTransferQuantitiesActionURL() throws Exception {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/commerce_inventory/transfer_quantities");
		portletURL.setParameter("sku", _sku);

		portletURL.setWindowState(LiferayWindowState.POP_UP);

		return portletURL.toString();
	}

	public PortletURL getTransitionInventoryPortletURL() {
		LiferayPortletResponse liferayPortletResponse =
			_cpRequestHelper.getLiferayPortletResponse();

		PortletURL portletURL = liferayPortletResponse.createActionURL();

		portletURL.setParameter(
			ActionRequest.ACTION_NAME,
			"/commerce_inventory/edit_commerce_inventory_item");
		portletURL.setParameter(Constants.CMD, "transition");
		portletURL.setParameter("sku", _sku);
		portletURL.setParameter("redirect", _cpRequestHelper.getCurrentURL());

		return portletURL;
	}

	public CreationMenu getWarehousesCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (_hasPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getAddQuantityActionURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							_cpRequestHelper.getRequest(), "add-inventory"));
					dropdownItem.setTarget("modal-lg");
				});

			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getTransferQuantitiesActionURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							_cpRequestHelper.getRequest(),
							"create-a-transfer"));
					dropdownItem.setTarget("modal-lg");
				});
		}

		return creationMenu;
	}

	private boolean _hasPermission() throws PrincipalException {
		PortletResourcePermission portletResourcePermission =
			_commerceInventoryWarehouseModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			PermissionThreadLocal.getPermissionChecker(), null,
			CommerceInventoryActionKeys.MANAGE_INVENTORY);
	}

	private final CommerceInventoryReplenishmentItemService
		_commerceInventoryReplenishmentItemService;
	private final CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;
	private final ModelResourcePermission<CommerceInventoryWarehouse>
		_commerceInventoryWarehouseModelResourcePermission;
	private final CommerceInventoryWarehouseService
		_commerceInventoryWarehouseService;
	private final CPRequestHelper _cpRequestHelper;
	private String _sku;

}
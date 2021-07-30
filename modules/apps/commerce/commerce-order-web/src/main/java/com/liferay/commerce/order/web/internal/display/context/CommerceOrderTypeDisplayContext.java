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

package com.liferay.commerce.order.web.internal.display.context;

import com.liferay.commerce.constants.CommerceOrderActionKeys;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.frontend.model.HeaderActionModel;
import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.order.web.internal.display.context.util.CommerceOrderRequestHelper;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.service.CommerceOrderTypeService;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.CreationMenu;
import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.RenderResponse;
import javax.portlet.RenderURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Riccardo Alberti
 */
public class CommerceOrderTypeDisplayContext {

	public CommerceOrderTypeDisplayContext(
		HttpServletRequest httpServletRequest,
		ModelResourcePermission<CommerceOrderType>
			commerceOrderTypeModelResourcePermission,
		CommerceOrderTypeService commerceOrderTypeService, Portal portal) {

		_commerceOrderRequestHelper = new CommerceOrderRequestHelper(
			httpServletRequest);

		_commerceOrderTypeModelResourcePermission =
			commerceOrderTypeModelResourcePermission;
		_commerceOrderTypeService = commerceOrderTypeService;
		_portal = portal;
	}

	public String getAddCommerceOrderTypeRenderURL() throws Exception {
		return PortletURLBuilder.createRenderURL(
			_commerceOrderRequestHelper.getLiferayPortletResponse()
		).setMVCRenderCommandName(
			"/commerce_order_type/add_commerce_order_type"
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public CommerceOrderType getCommerceOrderType() throws PortalException {
		long commerceOrderTypeId = ParamUtil.getLong(
			_commerceOrderRequestHelper.getRequest(), "commerceOrderTypeId");

		if (commerceOrderTypeId == 0) {
			return null;
		}

		return _commerceOrderTypeService.fetchCommerceOrderType(
			commerceOrderTypeId);
	}

	public long getCommerceOrderTypeId() throws PortalException {
		CommerceOrderType commerceOrderType = getCommerceOrderType();

		if (commerceOrderType == null) {
			return 0;
		}

		return commerceOrderType.getCommerceOrderTypeId();
	}

	public CreationMenu getCreationMenu() throws Exception {
		CreationMenu creationMenu = new CreationMenu();

		if (hasAddPermission()) {
			creationMenu.addDropdownItem(
				dropdownItem -> {
					dropdownItem.setHref(getAddCommerceOrderTypeRenderURL());
					dropdownItem.setLabel(
						LanguageUtil.get(
							_commerceOrderRequestHelper.getRequest(),
							"add-order-type"));
					dropdownItem.setTarget("modal");
				});
		}

		return creationMenu;
	}

	public String getEditCommerceOrderTypeActionURL() throws Exception {
		CommerceOrderType commerceOrderType = getCommerceOrderType();

		if (commerceOrderType == null) {
			return StringPool.BLANK;
		}

		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				_commerceOrderRequestHelper.getRequest(),
				CommercePricingPortletKeys.COMMERCE_PRICING_CLASSES,
				PortletRequest.ACTION_PHASE)
		).setActionName(
			"/commerce_order_type/edit_commerce_order_type"
		).setCMD(
			Constants.UPDATE
		).setParameter(
			"commerceOrderTypeId", commerceOrderType.getCommerceOrderTypeId()
		).setWindowState(
			LiferayWindowState.POP_UP
		).buildString();
	}

	public PortletURL getEditCommerceOrderTypeRenderURL() {
		return PortletURLBuilder.create(
			_portal.getControlPanelPortletURL(
				_commerceOrderRequestHelper.getRequest(),
				CommercePortletKeys.COMMERCE_ORDER_TYPE,
				PortletRequest.RENDER_PHASE)
		).setMVCRenderCommandName(
			"/commerce_order_type/edit_commerce_order_type"
		).buildPortletURL();
	}

	public List<HeaderActionModel> getHeaderActionModels() throws Exception {
		List<HeaderActionModel> headerActionModels = new ArrayList<>();

		RenderResponse renderResponse =
			_commerceOrderRequestHelper.getRenderResponse();

		RenderURL cancelURL = renderResponse.createRenderURL();

		HeaderActionModel cancelHeaderActionModel = new HeaderActionModel(
			null, cancelURL.toString(), null, "cancel");

		headerActionModels.add(cancelHeaderActionModel);

		if (hasPermission(ActionKeys.UPDATE)) {
			LiferayPortletResponse liferayPortletResponse =
				_commerceOrderRequestHelper.getLiferayPortletResponse();

			headerActionModels.add(
				new HeaderActionModel(
					"btn-primary", liferayPortletResponse.getNamespace() + "fm",
					getEditCommerceOrderTypeActionURL(), null, "save"));
		}

		return headerActionModels;
	}

	public boolean hasAddPermission() throws PortalException {
		PortletResourcePermission portletResourcePermission =
			_commerceOrderTypeModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			_commerceOrderRequestHelper.getPermissionChecker(), null,
			CommerceOrderActionKeys.ADD_COMMERCE_ORDER_TYPE);
	}

	public boolean hasPermission(String actionId) throws PortalException {
		return _commerceOrderTypeModelResourcePermission.contains(
			_commerceOrderRequestHelper.getPermissionChecker(),
			getCommerceOrderTypeId(), actionId);
	}

	private final CommerceOrderRequestHelper _commerceOrderRequestHelper;
	private final ModelResourcePermission<CommerceOrderType>
		_commerceOrderTypeModelResourcePermission;
	private final CommerceOrderTypeService _commerceOrderTypeService;
	private final Portal _portal;

}
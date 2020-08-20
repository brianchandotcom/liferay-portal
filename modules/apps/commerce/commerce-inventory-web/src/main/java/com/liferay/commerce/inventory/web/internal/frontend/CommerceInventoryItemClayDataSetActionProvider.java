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

package com.liferay.commerce.inventory.web.internal.frontend;

import static com.liferay.portal.kernel.security.permission.PermissionThreadLocal.getPermissionChecker;

import com.liferay.commerce.inventory.constants.CommerceInventoryActionKeys;
import com.liferay.commerce.inventory.web.internal.model.InventoryItem;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.theme.PortletDisplay;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + CommerceInventoryDataSetConstants.COMMERCE_DATA_SET_KEY_INVENTORY_ITEMS,
	service = ClayDataSetActionProvider.class
)
public class CommerceInventoryItemClayDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		InventoryItem inventoryItem = (InventoryItem)model;

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return DropdownItemListBuilder.add(
			() -> PortalPermissionUtil.contains(
				getPermissionChecker(),
				CommerceInventoryActionKeys.MANAGE_INVENTORY),
			dropdownItem -> {
				dropdownItem.setHref(
					_getCommerceInventoryItemEditURL(
						inventoryItem.getSku(), themeDisplay));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
			}
		).add(
			() -> PortalPermissionUtil.contains(
				getPermissionChecker(),
				CommerceInventoryActionKeys.MANAGE_INVENTORY),
			dropdownItem -> {
				dropdownItem.setHref(
					_getInventoryItemDeleteURL(
						inventoryItem.getSku(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private String _getCommerceInventoryItemEditURL(
		String sku, ThemeDisplay themeDisplay) {

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		PortletURL portletURL = PortletURLFactoryUtil.create(
			themeDisplay.getRequest(), portletDisplay.getId(),
			themeDisplay.getPlid(), PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "editCommerceInventoryItem");
		portletURL.setParameter("sku", String.valueOf(sku));

		return portletURL.toString();
	}

	private String _getInventoryItemDeleteURL(
		String sku, HttpServletRequest httpServletRequest) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			_portal.getOriginalServletRequest(httpServletRequest),
			CPPortletKeys.COMMERCE_INVENTORY, PortletRequest.ACTION_PHASE);

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter(
			ActionRequest.ACTION_NAME, "editCommerceInventoryWarehouse");
		portletURL.setParameter(Constants.CMD, Constants.DELETE);
		portletURL.setParameter("redirect", redirect);
		portletURL.setParameter("sku", String.valueOf(sku));

		return portletURL.toString();
	}

	@Reference
	private Portal _portal;

}
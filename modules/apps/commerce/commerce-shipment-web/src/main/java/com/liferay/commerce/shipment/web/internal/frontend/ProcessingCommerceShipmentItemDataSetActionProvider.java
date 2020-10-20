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

package com.liferay.commerce.shipment.web.internal.frontend;

import com.liferay.commerce.constants.CommerceActionKeys;
import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.constants.CommerceShipmentConstants;
import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
import com.liferay.commerce.frontend.model.ShipmentItem;
import com.liferay.commerce.model.CommerceShipment;
import com.liferay.commerce.model.CommerceShipmentItem;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;
import javax.portlet.WindowStateException;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_PROCESSING_SHIPMENT_ITEMS,
	service = ClayDataSetActionProvider.class
)
public class ProcessingCommerceShipmentItemDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		ShipmentItem shipmentItem = (ShipmentItem)model;

		CommerceShipmentItem commerceShipmentItem =
			_commerceShipmentItemService.getCommerceShipmentItem(
				shipmentItem.getShipmentItemId());

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		return DropdownItemListBuilder.add(
			() -> {
				CommerceShipment commerceShipment =
					commerceShipmentItem.getCommerceShipment();

				return _portletResourcePermission.contains(
					permissionChecker, null,
					CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS) &&
					   (commerceShipment.getStatus() ==
						   CommerceShipmentConstants.
							   SHIPMENT_STATUS_PROCESSING);
			},
			dropdownItem -> {
				dropdownItem.setHref(
					_getShipmentItemEditURL(
						commerceShipmentItem, httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
				dropdownItem.setTarget("sidePanel");
			}
		).add(
			() -> _portletResourcePermission.contains(
				permissionChecker, null,
				CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS),
			dropdownItem -> {
				dropdownItem.setHref(
					_getShipmentItemDeleteURL(
						shipmentItem.getShipmentItemId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private String _getShipmentItemDeleteURL(
		long commerceShipmentItemId, HttpServletRequest httpServletRequest) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_SHIPMENT,
			ActionRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_shipment/delete_commerce_shipment");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));
		portletURL.setParameter(
			"commerceShipmentItemId", String.valueOf(commerceShipmentItemId));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL.toString();
	}

	private String _getShipmentItemEditURL(
		CommerceShipmentItem commerceShipmentItem,
		HttpServletRequest httpServletRequest) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_SHIPMENT,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_shipment/edit_commerce_shipment_item");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));
		portletURL.setParameter(
			"commerceShipmentId",
			String.valueOf(commerceShipmentItem.getCommerceShipmentId()));
		portletURL.setParameter(
			"commerceShipmentItemId",
			String.valueOf(commerceShipmentItem.getCommerceShipmentItemId()));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ProcessingCommerceShipmentItemDataSetActionProvider.class);

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CommerceConstants.SHIPMENT_RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
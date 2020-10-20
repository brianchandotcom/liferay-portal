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
import com.liferay.commerce.constants.CommerceShipmentDataSetConstants;
import com.liferay.commerce.frontend.model.Shipment;
import com.liferay.frontend.taglib.clay.data.set.ClayDataSetActionProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItemListBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
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
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = "clay.data.provider.key=" + CommerceShipmentDataSetConstants.COMMERCE_DATA_SET_KEY_ORDER_SHIPMENTS,
	service = ClayDataSetActionProvider.class
)
public class CommerceOrderShipmentDataSetActionProvider
	implements ClayDataSetActionProvider {

	@Override
	public List<DropdownItem> getDropdownItems(
			HttpServletRequest httpServletRequest, long groupId, Object model)
		throws PortalException {

		Shipment shipment = (Shipment)model;

		return DropdownItemListBuilder.add(
			() -> _portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS),
			dropdownItem -> {
				dropdownItem.setHref(
					_getShipmentEditURL(
						shipment.getShipmentId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "edit"));
			}
		).add(
			() -> _portletResourcePermission.contains(
				PermissionThreadLocal.getPermissionChecker(), null,
				CommerceActionKeys.MANAGE_COMMERCE_SHIPMENTS),
			dropdownItem -> {
				dropdownItem.setHref(
					_getShipmentDeleteURL(
						shipment.getShipmentId(), httpServletRequest));
				dropdownItem.setLabel(
					LanguageUtil.get(httpServletRequest, "delete"));
			}
		).build();
	}

	private String _getShipmentDeleteURL(
		long commerceShipmentId, HttpServletRequest httpServletRequest) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_SHIPMENT,
			ActionRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_shipment/delete_commerce_shipment");
		portletURL.setParameter(
			"redirect", _portal.getCurrentURL(httpServletRequest));
		portletURL.setParameter(
			"commerceShipmentId", String.valueOf(commerceShipmentId));

		try {
			portletURL.setWindowState(LiferayWindowState.POP_UP);
		}
		catch (WindowStateException windowStateException) {
			_log.error(windowStateException, windowStateException);
		}

		return portletURL.toString();
	}

	private String _getShipmentEditURL(
		long commerceShipmentId, HttpServletRequest httpServletRequest) {

		PortletURL portletURL = _portal.getControlPanelPortletURL(
			httpServletRequest, CommercePortletKeys.COMMERCE_SHIPMENT,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter("backURL", portletURL.toString());

		portletURL.setParameter(
			"mvcRenderCommandName",
			"/commerce_shipment/edit_commerce_shipment");

		String redirect = ParamUtil.getString(
			httpServletRequest, "currentUrl",
			_portal.getCurrentURL(httpServletRequest));

		portletURL.setParameter("redirect", redirect);

		portletURL.setParameter(
			"commerceShipmentId", String.valueOf(commerceShipmentId));

		return portletURL.toString();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderShipmentDataSetActionProvider.class);

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CommerceConstants.SHIPMENT_RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
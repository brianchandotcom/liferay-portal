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

package com.liferay.commerce.shipment.web.internal.portlet.action;

import com.liferay.commerce.constants.CommerceConstants;
import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.commerce.service.CommerceShipmentItemService;
import com.liferay.commerce.shipment.web.internal.display.context.CommerceShipmentItemDisplayContext;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_SHIPMENT,
		"mvc.command.name=/commerce_shipment/edit_commerce_shipment_item"
	},
	service = MVCRenderCommand.class
)
public class EditCommerceShipmentItemMVCRenderCommand
	implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		CommerceShipmentItemDisplayContext commerceShipmentItemDisplayContext =
			new CommerceShipmentItemDisplayContext(
				_actionHelper, _portal.getHttpServletRequest(renderRequest),
				_commerceOrderItemService, _commerceShipmentItemService,
				_portletResourcePermission);

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			commerceShipmentItemDisplayContext);

		return "/shipment_item/edit_commerce_shipment_item.jsp";
	}

	@Reference
	private ActionHelper _actionHelper;

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

	@Reference
	private CommerceShipmentItemService _commerceShipmentItemService;

	@Reference
	private Portal _portal;

	@Reference(
		target = "(resource.name=" + CommerceConstants.RESOURCE_NAME_SHIPMENT + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
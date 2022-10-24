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

package com.liferay.commerce.product.type.virtual.order.content.web.internal.portlet.action;

import com.liferay.commerce.product.type.virtual.order.constants.CommerceVirtualOrderPortletKeys;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.PortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.File;
import java.io.FileInputStream;

import javax.portlet.PortletException;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceVirtualOrderPortletKeys.COMMERCE_VIRTUAL_ORDER_ITEM_CONTENT,
		"mvc.command.name=/commerce_virtual_order_item_content/download_commerce_virtual_order_item"
	},
	service = MVCResourceCommand.class
)
public class DownloadCommerceVirtualOrderItemMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	public void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws PortletException {

		long commerceVirtualOrderItemId = ParamUtil.getLong(
			resourceRequest, "commerceVirtualOrderItemId");

		try {
			File file = _commerceVirtualOrderItemService.getFile(
				commerceVirtualOrderItemId);

			PortletResponseUtil.sendFile(
				resourceRequest, resourceResponse, file.getName(),
				new FileInputStream(file), 0,
				MimeTypesUtil.getContentType(file),
				HttpHeaders.CONTENT_DISPOSITION_ATTACHMENT);
		}
		catch (Exception exception) {
			_log.error(exception);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DownloadCommerceVirtualOrderItemMVCResourceCommand.class);

	@Reference
	private CommerceVirtualOrderItemService _commerceVirtualOrderItemService;

}
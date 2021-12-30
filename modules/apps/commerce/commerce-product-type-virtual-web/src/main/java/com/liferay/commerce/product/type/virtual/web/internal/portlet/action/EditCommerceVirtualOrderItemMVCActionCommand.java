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

package com.liferay.commerce.product.type.virtual.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.exception.CommerceVirtualOrderItemFileEntryIdException;
import com.liferay.commerce.product.type.virtual.order.exception.NoSuchVirtualOrderItemException;
import com.liferay.commerce.product.type.virtual.order.service.CommerceVirtualOrderItemService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.concurrent.TimeUnit;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER,
		"mvc.command.name=/commerce_order/edit_commerce_virtual_order_item"
	},
	service = MVCActionCommand.class
)
public class EditCommerceVirtualOrderItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.UPDATE)) {
				_updateCommerceVirtualOrderItem(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof CommerceVirtualOrderItemException ||
				exception instanceof
					CommerceVirtualOrderItemFileEntryIdException) {

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else if (exception instanceof NoSuchVirtualOrderItemException ||
					 exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw exception;
			}
		}
	}

	private void _updateCommerceVirtualOrderItem(ActionRequest actionRequest)
		throws Exception {

		long commerceVirtualOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceVirtualOrderItemId");

		long fileEntryId = ParamUtil.getLong(actionRequest, "fileEntryId");
		String url = ParamUtil.getString(actionRequest, "url");
		int activationStatus = ParamUtil.getInteger(
			actionRequest, "activationStatus");
		long durationDays = ParamUtil.getLong(actionRequest, "durationDays");
		int usages = ParamUtil.getInteger(actionRequest, "usages");
		int maxUsages = ParamUtil.getInteger(actionRequest, "maxUsages");
		boolean active = ParamUtil.getBoolean(actionRequest, "active");

		long duration = TimeUnit.DAYS.toMillis(durationDays);

		_commerceVirtualOrderItemService.updateCommerceVirtualOrderItem(
			commerceVirtualOrderItemId, fileEntryId, url, activationStatus,
			duration, usages, maxUsages, active);
	}

	@Reference
	private CommerceVirtualOrderItemService _commerceVirtualOrderItemService;

}
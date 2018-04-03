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

package com.liferay.commerce.organization.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.CommerceOrderValidatorException;
import com.liferay.commerce.exception.NoSuchOrderItemException;
import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.service.CommerceOrderItemService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORGANIZATION_ORDER,
		"mvc.command.name=editCommerceOrderItem"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderItemMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		long commerceOrderItemId = ParamUtil.getLong(
			actionRequest, "commerceOrderItemId");

		try {
			if (cmd.equals(Constants.DELETE)) {
				_commerceOrderItemService.deleteCommerceOrderItem(
					commerceOrderItemId);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				int quantity = ParamUtil.getInteger(actionRequest, "quantity");

				CommerceOrderItem commerceOrderItem =
					_commerceOrderItemService.getCommerceOrderItem(
						commerceOrderItemId);

				_commerceOrderItemService.updateCommerceOrderItem(
					commerceOrderItem.getCommerceOrderItemId(), quantity,
					commerceOrderItem.getJson(), commerceOrderItem.getPrice());
			}
		}
		catch (CommerceOrderValidatorException cove) {
			hideDefaultErrorMessage(actionRequest);

			SessionErrors.add(actionRequest, cove.getClass(), cove);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrderItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	@Reference
	private CommerceOrderItemService _commerceOrderItemService;

}
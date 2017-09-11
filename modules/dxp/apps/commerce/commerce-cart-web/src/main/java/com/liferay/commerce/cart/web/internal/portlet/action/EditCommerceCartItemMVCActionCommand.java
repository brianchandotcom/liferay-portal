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

package com.liferay.commerce.cart.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.NoSuchCartItemException;
import com.liferay.commerce.model.CommerceCartItem;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPInstanceService;
import com.liferay.commerce.service.CommerceCartItemService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_CART,
		"mvc.command.name=editCommerceCartItem"
	},
	service = MVCActionCommand.class
)
public class EditCommerceCartItemMVCActionCommand extends BaseMVCActionCommand {

	@Override
	public boolean processAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortletException {

		return super.processAction(actionRequest, actionResponse);
	}

	protected void addCommerceCartItems(ActionRequest actionRequest)
		throws Exception {

		long[] addCPInstanceIds = null;

		long commerceCartId = ParamUtil.getLong(
			actionRequest, "commerceCartId");

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			addCPInstanceIds = new long[] {cpInstanceId};
		}
		else {
			addCPInstanceIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "cpInstanceIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceCartItem.class.getName(), actionRequest);

		for (long addCPInstanceId : addCPInstanceIds) {
			CPInstance cpInstance = _cpInstanceService.getCPInstance(
				addCPInstanceId);

			_commerceCartItemService.addCommerceCartItem(
				commerceCartId, cpInstance.getCPDefinitionId(),
				cpInstance.getCPInstanceId(), 1, cpInstance.getDDMContent(),
				serviceContext);
		}
	}

	protected void deleteCommerceCartItems(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceCartItemIds = null;

		long commerceCartItemId = ParamUtil.getLong(
			actionRequest, "commerceCartItemId");

		if (commerceCartItemId > 0) {
			deleteCommerceCartItemIds = new long[] {commerceCartItemId};
		}
		else {
			deleteCommerceCartItemIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "deleteCommerceCartItemIds"),
				0L);
		}

		for (long deleteCommerceCartItemId : deleteCommerceCartItemIds) {
			_commerceCartItemService.deleteCommerceCartItem(
				deleteCommerceCartItemId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) ||
				cmd.equals(Constants.ADD_MULTIPLE)) {

				addCommerceCartItems(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceCartItems(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				updateCommerceCartItem(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchCartItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceCartItem(ActionRequest actionRequest)
		throws PortalException {

		long commerceCartItemId = ParamUtil.getLong(
			actionRequest, "commerceCartItemId");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		CommerceCartItem commerceCartItem =
			_commerceCartItemService.getCommerceCartItem(commerceCartItemId);

		_commerceCartItemService.updateCommerceCartItem(
			commerceCartItem.getCommerceCartItemId(), quantity,
			commerceCartItem.getJson());
	}

	@Reference
	private CommerceCartItemService _commerceCartItemService;

	@Reference
	private CPInstanceService _cpInstanceService;

}
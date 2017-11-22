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

package com.liferay.commerce.warehouse.web.internal.portlet.action;

import com.liferay.commerce.exception.NoSuchWarehouseItemException;
import com.liferay.commerce.model.CommerceWarehouseItem;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.service.CommerceWarehouseItemService;
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

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editCommerceWarehouseItem"
	},
	service = MVCActionCommand.class
)
public class EditCommerceWarehouseItemMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCommerceWarehouseItems(ActionRequest actionRequest)
		throws Exception {

		long[] addCommerceWarehouseIds = null;

		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");
		long commerceWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseId");

		if (commerceWarehouseId > 0) {
			addCommerceWarehouseIds = new long[] {commerceWarehouseId};
		}
		else {
			addCommerceWarehouseIds = StringUtil.split(
				ParamUtil.getString(actionRequest, "commerceWarehouseIds"), 0L);
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceWarehouseItem.class.getName(), actionRequest);

		for (int i = 0; i < addCommerceWarehouseIds.length; i++) {
			commerceWarehouseId = addCommerceWarehouseIds[i];

			_commerceWarehouseItemService.addCommerceWarehouseItem(
				commerceWarehouseId, cpInstanceId, 0, serviceContext);
		}
	}

	protected void deleteCommerceWarehouseItems(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceWarehouseItemIds = null;

		long commerceWarehouseItemId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseItemId");

		if (commerceWarehouseItemId > 0) {
			deleteCommerceWarehouseItemIds =
				new long[] {commerceWarehouseItemId};
		}
		else {
			deleteCommerceWarehouseItemIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceWarehouseItemIds"),
				0L);
		}

		for (long deleteCommerceWarehouseItemId :
				deleteCommerceWarehouseItemIds) {

			_commerceWarehouseItemService.deleteCommerceWarehouseItem(
				deleteCommerceWarehouseItemId);
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

				addCommerceWarehouseItems(actionRequest);
			}

			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceWarehouseItems(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceWarehouseItem(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchWarehouseItemException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected CommerceWarehouseItem updateCommerceWarehouseItem(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceWarehouseItemId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseItemId");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceWarehouseItem.class.getName(), actionRequest);

		return _commerceWarehouseItemService.updateCommerceWarehouseItem(
			commerceWarehouseItemId, quantity, serviceContext);
	}

	@Reference
	private CommerceWarehouseItemService _commerceWarehouseItemService;

}
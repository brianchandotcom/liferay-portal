/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
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

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
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

		long commerceWarehouseId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseId");
		long commerceWarehouseItemId = ParamUtil.getLong(
			actionRequest, "commerceWarehouseItemId");
		long cpInstanceId = ParamUtil.getLong(actionRequest, "cpInstanceId");

		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceWarehouseItem.class.getName(), actionRequest);

		CommerceWarehouseItem commerceWarehouseItem = null;

		if (commerceWarehouseItemId > 0) {
			commerceWarehouseItem =
				_commerceWarehouseItemService.updateCommerceWarehouseItem(
					commerceWarehouseItemId, quantity, serviceContext);
		}
		else {
			commerceWarehouseItem =
				_commerceWarehouseItemService.addCommerceWarehouseItem(
					commerceWarehouseId, cpInstanceId, quantity,
					serviceContext);
		}

		return commerceWarehouseItem;
	}

	@Reference
	private CommerceWarehouseItemService _commerceWarehouseItemService;

}
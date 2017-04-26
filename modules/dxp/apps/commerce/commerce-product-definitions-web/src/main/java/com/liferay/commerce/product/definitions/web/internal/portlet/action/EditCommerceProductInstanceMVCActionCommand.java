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

package com.liferay.commerce.product.definitions.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CommerceProductPortletKeys;
import com.liferay.commerce.product.model.CommerceProductInstance;
import com.liferay.commerce.product.service.CommerceProductInstanceLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceProductPortletKeys.COMMERCE_PRODUCT_DEFINITIONS,
		"mvc.command.name=editProductInstance"
	},
	service = MVCActionCommand.class
)
public class EditCommerceProductInstanceMVCActionCommand
	extends BaseMVCActionCommand {

	protected void autoGenerateCommerceProductInstances(
			ActionRequest actionRequest)
		throws Exception {

		long commerceProductDefinitionId = ParamUtil.getLong(
			actionRequest, "commerceProductDefinitionId");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceProductInstance.class.getName(), actionRequest);

		// Generate commerce product instances

		_commerceProductInstanceService.buildCommerceProductInstances(
			commerceProductDefinitionId, serviceContext);
	}

	protected void deleteCommerceProductInstance(ActionRequest actionRequest)
		throws Exception {

		long commerceProductInstanceId = ParamUtil.getLong(
			actionRequest, "commerceProductInstanceId");

		_commerceProductInstanceService.deleteCommerceProductInstance(
			commerceProductInstanceId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceProductInstance(actionRequest);
			}
			else if (cmd.equals(Constants.ADD_MULTIPLE)) {
				autoGenerateCommerceProductInstances(actionRequest);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@Reference
	private CommerceProductInstanceLocalService _commerceProductInstanceService;

}
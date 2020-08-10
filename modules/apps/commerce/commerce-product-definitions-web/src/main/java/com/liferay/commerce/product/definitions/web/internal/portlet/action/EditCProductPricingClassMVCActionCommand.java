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

import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelService;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editCProductPricingClass"
	},
	service = MVCActionCommand.class
)
public class EditCProductPricingClassMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommercePricingClassCPDefinitionRel(
			ActionRequest actionRequest)
		throws Exception {

		long[] commercePricingClassCPDefinitionRelIds = null;

		long commercePricingClassCPDefinitionRelId = ParamUtil.getLong(
			actionRequest, "commercePricingClassCPDefinitionRelId");

		if (commercePricingClassCPDefinitionRelId > 0) {
			commercePricingClassCPDefinitionRelIds = new long[] {
				commercePricingClassCPDefinitionRelId
			};
		}
		else {
			commercePricingClassCPDefinitionRelIds = ParamUtil.getLongValues(
				actionRequest, "commercePricingClassCPDefinitionRelIds");
		}

		for (long deleteCommercePricingClassCPDefinitionRelId :
				commercePricingClassCPDefinitionRelIds) {

			_commercePricingClassCPDefinitionRelService.
				deleteCommercePricingClassCPDefinitionRel(
					deleteCommercePricingClassCPDefinitionRelId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommercePricingClassCPDefinitionRel(actionRequest);
			}
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	@Reference
	private CommercePricingClassCPDefinitionRelService
		_commercePricingClassCPDefinitionRelService;

}
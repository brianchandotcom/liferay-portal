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

package com.liferay.commerce.pricing.web.internal.portlet.action;

import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

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
		"javax.portlet.name=" + CommercePricingPortletKeys.COMMERCE_DISCOUNT,
		"mvc.command.name=editCommerceDiscountRule"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDiscountRuleMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceDiscountCPDefinition(
			ActionRequest actionRequest)
		throws PortalException {

		long commerceDiscountRuleId = ParamUtil.getLong(
			actionRequest, "commerceDiscountRuleId");

		CommerceDiscountRule commerceDiscountRule =
			_commerceDiscountRuleService.getCommerceDiscountRule(
				commerceDiscountRuleId);

		String type = commerceDiscountRule.getType();

		String typeSettings = commerceDiscountRule.getSettingsProperty(type);

		String[] typeSettingsArray = StringUtil.split(typeSettings);

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");

		typeSettingsArray = ArrayUtil.remove(
			typeSettingsArray, String.valueOf(cpDefinitionId));

		_commerceDiscountRuleService.updateCommerceDiscountRule(
			commerceDiscountRuleId, type, StringUtil.merge(typeSettingsArray));
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCommerceDiscountRule(actionRequest);
			}
			else {
				deleteCommerceDiscountCPDefinition(actionRequest);
			}
		}
		catch (Exception e) {
			SessionErrors.add(actionRequest, e.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	protected void updateCommerceDiscountRule(ActionRequest actionRequest)
		throws Exception {

		String type = ParamUtil.getString(actionRequest, "type");
		String typeSettings = ParamUtil.getString(
			actionRequest, "typeSettings");

		long commerceDiscountRuleId = ParamUtil.getLong(
			actionRequest, "commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			_commerceDiscountRuleService.updateCommerceDiscountRule(
				commerceDiscountRuleId, type, typeSettings);
		}
		else {
			long commerceDiscountId = ParamUtil.getLong(
				actionRequest, "commerceDiscountId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceDiscountRule.class.getName(), actionRequest);

			_commerceDiscountRuleService.addCommerceDiscountRule(
				commerceDiscountId, type, typeSettings, serviceContext);
		}
	}

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

}
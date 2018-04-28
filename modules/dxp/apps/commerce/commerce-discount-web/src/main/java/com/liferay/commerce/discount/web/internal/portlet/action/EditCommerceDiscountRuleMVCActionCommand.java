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

package com.liferay.commerce.discount.web.internal.portlet.action;

import com.liferay.commerce.discount.constants.CommerceDiscountPortletKeys;
import com.liferay.commerce.discount.exception.CommerceDiscountRuleTypeException;
import com.liferay.commerce.discount.exception.NoSuchDiscountException;
import com.liferay.commerce.discount.exception.NoSuchDiscountRuleException;
import com.liferay.commerce.discount.model.CommerceDiscountRule;
import com.liferay.commerce.discount.service.CommerceDiscountRuleService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

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
		"javax.portlet.name=" + CommerceDiscountPortletKeys.COMMERCE_DISCOUNT,
		"mvc.command.name=editCommerceDiscountRule"
	},
	service = MVCActionCommand.class
)
public class EditCommerceDiscountRuleMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceDiscountRules(ActionRequest actionRequest)
		throws PortalException {

		long[] deleteCommerceDiscountRuleIds = null;

		long commerceDiscountRuleId = ParamUtil.getLong(
			actionRequest, "commerceDiscountRuleId");

		if (commerceDiscountRuleId > 0) {
			deleteCommerceDiscountRuleIds = new long[] {commerceDiscountRuleId};
		}
		else {
			deleteCommerceDiscountRuleIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCommerceDiscountRuleIds"),
				0L);
		}

		for (long deleteCommerceDiscountRuleId :
				deleteCommerceDiscountRuleIds) {

			_commerceDiscountRuleService.deleteCommerceDiscountRule(
				deleteCommerceDiscountRuleId);
		}
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
			else if (cmd.equals(Constants.DELETE)) {
				deleteCommerceDiscountRules(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchDiscountException ||
				e instanceof NoSuchDiscountRuleException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceDiscountRuleTypeException) {
				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/edit_discount_rule.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected String getTypeSettings(
			long commerceDiscountRuleId, String type,
			ActionRequest actionRequest)
		throws PortalException {

		String typeSettings = ParamUtil.getString(
			actionRequest, "typeSettings");

		if (Validator.isNotNull(typeSettings)) {
			return typeSettings;
		}

		CommerceDiscountRule commerceDiscountRule = null;

		if (commerceDiscountRuleId > 0) {
			commerceDiscountRule =
				_commerceDiscountRuleService.getCommerceDiscountRule(
					commerceDiscountRuleId);
		}

		if ((commerceDiscountRule != null) &&
			type.equals(commerceDiscountRule.getType())) {

			typeSettings = commerceDiscountRule.getTypeSettings();
		}

		String[] typeSettingsArray = StringUtil.split(typeSettings);

		String[] addTypeSettings = ParamUtil.getStringValues(
			actionRequest, "addTypeSettings");
		String[] deleteTypeSettings = ParamUtil.getStringValues(
			actionRequest, "deleteTypeSettings");

		if (deleteTypeSettings.length > 0) {
			for (String deleteTypeSetting : deleteTypeSettings) {
				typeSettingsArray = ArrayUtil.remove(
					typeSettingsArray, deleteTypeSetting);
			}
		}

		if (addTypeSettings.length > 0) {
			typeSettingsArray = ArrayUtil.append(
				typeSettingsArray, addTypeSettings);
		}

		return StringUtil.merge(typeSettingsArray);
	}

	protected void updateCommerceDiscountRule(ActionRequest actionRequest)
		throws PortalException {

		long commerceDiscountRuleId = ParamUtil.getLong(
			actionRequest, "commerceDiscountRuleId");

		long commerceDiscountId = ParamUtil.getLong(
			actionRequest, "commerceDiscountId");

		String type = ParamUtil.getString(actionRequest, "type");

		String typeSettings = getTypeSettings(
			commerceDiscountRuleId, type, actionRequest);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceDiscountRule.class.getName(), actionRequest);

		if (commerceDiscountRuleId > 0) {
			_commerceDiscountRuleService.updateCommerceDiscountRule(
				commerceDiscountRuleId, type, typeSettings);
		}
		else {
			_commerceDiscountRuleService.addCommerceDiscountRule(
				commerceDiscountId, type, typeSettings, serviceContext);
		}
	}

	@Reference
	private CommerceDiscountRuleService _commerceDiscountRuleService;

}
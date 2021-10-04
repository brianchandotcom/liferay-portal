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

package com.liferay.commerce.order.rule.web.internal.portlet.action;

import com.liferay.commerce.order.rule.constants.CommerceOrderRuleEntryPortletKeys;
import com.liferay.commerce.order.rule.exception.NoSuchOrderRuleEntryException;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntry;
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

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
		"javax.portlet.name=" + CommerceOrderRuleEntryPortletKeys.COMMERCE_ORDER_RULE_ENTRY,
		"mvc.command.name=/commerce_order_rule_entry/edit_commerce_order_rule_entry_external_reference_code"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderRuleEntryExternalReferenceCodeMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			/*long commerceOrderRuleEntryId = ParamUtil.getLong(
				actionRequest, "commerceOrderRuleEntryId");

			CommerceOrderRuleEntry commerceOrderRuleEntry =
				_commerceOrderRuleEntryService.getCommerceOrderRuleEntry(
					commerceOrderRuleEntryId);

			String externalReferenceCode = ParamUtil.getString(
				actionRequest, "externalReferenceCode");

			_commerceOrderRuleEntryService.
				updateCommerceOrderRuleEntryExternalReferenceCode(
					externalReferenceCode,
					commerceOrderRuleEntry.getCommerceOrderRuleEntryId());*/
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchOrderRuleEntryException) {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				_log.error(exception, exception);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		EditCommerceOrderRuleEntryExternalReferenceCodeMVCActionCommand.class);

	@Reference
	private CommerceOrderRuleEntryService _commerceOrderRuleEntryService;

}
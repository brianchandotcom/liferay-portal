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
import com.liferay.commerce.order.rule.service.CommerceOrderRuleEntryRelService;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Objects;

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
		"mvc.command.name=/commerce_order_rule_entry/edit_commerce_order_rule_entry_qualifiers"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderRuleEntryQualifiersMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			/*if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				String channelQualifiers = ParamUtil.getString(
					actionRequest, "channelQualifiers");

				if (Objects.equals(channelQualifiers, "all")) {
					long commerceOrderRuleEntryId = ParamUtil.getLong(
						actionRequest, "commerceOrderRuleEntryId");

					_commerceOrderRuleEntryRelService.
						deleteCommerceOrderRuleEntryRels(
							CommerceChannel.class.getName(),
							commerceOrderRuleEntryId);
				}
			}*/
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");
		}
	}

	@Reference
	private CommerceOrderRuleEntryRelService _commerceOrderRuleEntryRelService;

}
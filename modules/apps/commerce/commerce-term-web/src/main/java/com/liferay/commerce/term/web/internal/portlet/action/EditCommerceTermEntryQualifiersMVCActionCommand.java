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

package com.liferay.commerce.term.web.internal.portlet.action;

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.term.constants.CommerceTermEntryPortletKeys;
import com.liferay.commerce.term.exception.DuplicateCommerceTermEntryRelException;
import com.liferay.commerce.term.service.CommerceTermEntryRelService;
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
		"javax.portlet.name=" + CommerceTermEntryPortletKeys.COMMERCE_TERM_ENTRY,
		"mvc.command.name=/commerce_term_entry/edit_commerce_term_entry_qualifiers"
	},
	service = MVCActionCommand.class
)
public class EditCommerceTermEntryQualifiersMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				_updateCommerceTermEntryQualifiers(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof DuplicateCommerceTermEntryRelException) {
				SessionErrors.add(actionRequest, exception.getClass());

				hideDefaultErrorMessage(actionRequest);
				hideDefaultSuccessMessage(actionRequest);

				String redirect = ParamUtil.getString(
					actionRequest, "redirect");

				sendRedirect(actionRequest, actionResponse, redirect);
			}
			else {
				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
		}
	}

	private void _updateCommerceTermEntryQualifiers(ActionRequest actionRequest)
		throws Exception {

		String orderTypeQualifiers = ParamUtil.getString(
			actionRequest, "orderTypeQualifiers");

		if (Objects.equals(orderTypeQualifiers, "all")) {
			_commerceTermEntryRelService.deleteCommerceTermEntryRels(
				CommerceOrderType.class.getName(),
				ParamUtil.getLong(actionRequest, "commerceTermEntryId"));
		}
	}

	@Reference
	private CommerceTermEntryRelService _commerceTermEntryRelService;

}
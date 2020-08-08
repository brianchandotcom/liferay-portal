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

package com.liferay.commerce.subscription.web.internal.portlet.action;

import com.liferay.commerce.exception.CommerceSubscriptionEntrySubscriptionStatusException;
import com.liferay.commerce.exception.CommerceSubscriptionTypeException;
import com.liferay.commerce.exception.NoSuchSubscriptionEntryException;
import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.subscription.CommerceSubscriptionEntryActionHelper;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.COMMERCE_SUBSCRIPTION_CONTENT_WEB,
		"mvc.command.name=editCommerceSubscriptionEntry"
	},
	service = MVCActionCommand.class
)
public class EditCommerceSubscriptionContentMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long commerceSubscriptionEntryId = ParamUtil.getLong(
			actionRequest, "commerceSubscriptionEntryId");

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals("activate")) {
				_commerceSubscriptionEntryActionHelper.
					activateCommerceSubscriptionEntry(
						commerceSubscriptionEntryId);
			}
			else if (cmd.equals("cancel")) {
				_commerceSubscriptionEntryActionHelper.
					cancelCommerceSubscriptionEntry(
						commerceSubscriptionEntryId);
			}
			else if (cmd.equals("suspend")) {
				_commerceSubscriptionEntryActionHelper.
					suspendCommerceSubscriptionEntry(
						commerceSubscriptionEntryId);
			}
		}
		catch (Exception exception) {
			if (exception instanceof
					CommerceSubscriptionEntrySubscriptionStatusException ||
				exception instanceof CommerceSubscriptionTypeException) {

				hideDefaultErrorMessage(actionRequest);

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter(
					"mvcRenderCommandName", "editCommerceSubscriptionEntry");
			}
			else if (exception instanceof NoSuchSubscriptionEntryException ||
					 exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw exception;
			}
		}
	}

	@Reference
	private CommerceSubscriptionEntryActionHelper
		_commerceSubscriptionEntryActionHelper;

}
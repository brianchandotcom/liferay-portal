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

package com.liferay.commerce.order.web.internal.portlet.action;

import com.liferay.commerce.constants.CommercePortletKeys;
import com.liferay.commerce.exception.CommerceOrderNoteContentException;
import com.liferay.commerce.exception.NoSuchOrderNoteException;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.service.CommerceOrderNoteService;
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
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CommercePortletKeys.COMMERCE_ORDER,
		"mvc.command.name=/commerce_order/edit_commerce_order_note"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderNoteMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				_deleteCommerceOrderNote(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				_updateCommerceOrderNote(actionRequest);
			}
		}
		catch (Exception exception) {
			if (exception instanceof NoSuchOrderNoteException ||
				exception instanceof PrincipalException) {

				SessionErrors.add(actionRequest, exception.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (exception instanceof CommerceOrderNoteContentException) {
				SessionErrors.add(actionRequest, exception.getClass());
			}
			else {
				throw exception;
			}
		}
	}

	private void _deleteCommerceOrderNote(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderNoteId = ParamUtil.getLong(
			actionRequest, "commerceOrderNoteId");

		_commerceOrderNoteService.deleteCommerceOrderNote(commerceOrderNoteId);
	}

	private void _updateCommerceOrderNote(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderNoteId = ParamUtil.getLong(
			actionRequest, "commerceOrderNoteId");

		String content = ParamUtil.getString(actionRequest, "content");
		boolean restricted = ParamUtil.getBoolean(actionRequest, "restricted");

		if (commerceOrderNoteId <= 0) {
			long commerceOrderId = ParamUtil.getLong(
				actionRequest, "commerceOrderId");

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				CommerceOrderNote.class.getName(), actionRequest);

			_commerceOrderNoteService.addCommerceOrderNote(
				commerceOrderId, content, restricted, serviceContext);
		}
		else {
			_commerceOrderNoteService.updateCommerceOrderNote(
				commerceOrderNoteId, content, restricted);
		}
	}

	@Reference
	private CommerceOrderNoteService _commerceOrderNoteService;

}
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

package com.liferay.commerce.organization.order.web.internal.portlet.action;

import com.liferay.commerce.exception.CommerceOrderNoteContentException;
import com.liferay.commerce.exception.NoSuchOrderNoteException;
import com.liferay.commerce.model.CommerceOrderNote;
import com.liferay.commerce.organization.order.web.internal.constants.CommerceOrganizationOrderPortletKeys;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + CommerceOrganizationOrderPortletKeys.COMMERCE_ORGANIZATION_ORDER,
		"mvc.command.name=editCommerceOrderNote"
	},
	service = MVCActionCommand.class
)
public class EditCommerceOrderNoteMVCActionCommand
	extends BaseMVCActionCommand {

	protected void deleteCommerceOrderNote(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderNoteId = ParamUtil.getLong(
			actionRequest, "commerceOrderNoteId");

		_commerceOrderNoteService.deleteCommerceOrderNote(commerceOrderNoteId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.DELETE)) {
				deleteCommerceOrderNote(actionRequest);
			}
			else if (cmd.equals(Constants.ADD) ||
					 cmd.equals(Constants.UPDATE)) {

				updateCommerceOrderNote(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrderNoteException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else if (e instanceof CommerceOrderNoteContentException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected void updateCommerceOrderNote(ActionRequest actionRequest)
		throws Exception {

		long commerceOrderNoteId = ParamUtil.getLong(
			actionRequest, "commerceOrderNoteId");

		long commerceOrderId = ParamUtil.getLong(
			actionRequest, "commerceOrderId");
		String content = ParamUtil.getString(actionRequest, "content");
		boolean restricted = ParamUtil.getBoolean(actionRequest, "restricted");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CommerceOrderNote.class.getName(), actionRequest);

		if (commerceOrderNoteId <= 0) {
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
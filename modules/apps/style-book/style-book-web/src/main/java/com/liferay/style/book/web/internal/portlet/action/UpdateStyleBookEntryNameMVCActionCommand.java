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

package com.liferay.style.book.web.internal.portlet.action;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.service.StyleBookEntryService;
import com.liferay.style.book.web.internal.handler.StyleBookEntryExceptionRequestHandler;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + StyleBookPortletKeys.STYLE_BOOK,
		"mvc.command.name=/style_book/update_style_book_entry_name"
	},
	service = MVCActionCommand.class
)
public class UpdateStyleBookEntryNameMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			_updateStyleBookEntry(actionRequest);

			if (SessionErrors.contains(
					actionRequest, "styleBookEntryNameInvalid")) {

				addSuccessMessage(actionRequest, actionResponse);
			}

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			JSONObject jsonObject = JSONUtil.put("redirectURL", redirect);

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException portalException) {
			SessionErrors.add(actionRequest, "styleBookEntryNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_styleBookEntryExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, portalException);
		}
	}

	private void _updateStyleBookEntry(ActionRequest actionRequest)
		throws PortalException {

		long styleBookEntryId = ParamUtil.getLong(
			actionRequest, "styleBookEntryId");

		String name = ParamUtil.getString(actionRequest, "name");

		_styleBookEntryService.updateName(styleBookEntryId, name);
	}

	@Reference
	private StyleBookEntryExceptionRequestHandler
		_styleBookEntryExceptionRequestHandler;

	@Reference
	private StyleBookEntryService _styleBookEntryService;

}
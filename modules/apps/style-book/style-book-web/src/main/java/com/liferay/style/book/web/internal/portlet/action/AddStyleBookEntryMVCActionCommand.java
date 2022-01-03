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

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.style.book.constants.StyleBookPortletKeys;
import com.liferay.style.book.model.StyleBookEntry;
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
		"mvc.command.name=/style_book/add_style_book_entry"
	},
	service = MVCActionCommand.class
)
public class AddStyleBookEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			StyleBookEntry styleBookEntry = _addStyleBookEntry(actionRequest);

			JSONObject jsonObject = JSONUtil.put(
				"redirectURL", _getRedirectURL(actionResponse, styleBookEntry));

			if (SessionErrors.contains(
					actionRequest, "styleBookEntryNameInvalid")) {

				addSuccessMessage(actionRequest, actionResponse);
			}

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

	private StyleBookEntry _addStyleBookEntry(ActionRequest actionRequest)
		throws PortalException {

		String name = ParamUtil.getString(actionRequest, "name");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		return _styleBookEntryService.addStyleBookEntry(
			serviceContext.getScopeGroupId(), name, StringPool.BLANK,
			serviceContext);
	}

	private String _getRedirectURL(
		ActionResponse actionResponse, StyleBookEntry styleBookEntry) {

		return PortletURLBuilder.createRenderURL(
			_portal.getLiferayPortletResponse(actionResponse)
		).setMVCRenderCommandName(
			"/style_book/edit_style_book_entry"
		).setParameter(
			"styleBookEntryId", styleBookEntry.getStyleBookEntryId()
		).buildString();
	}

	@Reference
	private Portal _portal;

	@Reference
	private StyleBookEntryExceptionRequestHandler
		_styleBookEntryExceptionRequestHandler;

	@Reference
	private StyleBookEntryService _styleBookEntryService;

}
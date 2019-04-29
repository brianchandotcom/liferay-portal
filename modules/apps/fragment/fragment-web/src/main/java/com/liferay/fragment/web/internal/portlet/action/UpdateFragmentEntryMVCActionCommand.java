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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.fragment.web.internal.handler.FragmentEntryExceptionRequestHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/update_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class UpdateFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		String name = ParamUtil.getString(actionRequest, "name");

		try {
			FragmentEntry fragmentEntry =
				_fragmentEntryService.updateFragmentEntry(
					fragmentEntryId, name);

			JSONObject jsonObject = JSONUtil.put(
				"redirectURL", getRedirectURL(actionResponse, fragmentEntry));

			if (SessionErrors.contains(actionRequest, "fragmentNameInvalid")) {
				addSuccessMessage(actionRequest, actionResponse);
			}

			JSONPortletResponseUtil.writeJSON(
				actionRequest, actionResponse, jsonObject);
		}
		catch (PortalException pe) {
			SessionErrors.add(actionRequest, "fragmentNameInvalid");

			hideDefaultErrorMessage(actionRequest);

			_fragmentEntryExceptionRequestHandler.handlePortalException(
				actionRequest, actionResponse, pe);
		}
	}

	protected String getRedirectURL(
		ActionResponse actionResponse, FragmentEntry fragmentEntry) {

		LiferayPortletResponse liferayPortletResponse =
			_portal.getLiferayPortletResponse(actionResponse);

		PortletURL portletURL = liferayPortletResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/view_fragment_entries");
		portletURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentEntry.getFragmentCollectionId()));

		return portletURL.toString();
	}

	@Reference
	private FragmentEntryExceptionRequestHandler
		_fragmentEntryExceptionRequestHandler;

	@Reference
	private FragmentEntryService _fragmentEntryService;

	@Reference
	private Portal _portal;

}
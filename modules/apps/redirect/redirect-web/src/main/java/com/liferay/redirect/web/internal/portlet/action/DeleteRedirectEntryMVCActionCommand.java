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

package com.liferay.redirect.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.redirect.service.RedirectEntryService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + RedirectPortletKeys.REDIRECT,
		"mvc.command.name=/redirect/delete_redirect_entry"
	},
	service = MVCActionCommand.class
)
public class DeleteRedirectEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long redirectEntryId = ParamUtil.getLong(
			actionRequest, "redirectEntryId");

		if (redirectEntryId > 0) {
			_redirectEntryService.deleteRedirectEntry(redirectEntryId);
		}
		else {
			long[] deleteRedirectEntryIds = ParamUtil.getLongValues(
				actionRequest, "rowIds");

			for (long deleteRedirectEntryId : deleteRedirectEntryIds) {
				_redirectEntryService.deleteRedirectEntry(
					deleteRedirectEntryId);
			}
		}
	}

	@Reference
	private RedirectEntryService _redirectEntryService;

}
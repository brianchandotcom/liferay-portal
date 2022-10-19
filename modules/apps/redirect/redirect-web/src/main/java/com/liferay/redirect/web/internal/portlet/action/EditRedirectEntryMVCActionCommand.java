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
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryService;
import com.liferay.redirect.web.internal.constants.RedirectPortletKeys;
import com.liferay.redirect.web.internal.util.RedirectUtil;

import java.util.Date;

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
		"mvc.command.name=/redirect/edit_redirect_entry"
	},
	service = MVCActionCommand.class
)
public class EditRedirectEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long redirectEntryId = ParamUtil.getLong(
			actionRequest, "redirectEntryId");

		String destinationURL = ParamUtil.getString(
			actionRequest, "destinationURL");

		if (Validator.isNotNull(destinationURL) &&
			!HttpComponentsUtil.hasProtocol(destinationURL)) {

			destinationURL =
				HttpComponentsUtil.getProtocol(actionRequest) + "://" +
					destinationURL;
		}

		Date expirationDate = _getExpirationDate(actionRequest, themeDisplay);
		boolean permanent = ParamUtil.getBoolean(actionRequest, "permanent");
		String sourceURL = ParamUtil.getString(actionRequest, "sourceURL");
		boolean updateChainedRedirectEntries = ParamUtil.getBoolean(
			actionRequest, "updateChainedRedirectEntries");

		try {
			if (redirectEntryId == 0) {
				_redirectEntryService.addRedirectEntry(
					themeDisplay.getScopeGroupId(), destinationURL,
					expirationDate, RedirectUtil.getGroupBaseURL(themeDisplay),
					permanent, sourceURL, updateChainedRedirectEntries,
					ServiceContextFactory.getInstance(
						RedirectEntry.class.getName(), actionRequest));
			}
			else {
				_redirectEntryService.updateRedirectEntry(
					redirectEntryId, destinationURL, expirationDate,
					RedirectUtil.getGroupBaseURL(themeDisplay), permanent,
					sourceURL, updateChainedRedirectEntries);
			}
		}
		catch (Exception exception) {
			SessionErrors.add(actionRequest, exception.getClass(), exception);

			actionResponse.setRenderParameter(
				"mvcRenderCommandName", "/redirect/edit_redirect_entry");

			hideDefaultSuccessMessage(actionRequest);
		}
	}

	private Date _getExpirationDate(
		ActionRequest actionRequest, ThemeDisplay themeDisplay) {

		String expirationDateString = ParamUtil.getString(
			actionRequest, "expirationDate");

		if (Validator.isNull(expirationDateString)) {
			return null;
		}

		return GetterUtil.getDate(
			expirationDateString,
			DateFormatFactoryUtil.getSimpleDateFormat(
				"yyyy-MM-dd", themeDisplay.getLocale()),
			null);
	}

	@Reference
	private RedirectEntryService _redirectEntryService;

}
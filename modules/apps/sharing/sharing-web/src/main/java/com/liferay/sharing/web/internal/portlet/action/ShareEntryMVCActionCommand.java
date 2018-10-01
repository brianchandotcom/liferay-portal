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

package com.liferay.sharing.web.internal.portlet.action;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.sharing.service.SharingEntryService;
import com.liferay.sharing.web.constants.SharingPortletKeys;
import com.liferay.sharing.web.internal.display.SharingEntryPermissionDisplayAction;

import java.util.Date;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"javax.portlet.name=" + SharingPortletKeys.SHARING,
		"mvc.command.name=/sharing/share"
	},
	service = MVCActionCommand.class
)
public class ShareEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long classNameId = ParamUtil.getLong(actionRequest, "classNameId");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		boolean shareable = ParamUtil.getBoolean(actionRequest, "shareable");
		String sharingEntryPermissionDisplayActionId = ParamUtil.getString(
			actionRequest, "sharingEntryPermissionDisplayActionId");
		String userEmailAddress = ParamUtil.getString(
			actionRequest, "userEmailAddress");

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Date expirationDate = ParamUtil.getDate(
			actionRequest, "expirationDate",
			DateFormatFactoryUtil.getDate(themeDisplay.getLocale()), null);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		SharingEntryPermissionDisplayAction
			sharingEntryPermissionDisplayAction =
				SharingEntryPermissionDisplayAction.parseFromActionId(
					sharingEntryPermissionDisplayActionId);

		String[] userEmailAddresses = StringUtil.split(userEmailAddress);

		for (String curUserEmailAddresses : userEmailAddresses) {
			User user = _userLocalService.fetchUserByEmailAddress(
				themeDisplay.getCompanyId(), curUserEmailAddresses);

			if (user != null) {
				_sharingEntryService.addOrUpdateSharingEntry(
					user.getUserId(), classNameId, classPK,
					themeDisplay.getScopeGroupId(), shareable,
					sharingEntryPermissionDisplayAction.
						getSharingEntryActions(),
					expirationDate, serviceContext);
			}
		}
	}

	@Reference
	private SharingEntryService _sharingEntryService;

	@Reference
	private UserLocalService _userLocalService;

}
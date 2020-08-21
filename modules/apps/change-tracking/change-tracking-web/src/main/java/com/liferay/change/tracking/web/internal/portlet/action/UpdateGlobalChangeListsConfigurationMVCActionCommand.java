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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.permission.PortletPermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.CHANGE_LISTS_CONFIGURATION,
		"mvc.command.name=/change_lists/update_global_change_lists_configuration"
	},
	service = MVCActionCommand.class
)
public class UpdateGlobalChangeListsConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_portletPermission.check(
			themeDisplay.getPermissionChecker(),
			CTPortletKeys.CHANGE_LISTS_CONFIGURATION, ActionKeys.CONFIGURATION);

		boolean enableChangeLists = ParamUtil.getBoolean(
			actionRequest, "enableChangeLists");

		if (enableChangeLists) {
			List<Group> groups = _groupLocalService.dslQuery(
				DSLQueryFactoryUtil.select(
					GroupTable.INSTANCE
				).from(
					GroupTable.INSTANCE
				).where(
					GroupTable.INSTANCE.companyId.eq(
						themeDisplay.getCompanyId()
					).and(
						GroupTable.INSTANCE.liveGroupId.neq(
							GroupConstants.DEFAULT_LIVE_GROUP_ID
						).or(
							GroupTable.INSTANCE.typeSettings.like(
								"%staged=true%")
						).withParentheses()
					)
				));

			for (Group group : groups) {
				if (group.isStaged() || group.isStagingGroup()) {
					SessionErrors.add(actionRequest, "stagingEnabled");

					return;
				}
			}

			_ctPreferencesLocalService.getCTPreferences(
				themeDisplay.getCompanyId(), 0);
		}
		else {
			CTPreferences ctPreferences =
				_ctPreferencesLocalService.fetchCTPreferences(
					themeDisplay.getCompanyId(), 0);

			if (ctPreferences != null) {
				_ctPreferencesLocalService.deleteCTPreferences(ctPreferences);
			}
		}

		HttpServletRequest httpServletRequest = _portal.getHttpServletRequest(
			actionRequest);

		boolean redirectToOverview = ParamUtil.getBoolean(
			actionRequest, "redirectToOverview");

		if (redirectToOverview) {
			hideDefaultSuccessMessage(actionRequest);

			SessionMessages.add(
				httpServletRequest, "requestProcessed",
				_language.get(
					httpServletRequest, "the-configuration-has-been-saved"));

			PortletURL redirectURL = PortletURLFactoryUtil.create(
				actionRequest, CTPortletKeys.CHANGE_LISTS,
				PortletRequest.RENDER_PHASE);

			sendRedirect(actionRequest, actionResponse, redirectURL.toString());
		}
		else {
			SessionMessages.add(
				actionRequest, "requestProcessed",
				_language.get(
					httpServletRequest, "the-configuration-has-been-saved"));
		}
	}

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PortletPermission _portletPermission;

}
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

package com.liferay.user.associated.data.web.internal.portlet.action;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.model.PortalPreferences;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferenceValueLocalService;
import com.liferay.portal.kernel.service.PortalPreferencesLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Fernando Vilela
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + ConfigurationAdminPortletKeys.INSTANCE_SETTINGS,
		"mvc.command.name=/portal_settings/save_user_layout_configuration"
	},
	service = MVCActionCommand.class
)
public class SaveUserLayoutConfigurationMVCActionCommand
	extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long companyId = themeDisplay.getCompanyId();

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		if (!permissionChecker.isCompanyAdmin(companyId)) {
			SessionErrors.add(actionRequest, PrincipalException.class);

			actionResponse.setRenderParameter("mvcPath", "/error.jsp");

			return;
		}

		PropsUtil.set(
			PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED,
			ParamUtil.getString(actionRequest, "userPrivateLayout"));

		PropsUtil.set(
			PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE,
			ParamUtil.getString(actionRequest, "userPrivateLayoutAutoCreate"));

		PropsUtil.set(
			PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED,
			ParamUtil.getString(actionRequest, "userPublicLayout"));

		PropsUtil.set(
			PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE,
			ParamUtil.getString(actionRequest, "userPublicLayoutAutoCreate"));

		PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_ENABLED));

		PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE =
			GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.LAYOUT_USER_PRIVATE_LAYOUTS_AUTO_CREATE));

		PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED = GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_ENABLED));

		PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE =
			GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.LAYOUT_USER_PUBLIC_LAYOUTS_AUTO_CREATE));

		PortalPreferences portalPreferences =
			_portalPreferencesLocalService.fetchPortalPreferences(
				companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY);

		com.liferay.portal.kernel.portlet.PortalPreferences
			newPortalPreferences =
				_portalPreferenceValueLocalService.getPortalPreferences(
					portalPreferences, false);

		newPortalPreferences.setValue(
			null, "userPrivateLayout",
			ParamUtil.getString(actionRequest, "userPrivateLayout"));

		newPortalPreferences.setValue(
			null, "userPrivateLayoutAutoCreate",
			ParamUtil.getString(actionRequest, "userPrivateLayoutAutoCreate"));

		newPortalPreferences.setValue(
			null, "userPublicLayout",
			ParamUtil.getString(actionRequest, "userPublicLayout"));

		newPortalPreferences.setValue(
			null, "userPublicLayoutAutoCreate",
			ParamUtil.getString(actionRequest, "userPublicLayoutAutoCreate"));

		_portalPreferencesLocalService.updatePreferences(
			companyId, PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			newPortalPreferences);
	}

	@Reference
	private PortalPreferencesLocalService _portalPreferencesLocalService;

	@Reference
	private PortalPreferenceValueLocalService
		_portalPreferenceValueLocalService;

}
/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.web.internal.configuration.helper.CTSettingsConfigurationHelper;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gislayne Vitorino
 */
@Component(
	property = {
		"jakarta.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/update_permissions"
	},
	service = MVCActionCommand.class
)
public class UpdatePermissionsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String permissionsJSON = ParamUtil.getString(
			actionRequest, "permissions");

		List<Map<String, Object>> rolePermissions =
			(List<Map<String, Object>>)_jsonFactory.looseDeserialize(
				permissionsJSON);

		for (Map<String, Object> rolePermission : rolePermissions) {
			List<String> actionIds = (List<String>)rolePermission.get(
				"actionIds");

			String roleName = (String)rolePermission.get("roleName");

			Role role = _roleLocalService.getRole(
				themeDisplay.getCompanyId(), roleName);

			if (roleName.equals(RoleConstants.OWNER)) {
				_ctSettingsConfigurationHelper.save(
					themeDisplay.getCompanyId(),
					HashMapBuilder.<String, Object>put(
						"defaultOwnerActionIds",
						actionIds.toArray(new String[0])
					).build());
			}

			List<String> modelResourceOwnerDefaultActions =
				ResourceActionsUtil.getModelResourceOwnerDefaultActions(
					CTCollection.class.getName());

			List<String> availableResourcePermissions =
				_resourcePermissionLocalService.
					getAvailableResourcePermissionActionIds(
						themeDisplay.getCompanyId(),
						CTCollection.class.getName(),
						ResourceConstants.SCOPE_COMPANY,
						String.valueOf(themeDisplay.getCompanyId()),
						role.getRoleId(), modelResourceOwnerDefaultActions);

			try (SafeCloseable safeCloseable =
					CTCollectionThreadLocal.
						setProductionModeWithSafeCloseable()) {

				for (String actionId : modelResourceOwnerDefaultActions) {
					boolean hasActionId = actionIds.contains(actionId);
					boolean hasPermission =
						availableResourcePermissions.contains(actionId);

					if (hasActionId && !hasPermission) {
						_resourcePermissionLocalService.addResourcePermission(
							themeDisplay.getCompanyId(),
							CTCollection.class.getName(),
							ResourceConstants.SCOPE_COMPANY,
							String.valueOf(themeDisplay.getCompanyId()),
							role.getRoleId(), actionId);
					}
					else if (!hasActionId && hasPermission) {
						_resourcePermissionLocalService.
							removeResourcePermission(
								themeDisplay.getCompanyId(),
								CTCollection.class.getName(),
								ResourceConstants.SCOPE_COMPANY,
								String.valueOf(themeDisplay.getCompanyId()),
								role.getRoleId(), actionId);
					}
				}
			}
		}
	}

	@Reference
	private CTSettingsConfigurationHelper _ctSettingsConfigurationHelper;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}
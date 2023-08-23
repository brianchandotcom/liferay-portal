/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.instance.lifecycle;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.constants.CTRoleConstants;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class AddDefaultRolesPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		Role role = _roleLocalService.fetchRole(
			company.getCompanyId(), CTRoleConstants.PUBLICATIONS_REVIEWER);

		if (role == null) {
			User guestUser = company.getGuestUser();

			role = _roleLocalService.addRole(
				guestUser.getUserId(), null, 0,
				CTRoleConstants.PUBLICATIONS_REVIEWER, null,
				HashMapBuilder.put(
					LocaleUtil.getDefault(),
					"Guest users who have access to a publication should be " +
						"assigned this role."
				).build(),
				RoleConstants.TYPE_PUBLICATIONS, null, null);
		}

		for (String resourceAction : _portletResourceActions) {
			ResourcePermission portletResourcePermission =
				_resourcePermissionLocalService.fetchResourcePermission(
					company.getCompanyId(), CTPortletKeys.PUBLICATIONS,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(company.getCompanyId()), role.getRoleId());

			if ((portletResourcePermission == null) ||
				!portletResourcePermission.hasActionId(resourceAction)) {

				_resourcePermissionLocalService.addResourcePermission(
					company.getCompanyId(), CTPortletKeys.PUBLICATIONS,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(company.getCompanyId()), role.getRoleId(),
					resourceAction);
			}
		}

		ResourcePermission modelResourcePermission =
			_resourcePermissionLocalService.fetchResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				role.getRoleId());

		if ((modelResourcePermission == null) ||
			!modelResourcePermission.hasActionId(ActionKeys.VIEW)) {

			_resourcePermissionLocalService.addResourcePermission(
				company.getCompanyId(), CTCollection.class.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				role.getRoleId(), ActionKeys.VIEW);
		}
	}

	private static final List<String> _portletResourceActions = Arrays.asList(
		ActionKeys.ACCESS_IN_CONTROL_PANEL, ActionKeys.VIEW);

	@Reference(
		target = "(javax.portlet.name=" + CTPortletKeys.PUBLICATIONS + ")"
	)
	private Portlet _portlet;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}
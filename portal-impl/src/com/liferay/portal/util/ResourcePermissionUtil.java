/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Resource;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.ResourcePermissionLocalServiceUtil;

import java.util.List;

/**
 * @author Juan Fernández
 * @author Sergio González
 */
public class ResourcePermissionUtil {

	public static void populateResourcePermissionActionIds(
			long groupId, Role role, Resource resource, List<String> actions,
			List<String> individualActions, List<String> groupActions,
			List<String> groupTemplateActions, List<String> companyActions)
		throws PortalException {

		individualActions.addAll(
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					resource.getCompanyId(), resource.getName(),
					resource.getScope(), resource.getPrimKey(),
					role.getRoleId(), actions));

		groupActions.addAll(
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					resource.getCompanyId(), resource.getName(),
					ResourceConstants.SCOPE_GROUP, String.valueOf(groupId),
					role.getRoleId(), actions));

		groupTemplateActions.addAll(
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					resource.getCompanyId(), resource.getName(),
					ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
					role.getRoleId(), actions));

		companyActions.addAll(
			ResourcePermissionLocalServiceUtil.
				getAvailableResourcePermissionActionIds(
					resource.getCompanyId(), resource.getName(),
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(resource.getCompanyId()), role.getRoleId(),
					actions));
	}

}
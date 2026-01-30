/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.model.listener;

import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pedro Leite
 */
@Component(service = ModelListener.class)
public class ListTypeDefinitionModelListener
	extends BaseModelListener<ListTypeDefinition> {

	@Override
	public void onAfterCreate(ListTypeDefinition listTypeDefinition)
		throws ModelListenerException {

		try {
			if (!StringUtil.equals(
					listTypeDefinition.getExternalReferenceCode(),
					"L_CMP_STATES")) {

				return;
			}

			Role role = _roleLocalService.getRole(
				listTypeDefinition.getCompanyId(), RoleConstants.USER);

			_resourcePermissionLocalService.setResourcePermissions(
				listTypeDefinition.getCompanyId(),
				ListTypeDefinition.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(listTypeDefinition.getListTypeDefinitionId()),
				role.getRoleId(), new String[] {ActionKeys.VIEW});
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}
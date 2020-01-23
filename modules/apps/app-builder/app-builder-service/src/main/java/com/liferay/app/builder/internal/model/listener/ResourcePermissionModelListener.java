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

package com.liferay.app.builder.internal.model.listener;

import com.liferay.app.builder.constants.AppBuilderConstants;
import com.liferay.data.engine.constants.DataEngineConstants;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.vulcan.permission.ModelPermissionsUtil;
import com.liferay.portal.vulcan.permission.Permission;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = ModelListener.class)
public class ResourcePermissionModelListener
	extends BaseModelListener<ResourcePermission> {

	@Override
	public void onAfterUpdate(ResourcePermission resourcePermission)
		throws ModelListenerException {

		if (!Objects.equals(
				AppBuilderConstants.RESOURCE_NAME,
				resourcePermission.getName())) {

			return;
		}

		try {
			ResourceAction resourceAction =
				_resourceActionLocalService.getResourceAction(
					resourcePermission.getName(), "MANAGE");

			if (!resourcePermission.hasAction(resourceAction)) {
				return;
			}

			Company company = _companyLocalService.getCompany(
				resourcePermission.getCompanyId());

			long siteGroupId = _portal.getSiteGroupId(company.getGroupId());

			_resourcePermissionLocalService.updateResourcePermissions(
				company.getCompanyId(), siteGroupId,
				DataEngineConstants.RESOURCE_NAME, String.valueOf(siteGroupId),
				ModelPermissionsUtil.toModelPermissions(
					company.getCompanyId(), _getPermissions(resourcePermission),
					siteGroupId, DataEngineConstants.RESOURCE_NAME,
					_resourceActionLocalService,
					_resourcePermissionLocalService, _roleLocalService));
		}
		catch (PortalException portalException) {
			_log.error(portalException, portalException);
		}
	}

	private Permission[] _getPermissions(ResourcePermission resourcePermission)
		throws PortalException {

		Role role = _roleLocalService.getRole(resourcePermission.getRoleId());

		return new Permission[] {
			new Permission() {
				{
					setActionIds(
						new String[] {
							"ADD_DATA_DEFINITION", "ADD_DATA_RECORD_COLLECTION"
						});
					setRoleName(role.getName());
				}
			}
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ResourcePermissionModelListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceActionLocalService _resourceActionLocalService;

	@Reference
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

}
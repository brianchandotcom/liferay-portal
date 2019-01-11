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

package com.liferay.data.engine.internal.security.permission;

import com.liferay.data.engine.constants.DEDataRecordCollectionConstants;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.data.engine.model.DEDataRecordCollection",
	service = ModelResourcePermission.class
)
public class DEDataRecordCollectionModelResourcePermission
	implements ModelResourcePermission<DEDataRecordCollection> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			DEDataRecordCollection deDataRecordCollection, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, deDataRecordCollection, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				DEDataRecordCollectionConstants.MODEL_RESOURCE_NAME,
				deDataRecordCollection.getDEDataRecordCollectionId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.setDEDataRecordCollectionId(primaryKey);

		check(permissionChecker, deDataRecordCollection, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			DEDataRecordCollection deDataRecordCollection, String actionId)
		throws PortalException {

		DDLRecordSet ddlRecordSet = ddlRecordSetLocalService.getRecordSet(
			deDataRecordCollection.getDEDataRecordCollectionId());

		if (permissionChecker.hasOwnerPermission(
				ddlRecordSet.getCompanyId(),
				DEDataRecordCollectionConstants.MODEL_RESOURCE_NAME,
				deDataRecordCollection.getDEDataRecordCollectionId(),
				ddlRecordSet.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			ddlRecordSet.getGroupId(),
			DEDataRecordCollectionConstants.MODEL_RESOURCE_NAME,
			deDataRecordCollection.getDEDataRecordCollectionId(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		DEDataRecordCollection deDataRecordCollection =
			new DEDataRecordCollection();

		deDataRecordCollection.setDEDataRecordCollectionId(primaryKey);

		return contains(permissionChecker, deDataRecordCollection, actionId);
	}

	@Override
	public String getModelName() {
		return DEDataRecordCollectionConstants.MODEL_RESOURCE_NAME;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

}
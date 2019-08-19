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

package com.liferay.data.engine.rest.internal.security.permission;

import com.liferay.data.engine.rest.internal.constants.DataDefinitionConstants;
import com.liferay.data.engine.rest.internal.constants.DataRecordCollectionConstants;
import com.liferay.data.engine.rest.internal.model.InternalDataRecordCollection;
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
	property = "model.class.name=" + DataRecordCollectionConstants.RESOURCE_NAME,
	service = ModelResourcePermission.class
)
public class InternalDataRecordCollectionModelResourcePermission
	implements ModelResourcePermission<InternalDataRecordCollection> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			InternalDataRecordCollection internalDataRecordCollection,
			String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, internalDataRecordCollection, actionId)) {

			throw new PrincipalException.MustHavePermission(
				permissionChecker, DataDefinitionConstants.RESOURCE_NAME,
				(long)internalDataRecordCollection.getPrimaryKeyObj(),
				actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		InternalDataRecordCollection internalDataRecordCollection =
			new InternalDataRecordCollection();

		internalDataRecordCollection.setPrimaryKeyObj(primaryKey);

		check(permissionChecker, internalDataRecordCollection, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			InternalDataRecordCollection internalDataRecordCollection,
			String actionId)
		throws PortalException {

		DDLRecordSet recordSet = ddlRecordSetLocalService.getRecordSet(
			(long)internalDataRecordCollection.getPrimaryKeyObj());

		if (permissionChecker.hasOwnerPermission(
				recordSet.getCompanyId(),
				InternalDataRecordCollection.class.getName(),
				(long)internalDataRecordCollection.getPrimaryKeyObj(),
				recordSet.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			recordSet.getGroupId(),
			InternalDataRecordCollection.class.getName(),
			(long)internalDataRecordCollection.getPrimaryKeyObj(), actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long primaryKey,
			String actionId)
		throws PortalException {

		InternalDataRecordCollection internalDataRecordCollection =
			new InternalDataRecordCollection();

		internalDataRecordCollection.setPrimaryKeyObj(primaryKey);

		return contains(
			permissionChecker, internalDataRecordCollection, actionId);
	}

	@Override
	public String getModelName() {
		return InternalDataRecordCollection.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

}
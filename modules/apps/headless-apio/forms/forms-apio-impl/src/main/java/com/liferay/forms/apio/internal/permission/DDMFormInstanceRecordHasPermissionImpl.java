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

package com.liferay.forms.apio.internal.permission;

import com.liferay.apio.architect.alias.routes.permission.HasNestedAddingPermissionFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.dynamic.data.lists.constants.DDLActionKeys;
import com.liferay.forms.apio.architect.identifier.FormInstanceRecordIdentifier;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.dynamic.data.mapping.model.DDMFormInstanceRecord"
)
public class DDMFormInstanceRecordHasPermissionImpl
	implements HasPermission<Long> {

	@Override
	public <S> HasNestedAddingPermissionFunction<S> forAddingIn(
		Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(FormInstanceRecordIdentifier.class)) {
			return (credentials, recordSetId) ->
				_ddlRecordSetModelResourcePermission.contains(
					(PermissionChecker)credentials.get(), (Long)recordSetId,
					DDLActionKeys.ADD_RECORD);
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long recordSetId)
		throws PortalException {

		return _ddlRecordSetModelResourcePermission.contains(
			(PermissionChecker)credentials.get(), recordSetId,
			ActionKeys.DELETE);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long recordSetId)
		throws PortalException {

		return _ddlRecordSetModelResourcePermission.contains(
			(PermissionChecker)credentials.get(), recordSetId,
			ActionKeys.UPDATE);
	}

	@Reference(
		target = "(model.class.name=com.liferay.dynamic.data.lists.model.DDLRecordSet)"
	)
	private ModelResourcePermission _ddlRecordSetModelResourcePermission;

}
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

package com.liferay.keyword.apio.internal.permission;

import com.liferay.apio.architect.alias.routes.permission.HasNestedAddingPermissionFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagService;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portlet.asset.service.permission.AssetTagsPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo Perez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.kernel.model.AssetTag",
	service = HasPermission.class
)
public class KeywordHasPermissionImpl implements HasPermission<Long> {

	@Override
	public <S> HasNestedAddingPermissionFunction<S> forAddingIn(
		Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(ContentSpace.class)) {
			return (credentials, groupId) -> {
				return AssetTagsPermission.contains(
					(PermissionChecker)credentials.get(), (Long)groupId,
					ActionKeys.MANAGE_TAG);
			};
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long assetTagId)
		throws PortalException {

		AssetTag assetTag = _assetTagService.getTag(assetTagId);

		return AssetTagsPermission.contains(
			(PermissionChecker)credentials.get(), assetTag.getGroupId(),
			ActionKeys.MANAGE_TAG);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long assetTagId)
		throws PortalException {

		AssetTag assetTag = _assetTagService.getTag(assetTagId);

		return AssetTagsPermission.contains(
			(PermissionChecker)credentials.get(), assetTag.getGroupId(),
			ActionKeys.MANAGE_TAG);
	}

	@Reference
	private AssetTagService _assetTagService;

}
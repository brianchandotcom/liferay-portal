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

package com.liferay.vocabulary.apio.internal.permission;

import com.liferay.apio.architect.alias.routes.permission.HasNestedAddingPermissionFunction;
import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.content.space.apio.architect.model.ContentSpace;
import com.liferay.portal.apio.permission.HasPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portlet.asset.service.permission.AssetCategoriesPermission;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo Pérez
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetVocabulary",
	service = HasPermission.class
)
public class VocabularyHasPermissionImpl implements HasPermission<Long> {

	@Override
	public <S> HasNestedAddingPermissionFunction<S> forAddingIn(
		Class<? extends Identifier<S>> identifierClass) {

		if (identifierClass.equals(ContentSpace.class)) {
			return (credentials, groupId) -> AssetCategoriesPermission.contains(
				(PermissionChecker)credentials.get(), (Long)groupId,
				ActionKeys.ADD_VOCABULARY);
		}

		return (credentials, s) -> false;
	}

	@Override
	public Boolean forDeleting(Credentials credentials, Long assetVocabularyId)
		throws PortalException {

		return AssetVocabularyPermission.contains(
			(PermissionChecker)credentials.get(), assetVocabularyId,
			ActionKeys.DELETE);
	}

	@Override
	public Boolean forUpdating(Credentials credentials, Long assetVocabularyId)
		throws PortalException {

		return AssetVocabularyPermission.contains(
			(PermissionChecker)credentials.get(), assetVocabularyId,
			ActionKeys.UPDATE);
	}

}
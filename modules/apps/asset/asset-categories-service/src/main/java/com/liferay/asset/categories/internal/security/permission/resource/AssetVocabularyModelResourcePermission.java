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

package com.liferay.asset.categories.internal.security.permission.resource;

import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portlet.asset.service.permission.AssetVocabularyPermission;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 * @author Lucas Marques
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.asset.kernel.model.AssetVocabulary",
	service = ModelResourcePermission.class
)
public class AssetVocabularyModelResourcePermission
	implements ModelResourcePermission<AssetVocabulary> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			AssetVocabulary assetVocabulary, String actionId)
		throws PortalException {

		AssetVocabularyPermission.check(
			permissionChecker, assetVocabulary, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long vocabularyId,
			String actionId)
		throws PortalException {

		AssetVocabularyPermission.check(
			permissionChecker, vocabularyId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			AssetVocabulary assetVocabulary, String actionId)
		throws PortalException {

		return AssetVocabularyPermission.contains(
			permissionChecker, assetVocabulary, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long vocabularyId,
			String actionId)
		throws PortalException {

		return AssetVocabularyPermission.contains(
			permissionChecker, vocabularyId, actionId);
	}

	@Override
	public String getModelName() {
		return AssetVocabulary.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

}
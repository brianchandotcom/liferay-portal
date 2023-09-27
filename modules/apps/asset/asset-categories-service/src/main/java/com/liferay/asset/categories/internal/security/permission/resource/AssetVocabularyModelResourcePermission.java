/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
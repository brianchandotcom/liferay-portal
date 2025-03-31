/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.security.permission.resource;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portlet.asset.service.permission.AssetTagsPermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pei-Jung Lan
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetTag",
	service = ModelResourcePermission.class
)
public class AssetTagModelResourcePermission
	implements ModelResourcePermission<AssetTag> {

	@Override
	public void check(
			PermissionChecker permissionChecker, AssetTag assetTag,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, assetTag, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetTag.class.getName(),
				assetTag.getTagId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long tagId, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, tagId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, AssetTag.class.getName(), tagId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, AssetTag assetTag,
		String actionId) {

		long groupId = 0;

		if (assetTag != null) {
			groupId = assetTag.getGroupId();
		}

		return AssetTagsPermission.contains(
			permissionChecker, groupId, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long tagId, String actionId) {

		return contains(
			permissionChecker, _assetTagLocalService.fetchAssetTag(tagId),
			actionId);
	}

	@Override
	public String getModelName() {
		return AssetTag.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private AssetTagLocalService _assetTagLocalService;

	@Reference(
		target = "(resource.name=" + AssetTagsPermission.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}
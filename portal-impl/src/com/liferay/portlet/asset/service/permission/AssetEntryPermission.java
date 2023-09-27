/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.permission;

import com.liferay.asset.kernel.AssetRendererFactoryRegistryUtil;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetRendererFactory;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Samuel Kong
 */
public class AssetEntryPermission {

	public static void check(
			PermissionChecker permissionChecker, AssetEntry entry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, entry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker,
				PortalUtil.getClassName(entry.getClassNameId()),
				entry.getClassPK(), actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		check(
			permissionChecker, AssetEntryLocalServiceUtil.getEntry(entryId),
			actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, className, classPK, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, className, classPK, actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, AssetEntry entry,
			String actionId)
		throws PortalException {

		String className = PortalUtil.getClassName(entry.getClassNameId());

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		try {
			return assetRendererFactory.hasPermission(
				permissionChecker, entry.getClassPK(), actionId);
		}
		catch (Exception exception) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, className, entry.getClassPK(), exception,
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long entryId, String actionId)
		throws PortalException {

		return contains(
			permissionChecker, AssetEntryLocalServiceUtil.getEntry(entryId),
			actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String className, long classPK,
			String actionId)
		throws PortalException {

		AssetRendererFactory<?> assetRendererFactory =
			AssetRendererFactoryRegistryUtil.getAssetRendererFactoryByClassName(
				className);

		try {
			return assetRendererFactory.hasPermission(
				permissionChecker, classPK, actionId);
		}
		catch (Exception exception) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, className, classPK, exception, actionId);
		}
	}

}
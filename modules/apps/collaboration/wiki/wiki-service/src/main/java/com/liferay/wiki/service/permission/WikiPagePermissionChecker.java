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

package com.liferay.wiki.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 1.7.0, with no direct replacement
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.wiki.model.WikiPage"}
)
@Deprecated
public class WikiPagePermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException {

		_wikiPageModelResourcePermission.check(
			permissionChecker, resourcePrimKey, actionId);
	}

	/**
	 * @deprecated As of 1.6.0, with no direct replacement
	 */
	@Deprecated
	public static void check(
			PermissionChecker permissionChecker, long nodeId, String title,
			double version, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, nodeId, title, version, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WikiNode.class.getName(), nodeId, actionId);
		}
	}

	/**
	 * @deprecated As of 1.6.0, with no direct replacement
	 */
	@Deprecated
	public static void check(
			PermissionChecker permissionChecker, long nodeId, String title,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, nodeId, title, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WikiNode.class.getName(), nodeId, actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, WikiPage page, String actionId)
		throws PortalException {

		_wikiPageModelResourcePermission.check(
			permissionChecker, page, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return _wikiPageModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	/**
	 * @deprecated As of 1.6.0, with no direct replacement
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String title,
			double version, String actionId)
		throws PortalException {

		try {
			WikiPage page = _wikiPageLocalService.getPage(
				nodeId, title, version);

			return contains(permissionChecker, page, actionId);
		}
		catch (NoSuchPageException nspe) {
			return WikiNodePermissionChecker.contains(
				permissionChecker, nodeId, ActionKeys.VIEW);
		}
	}

	/**
	 * @deprecated As of 1.6.0, with no direct replacement
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String title,
			String actionId)
		throws PortalException {

		try {
			WikiPage page = _wikiPageLocalService.getPage(nodeId, title, null);

			return contains(permissionChecker, page, actionId);
		}
		catch (NoSuchPageException nspe) {
			return WikiNodePermissionChecker.contains(
				permissionChecker, nodeId, ActionKeys.VIEW);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, WikiPage page, String actionId)
		throws PortalException {

		return _wikiPageModelResourcePermission.contains(
			permissionChecker, page, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_wikiPageModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.wiki.model.WikiPage)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<WikiPage> modelResourcePermission) {

		_wikiPageModelResourcePermission = modelResourcePermission;
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
	}

	private static WikiPageLocalService _wikiPageLocalService;
	private static ModelResourcePermission<WikiPage>
		_wikiPageModelResourcePermission;

}
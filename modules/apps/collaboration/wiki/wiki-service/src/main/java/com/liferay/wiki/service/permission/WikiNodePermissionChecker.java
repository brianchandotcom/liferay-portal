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
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @deprecated As of 1.7.0, with no direct replacement
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.wiki.model.WikiNode"}
)
@Deprecated
public class WikiNodePermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long nodeId, String actionId)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			permissionChecker, nodeId, actionId);
	}

	/**
	 * @deprecated As of 1.6.0, with no direct replacement
	 */
	@Deprecated
	public static void check(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(groupId, name);

		_wikiNodeModelResourcePermission.check(
			permissionChecker, node, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, WikiNode node, String actionId)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			permissionChecker, node, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String actionId)
		throws PortalException {

		return _wikiNodeModelResourcePermission.contains(
			permissionChecker, nodeId, actionId);
	}

	/**
	 * @deprecated As of 1.6.0, with no direct replacement
	 */
	@Deprecated
	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(groupId, name);

		return _wikiNodeModelResourcePermission.contains(
			permissionChecker, node, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, WikiNode node, String actionId)
		throws PortalException {

		return _wikiNodeModelResourcePermission.contains(
			permissionChecker, node, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_wikiNodeModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.wiki.model.WikiNode)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<WikiNode> modelResourcePermission) {

		_wikiNodeModelResourcePermission = modelResourcePermission;
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	private static WikiNodeLocalService _wikiNodeLocalService;
	private static ModelResourcePermission<WikiNode>
		_wikiNodeModelResourcePermission;

}
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
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.wiki.model.WikiNode"}
)
public class WikiNodePermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long nodeId, String actionId)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(nodeId);

		check(permissionChecker, node, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(groupId, name);

		check(permissionChecker, node, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, WikiNode node, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, node, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WikiNode.class.getName(), node.getNodeId(),
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String actionId)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(nodeId);

		return contains(permissionChecker, node, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long groupId, String name,
			String actionId)
		throws PortalException {

		WikiNode node = _wikiNodeLocalService.getNode(groupId, name);

		return contains(permissionChecker, node, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, WikiNode node, String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, node.getGroupId(), WikiNode.class.getName(),
			node.getNodeId(), WikiPortletKeys.WIKI, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (permissionChecker.hasOwnerPermission(
				node.getCompanyId(), WikiNode.class.getName(), node.getNodeId(),
				node.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			node.getGroupId(), WikiNode.class.getName(), node.getNodeId(),
			actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setWikiNodeLocalService(
		WikiNodeLocalService wikiNodeLocalService) {

		_wikiNodeLocalService = wikiNodeLocalService;
	}

	private static WikiNodeLocalService _wikiNodeLocalService;

}
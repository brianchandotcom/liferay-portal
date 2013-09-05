/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.wiki.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.NoSuchPageResourceException;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.WikiPageLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class WikiPagePermission {

	public static void check(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, resourcePrimKey, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long nodeId, String title,
			double version, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, nodeId, title, version, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, long nodeId, String title,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, nodeId, title, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, WikiPage page, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, page, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException, SystemException {

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(
				resourcePrimKey, (Boolean)null);

			return contains(permissionChecker, page, actionId);
		}
		catch (NoSuchPageResourceException nspre) {
			return false;
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String title,
			double version, String actionId)
		throws PortalException, SystemException {

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(
				nodeId, title, version);

			return contains(permissionChecker, page, actionId);
		}
		catch (NoSuchPageException nspe) {
			return WikiNodePermission.contains(
				permissionChecker, nodeId, ActionKeys.VIEW);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long nodeId, String title,
			String actionId)
		throws PortalException, SystemException {

		try {
			WikiPage page = WikiPageLocalServiceUtil.getPage(
				nodeId, title, null);

			return contains(permissionChecker, page, actionId);
		}
		catch (NoSuchPageException nspe) {
			return WikiNodePermission.contains(
				permissionChecker, nodeId, ActionKeys.VIEW);
		}
	}

	public static boolean contains(
		PermissionChecker permissionChecker, WikiPage page, String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, page.getGroupId(), WikiPage.class.getName(),
			page.getPageId(), PortletKeys.WIKI, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (actionId.equals(ActionKeys.VIEW)) {
			WikiPage redirectPage = page.getRedirectPage();

			if (redirectPage != null) {
				page = redirectPage;
			}

			if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
				WikiNode node = page.getNode();

				if (!WikiNodePermission.contains(
						permissionChecker, node, actionId)) {

					return false;
				}

				while (page != null) {
					if (!_hasPermission(permissionChecker, page, actionId)) {
						return false;
					}

					page = page.getParentPage();
				}

				return true;
			}
		}

		if (page.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, page.getGroupId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), actionId);

			if ((hasPermission != null) && hasPermission.booleanValue()) {
				return true;
			}
		}

		if (page.isDraft() && actionId.equals(ActionKeys.DELETE) &&
			(page.getStatusByUserId() == permissionChecker.getUserId())) {

			return true;
		}

		return _hasPermission(permissionChecker, page, actionId);
	}

	private static boolean _hasPermission(
		PermissionChecker permissionChecker, WikiPage page, String actionId) {

		if (permissionChecker.hasOwnerPermission(
				page.getCompanyId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), page.getUserId(), actionId) ||
			permissionChecker.hasPermission(
				page.getGroupId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), actionId)) {

			return true;
		}

		return false;
	}

}
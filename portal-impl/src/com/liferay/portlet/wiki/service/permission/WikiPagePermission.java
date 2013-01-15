/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.wiki.NoSuchPageException;
import com.liferay.portlet.wiki.NoSuchPageResourceException;
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

		if (actionId.equals(ActionKeys.VIEW)) {
			WikiPage redirectPage = page.getRedirectPage();

			if (redirectPage != null) {
				page = redirectPage;
			}
		}

		if (page.isPending()) {
			Boolean hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, page.getGroupId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), actionId);

			if (hasPermission != null) {
				return hasPermission.booleanValue();
			}
		}

		if (page.isDraft() && actionId.equals(ActionKeys.DELETE) &&
			(page.getStatusByUserId() == permissionChecker.getUserId())) {

			return true;
		}

		if (permissionChecker.hasOwnerPermission(
				page.getCompanyId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), page.getUserId(), actionId)) {

			return true;
		}

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			if (!WikiNodePermission.contains(
					permissionChecker, page.getNode(), ActionKeys.VIEW)) {

				return false;
			}

			WikiPage parentPage = page.getParentPage();

			if ((parentPage != null) &&
				!contains(permissionChecker, parentPage, ActionKeys.VIEW)) {

				return false;
			}
		}

		return permissionChecker.hasPermission(
			page.getGroupId(), WikiPage.class.getName(),
			page.getResourcePrimKey(), actionId);
	}

}
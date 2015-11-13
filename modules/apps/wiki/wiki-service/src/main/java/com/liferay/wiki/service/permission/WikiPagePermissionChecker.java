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
import com.liferay.portal.kernel.workflow.permission.WorkflowPermissionUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.exportimport.staging.permission.StagingPermissionUtil;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.exception.NoSuchPageException;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	immediate = true,
	property = {"model.class.name=com.liferay.wiki.model.WikiPage"}
)
public class WikiPagePermissionChecker implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long resourcePrimKey,
			String actionId)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.fetchPage(resourcePrimKey);

		if (page == null) {
			page = _wikiPageLocalService.getPageByPageId(resourcePrimKey);
		}

		check(permissionChecker, page, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, long nodeId, String title,
			double version, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, nodeId, title, version, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WikiNode.class.getName(), nodeId, actionId);
		}
	}

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

		if (!contains(permissionChecker, page, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, WikiPage.class.getName(), page.getPageId(),
				actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		WikiPage page = _wikiPageLocalService.fetchPage(classPK);

		if (page == null) {
			page = _wikiPageLocalService.getPageByPageId(classPK);
		}

		return contains(permissionChecker, page, actionId);
	}

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
		PermissionChecker permissionChecker, WikiPage page, String actionId) {

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, page.getGroupId(), WikiPage.class.getName(),
			page.getPageId(), WikiPortletKeys.WIKI, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		if (page.isDraft()) {
			if (actionId.equals(ActionKeys.VIEW) &&
				!contains(permissionChecker, page, ActionKeys.UPDATE)) {

				return false;
			}

			if (actionId.equals(ActionKeys.DELETE) &&
				(page.getStatusByUserId() == permissionChecker.getUserId())) {

				return true;
			}
		}
		else if (page.isPending()) {
			hasPermission = WorkflowPermissionUtil.hasPermission(
				permissionChecker, page.getGroupId(), WikiPage.class.getName(),
				page.getResourcePrimKey(), actionId);

			if ((hasPermission != null) && hasPermission.booleanValue()) {
				return true;
			}
		}
		else if (page.isScheduled()) {
			if (actionId.equals(ActionKeys.VIEW) &&
				!contains(permissionChecker, page, ActionKeys.UPDATE)) {

				return false;
			}
		}

		if (actionId.equals(ActionKeys.VIEW)) {
			WikiPage redirectPage = page.fetchRedirectPage();

			if (redirectPage != null) {
				page = redirectPage;
			}

			if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
				WikiNode node = page.getNode();

				if (!WikiNodePermissionChecker.contains(
						permissionChecker, node, actionId)) {

					return false;
				}

				while (page != null) {
					if (!_hasPermission(permissionChecker, page, actionId)) {
						return false;
					}

					page = page.fetchParentPage();
				}

				return true;
			}
		}

		return _hasPermission(permissionChecker, page, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setWikiPageLocalService(
		WikiPageLocalService wikiPageLocalService) {

		_wikiPageLocalService = wikiPageLocalService;
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

	private static WikiPageLocalService _wikiPageLocalService;

}
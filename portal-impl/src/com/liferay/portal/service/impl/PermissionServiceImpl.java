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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.AuditedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupedModel;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PermissionedModel;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.PermissionServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.asset.model.AssetRendererFactory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalFeedPermission;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.service.permission.PollsQuestionPermission;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.shopping.service.permission.ShoppingCategoryPermission;
import com.liferay.portlet.shopping.service.permission.ShoppingItemPermission;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.softwarecatalog.service.permission.SCFrameworkVersionPermission;
import com.liferay.portlet.softwarecatalog.service.permission.SCProductEntryPermission;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.service.permission.WikiNodePermission;
import com.liferay.portlet.wiki.service.permission.WikiPagePermission;

import java.util.List;

/**
 * Provides the remote service for checking permissions.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PermissionServiceImpl extends PermissionServiceBaseImpl {

	/**
	 * Checks to see if the group has permission to the service.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the service name
	 * @param  primKey the primary key of the service
	 * @throws PortalException if the group did not have permission to the
	 *         service, if a group with the primary key could not be found or if
	 *         the permission information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void checkPermission(long groupId, String name, long primKey)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, name, String.valueOf(primKey));
	}

	/**
	 * Checks to see if the group has permission to the service.
	 *
	 * @param  groupId the primary key of the group
	 * @param  name the service name
	 * @param  primKey the primary key of the service
	 * @throws PortalException if the group did not have permission to the
	 *         service, if a group with the primary key could not be found or if
	 *         the permission information was invalid
	 * @throws SystemException if a system exception occurred
	 */
	@Override
	public void checkPermission(long groupId, String name, String primKey)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId, String name,
			String primKey)
		throws PortalException, SystemException {

		if (name.equals(BlogsEntry.class.getName())) {
			BlogsEntryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(BookmarksEntry.class.getName())) {
			BookmarksEntryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(BookmarksFolder.class.getName())) {
			BookmarksFolderPermission.check(
				permissionChecker, groupId, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(DLFileEntry.class.getName())) {
			DLFileEntryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(DLFolder.class.getName())) {
			DLFolderPermission.check(
				permissionChecker, groupId, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(Group.class.getName())) {
			GroupPermissionUtil.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(JournalArticle.class.getName())) {
			JournalArticlePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(JournalFeed.class.getName())) {
			JournalFeedPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(Layout.class.getName())) {
			LayoutPermissionUtil.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(MBCategory.class.getName())) {
			MBCategoryPermission.check(
				permissionChecker, groupId, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(MBMessage.class.getName())) {
			MBMessagePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(PollsQuestion.class.getName())) {
			PollsQuestionPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(SCFrameworkVersion.class.getName())) {
			SCFrameworkVersionPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(SCProductEntry.class.getName())) {
			SCProductEntryPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(ShoppingCategory.class.getName())) {
			ShoppingCategoryPermission.check(
				permissionChecker, groupId, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(ShoppingItem.class.getName())) {
			ShoppingItemPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(Team.class.getName())) {
			long teamId = GetterUtil.getLong(primKey);

			Team team = teamPersistence.findByPrimaryKey(teamId);

			GroupPermissionUtil.check(
				permissionChecker, team.getGroupId(), ActionKeys.MANAGE_TEAMS);
		}
		else if (name.equals(User.class.getName())) {
			long userId = GetterUtil.getLong(primKey);

			User user = userPersistence.findByPrimaryKey(userId);

			UserPermissionUtil.check(
				permissionChecker, userId, user.getOrganizationIds(),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(WikiNode.class.getName())) {
			WikiNodePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(WikiPage.class.getName())) {
			WikiPagePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if ((primKey != null) &&
				 primKey.contains(PortletConstants.LAYOUT_SEPARATOR)) {

			int pos = primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

			long plid = GetterUtil.getLong(primKey.substring(0, pos));

			String portletId = primKey.substring(
				pos + PortletConstants.LAYOUT_SEPARATOR.length());

			PortletPermissionUtil.check(
				permissionChecker, plid, portletId, ActionKeys.CONFIGURATION);
		}
		else if (!permissionChecker.hasPermission(
					groupId, name, primKey, ActionKeys.PERMISSIONS)) {

			AssetRendererFactory assetRendererFactory =
				AssetRendererFactoryRegistryUtil.
					getAssetRendererFactoryByClassName(name);

			if (assetRendererFactory != null) {
				try {
					if (assetRendererFactory.hasPermission(
							permissionChecker, GetterUtil.getLong(primKey),
							ActionKeys.PERMISSIONS)) {

						return;
					}
				}
				catch (Exception e) {
				}
			}

			long ownerId = 0;

			if (resourceBlockLocalService.isSupported(name)) {
				PermissionedModel permissionedModel =
					resourceBlockLocalService.getPermissionedModel(
						name, GetterUtil.getLong(primKey));

				if (permissionedModel instanceof GroupedModel) {
					GroupedModel groupedModel = (GroupedModel)permissionedModel;

					ownerId = groupedModel.getUserId();
				}
				else if (permissionedModel instanceof AuditedModel) {
					AuditedModel auditedModel = (AuditedModel)permissionedModel;

					ownerId = auditedModel.getUserId();
				}
			}
			else {
				ResourcePermission resourcePermission =
					resourcePermissionLocalService.getResourcePermission(
						permissionChecker.getCompanyId(), name,
						ResourceConstants.SCOPE_INDIVIDUAL, primKey,
						permissionChecker.getOwnerRoleId());

				ownerId = resourcePermission.getOwnerId();
			}

			if (permissionChecker.hasOwnerPermission(
					permissionChecker.getCompanyId(), name, primKey, ownerId,
					ActionKeys.PERMISSIONS)) {

				return;
			}

			Role role = null;

			if (name.equals(Role.class.getName())) {
				long roleId = GetterUtil.getLong(primKey);

				role = rolePersistence.findByPrimaryKey(roleId);
			}

			if ((role != null) && role.isTeam()) {
				Team team = teamPersistence.findByPrimaryKey(role.getClassPK());

				TeamPermissionUtil.check(
					permissionChecker, team.getTeamId(),
					ActionKeys.PERMISSIONS);
			}
			else {
				List<String> resourceActions =
					ResourceActionsUtil.getResourceActions(name);

				if (!resourceActions.contains(ActionKeys.DEFINE_PERMISSIONS) ||
					!permissionChecker.hasPermission(
						groupId, name, primKey,
						ActionKeys.DEFINE_PERMISSIONS)) {

					throw new PrincipalException();
				}
			}
		}
	}

}
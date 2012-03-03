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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerBag;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.base.PermissionServiceBaseImpl;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.LayoutPermissionUtil;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.service.permission.TeamPermissionUtil;
import com.liferay.portal.service.permission.UserPermissionUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.permission.BlogsEntryPermission;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.service.permission.BookmarksEntryPermission;
import com.liferay.portlet.bookmarks.service.permission.BookmarksFolderPermission;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.permission.CalEventPermission;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.service.permission.DLFileEntryPermission;
import com.liferay.portlet.documentlibrary.service.permission.DLFolderPermission;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.permission.JournalArticlePermission;
import com.liferay.portlet.journal.service.permission.JournalFeedPermission;
import com.liferay.portlet.journal.service.permission.JournalStructurePermission;
import com.liferay.portlet.journal.service.permission.JournalTemplatePermission;
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
import java.util.Map;

/**
 * The implementation of the permission remote service.
 *
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class PermissionServiceImpl extends PermissionServiceBaseImpl {

	/**
	 * Checks to see if the group has permission to the resource.
	 *
	 * @param  groupId the primary key of the group
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if the group did not have permission to the
	 *         resource, or if a group or resource with the primary key could
	 *         not be found or was invalid
	 * @throws SystemException if a system exception occurred
	 */
	public void checkPermission(long groupId, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);
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
	public void checkPermission(long groupId, String name, long primKey)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
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
	public void checkPermission(long groupId, String name, String primKey)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, name, primKey);
	}

	/**
	 * Returns <code>true</code> if the group has permission to perform the
	 * action on the resource.
	 *
	 * @param  groupId the primary key of the group
	 * @param  actionId the action's ID
	 * @param  resourceId the primary key of the resource
	 * @return <code>true</code> if the group has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasGroupPermission(
			long groupId, String actionId, long resourceId)
		throws SystemException {

		return permissionLocalService.hasGroupPermission(
			groupId, actionId, resourceId);
	}

	/**
	 * Returns <code>true</code> if the user has permission to perform the
	 * action on the resource.
	 *
	 * @param  userId the primary key of the user
	 * @param  actionId the action's ID
	 * @param  resourceId the primary key of the resource
	 * @return <code>true</code> if the user has permission to perform the
	 *         action on the resource; <code>false</code> otherwise
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasUserPermission(
			long userId, String actionId, long resourceId)
		throws SystemException {

		return permissionLocalService.hasUserPermission(
			userId, actionId, resourceId);
	}

	/**
	 * Returns <code>true</code> if the user has permission to perform the
	 * action on the resources.
	 *
	 * <p>
	 * This method does not support resources managed by the resource block
	 * system.
	 * </p>
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group containing the resource
	 * @param  resources representations of the resource at each scope level
	 *         returned by {@link
	 *         com.liferay.portal.security.permission.AdvancedPermissionChecker#getResources(
	 *         long, long, String, String, String)}
	 * @param  actionId the action's ID
	 * @param  permissionCheckerBag the permission checker bag
	 * @return <code>true</code> if the user has permission to perform the
	 *         action on the resources; <code>false</code> otherwise
	 * @throws PortalException if a resource action based on any one of the
	 *         resources and the action ID could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public boolean hasUserPermissions(
			long userId, long groupId, List<Resource> resources,
			String actionId, PermissionCheckerBag permissionCheckerBag)
		throws PortalException, SystemException {

		return permissionLocalService.hasUserPermissions(
			userId, groupId, resources, actionId, permissionCheckerBag);
	}

	/**
	 * Sets the group's permissions to perform the actions on the resource,
	 * replacing the group's existing permissions on the resource.
	 *
	 * @param  groupId the primary key of the group
	 * @param  actionIds the primary keys of the actions
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if a group with the primary key could not be
	 *         found or if the group did not have permission to the resource
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroupPermissions(
			long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setGroupPermissions(
			groupId, actionIds, resourceId);
	}

	/**
	 * Sets the entity's group permissions to perform the actions on the
	 * resource, replacing the entity's existing group permissions on the
	 * resource. Only {@link com.liferay.portal.model.Organization} and {@link
	 * com.liferay.portal.model.UserGroup} class entities are supported.
	 *
	 * @param  className the class name of an organization or user group
	 * @param  classPK the primary key of the class
	 * @param  groupId the primary key of the group
	 * @param  actionIds the primary keys of the actions
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if the group did not have permission to the
	 *         resource, if an entity with the class name and primary key could
	 *         not be found, or if the entity's associated group could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public void setGroupPermissions(
			String className, String classPK, long groupId, String[] actionIds,
			long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setGroupPermissions(
			className, classPK, groupId, actionIds, resourceId);
	}

	/**
	 * Sets the permissions of each role to perform respective actions on the
	 * resource, replacing the existing permissions of each role on the
	 * resource.
	 *
	 * @param  groupId the primary key of the group
	 * @param  companyId the primary key of the company
	 * @param  roleIdsToActionIds the map of roles to their new actions on the
	 *         resource
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if the group did not have permission to the
	 *         resource
	 * @throws SystemException if a system exception occurred
	 */
	public void setIndividualPermissions(
			long groupId, long companyId,
			Map<Long, String[]> roleIdsToActionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setRolesPermissions(
			companyId, roleIdsToActionIds, resourceId);
	}

	/**
	 * Sets the organization permission to perform the actions on the resource
	 * for a particular group, replacing the organization's existing permissions
	 * on the resource.
	 *
	 * @param  organizationId the primary key of the organization
	 * @param  groupId the primary key of the group in which to scope the
	 *         permissions
	 * @param  actionIds the primary keys of the actions
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if the group did not have permission to the
	 *         resource or if an organization with the primary key could not be
	 *         found
	 * @throws SystemException if a system exception occurred
	 */
	public void setOrgGroupPermissions(
			long organizationId, long groupId, String[] actionIds,
			long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setOrgGroupPermissions(
			organizationId, groupId, actionIds, resourceId);
	}

	/**
	 * Sets the role's permissions to perform the action on the named resource,
	 * replacing the role's existing permissions on the resource.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupId the primary key of the group
	 * @param  name the resource name
	 * @param  scope the resource scope
	 * @param  primKey the resource primKey
	 * @param  actionId the action's ID
	 * @throws PortalException if the group did not have permission to the role
	 *         or if the scope was {@link
	 *         com.liferay.portal.model.ResourceConstants#SCOPE_INDIVIDUAL}
	 * @throws SystemException if a system exception occurred
	 */
	public void setRolePermission(
			long roleId, long groupId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		User user = getUser();

		permissionLocalService.setRolePermission(
			roleId, user.getCompanyId(), name, scope, primKey, actionId);
	}

	/**
	 * Sets the role's permissions to perform the actions on the resource,
	 * replacing the role's existing permissions on the resource.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupId the primary key of the group
	 * @param  actionIds the primary keys of the actions
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if the group did not have permission to the
	 *         resource or if a role with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setRolePermissions(
			long roleId, long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setRolePermissions(
			roleId, actionIds, resourceId);
	}

	/**
	 * Sets the user's permissions to perform the actions on the resource,
	 * replacing the user's existing permissions on the resource.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @param  actionIds the primary keys of the actions
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if the group did not have permission to the
	 *         resource or if a user with the primary key could not be found
	 * @throws SystemException if a system exception occurred
	 */
	public void setUserPermissions(
			long userId, long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.setUserPermissions(
			userId, actionIds, resourceId);
	}

	/**
	 * Removes the permission from the role.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupId the primary key of the group
	 * @param  permissionId the primary key of the permission
	 * @throws PortalException if the group did not have permission to the role
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetRolePermission(
			long roleId, long groupId, long permissionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		permissionLocalService.unsetRolePermission(roleId, permissionId);
	}

	/**
	 * Removes the role's permissions to perform the action on the named
	 * resource with the scope and primKey.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupId the primary key of the group
	 * @param  name the resource name
	 * @param  scope the resource scope
	 * @param  primKey the resource primKey
	 * @param  actionId the action's ID
	 * @throws PortalException if the group did not have permission to the role
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetRolePermission(
			long roleId, long groupId, String name, int scope, String primKey,
			String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		User user = getUser();

		permissionLocalService.unsetRolePermission(
			roleId, user.getCompanyId(), name, scope, primKey, actionId);
	}

	/**
	 * Removes the role's permissions to perform the action on the named
	 * resource.
	 *
	 * @param  roleId the primary key of the role
	 * @param  groupId the primary key of the group
	 * @param  name the resource name
	 * @param  scope the resource scope
	 * @param  actionId the action's ID
	 * @throws PortalException if the group did not have permission to the role
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetRolePermissions(
			long roleId, long groupId, String name, int scope, String actionId)
		throws PortalException, SystemException {

		checkPermission(
			getPermissionChecker(), groupId, Role.class.getName(), roleId);

		User user = getUser();

		permissionLocalService.unsetRolePermissions(
			roleId, user.getCompanyId(), name, scope, actionId);
	}

	/**
	 * Removes the user's permissions to perform the actions on the resource.
	 *
	 * @param  userId the primary key of the user
	 * @param  groupId the primary key of the group
	 * @param  actionIds the primary keys of the actions
	 * @param  resourceId the primary key of the resource
	 * @throws PortalException if the group did not have permission to the
	 *         resource
	 * @throws SystemException if a system exception occurred
	 */
	public void unsetUserPermissions(
			long userId, long groupId, String[] actionIds, long resourceId)
		throws PortalException, SystemException {

		checkPermission(getPermissionChecker(), groupId, resourceId);

		permissionLocalService.unsetUserPermissions(
			userId, actionIds, resourceId);
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId, long resourceId)
		throws PortalException, SystemException {

		Resource resource = resourcePersistence.findByPrimaryKey(resourceId);

		checkPermission(
			permissionChecker, groupId, resource.getName(),
			resource.getPrimKey().toString());
	}

	protected void checkPermission(
			PermissionChecker permissionChecker, long groupId, String name,
			long primKey)
		throws PortalException, SystemException {

		checkPermission(
			permissionChecker, groupId, name, String.valueOf(primKey));
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
		else if (name.equals(CalEvent.class.getName())) {
			CalEventPermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
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
		else if (name.equals(JournalStructure.class.getName())) {
			JournalStructurePermission.check(
				permissionChecker, GetterUtil.getLong(primKey),
				ActionKeys.PERMISSIONS);
		}
		else if (name.equals(JournalTemplate.class.getName())) {
			JournalTemplatePermission.check(
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
				 (primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR) != -1)) {

			int pos = primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR);

			long plid = GetterUtil.getLong(primKey.substring(0, pos));

			String portletId = primKey.substring(
				pos + PortletConstants.LAYOUT_SEPARATOR.length(),
				primKey.length());

			PortletPermissionUtil.check(
				permissionChecker, plid, portletId, ActionKeys.CONFIGURATION);
		}
		else if (!permissionChecker.hasPermission(
					groupId, name, primKey, ActionKeys.PERMISSIONS)) {

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
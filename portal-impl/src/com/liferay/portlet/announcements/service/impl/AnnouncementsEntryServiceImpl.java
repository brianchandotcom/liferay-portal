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

package com.liferay.portlet.announcements.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.Team;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.GroupPermissionUtil;
import com.liferay.portal.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.service.permission.PortalPermissionUtil;
import com.liferay.portal.service.permission.RolePermissionUtil;
import com.liferay.portal.service.permission.UserGroupPermissionUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.announcements.constants.AnnouncementsConstants;
import com.liferay.portlet.announcements.model.AnnouncementsEntry;
import com.liferay.portlet.announcements.service.base.AnnouncementsEntryServiceBaseImpl;
import com.liferay.portlet.announcements.service.permission.AnnouncementsEntryPermission;

/**
 * @author Brian Wing Shun Chan
 */
public class AnnouncementsEntryServiceImpl
	extends AnnouncementsEntryServiceBaseImpl {

	@Override
	public AnnouncementsEntry addEntry(
			long plid, long classNameId, long classPK, String title,
			String content, String url, String type, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, boolean displayImmediately,
			int expirationDateMonth, int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, int priority, boolean alert)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (alert) {
			String portletId = PortletProviderUtil.getPortletId(
				AnnouncementsConstants.CLASS_NAME_ALERTS_ENTRY,
				PortletProvider.Action.MANAGE);

			AnnouncementsEntryPermission.check(
				permissionChecker, plid, portletId, ActionKeys.ADD_ENTRY);
		}
		else {
			String portletId = PortletProviderUtil.getPortletId(
				AnnouncementsEntry.class.getName(),
				PortletProvider.Action.MANAGE);

			AnnouncementsEntryPermission.check(
				permissionChecker, plid, portletId, ActionKeys.ADD_ENTRY);
		}

		if (classNameId == 0) {
			if (!PortalPermissionUtil.contains(
					permissionChecker, ActionKeys.ADD_GENERAL_ANNOUNCEMENTS)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, PortletKeys.PORTAL, PortletKeys.PORTAL,
					ActionKeys.ADD_GENERAL_ANNOUNCEMENTS);
			}
		}
		else {
			String className = PortalUtil.getClassName(classNameId);

			if (className.equals(Group.class.getName()) &&
				!GroupPermissionUtil.contains(
					permissionChecker, classPK,
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, className, classPK,
					ActionKeys.MANAGE_ANNOUNCEMENTS);
			}

			if (className.equals(Organization.class.getName()) &&
				!OrganizationPermissionUtil.contains(
					permissionChecker, classPK,
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, className, classPK,
					ActionKeys.MANAGE_ANNOUNCEMENTS);
			}

			if (className.equals(Role.class.getName())) {
				Role role = roleLocalService.getRole(classPK);

				if (role.isTeam()) {
					Team team = teamLocalService.getTeam(role.getClassPK());

					if (!GroupPermissionUtil.contains(
							permissionChecker, team.getGroupId(),
							ActionKeys.MANAGE_ANNOUNCEMENTS) ||
						!RolePermissionUtil.contains(
							permissionChecker, team.getGroupId(), classPK,
							ActionKeys.MANAGE_ANNOUNCEMENTS)) {

						throw new PrincipalException.MustHavePermission(
							permissionChecker, Team.class.getName(), classPK,
							ActionKeys.MANAGE_ANNOUNCEMENTS);
					}
				}
				else if (!RolePermissionUtil.contains(
							permissionChecker, classPK,
							ActionKeys.MANAGE_ANNOUNCEMENTS)) {

					throw new PrincipalException.MustHavePermission(
						permissionChecker, className, classPK,
						ActionKeys.MANAGE_ANNOUNCEMENTS);
				}
			}

			if (className.equals(UserGroup.class.getName()) &&
				!UserGroupPermissionUtil.contains(
					permissionChecker, classPK,
					ActionKeys.MANAGE_ANNOUNCEMENTS)) {

				throw new PrincipalException.MustHavePermission(
					permissionChecker, className, classPK,
					ActionKeys.MANAGE_ANNOUNCEMENTS);
			}
		}

		return announcementsEntryLocalService.addEntry(
			getUserId(), classNameId, classPK, title, content, url, type,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, displayImmediately, expirationDateMonth,
			expirationDateDay, expirationDateYear, expirationDateHour,
			expirationDateMinute, priority, alert);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addEntry(long, long, long,
	 *             String, String, String, String, int, int, int, int, int,
	 *             boolean, int, int, int, int, int, int, boolean)}
	 */
	@Deprecated
	@Override
	public AnnouncementsEntry addEntry(
			long plid, long classNameId, long classPK, String title,
			String content, String url, String type, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, int priority,
			boolean alert)
		throws PortalException {

		return addEntry(
			plid, classNameId, classPK, title, content, url, type,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, false, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority, alert);
	}

	@Override
	public void deleteEntry(long entryId) throws PortalException {
		AnnouncementsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.DELETE);

		announcementsEntryLocalService.deleteEntry(entryId);
	}

	@Override
	public AnnouncementsEntry fetchAnnouncementsEntry(long entryId)
		throws PortalException {

		AnnouncementsEntry entry =
			announcementsEntryLocalService.fetchAnnouncementsEntry(entryId);

		if (entry != null) {
			AnnouncementsEntryPermission.check(
				getPermissionChecker(), entry, ActionKeys.VIEW);
		}

		return entry;
	}

	@Override
	public AnnouncementsEntry getEntry(long entryId) throws PortalException {
		AnnouncementsEntry entry = announcementsEntryLocalService.getEntry(
			entryId);

		AnnouncementsEntryPermission.check(
			getPermissionChecker(), entry, ActionKeys.VIEW);

		return entry;
	}

	@Override
	public AnnouncementsEntry updateEntry(
			long entryId, String title, String content, String url, String type,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute,
			boolean displayImmediately, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute, int priority)
		throws PortalException {

		AnnouncementsEntryPermission.check(
			getPermissionChecker(), entryId, ActionKeys.UPDATE);

		return announcementsEntryLocalService.updateEntry(
			getUserId(), entryId, title, content, url, type, displayDateMonth,
			displayDateDay, displayDateYear, displayDateHour, displayDateMinute,
			displayImmediately, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			priority);
	}

}
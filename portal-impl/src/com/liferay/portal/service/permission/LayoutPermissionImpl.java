/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.permission;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.OrganizationLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.sites.util.SitesUtil;

import java.util.List;

/**
 * @author Charles May
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 */
public class LayoutPermissionImpl implements LayoutPermission {

	public void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, layout, actionId)) {
			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException, SystemException {

		if (!contains(
				permissionChecker, groupId, privateLayout, layoutId,
				actionId)) {

			throw new PrincipalException();
		}
	}

	public void check(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, plid, actionId)) {
			throw new PrincipalException();
		}
	}

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException, SystemException {

		return contains(permissionChecker, layout, null, actionId);
	}

	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, String actionId)
		throws PortalException, SystemException {

		if (actionId.equals(ActionKeys.VIEW)) {
			User user = UserLocalServiceUtil.getUserById(
				permissionChecker.getUserId());

			return isViewableGroup(
				user, layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), controlPanelCategory, permissionChecker);
		}

		if ((layout.isPrivateLayout() &&
			 !PropsValues.LAYOUT_USER_PRIVATE_LAYOUTS_MODIFIABLE) ||
			(layout.isPublicLayout() &&
			 !PropsValues.LAYOUT_USER_PUBLIC_LAYOUTS_MODIFIABLE)) {

			if (actionId.equals(ActionKeys.UPDATE)) {
				Group group = GroupLocalServiceUtil.getGroup(
					layout.getGroupId());

				if (group.isUser()) {
					return false;
				}
			}
		}

			Group group = layout.getGroup();

			if (!group.isLayoutSetPrototype() &&
				isAttemptToModifyLockedLayout(layout, actionId)) {

				return false;
			}

		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) &&
			!group.isUser()) {

			// This is new way of doing an ownership check without having to
			// have a userId field on the model. When the instance model was
			// first created we set the user's userId as the ownerId of the
			// individual scope ResourcePermission of the Owner Role.
			// Therefore ownership can be determined by obtaining the Owner role
			// ResourcePermission for the current instance model and testing it
			// with the hasOwnerPermission call.

			ResourcePermission resourcePermission =
				ResourcePermissionLocalServiceUtil.getResourcePermission(
					layout.getCompanyId(), Layout.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(layout.getPlid()),
					permissionChecker.getOwnerRoleId());

			if (permissionChecker.hasOwnerPermission(
					layout.getCompanyId(), Layout.class.getName(),
					String.valueOf(layout.getPlid()),
					resourcePermission.getOwnerId(), actionId)) {

				return true;
			}
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, layout.getGroupId(),
				ActionKeys.MANAGE_LAYOUTS)) {

			return true;
		}
		else if (actionId.equals(ActionKeys.ADD_LAYOUT) &&
				 GroupPermissionUtil.contains(
					 permissionChecker, layout.getGroupId(),
					 ActionKeys.ADD_LAYOUT)) {

			return true;
		}

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE) {
			// Check upward recursively to see if any pages above grant the
			// action.

			long parentLayoutId = layout.getParentLayoutId();

			while (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					layout.getParentLayoutId());

				if (contains(
						permissionChecker, parentLayout, controlPanelCategory,
						actionId)) {

					return true;
				}

				parentLayoutId = parentLayout.getParentLayoutId();
			}
		}

		try {
			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				if (ResourcePermissionLocalServiceUtil.
						getResourcePermissionsCount(
							layout.getCompanyId(), Layout.class.getName(),
							ResourceConstants.SCOPE_INDIVIDUAL,
							String.valueOf(layout.getPlid())) == 0) {

					throw new NoSuchResourceException();
				}
			}
			else {
				ResourceLocalServiceUtil.getResource(
					layout.getCompanyId(), Layout.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(layout.getPlid()));
			}
		}
		catch (NoSuchResourceException nsre) {
			boolean addGroupPermission = true;
			boolean addGuestPermission = true;

			if (layout.isPrivateLayout()) {
				addGuestPermission = false;
			}

			ResourceLocalServiceUtil.addResources(
				layout.getCompanyId(), layout.getGroupId(), 0,
				Layout.class.getName(), layout.getPlid(), false,
				addGroupPermission, addGuestPermission);
		}

		return permissionChecker.hasPermission(
			layout.getGroupId(), Layout.class.getName(), layout.getPlid(),
			actionId);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String actionId)
		throws PortalException, SystemException {

		return contains(
			permissionChecker, groupId, privateLayout, layoutId, null,
			actionId);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String controlPanelCategory,
			String actionId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.getLayout(
			groupId, privateLayout, layoutId);

		if (isAttemptToModifyLockedLayout(layout, actionId)) {
			return false;
		}

		return contains(
			permissionChecker, layout, controlPanelCategory, actionId);
	}

	public boolean contains(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException, SystemException {

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		return contains(permissionChecker, layout, actionId);
	}

	protected boolean isAttemptToModifyLockedLayout(
		Layout layout, String actionId) {

		if (SitesUtil.isLayoutLocked(layout) &&
			(ActionKeys.CUSTOMIZE.equals(actionId) ||
			 ActionKeys.UPDATE.equals(actionId))) {

			return true;
		}

		return false;
	}

	protected boolean isViewableGroup(
			User user, long groupId, boolean privateLayout, long layoutId,
			String controlPanelCategory, PermissionChecker permissionChecker)
		throws PortalException, SystemException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		// Inactive sites are not viewable

		if (!group.isActive()) {
			return false;
		}
		else if (group.isStagingGroup()) {
			Group liveGroup = group.getLiveGroup();

			if (!liveGroup.isActive()) {
				return false;
			}
		}

		// User private layouts are only viewable by the user and anyone who can
		// update the user. The user must also be active.

		if (group.isUser()) {
			long groupUserId = group.getClassPK();

			if (groupUserId == user.getUserId()) {
				return true;
			}
			else {
				User groupUser = UserLocalServiceUtil.getUserById(groupUserId);

				if (!groupUser.isActive()) {
					return false;
				}

				if (privateLayout) {
					if (UserPermissionUtil.contains(
							permissionChecker, groupUserId,
							groupUser.getOrganizationIds(),
							ActionKeys.MANAGE_LAYOUTS) ||
						UserPermissionUtil.contains(
							permissionChecker, groupUserId,
							groupUser.getOrganizationIds(),
							ActionKeys.UPDATE)) {

						return true;
					}
					else {
						return false;
					}
				}
			}
		}

		// If the current group is staging, only users with editorial rights
		// can access it

		if (group.isStagingGroup()) {
			if (GroupPermissionUtil.contains(
					permissionChecker, groupId, ActionKeys.VIEW_STAGING)) {

				return true;
			}

			return false;
		}

		// Most public layouts are viewable

		if (!privateLayout) {
			return true;
		}

		// Control panel layouts are only viewable by authenticated users

		if (group.isControlPanel()) {
			if (PortalPermissionUtil.contains(
					permissionChecker, ActionKeys.VIEW_CONTROL_PANEL)) {

				return true;
			}
			else {
				if (Validator.isNotNull(controlPanelCategory)) {
					return true;
				}
				else {
					return false;
				}
			}
		}

		// Site layouts are only viewable by users who are members of the site
		// or by users who can update the site

		if (group.isSite()) {
			if (GroupLocalServiceUtil.hasUserGroup(user.getUserId(), groupId)) {
				return true;
			}
			else if (GroupPermissionUtil.contains(
						permissionChecker, groupId,
						ActionKeys.MANAGE_LAYOUTS) ||
					 GroupPermissionUtil.contains(
						permissionChecker, groupId, ActionKeys.UPDATE)) {

				return true;
			}
		}

		// Organization site layouts are also viewable by users who belong to
		// the organization or by users who can update organization

		if (group.isCompany()) {
			return false;
		}
		else if (group.isLayoutPrototype()) {
			if (LayoutPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.VIEW)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.VIEW)) {

				return true;
			}
			else {
				return false;
			}
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			if (OrganizationLocalServiceUtil.hasUserOrganization(
					user.getUserId(), organizationId, false, true, false)) {

				return true;
			}
			else if (OrganizationPermissionUtil.contains(
						permissionChecker, organizationId, ActionKeys.UPDATE)) {

				return true;
			}

			if (!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {
				List<Organization> userOrgs =
					OrganizationLocalServiceUtil.getUserOrganizations(
						user.getUserId(), true);

				for (Organization organization : userOrgs) {
					for (Organization ancestorOrganization :
							organization.getAncestors()) {

						if (organizationId ==
								ancestorOrganization.getOrganizationId()) {

							return true;
						}
					}
				}
			}
		}

		return false;
	}

}
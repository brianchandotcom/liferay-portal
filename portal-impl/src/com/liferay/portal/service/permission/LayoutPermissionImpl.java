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

package com.liferay.portal.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.staging.permission.StagingPermissionUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutType;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.VirtualLayout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
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
@OSGiBeanProperties(
	property = {"model.class.name=com.liferay.portal.model.Layout"}
)
public class LayoutPermissionImpl
	implements BaseModelPermissionChecker, LayoutPermission {

	@Override
	public void check(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException {

		if (!contains(
				permissionChecker, layout, checkViewableGroup, actionId)) {

			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, layout, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long groupId, long layoutId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, groupId, layoutId, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, plid, actionId)) {
			throw new PrincipalException();
		}
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException {

		if (isAttemptToModifyLockedLayout(layout, actionId)) {
			return false;
		}

		Boolean hasPermission = StagingPermissionUtil.hasPermission(
			permissionChecker, layout.getGroup(), Layout.class.getName(),
			layout.getGroupId(), null, actionId);

		if (hasPermission != null) {
			return hasPermission.booleanValue();
		}

		return containsWithViewableGroup(
			permissionChecker, layout, checkViewableGroup, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		return contains(permissionChecker, layout, false, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             Layout, boolean, String)}
	 */
	@Deprecated
	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, boolean checkViewableGroup,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker, layout, checkViewableGroup, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             Layout, String)}
	 */
	@Deprecated
	@Override
	public boolean contains(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, String actionId)
		throws PortalException {

		return contains(permissionChecker, layout, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #contains(PermissionChecker,
	 *             long, long, String)}
	 */
	@Deprecated
	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId,
			boolean privateLayout, long layoutId, String controlPanelCategory,
			String actionId)
		throws PortalException {

		return contains(permissionChecker, groupId, layoutId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long groupId, long layoutId,
			String actionId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.getLayout(groupId, layoutId);

		return contains(permissionChecker, layout, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long plid, String actionId)
		throws PortalException {

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		return contains(permissionChecker, layout, actionId);
	}

	@Override
	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkLayoutUpdateable, String actionId)
		throws PortalException {

		if (layout.isTypeControlPanel()) {
			return false;
		}

		if (checkLayoutUpdateable && !actionId.equals(ActionKeys.CUSTOMIZE) &&
			!actionId.equals(ActionKeys.VIEW) &&
			(layout instanceof VirtualLayout)) {

			return false;
		}

		if (actionId.equals(ActionKeys.CUSTOMIZE) &&
			(layout instanceof VirtualLayout)) {

			VirtualLayout virtualLayout = (VirtualLayout)layout;

			layout = virtualLayout.getWrappedModel();
		}

		if (actionId.equals(ActionKeys.ADD_LAYOUT)) {
			if (!SitesUtil.isLayoutSortable(layout)) {
				return false;
			}

			LayoutType layoutType = layout.getLayoutType();

			if (!layoutType.isParentable()) {
				return false;
			}
		}

		if (actionId.equals(ActionKeys.DELETE) &&
			!SitesUtil.isLayoutDeleteable(layout)) {

			return false;
		}

		Group group = layout.getGroup();

		if (checkLayoutUpdateable && !group.isLayoutSetPrototype() &&
			isAttemptToModifyLockedLayout(layout, actionId)) {

			return false;
		}

		User user = permissionChecker.getUser();

		if (!user.isDefaultUser() && !group.isUser()) {

			// This is new way of doing an ownership check without having to
			// have a userId field on the model. When the instance model was
			// first created, we set the user's userId as the ownerId of the
			// individual scope ResourcePermission of the Owner Role. Therefore,
			// ownership can be determined by obtaining the Owner role
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

		if (actionId.equals(ActionKeys.ADD_LAYOUT) &&
			GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.ADD_LAYOUT)) {

			return true;
		}

		if (GroupPermissionUtil.contains(
				permissionChecker, group, ActionKeys.MANAGE_LAYOUTS)) {

			return true;
		}

		if (PropsValues.PERMISSIONS_VIEW_DYNAMIC_INHERITANCE &&
			!actionId.equals(ActionKeys.VIEW)) {

			// Check upward recursively to see if any pages above grant the
			// action

			long parentLayoutId = layout.getParentLayoutId();

			while (parentLayoutId != LayoutConstants.DEFAULT_PARENT_LAYOUT_ID) {
				Layout parentLayout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), parentLayoutId);

				if (contains(permissionChecker, parentLayout, actionId)) {
					return true;
				}

				parentLayoutId = parentLayout.getParentLayoutId();
			}
		}

		int resourcePermissionsCount =
			ResourcePermissionLocalServiceUtil.getResourcePermissionsCount(
				layout.getCompanyId(), Layout.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(layout.getPlid()));

		if (resourcePermissionsCount == 0) {
			boolean addGroupPermission = true;
			boolean addGuestPermission = true;

			ResourceLocalServiceUtil.addResources(
				layout.getCompanyId(), layout.getGroupId(), 0,
				Layout.class.getName(), layout.getPlid(), false,
				addGroupPermission, addGuestPermission);
		}

		return permissionChecker.hasPermission(
			layout.getGroupId(), Layout.class.getName(), layout.getPlid(),
			actionId);
	}

	@Override
	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout, String actionId)
		throws PortalException {

		return containsWithoutViewableGroup(
			permissionChecker, layout, true, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #containsWithoutViewableGroup(PermissionChecker, Layout,
	 *             boolean, String)}
	 */
	@Deprecated
	@Override
	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, boolean checkLayoutUpdateable,
			String actionId)
		throws PortalException {

		return containsWithoutViewableGroup(
			permissionChecker, layout, checkLayoutUpdateable, actionId);
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link
	 *             #containsWithoutViewableGroup(PermissionChecker, Layout,
	 *             String)}
	 */
	@Deprecated
	@Override
	public boolean containsWithoutViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			String controlPanelCategory, String actionId)
		throws PortalException {

		return containsWithoutViewableGroup(
			permissionChecker, layout, actionId);
	}

	protected boolean containsWithViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkViewableGroup, String actionId)
		throws PortalException {

		if (actionId.equals(ActionKeys.VIEW) && checkViewableGroup) {
			return isViewableGroup(
				permissionChecker, layout, checkViewableGroup);
		}

		return containsWithoutViewableGroup(
			permissionChecker, layout, actionId);
	}

	protected boolean isAttemptToModifyLockedLayout(
		Layout layout, String actionId) {

		if ((ActionKeys.CUSTOMIZE.equals(actionId) ||
			 ActionKeys.UPDATE.equals(actionId)) &&
			!SitesUtil.isLayoutUpdateable(layout)) {

			return true;
		}

		return false;
	}

	protected boolean isViewableGroup(
			PermissionChecker permissionChecker, Layout layout,
			boolean checkResourcePermission)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(layout.getGroupId());

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

			if (groupUserId == permissionChecker.getUserId()) {
				return true;
			}

			User groupUser = UserLocalServiceUtil.getUserById(groupUserId);

			if (!groupUser.isActive()) {
				return false;
			}
		}

		// If the current group is staging, only users with editorial rights can
		// access it

		if (group.isStagingGroup()) {
			if (GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.VIEW_STAGING)) {

				return true;
			}

			return false;
		}

		// Site layouts are only viewable by users who are members of the site
		// or by users who can update the site

		if (group.isSite()) {
			if (GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.MANAGE_LAYOUTS) ||
				GroupPermissionUtil.contains(
					permissionChecker, group, ActionKeys.UPDATE)) {

				return true;
			}

			if (!permissionChecker.isGroupMember(group.getGroupId())) {
				return false;
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

			return false;
		}
		else if (group.isLayoutSetPrototype()) {
			if (LayoutSetPrototypePermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.VIEW)) {

				return true;
			}

			return false;
		}
		else if (group.isOrganization()) {
			long organizationId = group.getOrganizationId();

			if (OrganizationLocalServiceUtil.hasUserOrganization(
					permissionChecker.getUserId(), organizationId, false,
					false)) {

				return true;
			}
			else if (OrganizationPermissionUtil.contains(
						permissionChecker, organizationId, ActionKeys.UPDATE)) {

				return true;
			}

			if (!PropsValues.ORGANIZATIONS_MEMBERSHIP_STRICT) {
				List<Organization> userOrgs =
					OrganizationLocalServiceUtil.getUserOrganizations(
						permissionChecker.getUserId());

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
		else if (group.isUserGroup()) {
			if (UserGroupPermissionUtil.contains(
					permissionChecker, group.getClassPK(), ActionKeys.UPDATE)) {

				return true;
			}
		}

		// Only check the actual Layout if all of the above failed

		if (containsWithoutViewableGroup(
				permissionChecker, layout, ActionKeys.VIEW)) {

			return true;
		}

		// As a last resort, check if any top level pages are viewable by the
		// user

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			layout.getGroupId(), LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);

		for (Layout curLayout : layouts) {
			if (containsWithoutViewableGroup(
					permissionChecker, curLayout, ActionKeys.VIEW) &&
				!curLayout.isHidden()) {

				return true;
			}
		}

		return false;
	}

}
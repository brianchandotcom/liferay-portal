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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.service.ResourceActionLocalServiceUtil;

/**
 * Stores the permissions assigned to roles under permissions version 6. A
 * resource permission gives a role the ability to perform a set of actions on
 * certain resources.
 *
 * <p>
 * The type of resource a permission applies to is specified by the
 * <code>name</code> attribute. It will either be the numeric ID of a portlet,
 * or the fully qualified class name of a model (such as a layout or document
 * library folder).
 * </p>
 *
 * <p>
 * These permissions can apply in one of four scopes: company, group,
 * group-template, or individual. The scope of a permission determines how
 * broadly it applies to resources in the portal. Company scope is the broadest,
 * and grants a user with the role permissions for every resource of the type
 * within the company. Likewise, group scope gives users with the role
 * permissions for every resource within the specified group, and individual
 * scope only applies to a single resource of the type. Group-template scope is
 * similar to group scope, except that it does not automatically apply to a
 * specific group. A user must be a member of a group (generally either a site
 * or an organization), and they must have been given the role within that group
 * before they are granted its permissions.
 * </p>
 *
 * <p>
 * Note: Lacking permission to perform an action on a resource at one scope does
 * not necessarily mean that a role does not have permission to perform that
 * action. For instance, a message boards moderator role will not have
 * individual scope permissions to edit every post, but it will have edit
 * permissions at the group or company level, which is sufficient. Every scope
 * must be checked.
 * </p>
 *
 * <p>
 * The scope of the resource permission also determines the meaning of the
 * <code>primKey</code> attribute. Its different uses are listed below:
 * </p>
 *
 * <ul>
 * <li>
 * Company scope - <code>primKey</code> is the primary key of the company
 * </li>
 * <li>
 * Group scope - <code>primKey</code> is the primary key of the group the
 * permission applies within
 * </li>
 * <li>
 * Group-template scope - <code>primKey</code> is always <code>0</code>
 * </li>
 * <li>
 * Individual scope - If the permission applies to a model instance,
 * <code>primkey</code> will be the primary key of the instance. If the
 * permission is for a portlet, <code>primKey</code> will contain the primary
 * key of the layout containing the portlet, followed by &quot;_LAYOUT_&quot;
 * and the portlet ID. The instance ID will also be present for instanceable
 * portlets, preceded by &quot;_INSTANCE_&quot;.
 * </li>
 * </ul>
 *
 * <p>
 * The <code>actionIds</code> attribute stores the bitwise IDs of all the
 * actions allowed by this permission.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see    ResourceActionImpl
 */
public class ResourcePermissionImpl extends ResourcePermissionBaseImpl {

	@Override
	public void addResourceAction(String actionId) throws PortalException {
		ResourceAction resourceAction =
			ResourceActionLocalServiceUtil.getResourceAction(
				getName(), actionId);

		long actionIds = getActionIds() | resourceAction.getBitwiseValue();

		setActionIds(actionIds);
		setViewActionId((actionIds % 2) == 1);
	}

	@Override
	public boolean hasAction(ResourceAction resourceAction) {
		if ((resourceAction != null) &&
			((getActionIds() & resourceAction.getBitwiseValue()) != 0)) {

			return true;
		}

		return false;
	}

	@Override
	public boolean hasActionId(String actionId) {
		ResourceAction resourceAction =
			ResourceActionLocalServiceUtil.fetchResourceAction(
				getName(), actionId);

		return hasAction(resourceAction);
	}

	@Override
	public void removeResourceAction(String actionId) throws PortalException {
		ResourceAction resourceAction =
			ResourceActionLocalServiceUtil.getResourceAction(
				getName(), actionId);

		long actionIds = getActionIds() & ~resourceAction.getBitwiseValue();

		setActionIds(actionIds);
		setViewActionId((actionIds % 2) == 1);
	}

}
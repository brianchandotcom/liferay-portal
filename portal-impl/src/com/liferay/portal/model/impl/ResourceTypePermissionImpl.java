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

package com.liferay.portal.model.impl;

/**
 * Stores the actions a role has permission to perform on all resources of the
 * type within the group/company.
 *
 * <p>
 * Resource type permissions can exist at either company or group scope. They
 * are roughly equivalent to resource permissions with company, group, or
 * group-template scope. However, resource type permissions make no distinction
 * between company and group-template scope, storing both as company scope
 * permissions. The reason for this simplification is that regular roles cannot
 * have group-template scope permissions, and site/organization roles can only
 * have group-template scope permissions. Therefore, resource type permissions
 * depend on the type of their associated role to distinguish between the two
 * scopes.
 * </p>
 *
 * <p>
 * Do not confuse resource type permissions with resource block permissions;
 * they serve very different purposes. Resource block permissions store the
 * permissions on a single resource block, and are simply a representation of
 * who can do what to the resources within the block. Resource type permissions
 * grant permissions to perform actions on all resources of a type within a
 * group or company. Any permissions granted to a role with a resource type
 * permission are automatically added to all the resource blocks for that
 * resource type within the group/company.
 * </p>
 *
 * <p>
 * For example, if a company scope resource type permission is granted to a role
 * to edit blog entries, all the resource blocks within the company for blog
 * entries are modified to grant the role permission to edit the blog entries
 * they contain. Thus, granting and revoking resource type permissions will also
 * cause those same permissions to be granted/revoked at the resource block
 * permission level.
 * </p>
 *
 * <p>
 * Copying permissions from the company and group scope to the resource block
 * scope eliminates the need to check multiple scopes when determining if a role
 * has permission to perform an action on a resource. All the necessary
 * information is cached at the most granular level.
 * </p>
 *
 * <p>
 * Rather than using a separate &quot;scope&quot; attribute as {@linkplain
 * ResourcePermissionImpl resource permissions} do, company scope resource type
 * permissions simply have a <code>groupId</code> of <code>0</code>
 * </p>
 *
 * <p>
 * The type of resource the permission grants access to is specified by the
 * <code>name</code> attribute, which must be the fully qualified class name of
 * a model (such as a blog entry).
 * </p>
 *
 * <p>
 * The <code>actionIds</code> attribute stores the bitwise IDs of all the
 * actions allowed by this permission.
 * </p>
 *
 * @author Connor McKay
 */
public class ResourceTypePermissionImpl extends ResourceTypePermissionBaseImpl {

	@Override
	public boolean isCompanyScope() {
		if (getGroupId() == 0) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public boolean isGroupScope() {
		return !isCompanyScope();
	}

}
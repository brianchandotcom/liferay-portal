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

package com.liferay.portal.kernel.security.permission.contributor;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.UserBag;

import org.osgi.annotation.versioning.ProviderType;

/**
 * RoleCollection is used as the argument to {@link
 * RoleContributor#contribute(RoleCollection)}. It holds the managed
 * collection of roleIds starting with the <em>initial</em> set calculated
 * from persisted role assignment and role inheritance.
 *
 * @author Carlos Sierra Andrés
 * @author Raymond Augé
 *
 * @review
 */
@ProviderType
public interface RoleCollection {

	/**
	 * Add a roleId to the collection.
	 *
	 * @param  roleId to add to the collection
	 * @return true if the roleId was added to the collection
	 */
	public boolean addRoleId(long roleId);

	/**
	 * Get the companyId of the Company being checked.
	 *
	 * @return the companyId of the Company being checked
	 */
	public long getCompanyId();

	/**
	 * Get the groupId of the Group currently being permission checked.
	 *
	 * @return the groupId of the Group currently being permission checked
	 */
	public long getGroupId();

	/**
	 * Get the initial set of roles calculated from persisted assignment and
	 * inheritance.
	 *
	 * @return the initial set of roles calculated from persisted assignment
	 *         and inheritance
	 */
	public long[] getInitialRoleIds();

	public User getUser();

	public UserBag getUserBag();

	/**
	 * Check if a Role is already in the collection by roleId.
	 *
	 * @param  roleId the roleId to check
	 * @return true of the Role is in the collection
	 */
	public boolean hasRoleId(long roleId);

	/**
	 * Returns <code>true</code> if the user is signed in.
	 *
	 * @return <code>true</code> if the user is signed in; <code>false</code>
	 *         otherwise
	 */
	public boolean isSignedIn();

	public boolean removeRoleId(long roleId);

}
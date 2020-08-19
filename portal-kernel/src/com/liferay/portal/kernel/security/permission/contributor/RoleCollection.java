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
 * Represents a managed collection of role IDs, starting with the
 * <em>initial</em> set calculated from persisted role assignment and role
 * inheritance. The roles can be contributed via {@link
 * RoleContributor#contribute(RoleCollection)}.
 *
 * @author Carlos Sierra Andrés
 * @author Raymond Augé
 */
@ProviderType
public interface RoleCollection {

	/**
	 * Adds the role ID to the collection.
	 *
	 * @param  roleId the ID of the role
	 * @return <code>true</code> if the role ID was added to the collection
	 */
	public boolean addRoleId(long roleId);

	/**
	 * Returns the primary key of the company whose permissions are being
	 * checked.
	 *
	 * @return the primary key of the company whose permissions are being
	 *         checked
	 */
	public long getCompanyId();

	/**
	 * Returns the primary key of the group whose permissions are being checked.
	 *
	 * @return the groupId of the Group currently being permission checked
	 */
	public long getGroupId();

	/**
	 * Returns the IDs of the initial set of roles calculated from persisted
	 * assignment and inheritance.
	 *
	 * @return the IDs of the initial set of roles calculated from persisted
	 *         assignment and inheritance
	 */
	public long[] getInitialRoleIds();

	public User getUser();

	public UserBag getUserBag();

	/**
	 * Returns <code>true</code> if the collection has the role ID.
	 *
	 * @param  roleId the ID of the role
	 * @return <code>true</code> if the collection has the role ID;
	 *         <code>false</code> otherwise
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
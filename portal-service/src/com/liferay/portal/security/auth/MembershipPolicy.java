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

package com.liferay.portal.security.auth;

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;

import java.util.List;

/**
 * @author Sergio González
 */
public interface MembershipPolicy {

	public List<Role> getForbiddenRoles(Group group, User user);

	public List<Role> getMandatoryRoles(Group group, User user);

	public List<Group> getMandatorySites(User user);

	public boolean isMembershipAllowed(Group group, User user);

}
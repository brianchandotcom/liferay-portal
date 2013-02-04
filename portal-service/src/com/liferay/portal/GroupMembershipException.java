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

package com.liferay.portal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;

import java.util.List;

/**
 * @author Eduardo Garcia
 */
public class GroupMembershipException extends PortalException {

	public static final int MEMBERSHIP_MANDATORY = 1;

	public static final int MEMBERSHIP_NOT_ALLOWED = 2;

	public GroupMembershipException(
		int type, List<Group> groups, List<User> users) {

		_users = users;
		_groups = groups;
		_type = type;
	}

	public List<Group> getGroups() {
		return _groups;
	}

	public int getType() {
		return _type;
	}

	public List<User> getUsers() {
		return _users;
	}

	private List<Group> _groups;
	private int _type;
	private List<User> _users;

}
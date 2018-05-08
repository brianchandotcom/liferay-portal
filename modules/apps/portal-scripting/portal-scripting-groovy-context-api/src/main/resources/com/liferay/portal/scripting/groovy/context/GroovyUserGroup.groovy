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

package com.liferay.portal.scripting.groovy.context;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.petra.string.StringPool;

/**
 * @author Michael C. Han
 */
class GroovyUserGroup {

	static UserGroup fetchUserGroup(
		GroovyScriptingContext groovyScriptingContext, String name) {

		return UserGroupLocalServiceUtil.fetchUserGroup(
			groovyScriptingContext.companyId, name);
	}

	GroovyUserGroup(String name_) {
		name = name_;
	}

	void addUsers(
		GroovyScriptingContext groovyScriptingContext,
		GroovyUser... groovyUsers) {

		if (userGroup == null) {
			create(groovyScriptingContext);
		}

		List<User> users = new ArrayList<>(groovyUsers.length);

		for (GroovyUser groovyUser : groovyUsers) {
			users.add(groovyUser.user);
		}

		UserLocalServiceUtil.addUserGroupUsers(
			userGroup.getUserGroupId(), users);
	}

	void create(GroovyScriptingContext groovyScriptingContext) {
		userGroup = UserGroupLocalServiceUtil.fetchUserGroup(
			groovyScriptingContext.companyId, name);

		if (userGroup != null) {
			return;
		}

		userGroup = UserGroupLocalServiceUtil.addUserGroup(
			groovyScriptingContext.defaultUserId,
			groovyScriptingContext.companyId, name, StringPool.BLANK,
			groovyScriptingContext.serviceContext);
	}

	String name;
	UserGroup userGroup;

}
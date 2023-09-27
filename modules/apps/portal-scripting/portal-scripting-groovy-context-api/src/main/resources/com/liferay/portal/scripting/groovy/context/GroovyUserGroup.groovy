/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.groovy.context;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

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
			groovyScriptingContext.guestUserId,
			groovyScriptingContext.companyId, name, StringPool.BLANK,
			groovyScriptingContext.serviceContext);
	}

	String name;
	UserGroup userGroup;

}
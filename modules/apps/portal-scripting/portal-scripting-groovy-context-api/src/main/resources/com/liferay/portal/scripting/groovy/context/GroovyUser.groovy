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

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Michael C. Han
 */
class GroovyUser {

	static User fetchUser(
		GroovyScriptingContext groovyScriptingContext, String name) {

		return UserLocalServiceUtil.fetchUserByScreenName(
			groovyScriptingContext.companyId, name);
	}

	GroovyUser(
		String emailAddress_, String password_, String firstName_,
		String lastName_, String jobTitle_) {

		this(
			emailAddress_, password_, firstName_, lastName_, jobTitle_, null,
			false);
	}

	GroovyUser(
		String emailAddress_, String password_, String firstName_,
		String lastName_, String jobTitle_, String uuid_) {

		this(
			emailAddress_, password_, firstName_, lastName_, jobTitle_, uuid_,
			false);
	}

	GroovyUser(
		String emailAddress_, String password_, String firstName_,
		String lastName_, String jobTitle_, String uuid_,
		Boolean resetPassword_) {

		emailAddress = emailAddress_;
		password = password_;
		firstName = firstName_;
		lastName = lastName_;
		jobTitle = jobTitle_;
		uuid = uuid_;
		resetPassword = resetPassword_;
	}

	void addRoles(
		GroovyScriptingContext groovyScriptingContext, String... roleNames) {

		List<Role> roles = new ArrayList<>(roleNames.length);

		for (String roleName : roleNames) {
			Role role = RoleLocalServiceUtil.fetchRole(
				groovyScriptingContext.companyId, roleName);

			roles.add(role);
		}

		RoleLocalServiceUtil.addUserRoles(user.getUserId(), roles);
	}

	void addSiteRoles(
		GroovyScriptingContext groovyScriptingContext, long groupId,
		String... roleNames) {

		long[] roleIds = new long[roleNames.length];

		for (int i = 0; i < roleNames.length; i++) {
			Role role = RoleLocalServiceUtil.fetchRole(
				groovyScriptingContext.companyId, roleNames[i]);

			roleIds[i] = role.getRoleId();
		}

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			user.getUserId(), groupId, roleIds);
	}

	void create(GroovyScriptingContext groovyScriptingContext) {
		user = UserLocalServiceUtil.fetchUserByEmailAddress(
			groovyScriptingContext.companyId, emailAddress);

		if (user != null) {
			return;
		}

		if (Validator.isNotNull(uuid)) {
			groovyScriptingContext.serviceContext.setUuid(uuid);
		}

		user = UserLocalServiceUtil.addUser(
			groovyScriptingContext.defaultUserId,
			groovyScriptingContext.companyId, false, password, password, true,
			null, emailAddress, 0, null, LocaleUtil.getDefault(), firstName,
			null, lastName, -1, -1, true, 1, 1, 1977, jobTitle, new long[0],
			new long[0], new long[0], new long[0], false,
			groovyScriptingContext.serviceContext);

		if (resetPassword) {
			updatePasswordReset(resetPassword);
		}
	}

	void joinOrganizations(
		GroovyScriptingContext groovyScriptingContext,
		String... organizationNames) {

		for (String organizationName : organizationNames) {
			Organization organization = GroovyOrganization.fetchOrganization(
				groovyScriptingContext, organizationName);

			if (organization != null) {
				UserLocalServiceUtil.addOrganizationUser(
					organization.getOrganizationId(), user.getUserId());
			}
		}
	}

	void joinSites(
		GroovyScriptingContext liferayScriptingContext, String... siteNames) {

		for (String siteName : siteNames) {
			Group group = GroupLocalServiceUtil.fetchGroup(
				liferayScriptingContext.companyId, siteName);

			UserLocalServiceUtil.addGroupUser(
				group.getGroupId(), user.getUserId());
		}
	}

	void updatePasswordReset(Boolean passwordReset) {
		if (user != null) {
			UserLocalServiceUtil.updatePasswordReset(
				user.userId, passwordReset);
		}
	}

	String emailAddress;
	String firstName;
	String jobTitle;
	String lastName;
	String password;
	boolean resetPassword;
	User user;
	String uuid;

}
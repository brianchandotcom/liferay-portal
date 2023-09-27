/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.groovy.context;

import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserConstants;
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
			groovyScriptingContext.guestUserId,
			groovyScriptingContext.companyId, false, password, password, true,
			null, emailAddress, LocaleUtil.getDefault(), firstName, null,
			lastName, -1, -1, true, 1, 1, 1977, jobTitle,
			UserConstants.TYPE_REGULAR, new long[0], new long[0], new long[0],
			new long[0], false, groovyScriptingContext.serviceContext);

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
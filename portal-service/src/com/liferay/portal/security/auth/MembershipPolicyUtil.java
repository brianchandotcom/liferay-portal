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
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;

import java.util.Set;

/**
 * @author Sergio González
 */
public class MembershipPolicyUtil {

	public static Set<Group> getForbiddenGroups(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getForbiddenGroups(user);
	}

	public static Set<Organization> getForbiddenOrganizations(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getForbiddenOrganizations(user);
	}

	public static Set<Role> getForbiddenRoles(Group group, User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getForbiddenRoles(group, user);
	}

	public static Set<Role> getForbiddenRoles(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getForbiddenRoles(user);
	}

	public static Set<Role> getForbiddenRoles(
		User user, Organization organization) {

		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getForbiddenRoles(organization, user);
	}

	public static Set<UserGroup> getForbiddenUserGroups(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getForbiddenUserGroups(user);
	}

	public static Set<Group> getMandatoryGroups(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getMandatoryGroups(user);
	}

	public static Set<Organization> getMandatoryOrganizations(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getMandatoryOrganizations(user);
	}

	public static Set<Role> getMandatoryRoles(Group group, User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getMandatoryRoles(group, user);
	}

	public static Set<Role> getMandatoryRoles(
		Organization organization, User user) {

		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getMandatoryRoles(organization, user);
	}

	public static Set<Role> getMandatoryRoles(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getMandatoryRoles(user);
	}

	public static Set<UserGroup> getMandatoryUserGroups(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.getMandatoryUserGroups(user);
	}

	public static boolean isApplicableUser(User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isApplicableUser(user);
	}

	public static boolean isMembershipAllowed(
		Group group, Role role, User user) {

		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipAllowed(group, role, user);
	}

	public static boolean isMembershipAllowed(Group group, User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipAllowed(group, user);
	}

	public static boolean isMembershipAllowed(
		Organization organization, Role role, User user) {

		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipAllowed(organization, role, user);
	}

	public static boolean isMembershipAllowed(
		Organization organization, User user) {

		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipAllowed(organization, user);
	}

	public static boolean isMembershipAllowed(Role role, User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipAllowed(role, user);
	}

	public static boolean isMembershipAllowed(UserGroup userGroup, User user) {
		MembershipPolicy membershipPolicy =
			MembershipPolicyFactoryUtil.getMembershipPolicy();

		return membershipPolicy.isMembershipAllowed(userGroup, user);
	}

}
/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserGroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.comparator.UserScreenNameComparator;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.directory.ldap.UserDirectory;
import com.liferay.vldap.server.internal.util.LdapUtil;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.directory.api.ldap.model.name.Dn;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class UserBuilder extends DirectoryBuilder {

	public UserBuilder() {
		attributeValidator.addAlwaysValidAttribute("cn");
		attributeValidator.addAlwaysValidAttribute("gidNumber");
		attributeValidator.addAlwaysValidAttribute("givenName");
		attributeValidator.addAlwaysValidAttribute("mail");
		attributeValidator.addAlwaysValidAttribute("member");
		attributeValidator.addAlwaysValidAttribute("sambaSID");
		attributeValidator.addAlwaysValidAttribute("sn");
		attributeValidator.addAlwaysValidAttribute("status");
		attributeValidator.addAlwaysValidAttribute("uid");
		attributeValidator.addAlwaysValidAttribute("uidNumber");
		attributeValidator.addAlwaysValidAttribute("uuid");

		attributeValidator.addValidAttributeValues("createTimestamp", "*");
		attributeValidator.addValidAttributeValues("displayName", "*");
		attributeValidator.addValidAttributeValues("modifyTimestamp", "*");
		attributeValidator.addValidAttributeValues(
			"objectclass", "groupOfNames", "inetOrgPerson", "liferayPerson",
			"sambaSAMAccount", "top", "*");
	}

	@Override
	public List<Directory> buildDirectories(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws Exception {

		List<Directory> directories = new ArrayList<>();

		for (Company company : searchBase.getCompanies()) {
			List<User> users = getUsers(company, searchBase, filterConstraints);

			for (User user : users) {
				Directory directory = new UserDirectory(
					searchBase.getTop(), company, user);

				directories.add(directory);
			}
		}

		return directories;
	}

	public List<User> getUsers(
			Company company, SearchBase searchBase,
			List<FilterConstraint> filterConstraints)
		throws Exception {

		if (filterConstraints.isEmpty()) {
			return UserLocalServiceUtil.getCompanyUsers(
				company.getCompanyId(), 0, (int)searchBase.getSizeLimit());
		}

		List<User> users = new ArrayList<>();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		Group community = searchBase.getCommunity();

		if (community != null) {
			params.put("usersGroups", community.getGroupId());
		}

		Organization organization = searchBase.getOrganization();

		if (organization != null) {
			params.put("usersOrgs", organization.getOrganizationId());
		}

		Role role = searchBase.getRole();

		if (role != null) {
			params.put("usersRoles", role.getRoleId());
		}

		UserGroup userGroup = searchBase.getUserGroup();

		if (userGroup != null) {
			params.put("usersUserGroups", userGroup.getUserGroupId());
		}

		for (FilterConstraint filterConstraint : filterConstraints) {
			if (!isValidFilterConstraint(filterConstraint)) {
				continue;
			}

			LinkedHashMap<String, Object> filterConstraintParams =
				new LinkedHashMap<>(params);

			String member = filterConstraint.getValue("member");

			if (!putParams(filterConstraintParams, member)) {
				continue;
			}

			String gidNumber = filterConstraint.getValue("gidNumber");

			if ((gidNumber != null) && !gidNumber.equals(StringPool.STAR) &&
				!gidNumber.equals(PortletPropsValues.POSIX_GROUP_ID)) {

				continue;
			}

			String uuid = filterConstraint.getValue("uuid");

			if (uuid != null) {
				users.add(
					UserLocalServiceUtil.getUserByUuidAndCompanyId(
						uuid, company.getCompanyId()));

				continue;
			}

			String screenName = filterConstraint.getValue("cn");

			if (screenName == null) {
				screenName = filterConstraint.getValue("uid");
			}

			if ((screenName != null) && screenName.equals(StringPool.STAR)) {
				screenName = null;
			}

			String emailAddress = filterConstraint.getValue("mail");

			String uidNumber = filterConstraint.getValue("uidNumber");
			String sambaSID = filterConstraint.getValue("sambaSID");

			List<User> searchUsers = null;

			if (uidNumber != null) {
				searchUsers = new ArrayList<>();

				long userId = GetterUtil.getLong(uidNumber);

				User user = UserLocalServiceUtil.fetchUser(userId);

				if (user == null) {
					continue;
				}

				searchUsers.add(user);
			}
			else if (sambaSID != null) {
				searchUsers = new ArrayList<>();

				int x = sambaSID.lastIndexOf(CharPool.DASH);

				if (x == -1) {
					continue;
				}

				int y = sambaSID.lastIndexOf(CharPool.DASH, x - 1);

				if (y == -1) {
					continue;
				}

				long userId = GetterUtil.getLong(sambaSID.substring(x + 1));

				User user = UserLocalServiceUtil.fetchUser(userId);

				if (user == null) {
					continue;
				}

				long companyId = GetterUtil.getLong(
					sambaSID.substring(y + 1, x));

				if (companyId != user.getCompanyId()) {
					continue;
				}

				searchUsers.add(user);
			}
			else {
				String firstName = filterConstraint.getValue("givenName");
				String lastName = filterConstraint.getValue("sn");
				int status = GetterUtil.getInteger(
					filterConstraint.getValue("status"));

				searchUsers = UserLocalServiceUtil.search(
					company.getCompanyId(), firstName, null, lastName,
					screenName, emailAddress, status, filterConstraintParams,
					true, 0, PortletPropsValues.SEARCH_MAX_SIZE,
					new UserScreenNameComparator());
			}

			for (User user : searchUsers) {
				if ((screenName != null) &&
					!screenName.equals(user.getScreenName())) {

					continue;
				}

				if ((emailAddress != null) &&
					!emailAddress.equals(user.getEmailAddress())) {

					continue;
				}

				users.add(user);
			}

			users = ListUtil.unique(users);

			if (users.size() > searchBase.getSizeLimit()) {
				users = ListUtil.subList(
					users, 0, (int)searchBase.getSizeLimit());
			}
		}

		return users;
	}

	protected boolean putParams(Map<String, Object> params, String member)
		throws Exception {

		if (member == null) {
			return true;
		}

		Dn dn = new Dn(member);

		String companyWebId = LdapUtil.getRdnValue(dn, 1);
		String type = LdapUtil.getRdnValue(dn, 2);
		String typeValue = LdapUtil.getRdnValue(dn, 3);

		Company company = CompanyLocalServiceUtil.getCompanyByWebId(
			companyWebId);

		if (StringUtil.equalsIgnoreCase(type, "Communities")) {
			Group group = GroupLocalServiceUtil.getGroup(
				company.getCompanyId(), typeValue);

			params.put("usersGroups", group.getGroupId());
		}
		else if (StringUtil.equalsIgnoreCase(type, "Organizations")) {
			Organization organization =
				OrganizationLocalServiceUtil.getOrganization(
					company.getCompanyId(), typeValue);

			params.put("usersOrgs", organization.getOrganizationId());
		}
		else if (StringUtil.equalsIgnoreCase(type, "Roles")) {
			Role role = RoleLocalServiceUtil.getRole(
				company.getCompanyId(), typeValue);

			params.put("usersRoles", role.getRoleId());
		}
		else if (StringUtil.equalsIgnoreCase(type, "User Groups")) {
			UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
				company.getCompanyId(), typeValue);

			params.put("usersUserGroups", userGroup.getUserGroupId());
		}
		else {
			return false;
		}

		return true;
	}

}
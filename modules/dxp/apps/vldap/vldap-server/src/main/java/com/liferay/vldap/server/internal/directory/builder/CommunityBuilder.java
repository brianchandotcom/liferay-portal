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

package com.liferay.vldap.server.internal.directory.builder;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.CommunityDirectory;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.util.LdapUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.directory.api.ldap.model.exception.LdapInvalidDnException;
import org.apache.directory.api.ldap.model.name.Dn;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class CommunityBuilder extends DirectoryBuilder {

	public CommunityBuilder() {
		attributeValidator.addAlwaysValidAttribute("cn");
		attributeValidator.addAlwaysValidAttribute("description");
		attributeValidator.addAlwaysValidAttribute("member");
		attributeValidator.addAlwaysValidAttribute("ou");

		attributeValidator.addValidAttributeValues(
			"objectclass", "groupOfNames", "liferayCommunity",
			"organizationalUnit", "top", "*");
	}

	@Override
	public List<Directory> buildDirectories(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws LdapInvalidDnException {

		List<Directory> directories = new ArrayList<>();

		for (Company company : searchBase.getCompanies()) {
			List<Group> groups = getGroups(
				company, filterConstraints, (int)searchBase.getSizeLimit());

			for (Group group : groups) {
				Directory directory = new CommunityDirectory(
					searchBase.getTop(), company, group);

				directories.add(directory);
			}
		}

		return directories;
	}

	protected List<Group> getGroups(
			Company company, List<FilterConstraint> filterConstraints,
			int sizeLimit)
		throws LdapInvalidDnException {

		if (filterConstraints.isEmpty()) {
			return GroupLocalServiceUtil.getCompanyGroups(
				company.getCompanyId(), 0, sizeLimit);
		}

		List<Group> groups = new ArrayList<>();

		for (FilterConstraint filterConstraint : filterConstraints) {
			if (!isValidFilterConstraint(filterConstraint)) {
				continue;
			}

			String name = filterConstraint.getValue("ou");

			if (name == null) {
				name = filterConstraint.getValue("cn");
			}

			String description = filterConstraint.getValue("description");

			String member = filterConstraint.getValue("member");

			String screenName = LdapUtil.getRdnValue(new Dn(member), 3);

			if (screenName != null) {
				User user = UserLocalServiceUtil.fetchUserByScreenName(
					company.getCompanyId(), screenName);

				if (user == null) {
					continue;
				}

				for (Group group : user.getGroups()) {
					if ((name != null) && !name.equals(group.getName())) {
						continue;
					}

					if ((description != null) &&
						!description.equals(group.getDescription())) {

						continue;
					}

					groups.add(group);
				}
			}
			else {
				LinkedHashMap<String, Object> params = new LinkedHashMap<>();

				groups.addAll(
					GroupLocalServiceUtil.search(
						company.getCompanyId(), name, description, params, true,
						0, sizeLimit));
			}
		}

		return ListUtil.unique(groups);
	}

}
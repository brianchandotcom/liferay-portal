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
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.directory.ldap.UserGroupsDirectory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class UserGroupsBuilder extends DirectoryBuilder {

	public UserGroupsBuilder() {
		attributeValidator.addValidAttributeValues(
			"objectclass", "organizationalUnit", "top", "*");
		attributeValidator.addValidAttributeValues("ou", "User Groups", "*");
	}

	@Override
	public List<Directory> buildDirectories(
		SearchBase searchBase, List<FilterConstraint> filterConstraints) {

		List<Directory> directories = new ArrayList<>();

		for (Company company : searchBase.getCompanies()) {
			if (filterConstraints.isEmpty()) {
				Directory directory = new UserGroupsDirectory(
					searchBase.getTop(), company);

				directories.add(directory);
			}
			else {
				for (FilterConstraint filterConstraint : filterConstraints) {
					if (!isValidFilterConstraint(filterConstraint)) {
						continue;
					}

					Directory directory = new UserGroupsDirectory(
						searchBase.getTop(), company);

					directories.add(directory);
				}
			}
		}

		return directories;
	}

}
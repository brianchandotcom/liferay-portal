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

import com.liferay.portal.kernel.model.Company;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.directory.ldap.RolesDirectory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class RolesBuilder extends DirectoryBuilder {

	public RolesBuilder() {
		attributeValidator.addValidAttributeValues(
			"objectclass", "organizationalUnit", "top", "*");
		attributeValidator.addValidAttributeValues("ou", "Roles", "*");
	}

	@Override
	public List<Directory> buildDirectories(
		SearchBase searchBase, List<FilterConstraint> filterConstraints) {

		List<Directory> directories = new ArrayList<>();

		for (Company company : searchBase.getCompanies()) {
			if (filterConstraints.isEmpty()) {
				Directory directory = new RolesDirectory(
					searchBase.getTop(), company);

				directories.add(directory);
			}
			else {
				for (FilterConstraint filterConstraint : filterConstraints) {
					if (!isValidFilterConstraint(filterConstraint)) {
						continue;
					}

					Directory directory = new RolesDirectory(
						searchBase.getTop(), company);

					directories.add(directory);

					break;
				}
			}
		}

		return directories;
	}

}
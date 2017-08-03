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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.CompanyDirectory;
import com.liferay.vldap.server.internal.directory.ldap.Directory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class CompanyBuilder extends DirectoryBuilder {

	public CompanyBuilder() {
		attributeValidator.addAlwaysValidAttribute("ou");

		attributeValidator.addValidAttributeValues(
			"objectclass", "organizationalUnit", "top", "*");
	}

	@Override
	public List<Directory> buildDirectories(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws PortalException {

		List<Directory> directories = new ArrayList<>();

		List<Company> companies = getCompanies(filterConstraints);

		for (Company company : companies) {
			Directory directory = new CompanyDirectory(
				searchBase.getTop(), company);

			directories.add(directory);
		}

		return directories;
	}

	protected List<Company> getCompanies(
			List<FilterConstraint> filterConstraints)
		throws PortalException {

		if (filterConstraints.isEmpty()) {
			return CompanyLocalServiceUtil.getCompanies();
		}

		List<Company> companies = new ArrayList<>();

		for (FilterConstraint filterConstraint : filterConstraints) {
			if (!isValidFilterConstraint(filterConstraint)) {
				continue;
			}

			String companyWebId = filterConstraint.getValue("ou");

			if (companyWebId == null) {
				companies.addAll(CompanyLocalServiceUtil.getCompanies(false));
			}
			else {
				Company company = CompanyLocalServiceUtil.getCompanyByWebId(
					companyWebId);

				companies.add(company);
			}
		}

		return ListUtil.unique(companies);
	}

}
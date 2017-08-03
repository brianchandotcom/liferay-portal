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
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.directory.ldap.SambaMachineDirectory;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Minhchau Dang
 */
public class SambaMachineBuilder extends OrganizationBuilder {

	public SambaMachineBuilder() {
		attributeValidator.addAlwaysValidAttribute("sambaDomainName");

		attributeValidator.addValidAttributeValues(
			"objectclass", "sambaDomain");
	}

	@Override
	public List<Directory> buildDirectories(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws Exception {

		if (filterConstraints.isEmpty()) {
			return new ArrayList<>();
		}
		else if (searchBase.getOrganization() == null) {
			return buildAllOrganizationDirectories(
				searchBase, filterConstraints);
		}
		else {
			return buildSingleOrganizationDirectory(
				searchBase, filterConstraints);
		}
	}

	public List<Directory> buildDirectories(
		String top, Company company, Organization organization,
		String sambaDomainName) {

		List<Directory> directories = new ArrayList<>();

		for (String curSambaDomainName :
				PortletPropsValues.SAMBA_DOMAIN_NAMES) {

			if (Validator.isNotNull(sambaDomainName) &&
				!curSambaDomainName.equals(sambaDomainName)) {

				continue;
			}

			SambaMachineDirectory sambaMachineDirectory =
				new SambaMachineDirectory(
					top, company, organization, curSambaDomainName);

			directories.add(sambaMachineDirectory);
		}

		return directories;
	}

	protected void addSubdirectories(
		List<Directory> directories, String top, Company company,
		Organization organization, List<FilterConstraint> filterConstraints) {

		for (FilterConstraint filterConstraint : filterConstraints) {
			if (!isValidFilterConstraint(filterConstraint)) {
				continue;
			}

			String sambaDomainName = filterConstraint.getValue(
				"sambaDomainName");

			List<Directory> subdirectories = buildDirectories(
				top, company, organization, sambaDomainName);

			directories.addAll(subdirectories);
		}
	}

	protected List<Directory> buildAllOrganizationDirectories(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws Exception {

		List<Directory> directories = new ArrayList<>();

		for (Company company : searchBase.getCompanies()) {
			List<Organization> organizations = getOrganizations(
				company, filterConstraints, (int)searchBase.getSizeLimit());

			for (Organization organization : organizations) {
				addSubdirectories(
					directories, searchBase.getTop(), company, organization,
					filterConstraints);
			}
		}

		return directories;
	}

	protected List<Directory> buildSingleOrganizationDirectory(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws Exception {

		List<Directory> directories = new ArrayList<>();

		addSubdirectories(
			directories, searchBase.getTop(), searchBase.getCompany(),
			searchBase.getOrganization(), filterConstraints);

		return directories;
	}

}
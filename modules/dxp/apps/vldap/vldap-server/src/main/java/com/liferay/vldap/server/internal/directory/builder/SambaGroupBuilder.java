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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.vldap.server.internal.directory.FilterConstraint;
import com.liferay.vldap.server.internal.directory.SearchBase;
import com.liferay.vldap.server.internal.directory.ldap.Directory;
import com.liferay.vldap.server.internal.directory.ldap.SambaGroup;
import com.liferay.vldap.server.internal.directory.ldap.SambaGroupDirectory;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Minhchau Dang
 */
public class SambaGroupBuilder extends OrganizationBuilder {

	public SambaGroupBuilder() {
		attributeValidator.addAlwaysValidAttribute("cn");
		attributeValidator.addAlwaysValidAttribute("displayName");
		attributeValidator.addAlwaysValidAttribute("gidNumber");
		attributeValidator.addAlwaysValidAttribute("sambaGroupType");
		attributeValidator.addAlwaysValidAttribute("sambaSID");
		attributeValidator.addAlwaysValidAttribute("sambaSIDList");

		attributeValidator.addValidAttributeValues(
			"objectclass", "liferayRole", "posixGroup", "sambaGroupMapping",
			"top", "*");
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

		return buildSingleOrganizationDirectory(searchBase, filterConstraints);
	}

	protected void addSambaGroup(
		List<SambaGroup> sambaGroups, String name, String sambaSID,
		String gidNumber) {

		SambaGroup sambaGroup = new SambaGroup(name, sambaSID, gidNumber);

		sambaGroups.add(sambaGroup);
	}

	protected void addSubdirectories(
		List<Directory> directories, List<FilterConstraint> filterConstraints,
		SearchBase searchBase, Company company, Organization organization) {

		for (FilterConstraint filterConstraint : filterConstraints) {
			if (!isValidFilterConstraint(filterConstraint)) {
				continue;
			}

			String name = filterConstraint.getValue("cn");

			if (Validator.isNull(name)) {
				name = filterConstraint.getValue("displayName");
			}

			String sambaSID = filterConstraint.getValue("sambaSID");

			if (sambaSID == null) {
				sambaSID = filterConstraint.getValue("sambaSIDList");
			}

			String gidNumber = filterConstraint.getValue("gidNumber");

			List<Directory> subdirectories = getSambaGroupDirectories(
				searchBase.getTop(), company, organization, name, sambaSID,
				gidNumber);

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
					directories, filterConstraints, searchBase, company,
					organization);
			}
		}

		return directories;
	}

	protected List<Directory> buildSingleOrganizationDirectory(
			SearchBase searchBase, List<FilterConstraint> filterConstraints)
		throws Exception {

		List<Directory> directories = new ArrayList<>();

		addSubdirectories(
			directories, filterConstraints, searchBase, searchBase.getCompany(),
			searchBase.getOrganization());

		return directories;
	}

	protected void filterSambaGroups(
		List<SambaGroup> sambaGroups, String fieldName, String value) {

		if ((value == null) || value.equals(StringPool.STAR)) {
			return;
		}

		Iterator<SambaGroup> iterator = sambaGroups.iterator();

		while (iterator.hasNext()) {
			SambaGroup sambaGroup = iterator.next();

			String sambaGroupValue = null;

			if (fieldName.equals("gidNumber")) {
				sambaGroupValue = sambaGroup.getGIDNumber();
			}
			else if (fieldName.equals("name")) {
				sambaGroupValue = sambaGroup.getName();
			}
			else if (fieldName.equals("sambaSID")) {
				sambaGroupValue = sambaGroup.getSambaSID();
			}

			if ((sambaGroupValue == null) || !value.equals(sambaGroupValue)) {
				iterator.remove();
			}
		}
	}

	protected List<Directory> getSambaGroupDirectories(
		String top, Company company, Organization organization, String name,
		String sambaSID, String gidNumber) {

		List<Directory> directories = new ArrayList<>();

		List<SambaGroup> sambaGroups = getSambaGroups(company);

		filterSambaGroups(sambaGroups, "name", name);
		filterSambaGroups(sambaGroups, "gidNumber", gidNumber);
		filterSambaGroups(sambaGroups, "sambaSID", sambaSID);

		for (SambaGroup sambaGroup : sambaGroups) {
			Directory directory = new SambaGroupDirectory(
				top, company, organization, sambaGroup);

			directories.add(directory);
		}

		return directories;
	}

	protected List<SambaGroup> getSambaGroups(Company company) {
		List<SambaGroup> sambaGroups = new ArrayList<>();

		addSambaGroup(sambaGroups, "authenticated", "S-1-5-11", null);

		String domainPrefix = "S-1-5-21-" + company.getCompanyId();

		addSambaGroup(
			sambaGroups, "domain admins", domainPrefix + "-512",
			_ADMIN_POSIX_GROUP_ID);
		addSambaGroup(
			sambaGroups, "domain guests", domainPrefix + "-514",
			_GUEST_POSIX_GROUP_ID);
		addSambaGroup(
			sambaGroups, "domain users", domainPrefix + "-513",
			_USER_POSIX_GROUP_ID);

		addSambaGroup(sambaGroups, "everyone", "S-1-1-0", _USER_POSIX_GROUP_ID);
		addSambaGroup(sambaGroups, "network", "S-1-5-2", null);
		addSambaGroup(sambaGroups, "nobody", "S-1-0-0", _NOBODY_POSIX_GROUP_ID);
		addSambaGroup(
			sambaGroups, "nogroup", "S-1-5-32-546", _GUEST_POSIX_GROUP_ID);
		addSambaGroup(
			sambaGroups, "root", "S-1-5-32-544", _ADMIN_POSIX_GROUP_ID);
		addSambaGroup(
			sambaGroups, "users", "S-1-5-32-545", _USER_POSIX_GROUP_ID);

		return sambaGroups;
	}

	private static final String _ADMIN_POSIX_GROUP_ID = "0";

	private static final String _GUEST_POSIX_GROUP_ID = "65534";

	private static final String _NOBODY_POSIX_GROUP_ID = "99";

	private static final String _USER_POSIX_GROUP_ID =
		PortletPropsValues.POSIX_GROUP_ID;

}
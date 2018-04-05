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

import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RegionServiceUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Michael C. Han
 */
class GroovyOrganization {

	static Organization fetchOrganization(
		GroovyScriptingContext groovyScriptingContext, String name) {

		return OrganizationLocalServiceUtil.fetchOrganization(
			groovyScriptingContext.companyId, name);
	}

	GroovyOrganization(String name_) {
		name = name_;
	}

	GroovyOrganization(String name_, String parentOrganizationName_) {
		name = name_;
		parentOrganizationName = parentOrganizationName_;
	}

	GroovyOrganization(
		String comments_, String name_, String parentOrganizationName_,
		String type_, String regionCode, String countryName, boolean site_) {

		comments = comments_;
		name = name_;
		parentOrganizationName = parentOrganizationName_;
		type = type_;
		site = site_;

		regionId = RegionServiceUtil.fetchRegion(countryId, regionCode);
		countryId = CountryServiceUtil.getCountryByName(countryName);
	}

	void create(GroovyScriptingContext groovyScriptingContext) {
		organization = fetchOrganization(groovyScriptingContext, name);

		if (organization != null) {
			return;
		}

		long parentOrganizationId =
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID;

		if (Validator.isNotNull(parentOrganizationName)) {
			Organization parentOrganization = fetchOrganization(
				groovyScriptingContext, parentOrganizationName);

			if (parentOrganization != null) {
				parentOrganizationId = parentOrganization.getOrganizationId();
			}
		}

		if (type == null) {
			type = OrganizationConstants.TYPE_ORGANIZATION;
		}

		organization = OrganizationLocalServiceUtil.addOrganization(
			groovyScriptingContext.defaultUserId, parentOrganizationId, name,
			type, regionId, countryId,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, comments, site,
			groovyScriptingContext.getServiceContext());
	}

	String comments;
	long countryId;
	String name;
	Organization organization;
	String parentOrganizationName;
	long regionId;
	boolean site;
	String type;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.scripting.groovy.context;

import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
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
		countryId = CountryServiceUtil.getCountryByName(
			CompanyThreadLocal.getCompanyId(), countryName);
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
			groovyScriptingContext.guestUserId, parentOrganizationId, name,
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
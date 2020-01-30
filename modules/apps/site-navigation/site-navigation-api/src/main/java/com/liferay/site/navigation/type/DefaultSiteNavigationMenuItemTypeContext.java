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

package com.liferay.site.navigation.type;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;

import java.util.Optional;

/**
 * @author Pavel Savinov
 */
public class DefaultSiteNavigationMenuItemTypeContext
	implements SiteNavigationMenuItemTypeContext {

	public DefaultSiteNavigationMenuItemTypeContext(Company company) {
		_company = company;
	}

	public DefaultSiteNavigationMenuItemTypeContext(Group group) {
		_company = CompanyLocalServiceUtil.fetchCompany(group.getCompanyId());
		_group = group;
	}

	public Company getCompany() {
		return _company;
	}

	public Optional<Group> getGroupOptional() {
		return Optional.of(_group);
	}

	private final Company _company;
	private Group _group;

}
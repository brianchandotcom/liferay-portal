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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;

import java.util.Arrays;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(immediate = true, service = ModelListener.class)
public class OrganizationModelListener
	extends BaseEntityModelListener<Organization> {

	@Override
	protected List<String> getAttributes() {
		return _attributes;
	}

	@Override
	protected Organization getOriginalModel(Organization organization)
		throws Exception {

		return _organizationLocalService.getOrganization(
			organization.getOrganizationId());
	}

	@Override
	protected String getPrimaryKeyName() {
		return "organizationId";
	}

	private static final List<String> _attributes = Arrays.asList(
		"comments", "countryId", "name", "parentOrganizationId", "regionId");

	@Reference
	private OrganizationLocalService _organizationLocalService;

}
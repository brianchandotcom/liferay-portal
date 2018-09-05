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

package com.liferay.organization.apio.internal.architect.router;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.apio.architect.pagination.Pagination;
import com.liferay.apio.architect.router.NestedCollectionRouter;
import com.liferay.apio.architect.routes.NestedCollectionRoutes;
import com.liferay.organization.apio.architect.identifier.OrganizationIdentifier;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * Provides the information necessary to expose the <a
 * href="http://schema.org/Organization">Organization</a> resources contained
 * inside an <a href="http://schema.org/Organization">Organization</a> through a
 * web API. The resources are mapped from the internal model {@link
 * Organization}.
 *
 * @author Eduardo Perez
 * @review
 */
@Component(immediate = true)
public class OrganizationNestedCollectionRouter
	implements NestedCollectionRouter
		<Organization, Long, OrganizationIdentifier, Long,
		 OrganizationIdentifier> {

	@Override
	public NestedCollectionRoutes<Organization, Long, Long> collectionRoutes(
		NestedCollectionRoutes.Builder<Organization, Long, Long> builder) {

		return builder.addGetter(
			this::_getPageItems, Company.class
		).build();
	}

	private PageItems<Organization> _getPageItems(
		Pagination pagination, long parentOrganizationId, Company company) {

		List<Organization> organizations =
			_organizationService.getOrganizations(
				company.getCompanyId(), parentOrganizationId,
				pagination.getStartPosition(), pagination.getEndPosition());
		int count = _organizationService.getOrganizationsCount(
			company.getCompanyId(), parentOrganizationId);

		return new PageItems<>(organizations, count);
	}

	@Reference
	private OrganizationService _organizationService;

}
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

package com.liferay.commerce.organization.service.impl;

import com.liferay.commerce.organization.service.base.CommerceOrganizationServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Address;
import com.liferay.portal.kernel.model.EmailAddress;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
public class CommerceOrganizationServiceImpl
	extends CommerceOrganizationServiceBaseImpl {

	@Override
	public Organization addOrganization(
			long parentOrganizationId, String name, String type,
			ServiceContext serviceContext)
		throws PortalException {

		return commerceOrganizationLocalService.addOrganization(
			parentOrganizationId, name, type, serviceContext);
	}

	@Override
	public void addOrganizationUsers(
			long organizationId, String[] emailAddresses,
			ServiceContext serviceContext)
		throws PortalException {

		commerceOrganizationLocalService.addOrganizationUsers(
			organizationId, emailAddresses, serviceContext);
	}

	@Override
	public Organization getOrganization(long organizationId)
		throws PortalException {

		return organizationService.getOrganization(organizationId);
	}

	@Override
	public Address getOrganizationPrimaryAddress(long organizationId)
		throws PortalException {

		return commerceOrganizationLocalService.getOrganizationPrimaryAddress(
			organizationId);
	}

	@Override
	public EmailAddress getOrganizationPrimaryEmailAddress(long organizationId)
		throws PortalException {

		return
			commerceOrganizationLocalService.getOrganizationPrimaryEmailAddress(
				organizationId);
	}

	@Override
	public BaseModelSearchResult<Organization> searchOrganizations(
			long organizationId, String type, String keywords, int start,
			int end, Sort[] sorts)
		throws PortalException {

		return commerceOrganizationLocalService.searchOrganizations(
			organizationId, type, keywords, start, end, sorts);
	}

	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException {

		commerceOrganizationLocalService.unsetOrganizationUsers(
			organizationId, userIds);
	}

}
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
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.GroupPermissionUtil;
import com.liferay.portal.kernel.service.permission.OrganizationPermissionUtil;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CommerceOrganizationServiceImpl
	extends CommerceOrganizationServiceBaseImpl {

	@Override
	public Organization addOrganization(
			long parentOrganizationId, String name, String type,
			ServiceContext serviceContext)
		throws PortalException {

		if (parentOrganizationId ==
				OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID) {

			PortalPermissionUtil.check(
				getPermissionChecker(), ActionKeys.ADD_ORGANIZATION);
		}
		else {
			OrganizationPermissionUtil.check(
				getPermissionChecker(), parentOrganizationId,
				ActionKeys.ADD_ORGANIZATION);
		}

		return commerceOrganizationLocalService.addOrganization(
			parentOrganizationId, name, type, serviceContext);
	}

	@Override
	public void addOrganizationUsers(
			long organizationId, String[] emailAddresses,
			ServiceContext serviceContext)
		throws PortalException {

		GroupPermissionUtil.check(
			getPermissionChecker(), serviceContext.getScopeGroupId(),
			ActionKeys.ASSIGN_MEMBERS);

		commerceOrganizationLocalService.addOrganizationUsers(
			organizationId, emailAddresses, serviceContext);
	}

	@Override
	public void deleteOrganization(long organizationId) throws PortalException {
		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.DELETE);

		organizationLocalService.deleteOrganization(organizationId);
	}

	@Override
	public Organization fetchOrganization(long organizationId)
		throws PortalException {

		Organization organization = organizationLocalService.fetchOrganization(
			organizationId);

		if (organization != null) {
			_checkOrganization(organization.getOrganizationId());
		}

		return organization;
	}

	@Override
	public Organization getOrganization(long organizationId)
		throws PortalException {

		_checkOrganization(organizationId);

		return organizationLocalService.getOrganization(organizationId);
	}

	@Override
	public Address getOrganizationPrimaryAddress(long organizationId)
		throws PortalException {

		_checkOrganization(organizationId);

		return commerceOrganizationLocalService.getOrganizationPrimaryAddress(
			organizationId);
	}

	@Override
	public EmailAddress getOrganizationPrimaryEmailAddress(long organizationId)
		throws PortalException {

		_checkOrganization(organizationId);

		return
			commerceOrganizationLocalService.getOrganizationPrimaryEmailAddress(
				organizationId);
	}

	@Override
	public BaseModelSearchResult<Organization> searchOrganizations(
			long userId, long organizationId, String type, String keywords,
			int start, int end, Sort[] sorts)
		throws PortalException {

		return commerceOrganizationLocalService.searchOrganizations(
			userId, organizationId, type, keywords, start, end, sorts);
	}

	@Override
	public BaseModelSearchResult<Organization> searchOrganizationsByGroup(
			long groupId, long userId, String type, String keywords, int start,
			int end, Sort[] sorts)
		throws PortalException {

		return commerceOrganizationLocalService.searchOrganizationsByGroup(
			groupId, userId, type, keywords, start, end, sorts);
	}

	@Override
	public void unsetOrganizationUsers(long organizationId, long[] userIds)
		throws PortalException {

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		GroupPermissionUtil.check(
			getPermissionChecker(), organization.getGroupId(),
			ActionKeys.ASSIGN_MEMBERS);

		commerceOrganizationLocalService.unsetOrganizationUsers(
			organizationId, userIds);
	}

	@Override
	public Organization updateOrganization(
			long organizationId, String name, long emailAddressId,
			String address, long addressId, String street1, String street2,
			String street3, String city, String zip, long regionId,
			long countryId, boolean logo, byte[] logoBytes,
			ServiceContext serviceContext)
		throws PortalException {

		_checkOrganization(organizationId);

		OrganizationPermissionUtil.check(
			getPermissionChecker(), organizationId, ActionKeys.UPDATE);

		return commerceOrganizationLocalService.updateOrganization(
			organizationId, name, emailAddressId, address, addressId, street1,
			street2, street3, city, zip, regionId, countryId, logo, logoBytes,
			serviceContext);
	}

	private void _checkOrganization(long organizationId)
		throws PortalException {

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isOmniadmin() ||
			permissionChecker.isCompanyAdmin()) {

			return;
		}

		User user = getUser();

		long[] userOrganizationIds = user.getOrganizationIds();

		if (ArrayUtil.contains(userOrganizationIds, organizationId)) {
			return;
		}

		Organization organization = organizationLocalService.getOrganization(
			organizationId);

		long[] ancestorOrganizationIds =
			organization.getAncestorOrganizationIds();

		for (long ancestorOrganizationId : ancestorOrganizationIds) {
			if (ArrayUtil.contains(
					userOrganizationIds, ancestorOrganizationId)) {

				return;
			}
		}

		throw new PrincipalException.MustHavePermission(
			getUserId(), Organization.class.getName(), organizationId,
			ActionKeys.VIEW);
	}

}
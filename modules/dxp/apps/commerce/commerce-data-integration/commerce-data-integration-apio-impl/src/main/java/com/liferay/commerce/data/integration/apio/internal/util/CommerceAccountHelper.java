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

package com.liferay.commerce.data.integration.apio.internal.util;

import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = CommerceAccountHelper.class)
public class CommerceAccountHelper {

	public Organization createAccount(
			String name, Long parentOrganizationId, List<Long> userIds)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext();

		Organization organization =
			_commerceOrganizationLocalService.addOrganization(
				parentOrganizationId, name,
				CommerceOrganizationConstants.TYPE_ACCOUNT, serviceContext);

		_addMembers(userIds, organization);

		return organization;
	}

	public Organization updateAccount(
			Long organizationId, String name, List<Long> userIds)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext();

		Organization organization = _organizationLocalService.getOrganization(
			organizationId);

		organization = _commerceOrganizationLocalService.updateOrganization(
			organization.getOrganizationId(),
			organization.getParentOrganizationId(), name,
			organization.getType(), organization.getRegionId(),
			organization.getCountryId(), organization.getStatusId(),
			organization.getComments(), serviceContext);

		_addMembers(userIds, organization);

		return organization;
	}

	private void _addMembers(List<Long> userIds, Organization organization) {
		if (userIds != null) {
			_removeAllMembers(organization);

			for (Long userId : userIds) {
				try {
					User userMember = _userLocalService.getUser(userId);

					if (userMember != null) {
						_userLocalService.addOrganizationUser(
							organization.getOrganizationId(), userId);
					}
				}
				catch (PortalException pe) {
					_log.error("Unable to add member", pe);
				}
			}
		}
	}

	private ServiceContext _getServiceContext() throws PortalException {
		User user = _userLocalService.getUserById(
			PrincipalThreadLocal.getUserId());

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(user.getCompanyId());
		serviceContext.setTimeZone(user.getTimeZone());
		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private void _removeAllMembers(Organization organization) {
		_userLocalService.clearOrganizationUsers(
			organization.getOrganizationId());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountHelper.class);

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
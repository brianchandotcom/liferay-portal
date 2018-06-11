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

import aQute.bnd.osgi.resource.FilterParser;
import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.List;
import java.util.stream.LongStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = AccountHelper.class)
public class AccountHelper {

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
					_log.error("Error on add member", pe);
				}
			}
		}
	}

	public Organization createAccount(String name, Long parentOrganizationId, List<Long> userIds)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext();

		Organization organization = _commerceOrganizationLocalService.addOrganization(parentOrganizationId, name, CommerceOrganizationConstants.TYPE_ACCOUNT,
                serviceContext);

		_addMembers(userIds, organization);

		return organization;
	}

	public Organization updateAccount(Long organizationId, String name, List<Long> userIds)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext();

		Organization organization = _organizationLocalService.getOrganization(organizationId);
		organization.setName(name);

		organization = _commerceOrganizationLocalService.updateOrganization(organization.getOrganizationId(), organization.getParentOrganizationId(),
			organization.getName(), organization.getType(), organization.getRegionId(), organization.getCountryId(),
			organization.getStatusId(), organization.getComments(), serviceContext);

		_addMembers(userIds, organization);

		return organization;
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

	private static final Log _log = LogFactoryUtil.getLog(AccountHelper.class);

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private CommerceOrganizationLocalService _commerceOrganizationLocalService;

}
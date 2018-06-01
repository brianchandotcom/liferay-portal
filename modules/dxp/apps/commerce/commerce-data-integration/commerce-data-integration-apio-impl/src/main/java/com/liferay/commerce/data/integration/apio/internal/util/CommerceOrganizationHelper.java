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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ListTypeConstants;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.List;

/**
 * @author Rodrigo Guedes de Souza
 */
@Component(immediate = true, service = CommerceOrganizationHelper.class)
public class CommerceOrganizationHelper {

	public void addMembers(List<Long> userIds, Organization organization) {
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

	public Organization createOrganization(String name) throws PortalException {
		ServiceContext serviceContext = _getServiceContext();

		return _organizationService.addOrganization(
			0, name, OrganizationConstants.TYPE_ORGANIZATION, 0, 0,
			ListTypeConstants.ORGANIZATION_STATUS_DEFAULT, StringPool.BLANK,
			false, serviceContext);
	}

	public Organization updateOrganization(Long organizationId, String name)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext();

		return _organizationService.updateOrganization(
			organizationId, 0, name, OrganizationConstants.TYPE_ORGANIZATION,
			0, 0, ListTypeConstants.ORGANIZATION_STATUS_DEFAULT,
			StringPool.BLANK, false, serviceContext);
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

	private static final Log _log = LogFactoryUtil.getLog(CommerceOrganizationHelper.class);

	@Reference
	private OrganizationService _organizationService;

	@Reference
	private UserLocalService _userLocalService;

}
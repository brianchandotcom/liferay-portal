/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.external.reference.service.impl;

import com.liferay.external.reference.service.base.ERUserGroupLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Dylan Rebelak
 */
@Component(
	property = "model.class.name=com.liferay.portal.kernel.model.UserGroup",
	service = AopService.class
)
public class ERUserGroupLocalServiceImpl
	extends ERUserGroupLocalServiceBaseImpl {

	@Override
	public UserGroup addOrUpdateUserGroup(
			String externalReferenceCode, long userId, long companyId,
			String name, String description, ServiceContext serviceContext)
		throws PortalException {

		UserGroup userGroup =
			_userGroupLocalService.fetchUserGroupByExternalReferenceCode(
				externalReferenceCode, companyId);

		if (userGroup == null) {
			return _userGroupLocalService.addUserGroup(
				externalReferenceCode, userId, companyId, name, description,
				serviceContext);
		}

		return _userGroupLocalService.updateUserGroup(
			externalReferenceCode, companyId, userGroup.getUserGroupId(), name,
			description, serviceContext);
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}
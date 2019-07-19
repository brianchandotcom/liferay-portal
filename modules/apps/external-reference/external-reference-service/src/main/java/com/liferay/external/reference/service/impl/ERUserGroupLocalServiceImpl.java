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

package com.liferay.external.reference.service.impl;

import com.liferay.external.reference.service.base.ERUserGroupLocalServiceBaseImpl;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;

import org.osgi.service.component.annotations.Component;

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
			userGroupLocalService.fetchUserGroupByReferenceCode(
				companyId, externalReferenceCode);

		if (userGroup == null) {
			userGroup = userGroupLocalService.addUserGroup(
				userId, companyId, name, description, serviceContext);

			userGroup.setExternalReferenceCode(externalReferenceCode);

			userGroup = userGroupLocalService.updateUserGroup(userGroup);
		}
		else {
			userGroupLocalService.updateUserGroup(
				companyId, userGroup.getUserGroupId(), name, description,
				serviceContext);
		}

		return userGroup;
	}

}
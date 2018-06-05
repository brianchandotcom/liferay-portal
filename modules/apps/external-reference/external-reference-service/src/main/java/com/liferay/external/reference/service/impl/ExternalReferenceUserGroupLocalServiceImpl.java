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

import com.liferay.external.reference.service.base.ExternalReferenceUserGroupLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Dylan Rebelak
 */
public class ExternalReferenceUserGroupLocalServiceImpl
	extends ExternalReferenceUserGroupLocalServiceBaseImpl {

	/**
	 * Add or update an user group.
	 *
	 * @param  userId the primary key of the user
	 * @param  companyId the primary key of the user group's company
	 * @param  name the user group's name
	 * @param  description the user group's description
	 * @param  externalReferenceCode the user group's external reference code
	 * @param  serviceContext the service context to be applied (optionally
	 *         <code>null</code>). Can set expando bridge attributes for the
	 *         user group.
	 * @review
	 * @return the user group
	 */
	@Override
	public UserGroup upsertUserGroup(
			long userId, long companyId, String name, String description,
			String externalReferenceCode, ServiceContext serviceContext)
		throws PortalException {

		UserGroup userGroup =
			userGroupLocalService.fetchUserGroupByReferenceCode(
				companyId, externalReferenceCode);

		if (Validator.isNull(userGroup)) {
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
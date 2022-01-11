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

package com.liferay.headless.admin.user.internal.dto.v1_0.converter;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Erick Monteiro
 */
@Component(
	property = "dto.class.name=com.liferay.portal.kernel.model.UserGroup",
	service = {DTOConverter.class, UserGroupResourceDTOConverter.class}
)
public class UserGroupResourceDTOConverter
	implements DTOConverter
		<UserGroup, com.liferay.headless.admin.user.dto.v1_0.UserGroup> {

	@Override
	public String getContentType() {
		return com.liferay.headless.admin.user.dto.v1_0.UserGroup.class.
			getSimpleName();
	}

	@Override
	public com.liferay.headless.admin.user.dto.v1_0.UserGroup toDTO(
			DTOConverterContext dtoConverterContext, UserGroup userGroup)
		throws PortalException {

		if (userGroup == null) {
			return null;
		}

		return new com.liferay.headless.admin.user.dto.v1_0.UserGroup() {
			{
				actions = dtoConverterContext.getActions();
				description = userGroup.getDescription();
				externalReferenceCode = userGroup.getExternalReferenceCode();
				id = userGroup.getUserGroupId();
				name = userGroup.getName();
				usersCount = _userLocalService.getUserGroupUsersCount(
					userGroup.getUserGroupId(),
					WorkflowConstants.STATUS_APPROVED);
			}
		};
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
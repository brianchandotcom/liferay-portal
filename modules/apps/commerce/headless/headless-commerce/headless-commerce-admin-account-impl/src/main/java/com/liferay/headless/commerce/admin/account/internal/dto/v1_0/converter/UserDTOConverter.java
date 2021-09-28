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

package com.liferay.headless.commerce.admin.account.internal.dto.v1_0.converter;

import com.liferay.headless.commerce.admin.account.dto.v1_0.User;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.UserService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	enabled = false,
	property = "dto.class.name=com.liferay.portal.kernel.model.User",
	service = {DTOConverter.class, UserDTOConverter.class}
)
public class UserDTOConverter
	implements DTOConverter<com.liferay.portal.kernel.model.User, User> {

	@Override
	public String getContentType() {
		return User.class.getSimpleName();
	}

	@Override
	public User toDTO(DTOConverterContext dtoConverterContext)
		throws Exception {

		com.liferay.portal.kernel.model.User user = _userService.getUserById(
			(Long)dtoConverterContext.getId());

		return new User() {
			{
				email = user.getEmailAddress();
				externalReferenceCode = user.getExternalReferenceCode();
				firstName = user.getFirstName();
				id = user.getUserId();
				jobTitle = user.getJobTitle();
				lastName = user.getLastName();
				male = user.isMale();
				middleName = user.getMiddleName();
				roles = _getRoles(user);
			}
		};
	}

	private String[] _getRoles(com.liferay.portal.kernel.model.User user) {
		String[] roleNames = new String[0];

		List<Role> roles = user.getRoles();

		for (Role role : roles) {
			roleNames = ArrayUtil.append(roleNames, role.getName());
		}

		return roleNames;
	}

	@Reference
	private UserService _userService;

}
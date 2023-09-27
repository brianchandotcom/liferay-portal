/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	property = "dto.class.name=com.liferay.portal.kernel.model.User",
	service = DTOConverter.class
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
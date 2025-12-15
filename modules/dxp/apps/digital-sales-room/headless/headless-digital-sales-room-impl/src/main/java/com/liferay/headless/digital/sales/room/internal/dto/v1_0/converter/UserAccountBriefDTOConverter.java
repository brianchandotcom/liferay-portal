/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter;

import com.liferay.headless.digital.sales.room.dto.v1_0.UserAccountBrief;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterContext;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Stefano Motta
 */
@Component(
	property = {
		"application.name=Liferay.Headless.Digital.Sales.Room",
		"dto.class.name=com.liferay.headless.digital.sales.room.dto.v1_0.UserAccountBrief",
		"version=v1.0"
	},
	service = DTOConverter.class
)
public class UserAccountBriefDTOConverter
	implements DTOConverter<User, UserAccountBrief> {

	@Override
	public String getContentType() {
		return User.class.getSimpleName();
	}

	@Override
	public UserAccountBrief toDTO(
			DTOConverterContext dtoConverterContext, User user)
		throws Exception {

		if (!(dtoConverterContext instanceof
				UserAccountBriefDTOConverterContext) ||
			(user == null)) {

			return null;
		}

		return new UserAccountBrief() {
			{
				setAlternateName(user::getScreenName);
				setEmailAddress(user::getEmailAddress);
				setExternalReferenceCode(user::getExternalReferenceCode);
				setId(user::getUserId);
				setName(user::getFullName);
				setRoleKey(
					() -> {
						UserAccountBriefDTOConverterContext
							userAccountBriefDTOConverterContext =
								(UserAccountBriefDTOConverterContext)
									dtoConverterContext;

						List<UserGroupRole> userGroupRoles =
							_userGroupRoleLocalService.getUserGroupRoles(
								user.getUserId(),
								userAccountBriefDTOConverterContext.
									getGroupId());

						if (userGroupRoles.isEmpty()) {
							return null;
						}

						UserGroupRole userGroupRole = userGroupRoles.get(0);

						Role role = userGroupRole.getRole();

						return role.getName();
					});
			}
		};
	}

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}
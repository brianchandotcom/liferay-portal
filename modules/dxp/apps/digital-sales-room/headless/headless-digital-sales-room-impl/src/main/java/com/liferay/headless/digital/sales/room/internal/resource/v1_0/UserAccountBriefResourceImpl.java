/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.digital.sales.room.internal.resource.v1_0;

import com.liferay.headless.digital.sales.room.dto.v1_0.UserAccountBrief;
import com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.UserAccountBriefDTOConverterContext;
import com.liferay.headless.digital.sales.room.resource.v1_0.UserAccountBriefResource;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.RoleAssignmentException;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.liveusers.LiveUsers;
import com.liferay.portal.vulcan.dto.converter.DTOConverter;
import com.liferay.portal.vulcan.dto.converter.DTOConverterRegistry;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import jakarta.validation.ValidationException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Stefano Motta
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/user-account-brief.properties",
	scope = ServiceScope.PROTOTYPE, service = UserAccountBriefResource.class
)
public class UserAccountBriefResourceImpl
	extends BaseUserAccountBriefResourceImpl {

	@Override
	public void deleteDigitalSalesRoomUserAccountBrief(
			Long digitalSalesRoomId, Long userAccountBriefId)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		LiveUsers.leaveGroup(
			contextCompany.getCompanyId(), group.getGroupId(),
			userAccountBriefId);

		_userGroupRoleLocalService.deleteUserGroupRoles(
			new long[] {userAccountBriefId}, group.getGroupId());

		_userLocalService.deleteGroupUser(
			group.getGroupId(), userAccountBriefId);
	}

	@Override
	public Page<UserAccountBrief> getDigitalSalesRoomUserAccountBriefsPage(
			Long digitalSalesRoomId, Pagination pagination)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		return Page.of(
			null,
			transform(
				_userLocalService.getGroupUsers(
					group.getGroupId(), pagination.getStartPosition(),
					pagination.getEndPosition()),
				user -> _toUserAccountBrief(group.getGroupId(), user)),
			pagination,
			_userLocalService.getGroupUsersCount(group.getGroupId()));
	}

	@Override
	public UserAccountBrief postDigitalSalesRoomUserAccountBrief(
			Long digitalSalesRoomId, UserAccountBrief userAccountBrief)
		throws Exception {

		if (!FeatureFlagManagerUtil.isEnabled(
				contextCompany.getCompanyId(), "LPD-66359")) {

			throw new UnsupportedOperationException();
		}

		if (Validator.isNull(userAccountBrief.getEmailAddress())) {
			throw new ValidationException("Email Address is null");
		}

		Group group = _groupService.getGroup(digitalSalesRoomId);

		User user = _userLocalService.getUserByEmailAddress(
			group.getCompanyId(), userAccountBrief.getEmailAddress());

		_userLocalService.addGroupUser(group.getGroupId(), user.getUserId());

		if (Validator.isNotNull(userAccountBrief.getRoleKey())) {
			Role role = _roleLocalService.fetchRole(
				group.getCompanyId(), userAccountBrief.getRoleKey());

			if (role.getType() != RoleConstants.TYPE_SITE) {
				throw new RoleAssignmentException(
					StringBundler.concat(
						"Role type ",
						RoleConstants.getTypeLabel(role.getType()),
						" is not role type ",
						RoleConstants.getTypeLabel(RoleConstants.TYPE_SITE)));
			}

			_userGroupRoleLocalService.addUserGroupRoles(
				user.getUserId(), group.getGroupId(),
				new long[] {role.getRoleId()});
		}

		LiveUsers.joinGroup(
			group.getCompanyId(), group.getGroupId(), user.getUserId());

		return _toUserAccountBrief(group.getGroupId(), user);
	}

	private UserAccountBrief _toUserAccountBrief(long groupId, User user)
		throws Exception {

		return _userAccountBriefDTOConverter.toDTO(
			new UserAccountBriefDTOConverterContext(
				true, null, _dtoConverterRegistry, groupId, user.getUserId(),
				contextAcceptLanguage.getPreferredLocale(), contextUriInfo,
				contextUser),
			user);
	}

	@Reference
	private DTOConverterRegistry _dtoConverterRegistry;

	@Reference
	private GroupService _groupService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference(
		target = "(component.name=com.liferay.headless.digital.sales.room.internal.dto.v1_0.converter.UserAccountBriefDTOConverter)"
	)
	private DTOConverter<User, UserAccountBrief> _userAccountBriefDTOConverter;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
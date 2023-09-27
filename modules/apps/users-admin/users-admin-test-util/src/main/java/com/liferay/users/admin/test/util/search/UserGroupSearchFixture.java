/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.test.util.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.users.admin.test.util.search.UserGroupBlueprint.UserGroupBlueprintBuilder;
import com.liferay.users.admin.test.util.search.UserGroupBlueprintImpl.UserGroupBlueprintBuilderImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class UserGroupSearchFixture {

	public static UserGroupBlueprintBuilder getTestUserGroupBlueprintBuilder() {
		long companyId = getTestCompanyId();
		long groupId = getTestGroupId();
		long userId = getTestUserId();

		UserGroupBlueprintBuilder userGroupBlueprintBuilder =
			new UserGroupBlueprintBuilderImpl();

		return userGroupBlueprintBuilder.companyId(
			companyId
		).description(
			RandomTestUtil.randomString(50)
		).userId(
			userId
		).name(
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE)
		).serviceContext(
			new ServiceContext() {
				{
					setAddGroupPermissions(true);
					setAddGuestPermissions(true);
					setCompanyId(companyId);
					setScopeGroupId(groupId);
					setUserId(userId);
				}
			}
		);
	}

	public UserGroupSearchFixture(UserGroupLocalService userGroupLocalService) {
		_userGroupLocalService = userGroupLocalService;
	}

	public UserGroup addUserGroup(
		UserGroupBlueprintBuilder userGroupBlueprintBuilder) {

		UserGroup userGroup = _addUserGroup(userGroupBlueprintBuilder);

		_userGroups.add(userGroup);

		return userGroup;
	}

	public List<UserGroup> getUserGroups() {
		return Collections.unmodifiableList(_userGroups);
	}

	protected static long getTestCompanyId() {
		try {
			return TestPropsValues.getCompanyId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected static long getTestGroupId() {
		try {
			return TestPropsValues.getGroupId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	protected static long getTestUserId() {
		try {
			return TestPropsValues.getUserId();
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private UserGroup _addUserGroup(
		UserGroupBlueprintBuilder userGroupBlueprintBuilder) {

		UserGroupBlueprint userGroupBlueprint =
			userGroupBlueprintBuilder.build();

		try {
			return _userGroupLocalService.addUserGroup(
				userGroupBlueprint.getUserId(),
				userGroupBlueprint.getCompanyId(), userGroupBlueprint.getName(),
				userGroupBlueprint.getDescription(),
				userGroupBlueprint.getServiceContext());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final UserGroupLocalService _userGroupLocalService;
	private final List<UserGroup> _userGroups = new ArrayList<>();

}
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

package com.liferay.user.groups.admin.web.internal.search.test;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupLocalService;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.randomizerbumpers.UniqueStringRandomizerBumper;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
public class UserGroupFixture {

	public UserGroupFixture(
		Group group, UserGroupLocalService userGroupLocalService) {

		_group = group;
		_userGroupLocalService = userGroupLocalService;
	}

	public UserGroup createUserGroup() {
		return createUserGroup(Collections.emptyMap());
	}

	public UserGroup createUserGroup(Map<String, Serializable> expandoValues) {
		return createUserGroup(
			RandomTestUtil.randomString(
				NumericStringRandomizerBumper.INSTANCE,
				UniqueStringRandomizerBumper.INSTANCE),
			RandomTestUtil.randomString(50), expandoValues);
	}

	public UserGroup createUserGroup(String name) {
		return createUserGroup(
			name, RandomTestUtil.randomString(50), Collections.emptyMap());
	}

	public UserGroup createUserGroup(
		String name, String description,
		Map<String, Serializable> expandoValues) {

		ServiceContext serviceContext = _getServiceContext();

		serviceContext.setExpandoBridgeAttributes(expandoValues);

		UserGroup userGroup = addUserGroup(
			serviceContext.getUserId(), serviceContext.getCompanyId(), name,
			description, serviceContext);

		_userGroups.add(userGroup);

		return userGroup;
	}

	public List<UserGroup> getUserGroups() {
		return _userGroups;
	}

	public void updateDisplaySettings(Locale locale) throws Exception {
		Group group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(), null, locale);

		_group.setModelAttributes(group.getModelAttributes());
	}

	protected UserGroup addUserGroup(
		long userId, long companyId, String name, String description,
		ServiceContext serviceContext) {

		try {
			return _userGroupLocalService.addUserGroup(
				userId, companyId, name, description, serviceContext);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private ServiceContext _getServiceContext() {
		try {
			return ServiceContextTestUtil.getServiceContext(
				_group.getGroupId());
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final Group _group;
	private final UserGroupLocalService _userGroupLocalService;
	private final List<UserGroup> _userGroups = new ArrayList<>();

}
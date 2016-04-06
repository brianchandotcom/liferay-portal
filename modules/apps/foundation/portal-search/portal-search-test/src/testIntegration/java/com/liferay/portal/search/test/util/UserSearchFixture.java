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

package com.liferay.portal.kernel.search;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class UserSearchFixture {

	public Group addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	public void addUser(Group group, String... assetTagNames) throws Exception {
		User user = UserTestUtil.addUser(group.getGroupId());

		_users.add(user);

		ServiceContext serviceContext = getServiceContext(group);

		serviceContext.setAssetTagNames(assetTagNames);

		UserTestUtil.updateUser(user, serviceContext);

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(
			user.getModelClassName(), user.getPrimaryKey());

		_assetTags.addAll(assetTags);
	}

	public List<AssetTag> getAssetTags() {
		return _assetTags;
	}

	public List<Group> getGroups() {
		return _groups;
	}

	public List<User> getUsers() {
		return _users;
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	protected static ServiceContext getServiceContext(Group group)
		throws Exception {

		return ServiceContextTestUtil.getServiceContext(
			group.getGroupId(), TestPropsValues.getUserId());
	}

	private final List<AssetTag> _assetTags = new ArrayList<>();
	private final List<Group> _groups = new ArrayList<>();
	private final List<User> _users = new ArrayList<>();

}
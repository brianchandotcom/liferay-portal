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

package com.liferay.portal.search.test.internal.util;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class UserSearchFixture {

	public Group addGroup() throws Exception {
		Group group = GroupTestUtil.addGroup();

		_groups.add(group);

		return group;
	}

	public User addUser(Group group, String... assetTagNames) throws Exception {
		User user1 = UserTestUtil.addUser(group.getGroupId());

		_users.add(user1);

		ServiceContext serviceContext = getServiceContext(group);

		serviceContext.setAssetTagNames(assetTagNames);

		User user2 = UserTestUtil.updateUser(user1, serviceContext);

		List<AssetTag> assetTags = AssetTagLocalServiceUtil.getTags(
			user2.getModelClassName(), user2.getPrimaryKey());

		_assetTags.addAll(assetTags);

		return user2;
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

	public void setUp() throws Exception {
		_permissionChecker = PermissionThreadLocal.getPermissionChecker();

		PermissionThreadLocal.setPermissionChecker(
			new DummyPermissionChecker() {

				@Override
				public boolean hasPermission(
					Group group, String name, long primKey, String actionId) {

					return true;
				}

				@Override
				public boolean hasPermission(
					long groupId, String name, long primKey, String actionId) {

					return true;
				}

			});

		_principal = PrincipalThreadLocal.getName();

		PrincipalThreadLocal.setName(TestPropsValues.getUserId());
	}

	public void tearDown() {
		PermissionThreadLocal.setPermissionChecker(_permissionChecker);

		PrincipalThreadLocal.setName(_principal);
	}

	public Map<String, String> toMap(List<Document> list) {
		Map<String, String> map = new HashMap<>(list.size());

		for (Document document : list) {
			String[] values = document.getValues(Field.ASSET_TAG_NAMES);

			Arrays.sort(values);

			map.put(document.get("screenName"), StringUtil.merge(values));
		}

		return map;
	}

	public Map<String, String> toMap(User user, String... tags) {
		return Collections.singletonMap(
			user.getScreenName(), toStringTags(tags));
	}

	public String toStringTags(String[] tags) {
		List<String> list = new ArrayList<>(tags.length);

		for (String tag : tags) {
			list.add(StringUtil.toLowerCase(tag));
		}

		Collections.sort(list);

		return StringUtil.merge(list);
	}

	protected static ServiceContext getServiceContext(Group group)
		throws Exception {

		return ServiceContextTestUtil.getServiceContext(
			group.getGroupId(), TestPropsValues.getUserId());
	}

	private final List<AssetTag> _assetTags = new ArrayList<>();
	private final List<Group> _groups = new ArrayList<>();
	private PermissionChecker _permissionChecker;
	private String _principal;
	private final List<User> _users = new ArrayList<>();

}
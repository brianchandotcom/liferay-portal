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

package com.liferay.contacts.service.impl;

import com.liferay.contacts.model.Entry;
import com.liferay.contacts.service.base.EntryServiceBaseImpl;
import com.liferay.contacts.util.ContactsUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Bruno Farache
 */
public class EntryServiceImpl extends EntryServiceBaseImpl {

	@Override
	public JSONArray searchUsersAndContacts(
			long companyId, String keywords, int start, int end)
		throws PortalException {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		long userId = getUserId();

		Set<BaseModel<?>> contacts = new HashSet<>();

		JSONObject jsonObject = null;

		PermissionChecker permissionChecker = getPermissionChecker();

		if (permissionChecker.isCompanyAdmin()) {
			contacts.addAll(
				entryLocalService.searchUsersAndContacts(
					companyId, userId, keywords, start, end));
		}
		else {
			User user = userLocalService.getUser(userId);

			contacts.add(user);

			List<Group> groups = groupLocalService.getUserGroups(userId, true);

			for (Group group : groups) {
				List<User> groupUsers = userLocalService.getGroupUsers(
					group.getGroupId());

				contacts.addAll(groupUsers);
			}

			List<UserGroup> userGroups =
				userGroupLocalService.getUserUserGroups(userId);

			for (UserGroup userGroup : userGroups) {
				List<User> userGroupUsers = userLocalService.getUserGroupUsers(
					userGroup.getUserGroupId());

				contacts.addAll(userGroupUsers);
			}

			contacts.addAll(
				entryLocalService.search(userId, keywords, start, end));
		}

		for (BaseModel<?> contact : contacts) {
			if (contact instanceof User) {
				jsonObject = ContactsUtil.getUserJSONObject(
					userId, (User)contact);
			}
			else {
				jsonObject = ContactsUtil.getEntryJSONObject((Entry)contact);
			}

			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

}
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
import com.liferay.portal.kernel.model.User;

import java.util.List;

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

		List<BaseModel<?>> contacts = entryLocalService.searchUsersAndContacts(
			companyId, userId, keywords, start, end);

		for (BaseModel<?> contact : contacts) {
			JSONObject jsonObject = null;

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
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

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.service.CTCollectionTemplateLocalServiceUtil;
import com.liferay.json.storage.service.JSONStorageEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class CTCollectionTemplateImpl extends CTCollectionTemplateBaseImpl {

	public JSONObject getJSONObject() {
		return JSONStorageEntryLocalServiceUtil.getJSONObject(
			ClassNameLocalServiceUtil.getClassNameId(getModelClassName()),
			getCtCollectionTemplateId());
	}

	public String getParsedPublicationDescription() {
		return CTCollectionTemplateLocalServiceUtil.parseTokens(
			getCtCollectionTemplateId(), getPublicationDescription());
	}

	public String getParsedPublicationName() {
		return CTCollectionTemplateLocalServiceUtil.parseTokens(
			getCtCollectionTemplateId(), getPublicationName());
	}

	public String getPublicationDescription() {
		JSONObject jsonObject = getJSONObject();

		return String.valueOf(jsonObject.get("description"));
	}

	public String getPublicationName() {
		JSONObject jsonObject = getJSONObject();

		return String.valueOf(jsonObject.get("name"));
	}

	public String getUserName() {
		User user = UserLocalServiceUtil.fetchUser(getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getFullName();
	}

}
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

package com.liferay.user.groups.admin.item.selector.web.internal;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class UserGroupItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public UserGroupItemDescriptor(UserGroup userGroup) {
		_userGroup = userGroup;
	}

	@Override
	public String getIcon() {
		return "users";
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"name", HtmlUtil.escape(_userGroup.getName())
		).put(
			"userGroupId", _userGroup.getUserGroupId()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		int usersCount = UserLocalServiceUtil.searchCount(
			_userGroup.getCompanyId(), StringPool.BLANK,
			WorkflowConstants.STATUS_ANY,
			LinkedHashMapBuilder.<String, Object>put(
				"usersUserGroups", _userGroup.getUserGroupId()
			).build());

		return LanguageUtil.format(locale, "x-users", usersCount);
	}

	@Override
	public String getTitle(Locale locale) {
		return HtmlUtil.escape(_userGroup.getName());
	}

	private final UserGroup _userGroup;

}
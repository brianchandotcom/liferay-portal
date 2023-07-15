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

import com.liferay.item.selector.TableItemView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class UserGroupTableItemView implements TableItemView {

	public UserGroupTableItemView(UserGroup userGroup) {
		_userGroup = userGroup;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray("name", "description", "users");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		nameTextSearchEntry.setCssClass(
			"entry entry-selector table-cell-expand");
		nameTextSearchEntry.setName(HtmlUtil.escape(_userGroup.getName()));

		searchEntries.add(nameTextSearchEntry);

		TextSearchEntry descriptionTextSearchEntry = new TextSearchEntry();

		descriptionTextSearchEntry.setCssClass("table-cell-expand");
		descriptionTextSearchEntry.setName(
			HtmlUtil.escape(_userGroup.getDescription()));

		searchEntries.add(descriptionTextSearchEntry);

		TextSearchEntry usersTextSearchEntry = new TextSearchEntry();

		usersTextSearchEntry.setName(
			String.valueOf(
				UserLocalServiceUtil.searchCount(
					_userGroup.getCompanyId(), StringPool.BLANK,
					WorkflowConstants.STATUS_ANY,
					LinkedHashMapBuilder.<String, Object>put(
						"usersUserGroups", _userGroup.getUserGroupId()
					).build())));

		searchEntries.add(usersTextSearchEntry);

		return searchEntries;
	}

	private final UserGroup _userGroup;

}
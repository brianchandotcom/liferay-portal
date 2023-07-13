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

package com.liferay.users.admin.item.selector.web.internal;

import com.liferay.item.selector.TableItemView;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * @author Eudaldo Alonso
 */
public class UserTableItemView implements TableItemView {

	public UserTableItemView(User user) {
		_user = user;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray("name", "screen-name");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		nameTextSearchEntry.setCssClass(
			"table-cell-expand-smaller table-cell-minw-80");
		nameTextSearchEntry.setName(HtmlUtil.escape(_user.getFullName()));

		searchEntries.add(nameTextSearchEntry);

		TextSearchEntry screenNameTextSearchEntry = new TextSearchEntry();

		screenNameTextSearchEntry.setCssClass(
			"table-cell-expand-smaller table-cell-minw-150");
		screenNameTextSearchEntry.setName(
			HtmlUtil.escape(_user.getScreenName()));

		searchEntries.add(screenNameTextSearchEntry);

		return searchEntries;
	}

	private final User _user;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		_multipleSelection = false;
	}

	public UserTableItemView(User user, boolean multipleSelection) {
		_user = user;
		_multipleSelection = multipleSelection;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray("name", "screen-name");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		String cssClass = "table-cell-expand-smaller table-cell-minw-80";

		if (!_multipleSelection) {
			cssClass = cssClass.concat(" entry entry-selector");
		}

		nameTextSearchEntry.setCssClass(cssClass);
		nameTextSearchEntry.setName(HtmlUtil.escape(_user.getFullName()));

		searchEntries.add(nameTextSearchEntry);

		TextSearchEntry screenNameTextSearchEntry = new TextSearchEntry();

		cssClass = "table-cell-expand-smaller table-cell-minw-150";

		if (!_multipleSelection) {
			cssClass = cssClass.concat(" entry entry-selector");
		}

		screenNameTextSearchEntry.setCssClass(cssClass);
		screenNameTextSearchEntry.setName(
			HtmlUtil.escape(_user.getScreenName()));

		searchEntries.add(screenNameTextSearchEntry);

		return searchEntries;
	}

	private final boolean _multipleSelection;
	private final User _user;

}
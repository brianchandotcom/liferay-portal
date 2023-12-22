/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.item.selector.web.internal;

import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.item.selector.TableItemView;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author Jürgen Kappler
 */
public class DDMStructureItemTableItemView implements TableItemView {

	public DDMStructureItemTableItemView(
		DDMStructure ddmStructure, GroupLocalService groupLocalService,
		ThemeDisplay themeDisplay) {

		_ddmStructure = ddmStructure;
		_groupLocalService = groupLocalService;
		_themeDisplay = themeDisplay;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray("title", "user", "scope", "modified-date");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		TextSearchEntry titleTextSearchEntry = new TextSearchEntry();

		titleTextSearchEntry.setCssClass(
			"entry entry-selector table-cell-expand table-cell-minw-200");
		titleTextSearchEntry.setName(
			HtmlUtil.escape(_ddmStructure.getName(locale)));

		searchEntries.add(titleTextSearchEntry);

		TextSearchEntry userNameSearchEntry = new TextSearchEntry();

		userNameSearchEntry.setCssClass(
			"table-cell-expand-smaller table-cell-minw-150");
		userNameSearchEntry.setName(
			HtmlUtil.escape(_ddmStructure.getUserName()));

		searchEntries.add(userNameSearchEntry);

		TextSearchEntry scopeSearchEntry = new TextSearchEntry();

		scopeSearchEntry.setCssClass(
			"table-cell-expand-smallest table-cell-minw-150");

		Group group = _groupLocalService.fetchGroup(_ddmStructure.getGroupId());

		if (group != null) {
			scopeSearchEntry.setName(
				LanguageUtil.get(
					_themeDisplay.getLocale(),
					group.getScopeLabel(_themeDisplay)));
		}
		else {
			scopeSearchEntry.setName(StringPool.BLANK);
		}

		searchEntries.add(scopeSearchEntry);

		TextSearchEntry modifiedDateSearchEntry = new TextSearchEntry();

		modifiedDateSearchEntry.setCssClass(
			"table-cell-expand-smallest table-cell-ws-nowrap");

		Date modifiedDate = _ddmStructure.getModifiedDate();

		if (Objects.nonNull(modifiedDate)) {
			modifiedDateSearchEntry.setName(
				LanguageUtil.format(
					locale, "x-ago",
					LanguageUtil.getTimeDescription(
						locale,
						System.currentTimeMillis() - modifiedDate.getTime(),
						true)));
		}
		else {
			modifiedDateSearchEntry.setName(StringPool.BLANK);
		}

		searchEntries.add(modifiedDateSearchEntry);

		return searchEntries;
	}

	private final DDMStructure _ddmStructure;
	private final GroupLocalService _groupLocalService;
	private final ThemeDisplay _themeDisplay;

}
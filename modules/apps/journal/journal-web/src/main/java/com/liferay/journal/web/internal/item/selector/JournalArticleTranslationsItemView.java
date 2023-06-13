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

package com.liferay.journal.web.internal.item.selector;

import com.liferay.item.selector.TableItemView;
import com.liferay.journal.web.internal.util.JournalArticleTranslation;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchEntry;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.taglib.search.IconSearchEntry;
import com.liferay.taglib.search.TextSearchEntry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Barbara Cabrera
 */
public class JournalArticleTranslationsItemView implements TableItemView {

	public JournalArticleTranslationsItemView(
		JournalArticleTranslation articleTranslation) {

		_articleTranslation = articleTranslation;
	}

	@Override
	public List<String> getHeaderNames() {
		return ListUtil.fromArray(StringPool.BLANK, "language", "default");
	}

	@Override
	public List<SearchEntry> getSearchEntries(Locale locale) {
		List<SearchEntry> searchEntries = new ArrayList<>();

		IconSearchEntry languageIconSearchEntry = new IconSearchEntry();

		languageIconSearchEntry.setIcon(_articleTranslation.getLanguageTag());

		searchEntries.add(languageIconSearchEntry);

		TextSearchEntry nameTextSearchEntry = new TextSearchEntry();

		nameTextSearchEntry.setCssClass(
			"table-cell-expand table-cell-minw-200");
		nameTextSearchEntry.setName(
			HtmlUtil.escape(
				LocaleUtil.getLongDisplayName(
					_articleTranslation.getLocale(), Collections.emptySet())));

		searchEntries.add(nameTextSearchEntry);

		IconSearchEntry defaultLanguageIconSearchEntry = new IconSearchEntry();

		if (_articleTranslation.isDefault()) {
			defaultLanguageIconSearchEntry.setIcon("check-circle");
		}
		else {
			defaultLanguageIconSearchEntry.setIcon(StringPool.BLANK);
		}

		searchEntries.add(defaultLanguageIconSearchEntry);

		return searchEntries;
	}

	private final JournalArticleTranslation _articleTranslation;

}
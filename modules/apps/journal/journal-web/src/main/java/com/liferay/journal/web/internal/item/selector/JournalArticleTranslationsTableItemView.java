/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
public class JournalArticleTranslationsTableItemView implements TableItemView {

	public JournalArticleTranslationsTableItemView(
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

		if (_articleTranslation.isDefault()) {
			IconSearchEntry defaultLanguageIconSearchEntry =
				new IconSearchEntry();

			defaultLanguageIconSearchEntry.setIcon("check-circle");

			searchEntries.add(defaultLanguageIconSearchEntry);
		}
		else {
			TextSearchEntry emptyTextSearchEntry = new TextSearchEntry();

			emptyTextSearchEntry.setName(StringPool.BLANK);

			searchEntries.add(emptyTextSearchEntry);
		}

		return searchEntries;
	}

	private final JournalArticleTranslation _articleTranslation;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.test.util.search;

import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleContent extends LocalizedValuesMap {

	public JournalArticleContent() {
	}

	public JournalArticleContent(JournalArticleContent journalArticleContent) {
		_defaultLocale = journalArticleContent._defaultLocale;
		_name = journalArticleContent._name;

		Map<Locale, String> values = journalArticleContent.getValues();

		values.forEach((locale, value) -> put(locale, value));
	}

	public String getContentString() {
		List<Map<Locale, String>> list = getContents();

		return DDMStructureTestUtil.getSampleStructuredContent(
			_name, list, LocaleUtil.toLanguageId(_defaultLocale));
	}

	protected List<Map<Locale, String>> getContents() {
		Map<Locale, String> values = getValues();

		if (values.isEmpty()) {
			return Collections.emptyList();
		}

		return Collections.singletonList(values);
	}

	protected void setDefaultLocale(Locale defaultLocale) {
		_defaultLocale = defaultLocale;
	}

	protected void setName(String name) {
		_name = name;
	}

	private Locale _defaultLocale;
	private String _name;

}
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
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.summary;

import com.liferay.portal.search.summary.Summary;

import java.util.Locale;

/**
 * @author André de Oliveira
 */
public class SummaryImpl implements Summary {

	public SummaryImpl(String title, String content, Locale locale) {
		_title = title;
		_content = content;
		_locale = locale;
	}

	@Override
	public String getContent() {
		return _content;
	}

	@Override
	public Locale getLocale() {
		return _locale;
	}

	@Override
	public String getTitle() {
		return _title;
	}

	private final String _content;
	private final Locale _locale;
	private final String _title;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.test.util.search;

import com.liferay.portal.kernel.settings.LocalizedValuesMap;

import java.util.Locale;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class JournalArticleTitle extends LocalizedValuesMap {

	public JournalArticleTitle() {
	}

	public JournalArticleTitle(JournalArticleTitle journalArticleTitle) {
		Map<Locale, String> values = journalArticleTitle.getValues();

		values.forEach((locale, value) -> put(locale, value));
	}

}
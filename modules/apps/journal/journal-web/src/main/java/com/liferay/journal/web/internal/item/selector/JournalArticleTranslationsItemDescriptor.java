/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.item.selector;

import com.liferay.item.selector.ItemSelectorViewDescriptor;
import com.liferay.journal.web.internal.util.JournalArticleTranslation;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONUtil;

import java.util.Locale;

/**
 * @author Barbara Cabrera
 */
public class JournalArticleTranslationsItemDescriptor
	implements ItemSelectorViewDescriptor.ItemDescriptor {

	public JournalArticleTranslationsItemDescriptor(
		JournalArticleTranslation articleTranslation) {

		_articleTranslation = articleTranslation;
	}

	@Override
	public String getIcon() {
		return _articleTranslation.getLanguageTag();
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"languageId", _articleTranslation.getLanguageId()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _articleTranslation.getLanguageId();
	}

	private final JournalArticleTranslation _articleTranslation;

}
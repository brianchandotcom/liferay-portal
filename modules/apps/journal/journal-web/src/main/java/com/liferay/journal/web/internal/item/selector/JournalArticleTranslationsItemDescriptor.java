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
		JournalArticleTranslation journalArticleTranslation) {

		_journalArticleTranslation = journalArticleTranslation;
	}

	@Override
	public String getIcon() {
		return _journalArticleTranslation.getLanguageTag();
	}

	@Override
	public String getImageURL() {
		return null;
	}

	@Override
	public String getPayload() {
		return JSONUtil.put(
			"journalArticleTranslationId",
			_journalArticleTranslation.getLanguageId()
		).toString();
	}

	@Override
	public String getSubtitle(Locale locale) {
		return StringPool.BLANK;
	}

	@Override
	public String getTitle(Locale locale) {
		return _journalArticleTranslation.getLanguageId();
	}

	private final JournalArticleTranslation _journalArticleTranslation;

}
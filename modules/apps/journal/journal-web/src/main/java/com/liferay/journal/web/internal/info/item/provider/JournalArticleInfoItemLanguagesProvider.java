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

package com.liferay.journal.web.internal.info.item.provider;

import com.liferay.journal.model.JournalArticle;
import com.liferay.translation.info.item.provider.InfoItemLanguagesProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alicia García
 */
@Component(service = InfoItemLanguagesProvider.class)
public class JournalArticleInfoItemLanguagesProvider
	implements InfoItemLanguagesProvider<JournalArticle> {

	@Override
	public String[] getAvailableLanguageIds(JournalArticle journalArticle) {
		return journalArticle.getAvailableLanguageIds();
	}

	@Override
	public String getDefaultLanguageId(JournalArticle journalArticle) {
		return journalArticle.getDefaultLanguageId();
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
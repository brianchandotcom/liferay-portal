/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.display.context;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;

import java.util.List;

/**
 * @author Clara Izquierdo
 */
public class JournalVersionTabDisplayContext {

	public JournalVersionTabDisplayContext(JournalArticle article) {
		_article = article;
	}

	public List<JournalArticle> getJournalArticlesLatestVersions() {
		return JournalArticleServiceUtil.getArticlesByArticleId(
			_article.getGroupId(), _article.getArticleId(), 0,
			10, new ArticleVersionComparator());
	}

	public int getJournalArticlesVersionsCount() {
		return JournalArticleServiceUtil.getArticlesCountByArticleId(
			_article.getGroupId(), _article.getArticleId());
	}

	private final JournalArticle _article;

}
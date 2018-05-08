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

package com.liferay.portal.search.test.journal.util;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author André de Oliveira
 */
public class JournalArticleSearchFixture {

	public JournalArticle addArticle(
			JournalArticleBuilder journalArticleBuilder)
		throws Exception {

		JournalArticle journalArticle =
			journalArticleBuilder.addJournalArticle();

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	public List<JournalArticle> getJournalArticles() {
		return _journalArticles;
	}

	public void setUp() {
	}

	public void tearDown() {
	}

	public JournalArticle updateArticle(JournalArticle journalArticle)
		throws Exception {

		journalArticle = JournalArticleLocalServiceUtil.updateArticle(
			journalArticle.getUserId(), journalArticle.getGroupId(),
			journalArticle.getFolderId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), journalArticle.getContent(),
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId()));

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	private final List<JournalArticle> _journalArticles = new ArrayList<>();

}
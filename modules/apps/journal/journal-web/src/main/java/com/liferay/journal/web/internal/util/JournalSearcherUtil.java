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

package com.liferay.journal.web.internal.util;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalFolderLocalService;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.searcher.SearchRequestBuilderFactory;
import com.liferay.portal.search.searcher.SearchResponse;
import com.liferay.portal.search.searcher.Searcher;

import java.util.List;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = {})
public class JournalSearcherUtil {

	public static SearchResponse searchJournalArticleAndFolders(
		Consumer<SearchContext> searchContextConsumer) {

		return _searcher.search(
			_searchRequestBuilderFactory.builder(
			).emptySearchEnabled(
				true
			).modelIndexerClasses(
				JournalArticle.class, JournalFolder.class
			).withSearchContext(
				searchContextConsumer
			).build());
	}

	public static SearchResponse searchJournalArticles(
		Consumer<SearchContext> searchContextConsumer) {

		return _searcher.search(
			_searchRequestBuilderFactory.builder(
			).emptySearchEnabled(
				true
			).modelIndexerClasses(
				JournalArticle.class
			).withSearchContext(
				searchContextConsumer
			).build());
	}

	public static List<Object> transformJournalArticleAndFolders(
		List<Document> documents) {

		return TransformUtil.transform(
			documents,
			document -> {
				String className = document.get(Field.ENTRY_CLASS_NAME);

				if (className.equals(JournalArticle.class.getName())) {
					return _journalArticleLocalService.fetchLatestArticle(
						GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)),
						WorkflowConstants.STATUS_ANY, false);
				}

				return _journalFolderLocalService.fetchJournalFolder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
			});
	}

	public static List<JournalArticle> transformJournalArticles(
		List<Document> documents, boolean showVersions) {

		return TransformUtil.transform(
			documents,
			document -> {
				if (showVersions) {
					return _journalArticleLocalService.fetchArticle(
						GetterUtil.getLong(document.get(Field.GROUP_ID)),
						document.get(Field.ARTICLE_ID),
						GetterUtil.getDouble(document.get(Field.VERSION)));
				}

				return _journalArticleLocalService.fetchLatestArticle(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)),
					WorkflowConstants.STATUS_ANY, false);
			});
	}

	@Reference(unbind = "-")
	protected void setJournalArticleLocalService(
		JournalArticleLocalService journalArticleLocalService) {

		_journalArticleLocalService = journalArticleLocalService;
	}

	@Reference(unbind = "-")
	protected void setJournalFolderLocalService(
		JournalFolderLocalService journalFolderLocalService) {

		_journalFolderLocalService = journalFolderLocalService;
	}

	@Reference(unbind = "-")
	protected void setSearcher(Searcher searcher) {
		_searcher = searcher;
	}

	@Reference(unbind = "-")
	protected void setSearchRequestBuilderFactory(
		SearchRequestBuilderFactory searchRequestBuilderFactory) {

		_searchRequestBuilderFactory = searchRequestBuilderFactory;
	}

	private static JournalArticleLocalService _journalArticleLocalService;
	private static JournalFolderLocalService _journalFolderLocalService;
	private static Searcher _searcher;
	private static SearchRequestBuilderFactory _searchRequestBuilderFactory;

}
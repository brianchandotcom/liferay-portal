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
import com.liferay.osgi.util.service.Snapshot;
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

/**
 * @author Lourdes Fernández Besada
 */
public class JournalSearcherUtil {

	public static SearchResponse searchJournalArticleAndFolders(
		Consumer<SearchContext> searchContextConsumer) {

		Searcher searcher = _searcherSnapshot.get();
		SearchRequestBuilderFactory searchRequestBuilderFactory =
			_searchRequestBuilderFactorySnapshot.get();

		return searcher.search(
			searchRequestBuilderFactory.builder(
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

		Searcher searcher = _searcherSnapshot.get();
		SearchRequestBuilderFactory searchRequestBuilderFactory =
			_searchRequestBuilderFactorySnapshot.get();

		return searcher.search(
			searchRequestBuilderFactory.builder(
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
					JournalArticleLocalService journalArticleLocalService =
						_journalArticleLocalServiceSnapshot.get();

					return journalArticleLocalService.fetchLatestArticle(
						GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)),
						WorkflowConstants.STATUS_ANY, false);
				}

				JournalFolderLocalService journalFolderLocalService =
					_journalFolderLocalServiceSnapshot.get();

				return journalFolderLocalService.fetchJournalFolder(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)));
			});
	}

	public static List<JournalArticle> transformJournalArticles(
		List<Document> documents, boolean showVersions) {

		return TransformUtil.transform(
			documents,
			document -> {
				JournalArticleLocalService journalArticleLocalService =
					_journalArticleLocalServiceSnapshot.get();

				if (showVersions) {
					return journalArticleLocalService.fetchArticle(
						GetterUtil.getLong(document.get(Field.GROUP_ID)),
						document.get(Field.ARTICLE_ID),
						GetterUtil.getDouble(document.get(Field.VERSION)));
				}

				return journalArticleLocalService.fetchLatestArticle(
					GetterUtil.getLong(document.get(Field.ENTRY_CLASS_PK)),
					WorkflowConstants.STATUS_ANY, false);
			});
	}

	private static final Snapshot<JournalArticleLocalService>
		_journalArticleLocalServiceSnapshot = new Snapshot<>(
			JournalSearcherUtil.class, JournalArticleLocalService.class);
	private static final Snapshot<JournalFolderLocalService>
		_journalFolderLocalServiceSnapshot = new Snapshot<>(
			JournalSearcherUtil.class, JournalFolderLocalService.class);
	private static final Snapshot<Searcher> _searcherSnapshot = new Snapshot<>(
		JournalSearcherUtil.class, Searcher.class);
	private static final Snapshot<SearchRequestBuilderFactory>
		_searchRequestBuilderFactorySnapshot = new Snapshot<>(
			JournalSearcherUtil.class, SearchRequestBuilderFactory.class);

}
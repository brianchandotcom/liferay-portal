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

package com.liferay.message.boards.comment.internal.search;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerRegistry;
import com.liferay.portal.kernel.search.RelatedSearchResult;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.SummaryFactory;
import com.liferay.portal.kernel.search.result.SearchResultContributor;
import com.liferay.portal.kernel.search.result.SearchResultTranslator;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.search.internal.result.SearchResultManagerImpl;
import com.liferay.portal.search.internal.result.SearchResultTranslatorImpl;
import com.liferay.portal.search.internal.result.SummaryFactoryImpl;
import com.liferay.portal.search.test.util.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.util.SearchTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author André de Oliveira
 */
public class SearchResultUtilMBMessageTest
	extends BaseSearchResultUtilTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		Mockito.when(
			_commentManager.fetchComment(Mockito.anyLong())
		).thenReturn(
			_comment
		);

		Mockito.when(
			_comment.getCommentId()
		).thenReturn(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		Mockito.when(
			_mbMessage.getMessageId()
		).thenReturn(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		Mockito.when(
			_mbMessageLocalService.getMessage(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_mbMessage
		);

		Mockito.when(
			_mbMessageLocalService.getMessage(SearchTestUtil.ENTRY_CLASS_PK + 1)
		).thenReturn(
			_mbMessage
		);
	}

	@Test
	public void testMBMessage() {
		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createDocument(_CLASS_NAME_MB_MESSAGE));

		Assert.assertEquals(
			_CLASS_NAME_MB_MESSAGE, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ENTRY_CLASS_PK, searchResult.getClassPK());

		List<RelatedSearchResult<Comment>> commentRelatedSearchResults =
			searchResult.getCommentRelatedSearchResults();

		Assert.assertTrue(
			commentRelatedSearchResults.toString(),
			commentRelatedSearchResults.isEmpty());

		Mockito.verifyNoInteractions(_mbMessageLocalService);

		Assert.assertNull(searchResult.getSummary());

		assertEmptyFileEntryRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testMBMessageAttachment() {
		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createAttachmentDocument(_CLASS_NAME_MB_MESSAGE));

		Assert.assertEquals(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME,
			searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK,
			searchResult.getClassPK());

		List<RelatedSearchResult<Comment>> relatedSearchResults =
			searchResult.getCommentRelatedSearchResults();

		RelatedSearchResult<Comment> relatedSearchResult =
			relatedSearchResults.get(0);

		Comment comment = relatedSearchResult.getModel();

		Assert.assertEquals(_mbMessage.getMessageId(), comment.getCommentId());

		Assert.assertEquals(
			relatedSearchResults.toString(), 1, relatedSearchResults.size());
		Assert.assertNull(searchResult.getSummary());

		assertEmptyFileEntryRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testTwoDocumentsWithSameAttachmentOwner() {
		Document document1 = SearchTestUtil.createAttachmentDocument(
			_CLASS_NAME_MB_MESSAGE, SearchTestUtil.ENTRY_CLASS_PK);
		Document document2 = SearchTestUtil.createAttachmentDocument(
			_CLASS_NAME_MB_MESSAGE, SearchTestUtil.ENTRY_CLASS_PK + 1);

		List<SearchResult> searchResults = SearchTestUtil.getSearchResults(
			searchResultTranslator, document1, document2);

		Assert.assertEquals(searchResults.toString(), 1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			searchResult.getClassName(),
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME);
		Assert.assertEquals(
			searchResult.getClassPK(),
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK);
	}

	@Override
	protected SearchResultTranslator createSearchResultTranslator() {
		SearchResultTranslatorImpl searchResultTranslatorImpl =
			new SearchResultTranslatorImpl();

		ReflectionTestUtil.setFieldValue(
			searchResultTranslatorImpl, "_searchResultManager",
			_createSearchResultManager());

		return searchResultTranslatorImpl;
	}

	private SearchResultContributor _createSearchResultContributor() {
		MBMessageCommentSearchResultContributor
			mbMessageCommentSearchResultContributor =
				new MBMessageCommentSearchResultContributor();

		ReflectionTestUtil.setFieldValue(
			mbMessageCommentSearchResultContributor, "_commentManager",
			_commentManager);
		ReflectionTestUtil.setFieldValue(
			mbMessageCommentSearchResultContributor, "_mbMessageLocalService",
			_mbMessageLocalService);

		return mbMessageCommentSearchResultContributor;
	}

	private SearchResultManager _createSearchResultManager() {
		SearchResultManagerImpl searchResultManagerImpl =
			new SearchResultManagerImpl();

		searchResultManagerImpl.addSearchResultContributor(
			_createSearchResultContributor());
		searchResultManagerImpl.setClassNameLocalService(classNameLocalService);
		searchResultManagerImpl.setSummaryFactory(_createSummaryFactory());

		return searchResultManagerImpl;
	}

	private SummaryFactory _createSummaryFactory() {
		SummaryFactoryImpl summaryFactoryImpl = new SummaryFactoryImpl();

		ReflectionTestUtil.setFieldValue(
			summaryFactoryImpl, "_indexerRegistry", _indexerRegistry);

		return summaryFactoryImpl;
	}

	private static final String _CLASS_NAME_MB_MESSAGE =
		MBMessage.class.getName();

	private final Comment _comment = Mockito.mock(Comment.class);
	private final CommentManager _commentManager = Mockito.mock(
		CommentManager.class);
	private final IndexerRegistry _indexerRegistry = Mockito.mock(
		IndexerRegistry.class);
	private final MBMessage _mbMessage = Mockito.mock(MBMessage.class);
	private final MBMessageLocalService _mbMessageLocalService = Mockito.mock(
		MBMessageLocalService.class);

}
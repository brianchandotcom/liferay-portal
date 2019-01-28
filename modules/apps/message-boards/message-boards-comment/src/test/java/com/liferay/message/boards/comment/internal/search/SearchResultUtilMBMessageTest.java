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
import com.liferay.portal.search.internal.result.SearchResultManagerImpl;
import com.liferay.portal.search.internal.result.SearchResultTranslatorImpl;
import com.liferay.portal.search.internal.result.SummaryFactoryImpl;
import com.liferay.portal.search.test.util.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.util.SearchTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@RunWith(PowerMockRunner.class)
public class SearchResultUtilMBMessageTest
	extends BaseSearchResultUtilTestCase {

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
	public void testMBMessage() throws Exception {
		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createDocument(_MB_MESSAGE_CLASS_NAME));

		Assert.assertEquals(
			_MB_MESSAGE_CLASS_NAME, searchResult.getClassName());
		Assert.assertEquals(
			SearchTestUtil.ENTRY_CLASS_PK, searchResult.getClassPK());

		List<RelatedSearchResult<Comment>> commentRelatedSearchResults =
			searchResult.getCommentRelatedSearchResults();

		Assert.assertTrue(
			commentRelatedSearchResults.toString(),
			commentRelatedSearchResults.isEmpty());

		Mockito.verifyZeroInteractions(_mbMessageLocalService);

		Assert.assertNull(searchResult.getSummary());

		assertEmptyFileEntryRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testMBMessageAttachment() throws Exception {
		SearchResult searchResult = assertOneSearchResult(
			SearchTestUtil.createAttachmentDocument(_MB_MESSAGE_CLASS_NAME));

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
			_MB_MESSAGE_CLASS_NAME, SearchTestUtil.ENTRY_CLASS_PK);
		Document document2 = SearchTestUtil.createAttachmentDocument(
			_MB_MESSAGE_CLASS_NAME, SearchTestUtil.ENTRY_CLASS_PK + 1);

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

	protected SearchResultContributor createSearchResultContributor() {
		MBMessageCommentSearchResultContributor
			mbMessageCommentSearchResultContributor =
				new MBMessageCommentSearchResultContributor();

		mbMessageCommentSearchResultContributor.setCommentManager(
			_commentManager);
		mbMessageCommentSearchResultContributor.setMBMessageLocalService(
			_mbMessageLocalService);

		return mbMessageCommentSearchResultContributor;
	}

	protected SearchResultManager createSearchResultManager() {
		SearchResultManagerImpl searchResultManagerImpl =
			new SearchResultManagerImpl();

		searchResultManagerImpl.addSearchResultContributor(
			createSearchResultContributor());
		searchResultManagerImpl.setClassNameLocalService(classNameLocalService);
		searchResultManagerImpl.setSummaryFactory(createSummaryFactory());

		return searchResultManagerImpl;
	}

	@Override
	protected SearchResultTranslator createSearchResultTranslator() {
		SearchResultTranslatorImpl searchResultTranslatorImpl =
			new SearchResultTranslatorImpl();

		searchResultTranslatorImpl.setSearchResultManager(
			createSearchResultManager());

		return searchResultTranslatorImpl;
	}

	protected SummaryFactory createSummaryFactory() {
		SummaryFactoryImpl summaryFactoryImpl = new SummaryFactoryImpl();

		summaryFactoryImpl.setIndexerRegistry(_indexerRegistry);

		return summaryFactoryImpl;
	}

	private static final String _MB_MESSAGE_CLASS_NAME =
		MBMessage.class.getName();

	@Mock
	private Comment _comment;

	@Mock
	private CommentManager _commentManager;

	@Mock
	private IndexerRegistry _indexerRegistry;

	@Mock
	private MBMessage _mbMessage;

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

}
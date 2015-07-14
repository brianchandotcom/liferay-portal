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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.comments.Comment;
import com.liferay.portal.kernel.comments.CommentManagerUtil;
import com.liferay.portal.search.test.BaseSearchResultUtilTestCase;
import com.liferay.portal.search.test.SearchTestUtil;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.registry.collections.ServiceTrackerCollections;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.internal.stubbing.answers.ThrowsExceptionClass;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest( {
	AssetRendererFactoryRegistryUtil.class, CommentManagerUtil.class,
	IndexerRegistryUtil.class, ServiceTrackerCollections.class
})
@RunWith(PowerMockRunner.class)
public class SearchResultUtilMBMessageTest
	extends BaseSearchResultUtilTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		stub(
			method(CommentManagerUtil.class, "fetchComment", long.class)
		).toReturn(
			_comment
		);

		when(
			_comment.getCommentId()
		).thenReturn(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		when(
			_mbMessage.getMessageId()
		).thenReturn(
			SearchTestUtil.ENTRY_CLASS_PK
		);

		when(
			mbMessageLocalService.getMessage(SearchTestUtil.ENTRY_CLASS_PK)
		).thenReturn(
			_mbMessage
		);

		when(
			mbMessageLocalService.getMessage(SearchTestUtil.ENTRY_CLASS_PK + 1)
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

		Assert.assertTrue(commentRelatedSearchResults.isEmpty());

		verifyZeroInteractions(mbMessageLocalService);

		Assert.assertNull(searchResult.getSummary());

		assertEmptyFileEntryRelatedSearchResults(searchResult);
		assertEmptyVersions(searchResult);
	}

	@Test
	public void testMBMessageAttachment() throws Exception {
		mockStatic(
			IndexerRegistryUtil.class,
			new ThrowsExceptionClass(IllegalStateException.class));

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
		Assert.assertEquals(1, relatedSearchResults.size());
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
			document1, document2);

		Assert.assertEquals(1, searchResults.size());

		SearchResult searchResult = searchResults.get(0);

		Assert.assertEquals(
			searchResult.getClassName(),
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_NAME);
		Assert.assertEquals(
			searchResult.getClassPK(),
			SearchTestUtil.ATTACHMENT_OWNER_CLASS_PK);
	}

	private static final String _MB_MESSAGE_CLASS_NAME =
		MBMessage.class.getName();

	@Mock
	private Comment _comment;

	@Mock
	private MBMessage _mbMessage;

}
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

package com.liferay.document.library.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;

import java.io.File;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
public class DLFileEntryAttachmentSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testSearchIncludeAttachment() throws Exception {
		String keyword = RandomTestUtil.randomString();

		WikiNode node = _addPageWithAttachment(keyword);

		FileEntry fileEntry = _addFileEntry(keyword);

		_assertSearchIncludeAttachment(keyword);

		WikiNodeLocalServiceUtil.deleteNode(node);

		DLAppLocalServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	private void _assertSearchIncludeAttachment(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			TestPropsValues.getGroupId());

		searchContext.setKeywords(keywords);

		PermissionThreadLocal.setPermissionChecker(
			_permissionCheckerFactory.create(TestPropsValues.getUser()));

		FacetedSearcher facetedSearcher =
			_facetedSearcherManager.createFacetedSearcher();

		Hits hits1 = facetedSearcher.search(searchContext);

		Assert.assertEquals(hits1.toString(), 1, hits1.getLength());

		searchContext.setIncludeAttachments(true);

		Hits hits2 = facetedSearcher.search(searchContext);

		Assert.assertEquals(hits2.toString(), 2, hits2.getLength());
	}

	@Inject
	private PermissionCheckerFactory _permissionCheckerFactory;

	private WikiNode _addPageWithAttachment(String name) throws Exception {
		long groupId = TestPropsValues.getGroupId();
		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		WikiNode node = WikiNodeLocalServiceUtil.addNode(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(50), serviceContext);

		serviceContext.setCommand(Constants.ADD);

		String pageTitle = RandomTestUtil.randomString();

		WikiPageLocalServiceUtil.addPage(
			userId, node.getNodeId(), pageTitle, RandomTestUtil.randomString(),
			"Summary", false, serviceContext);

		File file = FileUtil.createTempFile(_CONTENT.getBytes());

		String mimeType = MimeTypesUtil.getExtensionContentType("docx");

		WikiPageLocalServiceUtil.addPageAttachment(
			userId, node.getNodeId(), pageTitle, name, file, mimeType);

		return node;
	}

	private FileEntry _addFileEntry(String title) throws PortalException {
		long groupId = TestPropsValues.getGroupId();
		long userId = TestPropsValues.getUserId();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId, userId);

		return DLAppLocalServiceUtil.addFileEntry(
			userId, groupId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK, _CONTENT.getBytes(),
			serviceContext);
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	@Inject
	private static FacetedSearcherManager _facetedSearcherManager;

}
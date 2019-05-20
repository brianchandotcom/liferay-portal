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
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcher;
import com.liferay.portal.kernel.search.facet.faceted.searcher.FacetedSearcherManager;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactory;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

/**
 * @author Istvan Sajtos
 */
@RunWith(Arquillian.class)
@Sync
public class DLFileEntryAttachmentSearchTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testSearchIncludeAttachment() throws Exception {
		String keyword = RandomTestUtil.randomString();

		_addPageWithAttachment(keyword);

		_addFileEntry(keyword);

		_assertSearchIncludeAttachment(keyword);
	}

	private void _assertSearchIncludeAttachment(String keywords)
		throws Exception {

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			_group.getGroupId());

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

	private void _addPageWithAttachment(String name) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setCommand(Constants.ADD);

		WikiNode node = WikiNodeLocalServiceUtil.addNode(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(50), serviceContext);

		String pageTitle = RandomTestUtil.randomString();

		WikiPageLocalServiceUtil.addPage(
			serviceContext.getUserId(), node.getNodeId(), pageTitle,
			RandomTestUtil.randomString(), "Summary", false, serviceContext);

		File file = FileUtil.createTempFile(_CONTENT.getBytes());

		WikiPageLocalServiceUtil.addPageAttachment(
			serviceContext.getUserId(), node.getNodeId(), pageTitle, name, file,
			MimeTypesUtil.getExtensionContentType("docx"));
	}

	private void _addFileEntry(String title) throws PortalException {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLAppLocalServiceUtil.addFileEntry(
			serviceContext.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK, _CONTENT.getBytes(),
			serviceContext);
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	@Inject
	private static FacetedSearcherManager _facetedSearcherManager;

	@DeleteAfterTestRun
	private Group _group;

}
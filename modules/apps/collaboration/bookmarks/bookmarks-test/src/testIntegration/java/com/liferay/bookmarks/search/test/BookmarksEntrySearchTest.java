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

package com.liferay.bookmarks.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.model.BookmarksFolderConstants;
import com.liferay.bookmarks.service.BookmarksEntryServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.bookmarks.util.test.BookmarksTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
@Sync
public class BookmarksEntrySearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Ignore
	@Override
	@Test
	public void testLocalizedSearch() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchAttachments() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchComments() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireAllVersions() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchExpireLatestVersion() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchVersions() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		BookmarksFolder folder = (BookmarksFolder)parentBaseModel;

		long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		return BookmarksTestUtil.addEntry(
			keywords, folderId, true, serviceContext);
	}

	@Override
	protected void deleteBaseModel(long primaryKey) throws Exception {
		BookmarksEntryServiceUtil.deleteEntry(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BookmarksEntry.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return BookmarksTestUtil.addFolder(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		serviceContext.setScopeGroupId(group.getGroupId());

		return BookmarksTestUtil.addFolder(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getParentBaseModelClassName() {
		return BookmarksFolder.class.getName();
	}

	@Override
	protected String getSearchKeywords() {
		return "Entry";
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		BookmarksEntryServiceUtil.moveEntryToTrash(primaryKey);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		BookmarksFolderServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	protected Hits searchGroupEntries(long groupId, long creatorUserId)
		throws Exception {

		return BookmarksEntryServiceUtil.search(
			groupId, creatorUserId, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		BookmarksEntry entry = (BookmarksEntry)baseModel;

		entry.setName(keywords);

		return BookmarksTestUtil.updateEntry(entry, keywords);
	}

}
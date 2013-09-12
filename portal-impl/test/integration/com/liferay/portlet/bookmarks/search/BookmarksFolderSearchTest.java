/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.bookmarks.search;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.model.BookmarksFolderConstants;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class BookmarksFolderSearchTest extends BaseSearchTestCase {

	@Ignore()
	@Override
	@Test
	public void testSearchAttachments() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchByDDMStructureField() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchComments() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchExpireAllVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchExpireLatestVersion() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchMyEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchRecentEntries() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchStatus() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchVersions() throws Exception {
	}

	@Ignore()
	@Override
	@Test
	public void testSearchWithinDDMStructure() throws Exception {
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		BookmarksFolder parentFolder = (BookmarksFolder)parentBaseModel;

		long folderId = BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentFolder != null) {
			folderId = parentFolder.getFolderId();
		}

		return BookmarksTestUtil.addFolder(folderId, keywords, serviceContext);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BookmarksFolder.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return BookmarksTestUtil.addFolder(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			ServiceTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return BookmarksTestUtil.addFolder(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

}
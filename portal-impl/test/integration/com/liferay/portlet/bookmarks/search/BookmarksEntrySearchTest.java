/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.bookmarks.util.BookmarksTestUtil;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class BookmarksEntrySearchTest extends BaseSearchTestCase {

	@Override
	public void testSearchAttachments() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchByDDMStructureField() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchComments() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchExpireAllVersions() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchExpireLatestVersion() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchStatus() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchVersions() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testSearchWithinDDMStructure() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		BookmarksFolder folder = (BookmarksFolder)parentBaseModel;

		return BookmarksTestUtil.addEntry(
			group.getGroupId(), folder.getFolderId(), true);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return BookmarksEntry.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return BookmarksTestUtil.addFolder(
			group.getGroupId(), ServiceTestUtil.randomString());
	}

	@Override
	protected String getSearchKeywords() {
		return "Test";
	}

}
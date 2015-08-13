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

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.test.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class JournalFolderSearchTest extends BaseSearchTestCase {

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

	@Override
	@Test
	public void testSearchBaseModel() throws Exception {
		searchBaseModel(1);
	}

	@Override
	@Test
	public void testSearchBaseModelWithDelete() throws Exception {
		searchBaseModelWithDelete(1);
	}

	@Override
	@Test
	public void testSearchBaseModelWithTrash() throws Exception {
		searchBaseModelWithTrash(1);
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
	public void testSearchMyEntries() throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSearchRecentEntries() throws Exception {
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

		JournalFolder parentFolder = (JournalFolder)parentBaseModel;

		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (parentFolder != null) {
			folderId = parentFolder.getFolderId();
		}

		return JournalTestUtil.addFolder(folderId, keywords, serviceContext);
	}

	@Override
	protected void deleteBaseModel(BaseModel<?> baseModel) throws Exception {
		JournalFolderLocalServiceUtil.deleteFolder((JournalFolder)baseModel);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalFolder.class;
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = (JournalFolder)baseModel;

		folder.setName(keywords);

		return JournalFolderLocalServiceUtil.updateJournalFolder(folder);
	}

}
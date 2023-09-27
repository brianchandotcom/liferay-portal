/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.bookmarks.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.bookmarks.constants.BookmarksFolderConstants;
import com.liferay.bookmarks.model.BookmarksFolder;
import com.liferay.bookmarks.service.BookmarksFolderLocalServiceUtil;
import com.liferay.bookmarks.service.BookmarksFolderServiceUtil;
import com.liferay.bookmarks.test.util.BookmarksTestUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class BookmarksFolderSearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		UserTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();
	}

	@Override
	@Test
	public void testLocalizedSearch() {
	}

	@Override
	@Test
	public void testSearchAttachments() {
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

	@Override
	@Test
	public void testSearchByDDMStructureField() {
	}

	@Override
	@Test
	public void testSearchComments() {
	}

	@Override
	@Test
	public void testSearchCommentsByKeywords() throws Exception {
	}

	@Override
	@Test
	public void testSearchExpireAllVersions() {
	}

	@Override
	@Test
	public void testSearchExpireLatestVersion() {
	}

	@Override
	@Test
	public void testSearchMyEntries() {
	}

	@Override
	@Test
	public void testSearchRecentEntries() {
	}

	@Override
	@Test
	public void testSearchStatus() {
	}

	@Override
	@Test
	public void testSearchVersions() {
	}

	@Override
	@Test
	public void testSearchWithinDDMStructure() {
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
	protected void deleteBaseModel(long primaryKey) throws Exception {
		BookmarksFolderServiceUtil.deleteFolder(primaryKey);
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
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return BookmarksTestUtil.addFolder(
			BookmarksFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected BaseModel<?> updateBaseModel(
		BaseModel<?> baseModel, String keywords,
		ServiceContext serviceContext) {

		BookmarksFolder folder = (BookmarksFolder)baseModel;

		folder.setName(keywords);

		return BookmarksFolderLocalServiceUtil.updateBookmarksFolder(folder);
	}

}
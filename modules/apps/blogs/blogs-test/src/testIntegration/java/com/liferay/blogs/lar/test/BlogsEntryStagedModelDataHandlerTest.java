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

package com.liferay.blogs.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.TransactionalTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.lar.test.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.exportimport.lar.StagedModelDataHandlerUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class BlogsEntryStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), TransactionalTestRule.INSTANCE);

	@Test
	public void testCoverImageIsImported() throws Exception {
		initExport();

		BlogsEntry entry = addBlogsEntryWithCoverImage();

		StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, entry);

		initImport();

		BlogsEntry exportedEntry = (BlogsEntry)readExportedStagedModel(entry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			entry.getUuid(), liveGroup);

		FileEntry coverImageFileEntry = DLAppLocalServiceUtil.getFileEntry(
			importedEntry.getCoverImageFileEntryId());

		Folder coverImageFileEntryFolder = coverImageFileEntry.getFolder();

		Assert.assertEquals(
			liveGroup.getGroupId(), coverImageFileEntry.getGroupId());
		Assert.assertEquals(
			liveGroup.getGroupId(), coverImageFileEntryFolder.getGroupId());
	}

	@Test
	public void testSmallImageIsImported() throws Exception {
		initExport();

		BlogsEntry entry = addBlogsEntryWithSmallImage();

		StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, entry);

		initImport();

		BlogsEntry exportedEntry = (BlogsEntry)readExportedStagedModel(entry);

		Assert.assertNotNull(exportedEntry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			entry.getUuid(), liveGroup);

		FileEntry smallImageFileEntry = DLAppLocalServiceUtil.getFileEntry(
			importedEntry.getSmallImageFileEntryId());

		Folder smallImageFileEntryFolder = smallImageFileEntry.getFolder();

		Assert.assertEquals(
			liveGroup.getGroupId(), smallImageFileEntry.getGroupId());
		Assert.assertEquals(
			liveGroup.getGroupId(), smallImageFileEntryFolder.getGroupId());
	}

	protected BlogsEntry addBlogsEntry(
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws Exception {

		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), new Date(), true, true,
			new String[0], StringPool.BLANK, coverImageImageSelector,
			smallImageImageSelector, serviceContext);
	}

	protected BlogsEntry addBlogsEntryWithCoverImage() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		InputStream inputStream = getInputStream();

		String mimeType = MimeTypesUtil.getContentType(_IMAGE_TITLE);

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(inputStream), _IMAGE_TITLE, mimeType,
			_IMAGE_CROP_REGION);

		return addBlogsEntry(imageSelector, null, serviceContext);
	}

	protected BlogsEntry addBlogsEntryWithSmallImage() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId());

		InputStream inputStream = getInputStream();

		String mimeType = MimeTypesUtil.getContentType(_IMAGE_TITLE);

		ImageSelector imageSelector = new ImageSelector(
			FileUtil.getBytes(inputStream), _IMAGE_TITLE, mimeType,
			StringPool.BLANK);

		return addBlogsEntry(null, imageSelector, serviceContext);
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return addBlogsEntry(null, null, serviceContext);
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		BlogsEntry approvedEntry = addBlogsEntry(null, null, serviceContext);

		stagedModels.add(approvedEntry);

		BlogsEntry pendingEntry = BlogsTestUtil.addEntryWithWorkflow(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), false,
			serviceContext);

		stagedModels.add(pendingEntry);

		return stagedModels;
	}

	protected InputStream getInputStream() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResourceAsStream(
			"com/liferay/blogs/dependencies/test.jpg");
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group) {
		try {
			return BlogsEntryLocalServiceUtil.getBlogsEntryByUuidAndGroupId(
				uuid, group.getGroupId());
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return BlogsEntry.class;
	}

	@Override
	protected boolean isCommentableStagedModel() {
		return true;
	}

	@Override
	protected void validateImportedStagedModel(
			StagedModel stagedModel, StagedModel importedStagedModel)
		throws Exception {

		super.validateImportedStagedModel(stagedModel, importedStagedModel);

		BlogsEntry entry = (BlogsEntry)stagedModel;
		BlogsEntry importedEntry = (BlogsEntry)importedStagedModel;

		Assert.assertEquals(entry.getTitle(), importedEntry.getTitle());
		Assert.assertEquals(entry.getSubtitle(), importedEntry.getSubtitle());
		Assert.assertEquals(entry.getUrlTitle(), importedEntry.getUrlTitle());
		Assert.assertEquals(
			entry.getDescription(), importedEntry.getDescription());

		Calendar displayDateCalendar = Calendar.getInstance();
		Calendar importedDisplayDateCalendar = Calendar.getInstance();

		displayDateCalendar.setTime(entry.getDisplayDate());
		importedDisplayDateCalendar.setTime(importedEntry.getDisplayDate());

		displayDateCalendar.set(Calendar.SECOND, 0);
		displayDateCalendar.set(Calendar.MILLISECOND, 0);

		importedDisplayDateCalendar.set(Calendar.SECOND, 0);
		importedDisplayDateCalendar.set(Calendar.MILLISECOND, 0);

		Assert.assertEquals(displayDateCalendar, importedDisplayDateCalendar);

		Assert.assertEquals(
			entry.isAllowPingbacks(), importedEntry.isAllowPingbacks());
		Assert.assertEquals(
			entry.isAllowTrackbacks(), importedEntry.isAllowTrackbacks());
		Assert.assertEquals(
			StringUtil.trim(entry.getTrackbacks()),
			StringUtil.trim(importedEntry.getTrackbacks()));
		Assert.assertEquals(
			entry.getCoverImageCaption(), importedEntry.getCoverImageCaption());
		Assert.assertEquals(entry.isSmallImage(), importedEntry.isSmallImage());
	}

	private static final String _IMAGE_CROP_REGION =
		"{\"height\": 10, \"width\": 10, \"x\": 0, \"y\": 0}";

	private static final String _IMAGE_TITLE = "test.jpg";

}
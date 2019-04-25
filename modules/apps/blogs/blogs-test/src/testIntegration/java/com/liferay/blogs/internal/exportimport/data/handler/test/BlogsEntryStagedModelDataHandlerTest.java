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

package com.liferay.blogs.internal.exportimport.data.handler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.blogs.test.util.BlogsTestUtil;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

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
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Test
	public void testImportedCoverImage() throws Exception {
		initExport();

		BlogsEntry entry = addBlogsEntryWithCoverImage();

		StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, entry);

		initImport();

		BlogsEntry exportedEntry = (BlogsEntry)readExportedStagedModel(entry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			entry.getUuid(), liveGroup);

		FileEntry coverImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				importedEntry.getCoverImageFileEntryId());

		Folder coverImageFileEntryFolder = coverImageFileEntry.getFolder();

		Assert.assertEquals(
			liveGroup.getGroupId(), coverImageFileEntry.getGroupId());

		Assert.assertEquals(
			liveGroup.getGroupId(), coverImageFileEntryFolder.getGroupId());
	}

	@Test
	public void testImportedCoverImageAfterUpdate() throws Exception {
		initExport();

		BlogsEntry entry = addBlogsEntryWithCoverImage();

		StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, entry);

		initImport();

		BlogsEntry exportedEntry = (BlogsEntry)readExportedStagedModel(entry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			entry.getUuid(), liveGroup);

		long coverImageFileEntryId = importedEntry.getCoverImageFileEntryId();

		initExport();

		BlogsEntry updatedEntry = _updateBlogsEntry(entry);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, updatedEntry);

		initImport();

		BlogsEntry exportedUpdatedEntry = (BlogsEntry)readExportedStagedModel(
			updatedEntry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedUpdatedEntry);

		BlogsEntry importedUpdatedEntry = (BlogsEntry)getStagedModel(
			updatedEntry.getUuid(), liveGroup);

		Assert.assertEquals(
			coverImageFileEntryId,
			importedUpdatedEntry.getCoverImageFileEntryId());
	}

	@Test
	public void testImportedSmallImage() throws Exception {
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

		FileEntry smallImageFileEntry =
			PortletFileRepositoryUtil.getPortletFileEntry(
				importedEntry.getSmallImageFileEntryId());

		Folder smallImageFileEntryFolder = smallImageFileEntry.getFolder();

		Assert.assertEquals(
			liveGroup.getGroupId(), smallImageFileEntry.getGroupId());

		Assert.assertEquals(
			liveGroup.getGroupId(), smallImageFileEntryFolder.getGroupId());
	}

	@Test
	public void testImportedSmallImageAfterUpdate() throws Exception {
		initExport();

		BlogsEntry entry = addBlogsEntryWithSmallImage();

		StagedModelDataHandlerUtil.exportStagedModel(portletDataContext, entry);

		initImport();

		BlogsEntry exportedEntry = (BlogsEntry)readExportedStagedModel(entry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			entry.getUuid(), liveGroup);

		long smallImageFileEntryId = importedEntry.getSmallImageFileEntryId();

		initExport();

		BlogsEntry updatedEntry = _updateBlogsEntry(entry);

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, updatedEntry);

		initImport();

		BlogsEntry exportedUpdatedEntry = (BlogsEntry)readExportedStagedModel(
			updatedEntry);

		StagedModelDataHandlerUtil.importStagedModel(
			portletDataContext, exportedUpdatedEntry);

		BlogsEntry importedUpdatedEntry = (BlogsEntry)getStagedModel(
			updatedEntry.getUuid(), liveGroup);

		Assert.assertEquals(
			smallImageFileEntryId,
			importedUpdatedEntry.getSmallImageFileEntryId());
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

		return BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		List<StagedModel> stagedModels = new ArrayList<>();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		BlogsEntry approvedEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

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
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return BlogsEntryLocalServiceUtil.getBlogsEntryByUuidAndGroupId(
			uuid, group.getGroupId());
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

	private BlogsEntry _updateBlogsEntry(BlogsEntry blogsEntry)
		throws PortalException {

		return BlogsEntryLocalServiceUtil.updateEntry(
			blogsEntry.getUserId(), blogsEntry.getEntryId(),
			StringUtil.randomString(), StringUtil.randomString(),
			ServiceContextTestUtil.getServiceContext(
				stagingGroup.getGroupId(), TestPropsValues.getUserId()));
	}

	private static final String _IMAGE_CROP_REGION =
		"{\"height\": 10, \"width\": 10, \"x\": 0, \"y\": 0}";

	private static final String _IMAGE_TITLE = "test.jpg";

}
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

package com.liferay.adaptive.media.blogs.internal.exportimport.data.handler.test;

import com.liferay.adaptive.media.image.configuration.AMImageConfigurationHelper;
import com.liferay.adaptive.media.image.html.AMImageHTMLTagFactory;
import com.liferay.adaptive.media.test.util.html.HTMLAssert;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.test.util.lar.BaseWorkflowedStagedModelDataHandlerTestCase;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class AMBlogsEntryStagedModelDataHandlerTest
	extends BaseWorkflowedStagedModelDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_amImageConfigurationHelper.addAMImageConfigurationEntry(
			stagingGroup.getCompanyId(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			HashMapBuilder.put(
				"max-height", "600"
			).put(
				"max-width", "800"
			).build());
	}

	@Test
	public void testExportImportContentWithMultipleDynamicReferences()
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry1 = _addImageFileEntry(serviceContext);
		FileEntry fileEntry2 = _addImageFileEntry(serviceContext);

		String content = _getDynamicContent(fileEntry1, fileEntry2);

		BlogsEntry blogsEntry = _addBlogsEntry(content, _getServiceContext());

		exportImportStagedModel(blogsEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			blogsEntry.getUuid(), liveGroup);

		HTMLAssert.assertHTMLEquals(
			_getExpectedDynamicContent(fileEntry1, fileEntry2),
			importedEntry.getContent());
	}

	@Test
	public void testExportImportContentWithMultipleStaticReferences()
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry1 = _addImageFileEntry(serviceContext);
		FileEntry fileEntry2 = _addImageFileEntry(serviceContext);

		String content = _getStaticContent(fileEntry1, fileEntry2);

		BlogsEntry blogsEntry = _addBlogsEntry(content, serviceContext);

		exportImportStagedModel(blogsEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			blogsEntry.getUuid(), liveGroup);

		HTMLAssert.assertHTMLEquals(
			_getExpectedStaticContent(fileEntry1, fileEntry2),
			importedEntry.getContent());
	}

	@Test
	public void testExportImportContentWithNoReferences() throws Exception {
		String content = StringUtil.randomString();

		BlogsEntry blogsEntry = _addBlogsEntry(content, _getServiceContext());

		exportImportStagedModel(blogsEntry);

		BlogsEntry importedEntry = (BlogsEntry)getStagedModel(
			blogsEntry.getUuid(), liveGroup);

		HTMLAssert.assertHTMLEquals(
			blogsEntry.getContent(), importedEntry.getContent());
	}

	@Test
	public void testExportSucceedsWithInvalidReferences() throws Exception {
		int invalidFileEntryId = 9999999;

		String content = _getImgTag(invalidFileEntryId);

		BlogsEntry blogsEntry = _addBlogsEntry(content, _getServiceContext());

		initExport();

		StagedModelDataHandlerUtil.exportStagedModel(
			portletDataContext, blogsEntry);
	}

	@Override
	protected StagedModel addStagedModel(
			Group group,
			Map<String, List<StagedModel>> dependentStagedModelsMap)
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry = _addImageFileEntry(serviceContext);

		return _addBlogsEntry(_getImgTag(fileEntry), serviceContext);
	}

	@Override
	protected List<StagedModel> addWorkflowedStagedModels(Group group)
		throws Exception {

		ServiceContext serviceContext = _getServiceContext();

		FileEntry fileEntry = _addImageFileEntry(serviceContext);

		return Arrays.asList(
			_addBlogsEntry(_getImgTag(fileEntry), serviceContext));
	}

	@Override
	protected StagedModel getStagedModel(String uuid, Group group)
		throws PortalException {

		return _blogsEntryLocalService.getBlogsEntryByUuidAndGroupId(
			uuid, group.getGroupId());
	}

	@Override
	protected Class<? extends StagedModel> getStagedModelClass() {
		return BlogsEntry.class;
	}

	private BlogsEntry _addBlogsEntry(
			String content, ServiceContext serviceContext)
		throws Exception {

		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			content, new Date(), true, true, new String[0], StringPool.BLANK,
			null, null, serviceContext);
	}

	private FileEntry _addImageFileEntry(ServiceContext serviceContext)
		throws Exception {

		return _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), stagingGroup.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(getClass(), "image.jpg"), null, null,
			serviceContext);
	}

	private String _getDynamicContent(FileEntry... fileEntries)
		throws Exception {

		StringBundler sb = new StringBundler(fileEntries.length);

		for (FileEntry fileEntry : fileEntries) {
			sb.append(_getImgTag(fileEntry));
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _getExpectedDynamicContent(FileEntry... fileEntries)
		throws Exception {

		List<FileEntry> importedFileEntries = new ArrayList<>();

		for (FileEntry fileEntry : fileEntries) {
			importedFileEntries.add(
				_dlAppLocalService.getFileEntryByUuidAndGroupId(
					fileEntry.getUuid(), liveGroup.getGroupId()));
		}

		return _getDynamicContent(
			importedFileEntries.toArray(new FileEntry[0]));
	}

	private String _getExpectedStaticContent(FileEntry... fileEntries)
		throws Exception {

		StringBundler sb = new StringBundler(fileEntries.length * 2);

		for (FileEntry fileEntry : fileEntries) {
			FileEntry importedFileEntry =
				_dlAppLocalService.getFileEntryByUuidAndGroupId(
					fileEntry.getUuid(), liveGroup.getGroupId());

			sb.append(
				_amImageHTMLTagFactory.create(
					_getImgTag(importedFileEntry), importedFileEntry));

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private String _getImgTag(FileEntry fileEntry) throws Exception {
		return _getImgTag(fileEntry.getFileEntryId());
	}

	private String _getImgTag(long fileEntryId) throws Exception {
		return String.format(
			"<img alt=\"alt\" class=\"a class\" data-fileentryid=\"%s\" " +
				"src=\"theURL\" />",
			fileEntryId);
	}

	private String _getPictureTag(FileEntry fileEntry) throws Exception {
		StringBundler sb = new StringBundler(6);

		sb.append("<picture data-fileentryid=\"");
		sb.append(fileEntry.getFileEntryId());
		sb.append("\">");
		sb.append("<source></source>");
		sb.append(_getImgTag(fileEntry));
		sb.append("</picture>");

		return sb.toString();
	}

	private ServiceContext _getServiceContext() throws Exception {
		return ServiceContextTestUtil.getServiceContext(
			stagingGroup.getGroupId(), TestPropsValues.getUserId());
	}

	private String _getStaticContent(FileEntry... fileEntries)
		throws Exception {

		StringBundler sb = new StringBundler(fileEntries.length);

		for (FileEntry fileEntry : fileEntries) {
			sb.append(_getPictureTag(fileEntry));
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	@Inject
	private AMImageConfigurationHelper _amImageConfigurationHelper;

	@Inject
	private AMImageHTMLTagFactory _amImageHTMLTagFactory;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Inject
	private DLAppLocalService _dlAppLocalService;

}
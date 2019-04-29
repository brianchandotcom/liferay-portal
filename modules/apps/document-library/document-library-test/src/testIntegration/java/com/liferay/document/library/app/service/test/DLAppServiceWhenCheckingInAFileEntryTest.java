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

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileVersionLocalServiceUtil;
import com.liferay.document.library.sync.constants.DLSyncConstants;
import com.liferay.document.library.workflow.WorkflowHandlerInvocationCounter;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.service.test.BaseDLAppTestCase;

import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenCheckingInAFileEntryTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testShouldCallWorkflowHandler() throws Exception {
		try (WorkflowHandlerInvocationCounter<FileEntry>
				workflowHandlerInvocationCounter =
					new WorkflowHandlerInvocationCounter<>(
						DLFileEntryConstants.getClassName())) {

			FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(
				1,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", int.class, Map.class));

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext);

			Assert.assertEquals(
				1,
				workflowHandlerInvocationCounter.getCount(
					"updateStatus", int.class, Map.class));

			_withMaximumNumberOfVersions(
				5,
				() -> {
					DLAppServiceTestUtil.updateFileEntry(
						group.getGroupId(), fileEntry.getFileEntryId(),
						RandomTestUtil.randomString(), true);

					Assert.assertEquals(
						1,
						workflowHandlerInvocationCounter.getCount(
							"updateStatus", int.class, Map.class));

					DLAppServiceUtil.checkInFileEntry(
						fileEntry.getFileEntryId(),
						DLVersionNumberIncrease.MINOR,
						RandomTestUtil.randomString(), serviceContext);

					Assert.assertEquals(
						2,
						workflowHandlerInvocationCounter.getCount(
							"updateStatus", int.class, Map.class));
				});
		}
	}

	@Test
	public void testShouldFireSyncEvent() throws Exception {
		AtomicInteger counter =
			DLAppServiceTestUtil.registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		_withMaximumNumberOfVersions(
			5,
			() -> {
				DLAppServiceUtil.checkInFileEntry(
					fileEntry.getFileEntryId(), DLVersionNumberIncrease.MINOR,
					RandomTestUtil.randomString(), serviceContext);

				Assert.assertEquals(3, counter.get());
			});
	}

	@Test
	public void testShouldUpdateFileEntryTypeVersionIncrement()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, 0, serviceContext);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		Assert.assertEquals(0, dlFileEntry.getFileEntryTypeId());

		int numberOfVersions = 1;

		_withMaximumNumberOfVersions(
			numberOfVersions,
			() -> {
				for (int i = 0; i < (numberOfVersions + 10); i++) {
					DLAppServiceUtil.checkOutFileEntry(
						fileEntry.getFileEntryId(), serviceContext);

					FileEntry checkedOutFileEntry =
						DLAppServiceUtil.getFileEntry(
							fileEntry.getFileEntryId());

					serviceContext.setAttribute(
						"ddmFormValues", _SERIALIZED_DDM_FORM_VALUES);

					DLFileEntryType basicDocumentDLFileEntryType =
						DLFileEntryTypeLocalServiceUtil.
							getBasicDocumentDLFileEntryType();

					serviceContext.setAttribute(
						"fileEntryTypeId",
						basicDocumentDLFileEntryType.getFileEntryTypeId());

					FileEntry updatedFileEntry =
						DLAppServiceUtil.updateFileEntry(
							checkedOutFileEntry.getFileEntryId(),
							checkedOutFileEntry.getFileName(),
							checkedOutFileEntry.getMimeType(),
							checkedOutFileEntry.getTitle(),
							checkedOutFileEntry.getDescription(),
							StringUtil.randomString(),
							DLVersionNumberIncrease.MAJOR, null, 0,
							serviceContext);

					DLAppServiceUtil.checkInFileEntry(
						updatedFileEntry.getFileEntryId(),
						DLVersionNumberIncrease.MAJOR,
						StringUtil.randomString(), serviceContext);

					FileEntry checkedInFileEntry =
						DLAppServiceUtil.getFileEntry(
							updatedFileEntry.getFileEntryId());

					DLFileEntry checkedInDLFileEntry =
						(DLFileEntry)checkedInFileEntry.getModel();

					Assert.assertEquals(
						basicDocumentDLFileEntryType.getFileEntryTypeId(),
						checkedInDLFileEntry.getFileEntryTypeId());
				}
			});

		List<DLFileVersion> fileVersions =
			DLFileVersionLocalServiceUtil.getFileVersions(
				fileEntry.getFileEntryId(), WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			"The number of versions stored are: ", numberOfVersions,
			fileVersions.size());
	}

	@Test
	public void testShouldUpdateFileEntryTypeWithNoVersionIncrement()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), ContentTypes.APPLICATION_OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, 0, serviceContext);

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		Assert.assertEquals(0, dlFileEntry.getFileEntryTypeId());

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		FileEntry checkedOutFileEntry = DLAppServiceUtil.getFileEntry(
			fileEntry.getFileEntryId());

		serviceContext.setAttribute(
			"ddmFormValues", _SERIALIZED_DDM_FORM_VALUES);

		DLFileEntryType basicDocumentDLFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getBasicDocumentDLFileEntryType();

		serviceContext.setAttribute(
			"fileEntryTypeId",
			basicDocumentDLFileEntryType.getFileEntryTypeId());

		_withMaximumNumberOfVersions(
			5,
			() -> {
				FileEntry updatedFileEntry = DLAppServiceUtil.updateFileEntry(
					checkedOutFileEntry.getFileEntryId(),
					checkedOutFileEntry.getFileName(),
					checkedOutFileEntry.getMimeType(),
					checkedOutFileEntry.getTitle(),
					checkedOutFileEntry.getDescription(),
					StringUtil.randomString(), DLVersionNumberIncrease.NONE,
					null, 0, serviceContext);

				DLAppServiceUtil.checkInFileEntry(
					updatedFileEntry.getFileEntryId(),
					DLVersionNumberIncrease.NONE, StringUtil.randomString(),
					serviceContext);

				FileEntry checkedInFileEntry = DLAppServiceUtil.getFileEntry(
					updatedFileEntry.getFileEntryId());

				DLFileEntry checkedInDLFileEntry =
					(DLFileEntry)checkedInFileEntry.getModel();

				Assert.assertEquals(
					basicDocumentDLFileEntryType.getFileEntryTypeId(),
					checkedInDLFileEntry.getFileEntryTypeId());
			});
	}

	@Test
	public void testShouldUpdateTagNamesWithNoVersionIncrement()
		throws Exception {

		FileEntry fileEntry = DLAppServiceTestUtil.addFileEntry(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			new String[] {"tag1", "tag2"});

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		DLAppServiceUtil.checkOutFileEntry(
			fileEntry.getFileEntryId(), serviceContext);

		FileEntry checkedOutFileEntry = DLAppServiceUtil.getFileEntry(
			fileEntry.getFileEntryId());

		FileVersion latestFileVersion =
			checkedOutFileEntry.getLatestFileVersion(true);

		AssetEntry latestFileVersionAssetEntry =
			AssetEntryLocalServiceUtil.getEntry(
				DLFileEntryConstants.getClassName(),
				latestFileVersion.getPrimaryKey());

		Assert.assertArrayEquals(
			new String[] {"tag1", "tag2"},
			latestFileVersionAssetEntry.getTagNames());

		serviceContext.setAssetTagNames(new String[] {"tag3", "tag4"});

		_withMaximumNumberOfVersions(
			5,
			() -> {
				FileEntry updatedFileEntry = DLAppServiceUtil.updateFileEntry(
					checkedOutFileEntry.getFileEntryId(),
					checkedOutFileEntry.getFileName(),
					checkedOutFileEntry.getMimeType(),
					checkedOutFileEntry.getTitle(),
					checkedOutFileEntry.getDescription(),
					StringUtil.randomString(), DLVersionNumberIncrease.NONE,
					null, 0, serviceContext);

				DLAppServiceUtil.checkInFileEntry(
					updatedFileEntry.getFileEntryId(),
					DLVersionNumberIncrease.NONE, StringUtil.randomString(),
					serviceContext);

				FileEntry checkedInFileEntry = DLAppServiceUtil.getFileEntry(
					updatedFileEntry.getFileEntryId());

				FileVersion lastFileVersion =
					checkedInFileEntry.getFileVersion();

				AssetEntry lastFileVersionAssetEntry =
					AssetEntryLocalServiceUtil.getEntry(
						DLFileEntryConstants.getClassName(),
						lastFileVersion.getPrimaryKey());

				Assert.assertArrayEquals(
					new String[] {"tag3", "tag4"},
					lastFileVersionAssetEntry.getTagNames());
			});
	}

	private void _withMaximumNumberOfVersions(
			int maximumNumberOfVersions,
			UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		Dictionary<String, Object> dictionary = new HashMapDictionary<>();

		dictionary.put("maximumNumberOfVersions", maximumNumberOfVersions);

		try (ConfigurationTemporarySwapper configurationTemporarySwapper =
				new ConfigurationTemporarySwapper(
					"com.liferay.document.library.configuration." +
						"DLConfiguration",
					dictionary)) {

			unsafeRunnable.run();
		}
	}

	private static final String _SERIALIZED_DDM_FORM_VALUES =
		StringBundler.concat(
			"{\"availableLanguageIds\":[\"en_US\"],\"defaultLanguageId\":",
			"\"en_US\",\"fieldValues\":[{\"instanceId\":\"pvik\",\"name\":",
			"\"select2305\",\"value\":{\"en_US\":[\"strong\"]}},",
			"{\"instanceId\":\"wwtk\",\"name\":\"select3229\",\"value\":",
			"{\"en_US\":[\"advisor\"]}},{\"instanceId\":\"cclm\",\"name\":",
			"\"select4282\",\"value\":{\"en_US\":[\"awareness\"]}}]}");

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.scheduler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
@Sync
public class CheckFileEntrySchedulerJobConfigurationTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testCheckFileEntriesAnExpiredFileEntryCanBePublishedWithDisplayDate()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, null, null, null,
			serviceContext);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Date expirationDate = new Date(
			System.currentTimeMillis() - Time.MINUTE);

		dlFileEntry.setExpirationDate(expirationDate);

		dlFileEntry = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion =
			_dlFileVersionLocalService.fetchLatestFileVersion(
				dlFileEntry.getFileEntryId(), false);

		dlFileVersion.setExpirationDate(expirationDate);

		dlFileVersion = _dlFileVersionLocalService.updateDLFileVersion(
			dlFileVersion);

		_dlFileEntryLocalService.updateStatus(
			dlFileVersion.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_EXPIRED, serviceContext, new HashMap<>());

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileEntry.getStatus());

		Date displayDate = new Date(System.currentTimeMillis() + Time.DAY);

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.APPLICATION_OCTET_STREAM, fileEntry.getTitle(),
			"urltitle", StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, (byte[])null, displayDate, null,
			null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, dlFileEntry.getStatus());

		displayDate = new Date(System.currentTimeMillis() + 10);

		_dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.APPLICATION_OCTET_STREAM, fileEntry.getTitle(),
			"urltitle", StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, (byte[])null, displayDate, null,
			null, serviceContext);

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, dlFileEntry.getStatus());

		_dlFileEntryLocalService.checkFileEntries(_group.getCompanyId(), 1);

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
	}

	@Test
	public void testCheckFileEntriesAnExpiredFileEntryCanBeRepublished()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, null, null, null,
			serviceContext);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Date expirationDate = new Date(
			System.currentTimeMillis() - Time.MINUTE);

		dlFileEntry.setExpirationDate(expirationDate);

		dlFileEntry = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion =
			_dlFileVersionLocalService.fetchLatestFileVersion(
				dlFileEntry.getFileEntryId(), false);

		dlFileVersion.setExpirationDate(expirationDate);

		dlFileVersion = _dlFileVersionLocalService.updateDLFileVersion(
			dlFileVersion);

		_dlFileEntryLocalService.updateStatus(
			dlFileVersion.getUserId(), dlFileVersion.getFileVersionId(),
			WorkflowConstants.STATUS_EXPIRED, serviceContext, new HashMap<>());

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			dlFileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileEntry.getStatus());

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.APPLICATION_OCTET_STREAM, fileEntry.getTitle(),
			"urltitle", StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, (byte[])null, null, null, null,
			serviceContext);

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
	}

	@Test
	public void testCheckFileEntriesChangingCheckIntervalShouldExpireFileEntries()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry1 = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, null, null, null,
			serviceContext);

		FileEntry fileEntry2 = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, null, null, null,
			serviceContext);

		Date expirationDate = new Date(
			System.currentTimeMillis() - Time.MINUTE);

		DLFileEntry dlFileEntry1 = _dlFileEntryLocalService.getFileEntry(
			fileEntry1.getFileEntryId());

		dlFileEntry1.setExpirationDate(expirationDate);

		dlFileEntry1 = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry1);

		DLFileVersion dlFileVersion1 = dlFileEntry1.getFileVersion();

		dlFileVersion1.setExpirationDate(expirationDate);

		_dlFileVersionLocalService.updateDLFileVersion(dlFileVersion1);

		_dlFileEntryLocalService.checkFileEntries(_group.getCompanyId(), 2);

		dlFileEntry1 = _dlFileEntryLocalService.getFileEntry(
			fileEntry1.getFileEntryId());

		dlFileVersion1 = dlFileEntry1.getFileVersion();

		DLFileEntry dlFileEntry2 = _dlFileEntryLocalService.getFileEntry(
			fileEntry2.getFileEntryId());

		DLFileVersion dlFileVersion2 = dlFileEntry2.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileEntry1.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileVersion1.getStatus());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileEntry2.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileVersion2.getStatus());

		expirationDate = new Date(
			System.currentTimeMillis() - (3 * Time.MINUTE));

		dlFileEntry2 = _dlFileEntryLocalService.getFileEntry(
			fileEntry2.getFileEntryId());

		dlFileEntry2.setExpirationDate(expirationDate);

		dlFileEntry2 = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry2);

		dlFileVersion2 = dlFileEntry2.getFileVersion();

		dlFileVersion2.setExpirationDate(expirationDate);

		_dlFileVersionLocalService.updateDLFileVersion(dlFileVersion2);

		_dlFileEntryLocalService.checkFileEntries(_group.getCompanyId(), 4);

		dlFileEntry2 = _dlFileEntryLocalService.getFileEntry(
			fileEntry2.getFileEntryId());

		dlFileVersion2 = dlFileEntry2.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileEntry2.getStatus());

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileVersion2.getStatus());
	}

	@Test
	public void testExpireFileVersions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, null, null, null,
			serviceContext);

		Date expirationDate = new Date(
			System.currentTimeMillis() - Time.MINUTE);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		dlFileEntry.setExpirationDate(expirationDate);

		dlFileEntry = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		DLFileVersion dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setExpirationDate(expirationDate);

		_dlFileVersionLocalService.updateDLFileVersion(dlFileVersion);

		fileEntry = _dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), DLVersionNumberIncrease.MINOR,
			(byte[])null, null, null, null, serviceContext);

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		dlFileEntry.setExpirationDate(expirationDate);

		List<FileVersion> fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(fileVersions.toString(), 2, fileVersions.size());

		dlFileEntry = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		dlFileVersion = dlFileEntry.getFileVersion();

		dlFileVersion.setExpirationDate(expirationDate);

		dlFileVersion = _dlFileVersionLocalService.updateDLFileVersion(
			dlFileVersion);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileVersion.getStatus());

		_dlFileEntryLocalService.checkFileEntries(_group.getCompanyId(), 2);

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		dlFileVersion = dlFileEntry.getFileVersion();

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileEntry.getStatus());

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileVersion.getStatus());

		fileVersions = fileEntry.getFileVersions(
			WorkflowConstants.STATUS_EXPIRED);

		Assert.assertEquals(fileVersions.toString(), 2, fileVersions.size());
	}

	@Test
	public void testPublishFileEntry() throws Exception {
		Date displayDate = new Date(System.currentTimeMillis() + Time.DAY);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, displayDate, null,
			null, serviceContext);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, dlFileEntry.getStatus());

		displayDate = new Date(System.currentTimeMillis() + 10);

		_dlAppService.updateFileEntry(
			fileEntry.getFileEntryId(), StringPool.BLANK,
			ContentTypes.APPLICATION_OCTET_STREAM, fileEntry.getTitle(),
			"urltitle", StringPool.BLANK, StringPool.BLANK,
			DLVersionNumberIncrease.MINOR, (byte[])null, displayDate, null,
			null, serviceContext);

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, dlFileEntry.getStatus());

		_dlFileEntryLocalService.checkFileEntries(_group.getCompanyId(), 1);

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
	}

	@Inject
	private DLAppService _dlAppService;

	@Inject
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Inject
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@DeleteAfterTestRun
	private Group _group;

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.scheduler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLVersionNumberIncrease;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
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
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

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
	public void testExpireFileEntry() throws Exception {
		FileEntry fileEntry = _dlAppService.addFileEntry(
			null, _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ContentTypes.APPLICATION_OCTET_STREAM,
			RandomTestUtil.randomString(), null, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), (byte[])null, null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Date expirationDate = new Date(
			System.currentTimeMillis() - Time.MINUTE);

		dlFileEntry.setExpirationDate(expirationDate);

		dlFileEntry = _dlFileEntryLocalService.updateDLFileEntry(dlFileEntry);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());

		_dlFileEntryLocalService.checkFileEntries(_group.getCompanyId(), 2);

		dlFileEntry = _dlFileEntryLocalService.getFileEntry(
			fileEntry.getFileEntryId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, dlFileEntry.getStatus());
	}

	@FeatureFlags("LPD-10701")
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

	@DeleteAfterTestRun
	private Group _group;

}
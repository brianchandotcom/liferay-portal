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

package com.liferay.document.library.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.exception.NoSuchStorageQuotaException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.document.library.model.DLStorageQuota;
import com.liferay.document.library.service.DLStorageQuotaLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.apache.tika.mime.MimeTypes;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class DLAppDLStorageQuotaLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddFileEntryIncreasesStorageSize() throws Exception {
		long initialStorageSize = _getStorageSize();

		int size1 = RandomTestUtil.randomInt(1, 100);

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), MimeTypes.OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new byte[size1], null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		int size2 = size1 + RandomTestUtil.randomInt(1, 100);

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), MimeTypes.OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new byte[size2], null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		Assert.assertEquals(
			initialStorageSize + size1 + size2, _getStorageSize());
	}

	@Test
	public void testDeleteFileEntryDecreasesStorageSize() throws Exception {
		long initialStorageSize = _getStorageSize();

		int size = RandomTestUtil.randomInt(1, 100);

		_dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), MimeTypes.OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new byte[size], null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		FileEntry fileEntry = _dlAppLocalService.addFileEntry(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), MimeTypes.OCTET_STREAM,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(),
			new byte[size + RandomTestUtil.randomInt(1, 100)], null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		_dlAppLocalService.deleteFileEntry(fileEntry.getFileEntryId());

		Assert.assertEquals(initialStorageSize + size, _getStorageSize());
	}

	private long _getStorageSize() throws Exception {
		try {
			DLStorageQuota dlStorageQuota =
				_dlStorageQuotaLocalService.getCompanyDLStorageQuota(
					TestPropsValues.getCompanyId());

			return dlStorageQuota.getStorageSize();
		}
		catch (NoSuchStorageQuotaException noSuchStorageQuotaException) {
			return 0;
		}
	}

	@Inject
	private DLAppLocalService _dlAppLocalService;

	@Inject
	private DLStorageQuotaLocalService _dlStorageQuotaLocalService;

	@DeleteAfterTestRun
	private Group _group;

}
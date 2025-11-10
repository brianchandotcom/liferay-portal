/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.app.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.app.service.test.util.DLAppServiceTestUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalServiceUtil;
import com.liferay.document.library.test.util.BaseDLAppTestCase;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class DLAppServiceWhenMovingWithDLFileEntryTypeTest
	extends BaseDLAppTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testMoveDLFileEntryShouldHaveSameFileEntryType()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			group.getCompanyId());

		DLFileEntryType dlFileEntryType =
			DLFileEntryTypeLocalServiceUtil.getFileEntryType(
				companyGroup.getGroupId(), "DL_VIDEO_EXTERNAL_SHORTCUT");

		serviceContext.setAttribute(
			"fileEntryTypeId", dlFileEntryType.getFileEntryTypeId());

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			RandomTestUtil.randomString(), group.getGroupId(),
			parentFolder.getFolderId(), DLAppServiceTestUtil.FILE_NAME,
			ContentTypes.TEXT_PLAIN, DLAppServiceTestUtil.STRIPPED_FILE_NAME,
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK,
			BaseDLAppTestCase.CONTENT.getBytes(), null, null, null,
			serviceContext);

		FileEntry movedFileEntry = dlAppService.moveFileEntry(
			fileEntry.getFileEntryId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceContextTestUtil.getServiceContext(targetGroup.getGroupId()));

		DLFileEntry dlFileEntry = (DLFileEntry)movedFileEntry.getModel();

		Assert.assertEquals(
			dlFileEntryType.getFileEntryTypeId(),
			dlFileEntry.getFileEntryTypeId());
	}

}
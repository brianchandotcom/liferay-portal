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

package com.liferay.document.library.uad.test;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileShortcutLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = DLFileShortcutUADTestHelper.class)
public class DLFileShortcutUADTestHelper {

	public DLFileShortcut addDLFileShortcut(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		DLFolder dlFolder = _dlFolderLocalService.addFolder(
			userId, TestPropsValues.getGroupId(), TestPropsValues.getGroupId(),
			false, 0L, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), false, serviceContext);

		byte[] bytes = RandomTestUtil.randomBytes(
			TikaSafeRandomizerBumper.INSTANCE);

		InputStream is = new ByteArrayInputStream(bytes);

		DLFileEntry dlFileEntry = _dlFileEntryLocalService.addFileEntry(
			userId, dlFolder.getGroupId(), dlFolder.getRepositoryId(),
			dlFolder.getFolderId(), RandomTestUtil.randomString(),
			ContentTypes.TEXT_PLAIN, RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK,
			DLFileEntryTypeConstants.FILE_ENTRY_TYPE_ID_BASIC_DOCUMENT, null,
			null, is, bytes.length, serviceContext);

		return _dlFileShortcutLocalService.addFileShortcut(
			userId, TestPropsValues.getGroupId(), TestPropsValues.getGroupId(),
			dlFolder.getFolderId(), dlFileEntry.getFileEntryId(),
			serviceContext);
	}

	public DLFileShortcut addDLFileShortcutWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		DLFileShortcut dlFileShortcut = addDLFileShortcut(userId);

		return _dlFileShortcutLocalService.updateStatus(
			statusByUserId, dlFileShortcut.getFileShortcutId(),
			WorkflowConstants.STATUS_DRAFT,
			ServiceContextTestUtil.getServiceContext());
	}

	public void cleanUpDependencies(List<DLFileShortcut> dlFileShortcuts)
		throws Exception {

		for (DLFileShortcut dlFileShortcut : dlFileShortcuts) {
			_dlFileEntryLocalService.deleteFileEntry(
				dlFileShortcut.getToFileEntryId());

			_dlFolderLocalService.deleteFolder(dlFileShortcut.getFolderId());
		}
	}

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileShortcutLocalService _dlFileShortcutLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

}
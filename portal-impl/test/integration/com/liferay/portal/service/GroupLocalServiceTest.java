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

package com.liferay.portal.service;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class GroupLocalServiceTest {

	@Test
	public void testDeletingASiteDeletesAllFileEntries() throws Exception {
		Group group = GroupTestUtil.addGroup();

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertNotNull(
			DLAppLocalServiceUtil.getFileEntry(fileEntry.getFileEntryId()));

		Assert.assertNotNull(
			AssetEntryLocalServiceUtil.getEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId()));

		GroupLocalServiceUtil.deleteGroup(group.getGroupId());

		Assert.assertNull(
			AssetEntryLocalServiceUtil.fetchEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId()));

		try {
			DLAppLocalServiceUtil.getFileEntry(fileEntry.getFileEntryId());

			Assert.fail();
		}
		catch (NoSuchFileEntryException nsfee) {
		}
	}

}
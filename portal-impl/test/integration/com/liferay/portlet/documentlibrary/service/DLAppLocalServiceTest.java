/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class DLAppLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddFolder() throws Exception {
		Folder folder = addFolder(true);

		Assert.assertTrue(folder != null);
	}

	@Test
	public void testAddRootFolder() throws Exception {
		Folder folder = addFolder(false);

		Assert.assertTrue(folder != null);
	}

	protected Folder addFolder(boolean rootFolder) throws Exception {
		return addFolder(rootFolder, ServiceTestUtil.randomString());
	}

	protected Folder addFolder(boolean rootFolder, String name)
		throws Exception {

		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			Folder parentFolder = addFolder(
				parentFolderId, "Test Folder", true);

			parentFolderId = parentFolder.getFolderId();
		}

		return addFolder(parentFolderId, name);
	}

	protected Folder addFolder(long parentFolderId, String name)
		throws Exception {

		return addFolder(parentFolderId, name, false);
	}

	protected Folder addFolder(
			long parentFolderId, String name, boolean deleteExisting)
		throws Exception {

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			_group.getGroupId());

		if (deleteExisting) {
			try {
				Folder folder = DLAppLocalServiceUtil.getFolder(
					_group.getGroupId(), parentFolderId, name);

				DLAppLocalServiceUtil.deleteFolder(folder.getFolderId());
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		return DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(), parentFolderId,
			name, StringPool.BLANK, serviceContext);
	}

	private Group _group;

}
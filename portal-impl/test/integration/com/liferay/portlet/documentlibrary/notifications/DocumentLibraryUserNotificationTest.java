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

package com.liferay.portlet.documentlibrary.notifications;

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseUserNotificationTestCase;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DocumentLibraryUserNotificationTest
	extends BaseUserNotificationTestCase {

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), group.getGroupId(), _folder.getFolderId(),
			ServiceTestUtil.randomString());

		return (BaseModel<?>)fileEntry.getModel();
	}

	@Override
	protected void addContainerModel() throws Exception {
		_folder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.DOCUMENT_LIBRARY;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		DLAppLocalServiceUtil.subscribeFolder(
			TestPropsValues.getUserId(), group.getGroupId(),
			_folder.getFolderId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		FileEntry fileEntry = DLAppTestUtil.updateFileEntry(
			group.getGroupId(), (Long)baseModel.getPrimaryKeyObj(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			false);

		return (BaseModel<?>)fileEntry.getModel();
	}

	private Folder _folder;

}
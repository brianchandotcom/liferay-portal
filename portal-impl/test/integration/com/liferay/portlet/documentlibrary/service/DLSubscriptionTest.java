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

import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.TestPropsValues;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLSubscriptionTest extends BaseSubscriptionTestCase {

	@Override
	public long addBaseModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);

		String name = ServiceTestUtil.randomString();

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId,
			name, ContentTypes.APPLICATION_OCTET_STREAM, name, StringPool.BLANK,
			StringPool.BLANK, _CONTENT.getBytes(), serviceContext);

		return fileEntry.getFileEntryId();
	}

	@Override
	public long addContainerModel(long containerModelId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		return folder.getFolderId();
	}

	@Override
	public void addSubscriptionBaseModel(long baseModelId) {
	}

	@Override
	public void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		long classPK = containerModelId;

		if (containerModelId == DEFAULT_PARENT_CONTAINER_MODEL_ID) {
			classPK = group.getGroupId();
		}

		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			Folder.class.getName(), classPK);
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
	}

	@Override
	public long updateEntry(long baseModelId) throws Exception {
		return 0;
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

}
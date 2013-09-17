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
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
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
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLSubscriptionTest extends BaseSubscriptionTestCase {

	public long addContainer(long containerId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		Folder folder = DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), group.getGroupId(), containerId,
			ServiceTestUtil.randomString(), StringPool.BLANK, serviceContext);

		return folder.getFolderId();
	}

	public long addEntry(long containerId) throws Exception {
		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		serviceContext.setCommand(Constants.ADD);

		String name = ServiceTestUtil.randomString();

		FileEntry fileEntry = DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), group.getGroupId(), containerId, name,
			ContentTypes.APPLICATION_OCTET_STREAM, name, StringPool.BLANK,
			StringPool.BLANK, CONTENT.getBytes(), serviceContext);

		return fileEntry.getFileEntryId();
	}

	public void addSubscriptionContainer(long containerId) throws Exception {
		long classPK = containerId;

		if (containerId == DEFAULT_PARENT_CONTAINER_ID) {
			classPK = group.getGroupId();
		}

		SubscriptionLocalServiceUtil.addSubscription(
			TestPropsValues.getUserId(), group.getGroupId(),
			Folder.class.getName(), classPK);
	}

	public void addSubscriptionEntry(long entryId) throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionEntryWhenAddEntryInContainer()
		throws Exception {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionEntryWhenAddEntryInRootContainer()
		throws Exception {
	}

	public long updateEntry(long entryId) throws Exception {
		return 0;
	}

	protected static final String CONTENT =
		"Content: Enterprise. Open Source. For Life.";

}
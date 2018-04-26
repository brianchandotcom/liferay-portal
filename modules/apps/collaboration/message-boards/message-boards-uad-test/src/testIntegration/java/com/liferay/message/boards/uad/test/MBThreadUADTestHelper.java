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

package com.liferay.message.boards.uad.test;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(immediate = true, service = MBThreadUADTestHelper.class)
public class MBThreadUADTestHelper {

	public MBThread addMBThread(long userId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		MBCategory mbCategory = _mbCategoryLocalService.addCategory(
			userId, 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		MBMessage mbMessage = _mbMessageLocalService.addMessage(
			userId, RandomTestUtil.randomString(), TestPropsValues.getGroupId(),
			mbCategory.getCategoryId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return _mbThreadLocalService.getMBThread(mbMessage.getThreadId());
	}

	public MBThread addMBThreadWithStatusByUserId(
			long userId, long statusByUserId)
		throws Exception {

		MBThread mbThread = addMBThread(userId);

		return _mbThreadLocalService.updateStatus(
			statusByUserId, mbThread.getThreadId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	public void cleanUpDependencies(List<MBThread> mbThreads) throws Exception {
		for (MBThread mbThread : mbThreads) {
			_mbCategoryLocalService.deleteCategory(mbThread.getCategoryId());
		}
	}

	@Reference
	private MBCategoryLocalService _mbCategoryLocalService;

	@Reference
	private MBMessageLocalService _mbMessageLocalService;

	@Reference
	private MBThreadLocalService _mbThreadLocalService;

}
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

import com.liferay.message.boards.constants.MBCategoryConstants;
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

/**
 * @author Brian Wing Shun Chan
 */
public class MBThreadUADTestUtil {

	public static MBThread addMBThread(
			MBCategoryLocalService mbCategoryLocalService,
			MBMessageLocalService mbMessageLocalService,
			MBThreadLocalService mbThreadLocalService, long userId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		MBCategory mbCategory = mbCategoryLocalService.addCategory(
			userId, 0, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return addMBThread(
			mbMessageLocalService, mbThreadLocalService, userId,
			mbCategory.getCategoryId());
	}

	public static MBThread addMBThread(
			MBMessageLocalService mbMessageLocalService,
			MBThreadLocalService mbThreadLocalService, long userId,
			long parentMBCategoryId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		MBMessage mbMessage = mbMessageLocalService.addMessage(
			userId, RandomTestUtil.randomString(), TestPropsValues.getGroupId(),
			parentMBCategoryId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return mbThreadLocalService.getMBThread(mbMessage.getThreadId());
	}

	public static MBThread addMBThreadWithStatusByUserId(
			MBCategoryLocalService mbCategoryLocalService,
			MBMessageLocalService mbMessageLocalService,
			MBThreadLocalService mbThreadLocalService, long userId,
			long statusByUserId)
		throws Exception {

		MBThread mbThread = addMBThread(
			mbCategoryLocalService, mbMessageLocalService, mbThreadLocalService,
			userId);

		return mbThreadLocalService.updateStatus(
			statusByUserId, mbThread.getThreadId(),
			WorkflowConstants.STATUS_APPROVED);
	}

	public static void cleanUpDependencies(
			MBCategoryLocalService mbCategoryLocalService,
			List<MBThread> mbThreads)
		throws Exception {

		for (MBThread mbThread : mbThreads) {
			long mbCategoryId = mbThread.getCategoryId();

			if (mbCategoryId !=
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

				mbCategoryLocalService.deleteCategory(mbCategoryId);
			}
		}
	}

}
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

package com.liferay.portlet.messageboards.social;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;
import com.liferay.portlet.social.BaseSocialActivityInterpreterTestCase;
import com.liferay.portlet.social.model.SocialActivityInterpreter;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBMessageActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@Override
	protected void addActivities() throws Exception {
		message = MBTestUtil.addMessage(group.getGroupId());

		message = MBTestUtil.addMessage(
			group.getGroupId(), message.getCategoryId(), message.getThreadId(),
			message.getMessageId());
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new MBMessageActivityInterpreter();
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			MBActivityKeys.ADD_MESSAGE, MBActivityKeys.REPLY_MESSAGE
		};
	}

	@Override
	protected void moveEntitiesToTrash() throws Exception {
		MBThreadLocalServiceUtil.moveThreadToTrash(
			TestPropsValues.getUserId(), message.getThreadId());
	}

	@Override
	protected void renameEntities() throws Exception {
	}

	@Override
	protected void restoreEntitiesFromTrash() throws Exception {
		MBThreadLocalServiceUtil.restoreThreadFromTrash(
			TestPropsValues.getUserId(), message.getThreadId());
	}

	@Override
	protected boolean supportsRename(String className) {
		return false;
	}

	protected MBMessage message;

}
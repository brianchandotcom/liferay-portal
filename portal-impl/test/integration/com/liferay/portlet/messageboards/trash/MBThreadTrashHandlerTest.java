/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.trash;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class MBThreadTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	public void testTrashDuplicate() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashParentAndDeleteParent() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashParentAndRestoreModel() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionAndDelete() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	public void testTrashVersionAndRestore() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		String subject = _SUBJECT;
		String body = _BODY;
		String format = MBMessageConstants.DEFAULT_FORMAT;
		boolean anonymous = false;
		double priority = 0.0;
		boolean allowPingbacks = false;
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			new ArrayList<ObjectValuePair<String, InputStream>>(5);

		MBMessage message = MBMessageServiceUtil.addMessage(
			serviceContext.getScopeGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, subject, body,
			format, inputStreamOVPs, anonymous, priority, allowPingbacks,
			serviceContext);

		if (!approved) {
			message = MBMessageLocalServiceUtil.updateStatus(
				message.getStatusByUserId(), message.getMessageId(),
				WorkflowConstants.STATUS_DRAFT, serviceContext);
		}

		return message.getThread();
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return MBThreadLocalServiceUtil.getThread(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return MBThread.class;
	}

	@Override
	protected int getBaseModelsNotInTrashCount(BaseModel<?> parentBaseModel)
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition(
			WorkflowConstants.STATUS_ANY);

		return MBThreadLocalServiceUtil.getGroupThreadsCount(
			(Long)parentBaseModel.getPrimaryKeyObj(), queryDefinition);
	}

	@Override
	protected String getSearchKeywords() {
		return _SUBJECT;
	}

	@Override
	protected boolean isAssetEntryVisible(ClassedModel classedModel)
		throws Exception {

		MBMessage rootMessage = MBMessageLocalServiceUtil.getMBMessage(
			((MBThread)classedModel).getRootMessageId());

		return super.isAssetEntryVisible(rootMessage);
	}

	@Override
	protected boolean isIndexableBaseModel() {
		return false;
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		MBThreadServiceUtil.moveThreadToTrash(primaryKey);
	}

	private static final String _BODY = "Body";

	private static final String _SUBJECT = "Subject";

}
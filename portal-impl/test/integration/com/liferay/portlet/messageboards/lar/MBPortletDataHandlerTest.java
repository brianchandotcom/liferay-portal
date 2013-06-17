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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBBan;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThreadFlag;
import com.liferay.portlet.messageboards.service.MBBanLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadFlagLocalServiceUtil;
import com.liferay.portlet.messageboards.util.MBTestUtil;

import java.util.List;
import java.util.Map;

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
public class MBPortletDataHandlerTest extends BasePortletDataHandlerTestCase {

	@Override
	protected void addParameters(Map<String, String[]> parameterMap) {
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "messages", true);
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "thread-flags", true);
		addBooleanParameter(
			parameterMap, MBPortletDataHandler.NAMESPACE, "user-bans", true);
	}

	@Override
	protected void addStagedModels() throws Exception {
		MBCategory category = MBTestUtil.addCategory(stagingGroup.getGroupId());

		MBMessage message = MBTestUtil.addMessageWithWorkflow(
			stagingGroup.getGroupId(), category.getCategoryId(), true);

		MBTestUtil.addThreadFlag(
			stagingGroup.getGroupId(), message.getThread());

		MBTestUtil.addBan(stagingGroup.getGroupId());
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new MBPortletDataHandler();
	}

	@Override
	protected void deleteStagedModels() throws Exception {
		List<MBThreadFlag> threadFlags =
			MBThreadFlagLocalServiceUtil.getMBThreadFlags(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBThreadFlag threadFlag : threadFlags) {
			MBThreadFlagLocalServiceUtil.deleteThreadFlag(threadFlag);
		}

		List<MBBan> bans = MBBanLocalServiceUtil.getBans(
			stagingGroup.getGroupId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBBan ban : bans) {
			MBBanLocalServiceUtil.deleteBan(ban);
		}

		List<MBMessage> messages = MBMessageLocalServiceUtil.getGroupMessages(
			stagingGroup.getGroupId(), WorkflowConstants.STATUS_ANY,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (MBMessage message : messages) {
			MBMessageLocalServiceUtil.deleteMessage(message);
		}

		MBCategoryLocalServiceUtil.deleteCategories(
			portletDataContext.getScopeGroupId());

		MBStatsUserLocalServiceUtil.deleteStatsUsersByGroupId(
			portletDataContext.getScopeGroupId());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

}
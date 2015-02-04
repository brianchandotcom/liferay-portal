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

package com.liferay.portlet.messageboards.lar;

import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.lar.test.BasePortletExportImportTestCase;
import com.liferay.portal.model.StagedModel;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Kocsis
 */
public class MBExportImportTest extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	public String getNamespace() {
		return MBPortletDataHandler.NAMESPACE;
	}

	@Override
	public String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

	@Test
	public void testExportImportThreadsDeletions() throws Exception {
		MBMessage message = MBTestUtil.addMessage(group.getGroupId());

		MBThread thread = message.getThread();

		String messageUuid = message.getUuid();

		exportImportPortlet(getPortletId());

		// Delete the thread and not the message this time

		MBThreadLocalServiceUtil.deleteThread(thread);

		exportImportPortlet(getPortletId());

		MBMessage importedMessage =
			MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
				messageUuid, importedGroup.getGroupId());

		Assert.assertNotNull(importedMessage);

		Map<String, String[]> exportParameterMap = new LinkedHashMap<>();

		exportParameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {String.valueOf(true)});

		Map<String, String[]> importParameterMap = new LinkedHashMap<>();

		importParameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {String.valueOf(true)});

		exportImportPortlet(
			getPortletId(), exportParameterMap, importParameterMap);

		importedMessage =
			MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
				messageUuid, importedGroup.getGroupId());

		Assert.assertNull(importedMessage);
	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		return MBTestUtil.addMessage(groupId);
	}

	@Override
	protected StagedModel addStagedModel(long groupId, Date createdDate)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setCreateDate(createdDate);
		serviceContext.setModifiedDate(createdDate);

		return MBTestUtil.addMessage(
			groupId, MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			serviceContext);
	}

	@Override
	protected void deleteStagedModel(StagedModel stagedModel) throws Exception {
		MBMessageLocalServiceUtil.deleteMessage((MBMessage)stagedModel);
	}

	@Override
	protected Map<String, String[]> getExportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getExportParameterMap();

		addParameter(parameterMap, "messages", true);

		return parameterMap;
	}

	@Override
	protected Map<String, String[]> getImportParameterMap() throws Exception {
		Map<String, String[]> parameterMap = super.getImportParameterMap();

		addParameter(parameterMap, "messages", true);

		return parameterMap;
	}

	@Override
	protected StagedModel getStagedModel(String uuid, long groupId) {
		return MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
			uuid, groupId);
	}

}
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

package com.liferay.message.boards.lar.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.test.util.lar.BasePortletExportImportTestCase;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.constants.MBPortletKeys;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.portal.kernel.model.StagedModel;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Daniel Kocsis
 */
@RunWith(Arquillian.class)
public class MBExportImportTest extends BasePortletExportImportTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Override
	public String getNamespace() {
		return "message_boards";
	}

	@Override
	public String getPortletId() {
		return MBPortletKeys.MESSAGE_BOARDS_ADMIN;
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testExportImportThreadsDeletions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), TestPropsValues.getUserId());

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

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
			new String[] {Boolean.TRUE.toString()});

		Map<String, String[]> importParameterMap = new LinkedHashMap<>();

		importParameterMap.put(
			PortletDataHandlerKeys.DELETIONS,
			new String[] {Boolean.TRUE.toString()});

		exportImportPortlet(
			getPortletId(), exportParameterMap, importParameterMap);

		importedMessage =
			MBMessageLocalServiceUtil.fetchMBMessageByUuidAndGroupId(
				messageUuid, importedGroup.getGroupId());

		Assert.assertNull(importedMessage);
	}

	@Override
	protected StagedModel addStagedModel(long groupId) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), groupId,
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	@Override
	protected StagedModel addStagedModel(long groupId, Date createdDate)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId());

		serviceContext.setCommand(Constants.ADD);
		serviceContext.setCreateDate(createdDate);
		serviceContext.setModifiedDate(createdDate);

		return MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), RandomTestUtil.randomString(), groupId,
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
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
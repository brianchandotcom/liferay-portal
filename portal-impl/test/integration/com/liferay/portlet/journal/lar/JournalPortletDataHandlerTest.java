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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalPortletDataHandlerTest
	extends BasePortletDataHandlerTestCase {

	@Test
	public void testDeleteAllFolders() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), "parent");

		JournalFolder childFolder = JournalTestUtil.addFolder(
			group.getGroupId(), parentFolder.getFolderId(), "child");

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), childFolder.getFolderId());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		GroupLocalServiceUtil.deleteGroup(group);

		List<JournalFolder> folders = JournalFolderLocalServiceUtil.getFolders(
			group.getGroupId());

		Assert.assertEquals(0, folders.size());
	}

	@Override
	protected void addParameters(Map<String, String[]> parameterMap) {
		addBooleanParameter(
			parameterMap, JournalPortletDataHandler.NAMESPACE, "feeds", true);
		addBooleanParameter(
			parameterMap, JournalPortletDataHandler.NAMESPACE, "structures",
			true);
		addBooleanParameter(
			parameterMap, JournalPortletDataHandler.NAMESPACE, "web-content",
			true);
	}

	@Override
	protected void addStagedModels() throws Exception {
		Layout layout = LayoutTestUtil.addLayout(
			stagingGroup.getGroupId(), ServiceTestUtil.randomString());

		JournalFolder folder = JournalTestUtil.addFolder(
			stagingGroup.getGroupId(), ServiceTestUtil.randomString());

		JournalTestUtil.addArticle(
			stagingGroup.getGroupId(), folder.getFolderId(),
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			stagingGroup.getGroupId(), JournalArticle.class.getName());

		DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(),
			PortalUtil.getClassNameId(DDMStructure.class), -1L);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(), ddmStructure.getStructureId());

		DDMTemplate rendererDDMTemplate = DDMTemplateTestUtil.addTemplate(
			stagingGroup.getGroupId(), ddmStructure.getStructureId());

		JournalTestUtil.addFeed(
			stagingGroup.getGroupId(), layout.getPlid(),
			ServiceTestUtil.randomString(), ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), rendererDDMTemplate.getTemplateKey());
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new JournalPortletDataHandler();
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.JOURNAL;
	}

}
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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.lar.test.BasePortletDataHandlerTestCase;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.LayoutTestUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Zsolt Berentey
 */
@Sync
public class JournalPortletDataHandlerTest
	extends BasePortletDataHandlerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

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
		Layout layout = LayoutTestUtil.addLayout(stagingGroup);

		JournalFolder folder = JournalTestUtil.addFolder(
			stagingGroup.getGroupId(), RandomTestUtil.randomString());

		JournalTestUtil.addArticle(
			stagingGroup.getGroupId(), folder.getFolderId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

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
			RandomTestUtil.randomString(), ddmStructure.getStructureKey(),
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
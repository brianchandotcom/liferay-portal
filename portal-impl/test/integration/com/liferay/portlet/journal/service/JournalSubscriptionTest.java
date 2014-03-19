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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousMailExecutionTestListener;
import com.liferay.portal.util.BaseSubscriptionTestCase;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 * @author Roberto Díaz
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousMailExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalSubscriptionTest extends BaseSubscriptionTestCase {

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInContainerModel() {
	}

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
	}

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), containerModelId);

		return article.getResourcePrimKey();
	}

	@Override
	protected long addBaseModelWithClassType(long containerId, long classTypeId)
		throws Exception {

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			group.getGroupId(), containerId,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Test Article</title>", _ddmStructure.getStructureKey(),
			_ddmTemplate.getTemplateKey());

		return article.getResourcePrimKey();
	}

	@Override
	protected long addClassType() throws Exception {
		_ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		_ddmTemplate = DDMTemplateTestUtil.addTemplate(
			group.getGroupId(), _ddmStructure.getStructureId());

		return _ddmStructure.getStructureId();
	}

	@Override
	protected long addContainerModel(long containerModelId) throws Exception {
		JournalFolder folder = JournalTestUtil.addFolder(
			group.getGroupId(), containerModelId,
			ServiceTestUtil.randomString());

		return folder.getFolderId();
	}

	@Override
	protected void addSubscriptionClassType(long classTypeId) throws Exception {
		JournalArticleLocalServiceUtil.subscribeStructure(
			group.getGroupId(), TestPropsValues.getUserId(), classTypeId);
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		JournalFolderLocalServiceUtil.subscribe(
			TestPropsValues.getUserId(), group.getGroupId(), containerModelId);
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.JOURNAL;
	}

	@Override
	protected String getSubscriptionBodyPreferenceName() throws Exception {
		return "emailArticleAddedBody";
	}

	@Override
	protected long updateEntry(long baseModelId) throws Exception {
		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(
				baseModelId, WorkflowConstants.STATUS_APPROVED, true);

		article = JournalTestUtil.updateArticle(
			article, ServiceTestUtil.randomString());

		return article.getResourcePrimKey();
	}

	protected DDMStructure _ddmStructure;
	protected DDMTemplate _ddmTemplate;

}
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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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
@Transactional
public class JournalArticleFinderTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		_folder = JournalTestUtil.addFolder(_group.getGroupId(), "Folder 1");

		_article = JournalTestUtil.addArticle(
			_group.getGroupId(), _folder.getFolderId(), "Article 1",
			StringPool.BLANK);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), "Folder 2");

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), _ddmStructure.getStructureId());

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 2</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), folder.getFolderId(), "Article 3",
			StringPool.BLANK);

		article.setUserId(_USER_ID);

		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -1);

		article.setExpirationDate(calendar.getTime());
		article.setReviewDate(calendar.getTime());

		JournalArticleLocalServiceUtil.updateJournalArticle(article);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			PortalUtil.getClassNameId(JournalStructure.class),
			"<title>Article 4</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		_folderIds.clear();

		_folderIds.add(_folder.getFolderId());
		_folderIds.add(folder.getFolderId());
	}

	@Test
	public void testCountByC_G_F_C_A_V_T_D_C_T_S_T_D_R() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			3,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null,
				"Article", null, null, null, (String)null, null, null, null,
				null, true, queryDefinition));

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, null,
				null, null, null, _ddmStructure.getStructureKey(), null, null,
				null, null, true, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null,
				"Article", null, null, null, (String)null, null, null, null,
				null, true, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				PortalUtil.getClassNameId(JournalStructure.class), null, null,
				"Article", null, null, null, (String)null, null, null, null,
				null, true, queryDefinition));
	}

	@Test
	public void testCountByG_C_S() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				_ddmStructure.getStructureKey(), queryDefinition));
		Assert.assertEquals(
			2,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, "0",
				queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			0,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				_ddmStructure.getStructureKey(), queryDefinition));
		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, "0",
				queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT,
				_ddmStructure.getStructureKey(), queryDefinition));
		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_C_S(
				_group.getGroupId(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, "0",
				queryDefinition));
	}

	@Test
	public void testCountByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			4,
			JournalArticleFinderUtil.countByG_F(
				_group.getGroupId(), _folderIds, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_F(
				_group.getGroupId(), _folderIds, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			3,
			JournalArticleFinderUtil.countByG_F(
				_group.getGroupId(), _folderIds, queryDefinition));
	}

	@Test
	public void testCountByG_U_C() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			2,
			JournalArticleFinderUtil.countByG_U_F_C(
				_group.getGroupId(), TestPropsValues.getUserId(),
				Collections.<Long>emptyList(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			JournalArticleFinderUtil.countByG_U_F_C(
				_group.getGroupId(), _USER_ID, Collections.<Long>emptyList(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			0,
			JournalArticleFinderUtil.countByG_U_F_C(
				_group.getGroupId(), _USER_ID, Collections.<Long>emptyList(),
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition));
	}

	@Test
	public void testFindByExpirationDate() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<JournalArticle> articles =
			JournalArticleFinderUtil.findByExpirationDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
				queryDefinition);

		Assert.assertEquals(1, articles.size());

		JournalArticle article = articles.get(0);

		Assert.assertEquals(_USER_ID, article.getUserId());

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		articles = JournalArticleFinderUtil.findByExpirationDate(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
			queryDefinition);

		Assert.assertEquals(1, articles.size());

		article = articles.get(0);

		Assert.assertEquals(_USER_ID, article.getUserId());

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		articles = JournalArticleFinderUtil.findByExpirationDate(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
			queryDefinition);

		Assert.assertEquals(0, articles.size());
	}

	@Test
	public void testFindByR_D() throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -2);

		JournalArticle article = JournalArticleFinderUtil.findByR_D(
			_article.getResourcePrimKey(), new Date());

		Assert.assertNotNull(article);

		Assert.assertEquals(_folder.getFolderId(), article.getFolderId());
	}

	@Test
	public void testFindByReviewDate() throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -2);

		List<JournalArticle> articles =
			JournalArticleFinderUtil.findByReviewDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
				calendar.getTime());

		Assert.assertEquals(1, articles.size());

		JournalArticle article = articles.get(0);

		Assert.assertEquals(_USER_ID, article.getUserId());
	}

	private static final long _USER_ID = 1234L;

	private JournalArticle _article;
	private DDMStructure _ddmStructure;
	private JournalFolder _folder;
	private List<Long> _folderIds = new ArrayList<Long>();
	private Group _group;

}
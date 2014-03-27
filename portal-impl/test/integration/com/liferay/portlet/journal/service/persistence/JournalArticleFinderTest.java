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

package com.liferay.portlet.journal.service.persistence;

import com.google.common.collect.Lists;

import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
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
import com.liferay.portlet.journal.util.comparator.ArticleCreateDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;
import com.liferay.portlet.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleReviewDateComparator;
import com.liferay.portlet.journal.util.comparator.ArticleTitleComparator;
import com.liferay.portlet.journal.util.comparator.ArticleVersionComparator;

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
 * @author Laszlo Csontos
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class JournalArticleFinderTest {

	@Before
	public void setUp() throws Exception {
		JournalArticle addedArticle;

		_group = GroupTestUtil.addGroup();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		_folder = JournalTestUtil.addFolder(_group.getGroupId(), "Folder 1");

		_basicWebContentDDMStructure =
			DDMStructureTestUtil.addStructure(
				_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate basicWebContentTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), _basicWebContentDDMStructure.getStructureId());

		addedArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), _folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 1</title>",
			_basicWebContentDDMStructure.getStructureKey(),
			basicWebContentTemplate.getTemplateKey());
		_articles.add(addedArticle);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), "Folder 2");

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), _ddmStructure.getStructureId());

		addedArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 2</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());
		_articles.add(addedArticle);

		addedArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 3</title>",
			_basicWebContentDDMStructure.getStructureKey(),
			basicWebContentTemplate.getTemplateKey());
		_articles.add(addedArticle);

		addedArticle.setUserId(_USER_ID);

		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -1);

		addedArticle.setExpirationDate(calendar.getTime());
		addedArticle.setReviewDate(calendar.getTime());

		JournalArticleLocalServiceUtil.updateJournalArticle(addedArticle);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), addedArticle);

		addedArticle = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			PortalUtil.getClassNameId(JournalStructure.class),
			"<title>Article 4</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());
		_articles.add(addedArticle);

		_folderIds.clear();

		_folderIds.add(_folder.getFolderId());
		_folderIds.add(folder.getFolderId());

		_article = _articles.get(0);
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

	@Test
	public void testQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		doQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, "Article",
			null, null, null, (String)null, null, null, null, null, true,
			queryDefinition, 3);

		doQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, null,
			null, null, null, _ddmStructure.getStructureKey(), null, null, null,
			null, true, queryDefinition, 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		doQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, "Article",
			null, null, null, (String)null, null, null, null, null, true,
			queryDefinition, 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		doQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			PortalUtil.getClassNameId(JournalStructure.class), null, null,
			"Article", null, null, null, (String)null, null, null, null, null,
			true, queryDefinition, 1);
	}

	@Test
	public void testQueryByG_C_S() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		doQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_ddmStructure.getStructureKey(), queryDefinition, 1);

		doQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_basicWebContentDDMStructure.getStructureKey(), queryDefinition, 2);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		doQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_ddmStructure.getStructureKey(), queryDefinition, 0);

		doQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_basicWebContentDDMStructure.getStructureKey(), queryDefinition, 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		doQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_ddmStructure.getStructureKey(), queryDefinition, 1);
	}

	@Test
	public void testQueryByG_C_S_WithComparatorCreateDateAscending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleCreateDateComparator(true));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorCreateDateDescending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleCreateDateComparator(false));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorDisplayDateAscending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleDisplayDateComparator(true));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorDisplayDateDescending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleDisplayDateComparator(false));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorIDAscending() throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleIDComparator(true));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorIDDescending() throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleIDComparator(false));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorModifiedDateAscending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleModifiedDateComparator(true));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorModifiedDateDescending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleModifiedDateComparator(false));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorReviewDateAscending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleReviewDateComparator(true));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorReviewDateDescending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleReviewDateComparator(false));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorTitleAscending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleTitleComparator(true));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorTitleDescending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleTitleComparator(false));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorVersionAscending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleVersionComparator(true));
	}

	@Test
	public void testQueryByG_C_S_WithComparatorVersionDescending()
		throws Exception {

		prepareSortedArticles();
		doQueryByG_F_CheckingOrder(new ArticleVersionComparator(false));
	}

	@Test
	public void testQueryByG_F() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);
		doQueryByG_F_CheckingCount(
			_group.getGroupId(), _folderIds, queryDefinition, 4);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);
		doQueryByG_F_CheckingCount(
			_group.getGroupId(), _folderIds, queryDefinition, 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		doQueryByG_F_CheckingCount(
			_group.getGroupId(), _folderIds, queryDefinition, 3);
	}

	@Test
	public void testQueryByG_U_F_C() throws Exception {
		QueryDefinition queryDefinition = new QueryDefinition();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		doQueryByG_U_C(
			_group.getGroupId(), TestPropsValues.getUserId(),
			Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 2);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		doQueryByG_U_C(
			_group.getGroupId(), _USER_ID, Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 1);

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		doQueryByG_U_C(
			_group.getGroupId(), _USER_ID, Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 0);
	}

	protected void doQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String ddmStructureKey, String ddmTemplateKey, Date displayDateGT,
			Date displayDateLT, Date reviewDate, boolean andOperator,
			QueryDefinition queryDefinition, int expectedCount)
		throws Exception {

		int actualCount =
			JournalArticleFinderUtil.countByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				companyId, groupId, folderIds, classNameId, articleId, version,
				title, description, content, type, ddmStructureKey,
				ddmTemplateKey, displayDateGT, displayDateLT, reviewDate,
				andOperator, queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		List<JournalArticle> articles =
			JournalArticleFinderUtil.findByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
				companyId, groupId, folderIds, classNameId, articleId, version,
				title, description, content, type, ddmStructureKey,
				ddmTemplateKey, displayDateGT, displayDateLT, reviewDate,
				andOperator, queryDefinition);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void doQueryByG_C_S(
			long groupId, long classNameId, String ddmStructureKey,
			QueryDefinition queryDefinition, int expectedCount)
		throws Exception {

		int actualCount = JournalArticleFinderUtil.countByG_C_S(
			groupId, classNameId, ddmStructureKey, queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		List<JournalArticle> articles = JournalArticleFinderUtil.findByG_C_S(
			groupId, classNameId, ddmStructureKey, queryDefinition);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void doQueryByG_F_CheckingCount(
			long groupId, List<Long> folderIds, QueryDefinition queryDefinition,
			int expectedCount)
		throws Exception {

		int actualCount = JournalArticleFinderUtil.countByG_F(
			groupId, folderIds, queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		List<JournalArticle> articles = JournalArticleFinderUtil.findByG_F(
			groupId, folderIds, queryDefinition);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void doQueryByG_F_CheckingOrder(OrderByComparator comparator)
		throws Exception {

		QueryDefinition queryDefinition = new QueryDefinition();
		queryDefinition.setOrderByComparator(comparator);

		List<JournalArticle> expectedArticles;

		if (comparator.isAscending()) {
			expectedArticles = _articles;
		}
		else {
			expectedArticles = Lists.reverse(_articles);
		}

		List<JournalArticle> actualArticles =
			JournalArticleFinderUtil.findByG_F(
				_group.getGroupId(), _folderIds, queryDefinition);

		Assert.assertEquals(expectedArticles, actualArticles);
	}

	protected void doQueryByG_U_C(
			long groupId, long userId, List<Long> folderIds, long classNameId,
			QueryDefinition queryDefinition, int expectedCount)
		throws Exception {

		int actualCount = JournalArticleFinderUtil.countByG_U_F_C(
			groupId, userId, folderIds, classNameId, queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		List<JournalArticle> articles = JournalArticleFinderUtil.findByG_U_F_C(
			groupId, userId, folderIds, classNameId, queryDefinition);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void prepareSortedArticles() throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.YEAR, 2014);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.DATE, 1);

		for (int i = 0; i < _articles.size(); ++i) {
			calendar.add(Calendar.DATE, 1);
			Date sequenceDate = calendar.getTime();
			String sequenceString = String.valueOf((char) ('a' + i));

			JournalArticle article = _articles.get(i);

			article.setCreateDate(sequenceDate);
			article.setDisplayDate(sequenceDate);
			article.setModifiedDate(sequenceDate);
			article.setReviewDate(sequenceDate);
			article.setArticleId(sequenceString);
			article.setTitle(sequenceString);
			article.setVersion((double)i);

			JournalArticleLocalServiceUtil.updateJournalArticle(article);
		}
	}

	private static final long _USER_ID = 1234L;

	private JournalArticle _article;
<<<<<<< HEAD
	private DDMStructure _basicWebContentDDMStructure;
=======
	private List<JournalArticle> _articles = new ArrayList<JournalArticle>();
>>>>>>> LPS-43952 Added tests to reproduce the issue
	private DDMStructure _ddmStructure;
	private JournalFolder _folder;
	private List<Long> _folderIds = new ArrayList<Long>();
	private Group _group;

}
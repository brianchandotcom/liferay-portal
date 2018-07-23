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

package com.liferay.journal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.persistence.JournalArticleFinder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.comparator.ArticleCreateDateComparator;
import com.liferay.journal.util.comparator.ArticleDisplayDateComparator;
import com.liferay.journal.util.comparator.ArticleIDComparator;
import com.liferay.journal.util.comparator.ArticleModifiedDateComparator;
import com.liferay.journal.util.comparator.ArticleReviewDateComparator;
import com.liferay.journal.util.comparator.ArticleVersionComparator;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Zsolt Berentey
 * @author Laszlo Csontos
 */
@RunWith(Arquillian.class)
public class JournalArticleFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.SUPPORTS, "com.liferay.journal.service"));

	@Before
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		_folder = JournalTestUtil.addFolder(_group.getGroupId(), "Folder 1");

		_basicWebContentDDMStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		_basicWebContentDDMTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), _basicWebContentDDMStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), _folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 1</title>",
			_basicWebContentDDMStructure.getStructureKey(),
			_basicWebContentDDMTemplate.getTemplateKey());

		_articles.add(article);

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), "Folder 2");

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), _ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 2</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		_articles.add(article);

		article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 3</title>",
			_basicWebContentDDMStructure.getStructureKey(),
			_basicWebContentDDMTemplate.getTemplateKey());

		_articles.add(article);

		article.setUserId(_USER_ID);

		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -1);

		article.setExpirationDate(calendar.getTime());
		article.setReviewDate(calendar.getTime());

		JournalArticleLocalServiceUtil.updateJournalArticle(article);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);

		article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), folder.getFolderId(),
			PortalUtil.getClassNameId(DDMStructure.class),
			"<title>Article 4</title>", _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		_articles.add(article);

		_folderIds.clear();

		_folderIds.add(_folder.getFolderId());
		_folderIds.add(folder.getFolderId());

		_article = _articles.get(0);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();

		_serviceReference = _bundleContext.getServiceReference(
			JournalArticleFinder.class);

		_journalArticleFinder = _bundleContext.getService(_serviceReference);
	}

	@After
	public void tearDown() {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testDraftArticles() throws Exception {
		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		testQueryByG_C(
			_group.getGroupId(), Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 2);

		queryDefinition.setOwnerUserId(TestPropsValues.getUserId());

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), _folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			"<title>Article 1</title>",
			_basicWebContentDDMStructure.getStructureKey(),
			_basicWebContentDDMTemplate.getTemplateKey());

		article.setUserId(_USER_ID);
		article.setStatus(WorkflowConstants.STATUS_DRAFT);

		JournalArticleLocalServiceUtil.updateJournalArticle(article);

		_articles.add(article);

		queryDefinition.setIncludeOwner(true);
		queryDefinition.setOwnerUserId(_USER_ID);
		queryDefinition.setStatus(WorkflowConstants.STATUS_APPROVED);

		testQueryByG_C(
			_group.getGroupId(), Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 3);

		queryDefinition.setIncludeOwner(false);

		testQueryByG_C(
			_group.getGroupId(), Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 0);
	}

	@Test
	public void testFindByExpirationDate() throws Exception {

		// Status any

		QueryDefinition<JournalArticle> queryDefinition =
			new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<JournalArticle> articles =
			_journalArticleFinder.findByExpirationDate(
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
				queryDefinition);

		Assert.assertEquals(articles.toString(), 1, articles.size());

		JournalArticle article = articles.get(0);

		Assert.assertEquals(_USER_ID, article.getUserId());

		// Status in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		articles = _journalArticleFinder.findByExpirationDate(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
			queryDefinition);

		Assert.assertEquals(articles.toString(), 1, articles.size());

		article = articles.get(0);

		Assert.assertEquals(_USER_ID, article.getUserId());

		// Status not in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		articles = _journalArticleFinder.findByExpirationDate(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
			queryDefinition);

		Assert.assertEquals(articles.toString(), 0, articles.size());
	}

	@Test
	public void testFindByR_D() throws Exception {
		JournalArticle article = _journalArticleFinder.findByR_D(
			_article.getResourcePrimKey(), new Date());

		Assert.assertNotNull(article);

		Assert.assertEquals(_folder.getFolderId(), article.getFolderId());
	}

	@Test
	public void testFindByReviewDate() throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.add(Calendar.DATE, -2);

		List<JournalArticle> articles = _journalArticleFinder.findByReviewDate(
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, new Date(),
			calendar.getTime());

		Assert.assertEquals(articles.toString(), 1, articles.size());

		JournalArticle article = articles.get(0);

		Assert.assertEquals(_USER_ID, article.getUserId());
	}

	@Test
	public void testLocalizedQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(Locale.US, "Localized Article");
		titleMap.put(Locale.FRANCE, "Localized Article");

		JournalTestUtil.addArticle(
			_group.getGroupId(), _folder.getFolderId(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, titleMap, titleMap,
			titleMap, Locale.US, true, true, serviceContext);

		QueryDefinition<JournalArticle> queryDefinition =
			new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		int actualCount =
			_journalArticleFinder.countByC_G_F_C_A_V_T_D_C_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null,
				"\"Localized Article\"", null, null, null, (String)null, null,
				null, null, true, queryDefinition);

		Assert.assertEquals(1, actualCount);

		List<JournalArticle> articles =
			_journalArticleFinder.findByC_G_F_C_A_V_T_D_C_S_T_D_R(
				_group.getCompanyId(), _group.getGroupId(), _folderIds,
				JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null,
				"\"Localized Article\"", null, null, null, (String)null, null,
				null, null, true, queryDefinition);

		Assert.assertEquals(articles.toString(), 1, articles.size());
	}

	@Test
	public void testQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R() throws Exception {

		// Status any

		QueryDefinition<JournalArticle> queryDefinition =
			new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		testQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, "Article",
			null, null, null, (String)null, null, null, null, null, true,
			queryDefinition, 3);
		testQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, null,
			null, null, null, _ddmStructure.getStructureKey(), null, null, null,
			null, true, queryDefinition, 1);

		// Status in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		testQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, "Article",
			null, null, null, (String)null, null, null, null, null, true,
			queryDefinition, 1);

		// Status not in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		testQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			_group.getCompanyId(), _group.getGroupId(), _folderIds,
			PortalUtil.getClassNameId(DDMStructure.class), null, null,
			"Article", null, null, null, (String)null, null, null, null, null,
			true, queryDefinition, 1);
	}

	@Test
	public void testQueryByG_C_S() throws Exception {

		// Status any

		QueryDefinition<JournalArticle> queryDefinition =
			new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		testQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_ddmStructure.getStructureKey(), queryDefinition, 1);
		testQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_basicWebContentDDMStructure.getStructureKey(), queryDefinition, 2);

		// Status in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		testQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_ddmStructure.getStructureKey(), queryDefinition, 0);
		testQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_basicWebContentDDMStructure.getStructureKey(), queryDefinition, 1);

		// Status not in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		testQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_ddmStructure.getStructureKey(), queryDefinition, 1);
		testQueryByG_C_S(
			_group.getGroupId(), JournalArticleConstants.CLASSNAME_ID_DEFAULT,
			_basicWebContentDDMStructure.getStructureKey(), queryDefinition, 1);
	}

	@Test
	public void testQueryByG_F() throws Exception {

		// Status any

		QueryDefinition<JournalArticle> queryDefinition =
			new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		testQueryByG_F(_group.getGroupId(), _folderIds, queryDefinition, 4);

		// Status in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		testQueryByG_F(_group.getGroupId(), _folderIds, queryDefinition, 1);

		// Status not in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		testQueryByG_F(_group.getGroupId(), _folderIds, queryDefinition, 3);

		// Comparators

		testQueryByG_F(new ArticleCreateDateComparator(true));
		testQueryByG_F(new ArticleCreateDateComparator(false));
		testQueryByG_F(new ArticleDisplayDateComparator(true));
		testQueryByG_F(new ArticleDisplayDateComparator(false));
		testQueryByG_F(new ArticleIDComparator(true));
		testQueryByG_F(new ArticleIDComparator(false));
		testQueryByG_F(new ArticleModifiedDateComparator(true));
		testQueryByG_F(new ArticleModifiedDateComparator(false));
		testQueryByG_F(new ArticleReviewDateComparator(true));
		testQueryByG_F(new ArticleReviewDateComparator(false));
		testQueryByG_F(new ArticleVersionComparator(true));
		testQueryByG_F(new ArticleVersionComparator(false));
	}

	@Test
	public void testQueryByG_F_C() throws Exception {

		// Status any (constructor), which is status not in trash

		QueryDefinition<JournalArticle> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_ANY);

		queryDefinition.setOwnerUserId(TestPropsValues.getUserId());

		testQueryByG_C(
			_group.getGroupId(), Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 2);

		// Status any

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);
		queryDefinition.setOwnerUserId(_USER_ID);

		testQueryByG_C(
			_group.getGroupId(), Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 1);

		// Status in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);
		queryDefinition.setOwnerUserId(_USER_ID);

		testQueryByG_C(
			_group.getGroupId(), Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 1);

		// Status not in trash

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);
		queryDefinition.setOwnerUserId(_USER_ID);

		testQueryByG_C(
			_group.getGroupId(), Collections.<Long>emptyList(),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, queryDefinition, 0);
	}

	protected void prepareSortedArticles() throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.DATE, 1);
		calendar.set(Calendar.MONTH, 1);
		calendar.set(Calendar.YEAR, 2014);

		for (int i = 0; i < _articles.size(); ++i) {
			calendar.add(Calendar.DATE, 1);

			JournalArticle article = _articles.get(i);

			article.setCreateDate(calendar.getTime());
			article.setModifiedDate(calendar.getTime());
			article.setArticleId("a" + i);
			article.setVersion(i);
			article.setDisplayDate(calendar.getTime());
			article.setReviewDate(calendar.getTime());

			JournalArticleLocalServiceUtil.updateJournalArticle(article);
		}
	}

	protected void testQueryByC_G_F_C_A_V_T_D_C_T_S_T_D_R(
			long companyId, long groupId, List<Long> folderIds,
			long classNameId, String articleId, Double version, String title,
			String description, String content, String type,
			String ddmStructureKey, String ddmTemplateKey, Date displayDateGT,
			Date displayDateLT, Date reviewDate, boolean andOperator,
			QueryDefinition<JournalArticle> queryDefinition, int expectedCount)
		throws Exception {

		int actualCount =
			_journalArticleFinder.countByC_G_F_C_A_V_T_D_C_S_T_D_R(
				companyId, groupId, folderIds, classNameId, articleId, version,
				title, description, content, ddmStructureKey, ddmTemplateKey,
				displayDateGT, displayDateLT, reviewDate, andOperator,
				queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		List<JournalArticle> articles =
			_journalArticleFinder.findByC_G_F_C_A_V_T_D_C_S_T_D_R(
				companyId, groupId, folderIds, classNameId, articleId, version,
				title, description, content, ddmStructureKey, ddmTemplateKey,
				displayDateGT, displayDateLT, reviewDate, andOperator,
				queryDefinition);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void testQueryByG_C(
			long groupId, List<Long> folderIds, long classNameId,
			QueryDefinition<JournalArticle> queryDefinition, int expectedCount)
		throws Exception {

		int actualCount = _journalArticleFinder.countByG_F_C(
			groupId, folderIds, classNameId, queryDefinition);

		List<JournalArticle> articles = _journalArticleFinder.findByG_F_C(
			groupId, folderIds, classNameId, queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void testQueryByG_C_S(
			long groupId, long classNameId, String ddmStructureKey,
			QueryDefinition<JournalArticle> queryDefinition, int expectedCount)
		throws Exception {

		int actualCount = _journalArticleFinder.countByG_C_S(
			groupId, classNameId, ddmStructureKey, queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		List<JournalArticle> articles = _journalArticleFinder.findByG_C_S(
			groupId, classNameId, ddmStructureKey, queryDefinition);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void testQueryByG_F(
			long groupId, List<Long> folderIds,
			QueryDefinition<JournalArticle> queryDefinition, int expectedCount)
		throws Exception {

		int actualCount = _journalArticleFinder.countByG_F(
			groupId, folderIds, queryDefinition);

		Assert.assertEquals(expectedCount, actualCount);

		Locale siteDefaultLocale = LocaleUtil.getSiteDefault();

		List<JournalArticle> articles = _journalArticleFinder.findByG_F_L(
			groupId, folderIds, siteDefaultLocale, queryDefinition);

		actualCount = articles.size();

		Assert.assertEquals(expectedCount, actualCount);
	}

	protected void testQueryByG_F(
			OrderByComparator<JournalArticle> orderByComparator)
		throws Exception {

		prepareSortedArticles();

		QueryDefinition<JournalArticle> queryDefinition =
			new QueryDefinition<>();

		queryDefinition.setOrderByComparator(orderByComparator);

		List<JournalArticle> expectedArticles = null;

		if (orderByComparator.isAscending()) {
			expectedArticles = _articles;
		}
		else {
			expectedArticles = new ArrayList<>(_articles);

			Collections.reverse(expectedArticles);
		}

		Locale siteDefaultLocale = LocaleUtil.getSiteDefault();

		List<JournalArticle> actualArticles = _journalArticleFinder.findByG_F_L(
			_group.getGroupId(), _folderIds, siteDefaultLocale,
			queryDefinition);

		Assert.assertEquals(expectedArticles, actualArticles);
	}

	private static final long _USER_ID = 1234L;

	private JournalArticle _article;
	private final List<JournalArticle> _articles = new ArrayList<>();
	private DDMStructure _basicWebContentDDMStructure;
	private DDMTemplate _basicWebContentDDMTemplate;
	private BundleContext _bundleContext;
	private DDMStructure _ddmStructure;
	private JournalFolder _folder;
	private final List<Long> _folderIds = new ArrayList<>();

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticleFinder _journalArticleFinder;
	private ServiceReference<JournalArticleFinder> _serviceReference;

}
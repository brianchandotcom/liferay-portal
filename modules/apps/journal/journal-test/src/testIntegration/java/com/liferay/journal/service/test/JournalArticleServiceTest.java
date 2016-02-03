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

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.exception.RequiredTemplateException;
import com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureServiceUtil;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.impl.JournalArticleLocalServiceImpl;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ClassNameServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class JournalArticleServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Version 1",
			"This is a test article.");

		_testMode = PortalRunMode.isTestMode();

		PortalRunMode.setTestMode(true);

		ServiceTestUtil.setUser(TestPropsValues.getUser());
	}

	@After
	public void tearDown() throws Exception {
		if (_article != null) {
			JournalArticleLocalServiceUtil.deleteArticle(
				_group.getGroupId(), _article.getArticleId(),
				new ServiceContext());
		}

		PortalRunMode.setTestMode(_testMode);
	}

	@Test
	public void testAddArticle() throws Exception {
		Assert.assertEquals(
			"Version 1", _article.getTitle(LocaleUtil.getDefault()));
		Assert.assertTrue(_article.isApproved());
		Assert.assertEquals(1.0, _article.getVersion(), 0);
	}

	@Test(expected = StorageFieldRequiredException.class)
	public void testAddArticleWithEmptyRequiredHTMLField() throws Exception {
		Map<String, String> requiredFields = new HashMap<>();

		requiredFields.put("HTML2030", "");

		testAddArticleRequiredFields(
			"test-ddm-structure-html-required-field.xml",
			"test-journal-content-html-empty-required-field.xml",
			requiredFields);
	}

	@Test
	public void testAddArticleWithNotEmptyRequiredHTMLField() throws Exception {
		Map<String, String> requiredFields = new HashMap<>();

		requiredFields.put("HTML2030", "<p>Hello.</p>");

		testAddArticleRequiredFields(
			"test-ddm-structure-html-required-field.xml",
			"test-journal-content-html-required-field.xml", requiredFields);
	}

	@Test(expected = StructureDefinitionException.class)
	public void testCheckArticleWithInvalidStructure() throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		String content = "<?xml version=\"1.0\"?><root></root>";

		JournalArticle article = JournalTestUtil.addArticleWithXMLContent(
			content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey());

		checkArticleMatchesStructure(article, ddmStructure);
	}

	@Test
	public void testCheckArticleWithValidStructure() throws Exception {
		Group group = GroupTestUtil.addGroup();

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), parentFolder.getFolderId(), "title", "content");

		ClassName className = ClassNameServiceUtil.fetchClassName(
			JournalArticle.class.getName());

		DDMStructure ddmStructure = DDMStructureServiceUtil.getStructure(
			group.getGroupId(), className.getClassNameId(),
			article.getDDMStructureKey());

		checkArticleMatchesStructure(article, ddmStructure);
	}

	@Test
	public void testDeleteTemplateReferencedByJournalArticles()
		throws Exception {

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(), "<title>Test Article</title>",
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey());

		try {
			DDMTemplateLocalServiceUtil.deleteTemplate(
				ddmTemplate.getTemplateId());

			Assert.fail();
		}
		catch (RequiredTemplateException rte) {
		}
	}

	@Test
	public void testExpireArticle() throws Exception {
		updateAndExpireLatestArticle("Version 2");

		Assert.assertEquals(
			"Version 2", _article.getTitle(LocaleUtil.getDefault()));
		Assert.assertTrue(_article.isExpired());
		Assert.assertEquals(1.1, _article.getVersion(), 0);
	}

	@Test
	public void testFetchLatestArticleByApprovedStatusWhenArticleApproved()
		throws Exception {

		_article = JournalTestUtil.updateArticle(_article, "Version 2");

		_latestArticle = fetchLatestArticle(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			"Version 2", _latestArticle.getTitle(LocaleUtil.getDefault()));
		Assert.assertTrue(_latestArticle.isApproved());
		Assert.assertTrue(1.1 == _latestArticle.getVersion());
	}

	@Test
	public void testFetchLatestArticleByApprovedStatusWhenArticleExpired()
		throws Exception {

		updateAndExpireArticle();

		_latestArticle = fetchLatestArticle(WorkflowConstants.STATUS_APPROVED);

		Assert.assertNull(_latestArticle);
	}

	@Test
	public void testFetchLatestArticleByApprovedStatusWhenFirstArticleExpired()
		throws Exception {

		JournalTestUtil.updateArticle(_article, "Version 2");

		_article = JournalTestUtil.expireArticle(
			_group.getGroupId(), _article, 1.0);

		_latestArticle = fetchLatestArticle(WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(
			"Version 2", _latestArticle.getTitle(LocaleUtil.getDefault()));
		Assert.assertTrue(_latestArticle.isApproved());
		Assert.assertTrue(1.1 == _latestArticle.getVersion());
	}

	@Test
	public void testFetchLatestArticleByDraftStatusWhenNoDraftArticle()
		throws Exception {

		_article = JournalTestUtil.updateArticle(_article, "Version 2");

		_latestArticle = fetchLatestArticle(WorkflowConstants.STATUS_DRAFT);

		Assert.assertNull(_latestArticle);
	}

	@Test
	public void testFetchLatestArticleExpiredWithStatusAny() throws Exception {
		updateAndExpireLatestArticle("Version 2");

		_latestArticle = fetchLatestArticle(
			WorkflowConstants.STATUS_ANY, false);

		Assert.assertTrue(_latestArticle.isExpired());
		Assert.assertEquals(
			"Version 2", _latestArticle.getTitle(LocaleUtil.getDefault()));
		Assert.assertEquals(1.1, _latestArticle.getVersion(), 0);
	}

	@Test
	public void testFetchLatestArticleExpiredWithStatusApproved()
		throws Exception {

		updateAndExpireLatestArticle("Version 2");

		_latestArticle = fetchLatestArticle(
			WorkflowConstants.STATUS_APPROVED, false);

		Assert.assertTrue(_latestArticle.isApproved());
		Assert.assertEquals(
			"Version 1", _latestArticle.getTitle(LocaleUtil.getDefault()));
		Assert.assertEquals(1.0, _latestArticle.getVersion(), 0);
	}

	@Test
	public void testFetchLatestArticleExpiredWithStatusExpired()
		throws Exception {

		updateAndExpireLatestArticle("Version 2");

		_latestArticle = fetchLatestArticle(
			WorkflowConstants.STATUS_EXPIRED, false);

		Assert.assertTrue(_latestArticle.isExpired());
		Assert.assertEquals(
			"Version 2", _latestArticle.getTitle(LocaleUtil.getDefault()));
		Assert.assertEquals(1.1, _latestArticle.getVersion(), 0);
	}

	@Test
	public void testFetchLatestArticleNotExpiredWithStatusAny()
		throws Exception {

		_article = JournalTestUtil.updateArticle(_article, "Version 2");

		_latestArticle = fetchLatestArticle(
			WorkflowConstants.STATUS_ANY, false);

		Assert.assertTrue(_latestArticle.isApproved());
		Assert.assertEquals(
			"Version 2", _latestArticle.getTitle(LocaleUtil.getDefault()));
		Assert.assertEquals(1.1, _latestArticle.getVersion(), 0);
	}

	@Test
	public void testFetchLatestArticleNotExpiredWithStatusApproved()
		throws Exception {

		_article = JournalTestUtil.updateArticle(_article, "Version 2");

		_latestArticle = fetchLatestArticle(
			WorkflowConstants.STATUS_APPROVED, false);

		Assert.assertTrue(_latestArticle.isApproved());
		Assert.assertEquals(
			"Version 2", _latestArticle.getTitle(LocaleUtil.getDefault()));
		Assert.assertEquals(1.1, _latestArticle.getVersion(), 0);
	}

	@Test
	public void testFetchLatestArticleNotExpiredWithStatusExpired()
		throws Exception {

		_article = JournalTestUtil.updateArticle(_article, "Version 2");

		_latestArticle = fetchLatestArticle(
			WorkflowConstants.STATUS_EXPIRED, false);

		Assert.assertNull(_latestArticle);
	}

	@Test
	public void testGetGroupArticlesWhenUserNotNullAndStatusAny()
		throws Exception {

		List<JournalArticle> expectedArticles = addArticles(
			2, RandomTestUtil.randomString());

		_article = updateArticleStatus(
			_article, WorkflowConstants.STATUS_DRAFT);

		expectedArticles.add(_article);

		int count = JournalArticleServiceUtil.getGroupArticlesCount(
			_group.getGroupId(), _article.getUserId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(3, count);

		List<JournalArticle> articles =
			JournalArticleServiceUtil.getGroupArticles(
				_group.getGroupId(), _article.getUserId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(expectedArticles, articles);
	}

	@Test
	public void testGetGroupArticlesWhenUserNotNullAndStatusApproved()
		throws Exception {

		List<JournalArticle> expectedArticles = addArticles(
			2, RandomTestUtil.randomString());

		expectedArticles.add(0, _article);

		_article = updateArticleStatus(
			_article, WorkflowConstants.STATUS_DRAFT);

		int count = JournalArticleServiceUtil.getGroupArticlesCount(
			_group.getGroupId(), _article.getUserId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(3, count);

		List<JournalArticle> articles =
			JournalArticleServiceUtil.getGroupArticles(
				_group.getGroupId(), _article.getUserId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(expectedArticles, articles);
	}

	@Test
	public void testGetGroupArticlesWhenUserNullAndStatusAny()
		throws Exception {

		List<JournalArticle> expectedArticles = addArticles(
			2, RandomTestUtil.randomString());

		_article = updateArticleStatus(
			_article, WorkflowConstants.STATUS_DRAFT);

		expectedArticles.add(_article);

		int count = JournalArticleServiceUtil.getGroupArticlesCount(
			_group.getGroupId(), 0,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		Assert.assertEquals(3, count);

		List<JournalArticle> articles =
			JournalArticleServiceUtil.getGroupArticles(
				_group.getGroupId(), 0,
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(expectedArticles, articles);
	}

	@Test
	public void testGetGroupArticlesWhenUserNullAndStatusApproved()
		throws Exception {

		List<JournalArticle> expectedArticles = addArticles(
			2, RandomTestUtil.randomString());

		expectedArticles.add(0, _article);

		_article = updateArticleStatus(
			_article, WorkflowConstants.STATUS_DRAFT);

		int count = JournalArticleServiceUtil.getGroupArticlesCount(
			_group.getGroupId(), 0,
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(3, count);

		List<JournalArticle> articles =
			JournalArticleServiceUtil.getGroupArticles(
				_group.getGroupId(), 0,
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

		Assert.assertEquals(expectedArticles, articles);
	}

	@Test
	public void testSearchArticlesByKeyword() throws Exception {
		List<JournalArticle> expectedArticles = createArticlesWithKeyword(2);

		int count = countArticlesByKeyword(
			_keyword, WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(2, count);

		List<JournalArticle> articles = searchArticlesByKeyword(
			_keyword, WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(expectedArticles, articles);
	}

	@Test
	public void testSearchArticlesByKeywordAndStatus() throws Exception {
		List<JournalArticle> initialArticles = createArticlesWithKeyword(2);

		updateArticleStatus(
			initialArticles.get(0), WorkflowConstants.STATUS_DRAFT);

		int count = countArticlesByKeyword(
			_keyword, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(2, count);

		List<JournalArticle> articles = searchArticlesByKeyword(
			_keyword, WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(initialArticles, articles);
	}

	@Test
	public void testUpdateArticle() throws Exception {
		_article.setDisplayDate(new Date());

		_article = JournalTestUtil.updateArticle(_article, "Version 2");

		Assert.assertEquals(
			"Version 2", _article.getTitle(LocaleUtil.getDefault()));
		Assert.assertTrue(_article.isApproved());
		Assert.assertEquals(1.1, _article.getVersion(), 0);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			_article.getModelClassName(), _article.getResourcePrimKey());

		Assert.assertEquals(
			_article.getDisplayDate(), assetEntry.getPublishDate());
	}

	@Test
	public void testUpdateExpiredArticle() throws Exception {
		_article = JournalTestUtil.expireArticle(
			_group.getGroupId(), _article, _article.getVersion());

		Assert.assertTrue(_article.isExpired());

		_article.setDisplayDate(new Date());

		_article = JournalTestUtil.updateArticle(_article, "Version 2");

		Assert.assertEquals(
			"Version 2", _article.getTitle(LocaleUtil.getDefault()));
		Assert.assertTrue(_article.isApproved());
		Assert.assertEquals(1.1, _article.getVersion(), 0);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			_article.getModelClassName(), _article.getResourcePrimKey());

		Assert.assertEquals(
			_article.getDisplayDate(), assetEntry.getPublishDate());
	}

	protected List<JournalArticle> addArticles(int count, String content)
		throws Exception {

		List<JournalArticle> articles = new ArrayList<>(count);

		for (int i = 0; i < count; i++) {
			JournalArticle article = JournalTestUtil.addArticle(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), content);

			articles.add(article);
		}

		return articles;
	}

	protected void checkArticleMatchesStructure(
			JournalArticle article, DDMStructure ddmStructure)
		throws PortalException {

		new JournalArticleLocalServiceImpl() {

			@Override
			public void checkStructure(
					JournalArticle article, DDMStructure structure)
				throws PortalException {

				super.checkStructure(article, structure);
			}

		}.checkStructure(article, ddmStructure);
	}

	protected int countArticlesByKeyword(String keyword, int status)
		throws Exception {

		List<Long> folderIds = new ArrayList<>(1);

		folderIds.add(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return JournalArticleLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), _group.getGroupId(), folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, null,
			null, keyword, "", "", null, null, status, null, true);
	}

	protected List<JournalArticle> createArticlesWithKeyword(int count)
		throws Exception {

		_keyword = RandomTestUtil.randomString();

		List<JournalArticle> articles = searchArticlesByKeyword(
			_keyword, WorkflowConstants.STATUS_ANY);

		if (articles.isEmpty()) {
			return addArticles(count, _keyword);
		}
		else {
			createArticlesWithKeyword(count);
		}

		return null;
	}

	protected JournalArticle fetchLatestArticle(int status) throws Exception {
		return JournalArticleLocalServiceUtil.fetchLatestArticle(
			_group.getGroupId(), _article.getArticleId(), status);
	}

	protected JournalArticle fetchLatestArticle(
			int status, boolean preferApproved)
		throws Exception {

		return JournalArticleLocalServiceUtil.fetchLatestArticle(
			_article.getResourcePrimKey(), status, preferApproved);
	}

	protected String readText(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/journal/dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	protected List<JournalArticle> searchArticlesByKeyword(
			String keyword, int status)
		throws Exception {

		List<Long> folderIds = new ArrayList<>(1);

		folderIds.add(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		return JournalArticleLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), _group.getGroupId(), folderIds,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, null,
			null, keyword, "", "", null, null, status, null, false,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	protected void testAddArticleRequiredFields(
			String ddmStructureDefinition, String journalArticleContent,
			Map<String, String> requiredFields)
		throws Exception {

		String definition = readText(ddmStructureDefinition);

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		String xmlContent = readText(journalArticleContent);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		for (String requiredFieldName : requiredFields.keySet()) {
			Assert.assertTrue(ddmStructure.getFieldRequired(requiredFieldName));

			serviceContext.setAttribute(
				requiredFieldName, requiredFields.get(requiredFieldName));
		}

		JournalTestUtil.addArticleWithXMLContent(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, xmlContent,
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(),
			LocaleUtil.fromLanguageId(ddmStructure.getDefaultLanguageId()),
			serviceContext);
	}

	protected void updateAndExpireArticle() throws Exception {
		JournalTestUtil.updateArticle(_article, "Version 2");

		JournalTestUtil.expireArticle(_group.getGroupId(), _article);
	}

	protected void updateAndExpireLatestArticle(String title) throws Exception {
		JournalTestUtil.updateArticle(_article, title);

		_article = JournalTestUtil.expireArticle(
			_group.getGroupId(), _article, 1.1);
	}

	protected JournalArticle updateArticleStatus(
			JournalArticle article, int status)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		if (status == WorkflowConstants.STATUS_DRAFT) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}
		else {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		return JournalTestUtil.updateArticle(
			article, "Version 2", article.getContent(), false, true,
			serviceContext);
	}

	private JournalArticle _article;

	@DeleteAfterTestRun
	private Group _group;

	private String _keyword;
	private JournalArticle _latestArticle;
	private boolean _testMode;

}
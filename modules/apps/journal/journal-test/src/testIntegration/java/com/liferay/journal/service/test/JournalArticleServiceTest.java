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
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.dynamic.data.mapping.exception.RequiredTemplateException;
import com.liferay.dynamic.data.mapping.exception.StorageFieldRequiredException;
import com.liferay.dynamic.data.mapping.exception.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class JournalArticleServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		InvocationHandler invocationHandler = ProxyUtil.getInvocationHandler(
			_journalArticleLocalService);

		_journalArticleLocalServiceImplInstance = ReflectionTestUtil.invoke(
			invocationHandler, "getTarget", new Class<?>[0]);
	}

	@Before
	public void setUp() throws Exception {
		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		_group = GroupTestUtil.addGroup();

		_article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Version 1",
			"This is a test article.");

		ServiceTestUtil.setUser(TestPropsValues.getUser());

		PortalPreferences portalPreferenceces =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				TestPropsValues.getUserId(), true);

		_originalPortalPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portalPreferenceces);

		portalPreferenceces.setValue(
			"", "expireAllArticleVersionsEnabled", "true");

		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferenceces));
	}

	@After
	public void tearDown() throws Exception {
		if (_article != null) {
			JournalArticleLocalServiceUtil.deleteArticle(
				_group.getGroupId(), _article.getArticleId(),
				new ServiceContext());
		}

		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			_originalPortalPreferencesXML);
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

		ReflectionTestUtil.invoke(
			_journalArticleLocalServiceImplInstance, "checkStructure",
			new Class<?>[] {Long.TYPE, String.class, Double.TYPE},
			article.getGroupId(), article.getArticleId(), article.getVersion());
	}

	@Test
	public void testCheckArticleWithValidStructure() throws Exception {
		Group group = GroupTestUtil.addGroup();

		try {
			JournalFolder parentFolder = JournalTestUtil.addFolder(
				group.getGroupId(), RandomTestUtil.randomString());

			JournalArticle article = JournalTestUtil.addArticle(
				group.getGroupId(), parentFolder.getFolderId(), "title",
				"content");

			ReflectionTestUtil.invoke(
				_journalArticleLocalServiceImplInstance, "checkStructure",
				new Class<?>[] {Long.TYPE, String.class, Double.TYPE},
				article.getGroupId(), article.getArticleId(),
				article.getVersion());
		}
		finally {
			GroupLocalServiceUtil.deleteGroup(group);
		}
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
		Assert.assertTrue(_latestArticle.getVersion() == 1.1);
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
		Assert.assertTrue(_latestArticle.getVersion() == 1.1);
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
	public void testGetLatestArticlesByStatus() throws Exception {
		List<JournalArticle> articles = addArticles(
			1, RandomTestUtil.randomString());

		articles.add(0, _article);

		int count = JournalArticleServiceUtil.getLatestArticlesCount(
			_group.getGroupId(), WorkflowConstants.STATUS_APPROVED);

		Assert.assertEquals(2, count);

		List<JournalArticle> latestArticles =
			JournalArticleServiceUtil.getLatestArticles(
				_group.getGroupId(), WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(latestArticles, articles);

		_article = updateArticleStatus(
			_article, WorkflowConstants.STATUS_DRAFT);

		int draftCount = JournalArticleServiceUtil.getLatestArticlesCount(
			_group.getGroupId(), WorkflowConstants.STATUS_DRAFT);

		Assert.assertEquals(1, draftCount);

		List<JournalArticle> draftArticles =
			JournalArticleServiceUtil.getLatestArticles(
				_group.getGroupId(), WorkflowConstants.STATUS_DRAFT,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(_article, draftArticles.get(0));
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

	protected int countArticlesByKeyword(String keyword, int status)
		throws Exception {

		return JournalArticleLocalServiceUtil.searchCount(
			TestPropsValues.getCompanyId(), _group.getGroupId(),
			ListUtil.fromArray(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID),
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

		createArticlesWithKeyword(count);

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

		return JournalArticleLocalServiceUtil.search(
			TestPropsValues.getCompanyId(), _group.getGroupId(),
			ListUtil.fromArray(JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID),
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, null, null, null,
			null, keyword, "", "", null, null, status, null, false,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	protected void testAddArticleRequiredFields(
			String ddmStructureDefinition, String journalArticleContent,
			Map<String, String> requiredFields)
		throws Exception {

		String definition = readText(ddmStructureDefinition);

		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				definition);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		DDMForm ddmForm = ddmFormDeserializerDeserializeResponse.getDDMForm();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		String xmlContent = readText(journalArticleContent);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		for (Map.Entry<String, String> entry : requiredFields.entrySet()) {
			Assert.assertTrue(ddmStructure.getFieldRequired(entry.getKey()));

			serviceContext.setAttribute(entry.getKey(), entry.getValue());
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

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	private static Object _journalArticleLocalServiceImplInstance;

	private JournalArticle _article;

	@Inject(filter = "ddm.form.deserializer.type=xsd")
	private DDMFormDeserializer _ddmFormDeserializer;

	@DeleteAfterTestRun
	private Group _group;

	private String _keyword;
	private JournalArticle _latestArticle;
	private String _originalPortalPreferencesXML;

}
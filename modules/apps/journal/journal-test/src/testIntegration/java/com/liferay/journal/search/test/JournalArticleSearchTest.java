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

package com.liferay.journal.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalServiceUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.dynamic.data.mapping.test.util.search.TestOrderHelper;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.dynamic.data.mapping.util.DDMUtil;
import com.liferay.journal.configuration.JournalServiceConfiguration;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleConstants;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalArticleServiceUtil;
import com.liferay.journal.service.JournalFolderServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.ClassedModel;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.kernel.portlet.PortalPreferences;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.IndexSearcherHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.PortalPreferencesLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.SearchContextTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.test.util.BaseSearchTestCase;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 * @author Tibor Lipusz
 */
@RunWith(Arquillian.class)
public class JournalArticleSearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		super.setUp();

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		PortalPreferences portalPreferenceces =
			PortletPreferencesFactoryUtil.getPortalPreferences(
				TestPropsValues.getUserId(), true);

		_originalPortalPreferencesXML = PortletPreferencesFactoryUtil.toXML(
			portalPreferenceces);

		portalPreferenceces.setValue("", "articleCommentsEnabled", "true");

		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			PortletPreferencesFactoryUtil.toXML(portalPreferenceces));

		_journalServiceConfiguration =
			ConfigurationProviderUtil.getCompanyConfiguration(
				JournalServiceConfiguration.class,
				TestPropsValues.getCompanyId());

		setUpDDMIndexer();
	}

	@After
	public void tearDown() throws Exception {
		PortalPreferencesLocalServiceUtil.updatePreferences(
			TestPropsValues.getCompanyId(),
			PortletKeys.PREFS_OWNER_TYPE_COMPANY,
			_originalPortalPreferencesXML);
	}

	@Test
	public void testArticleIdCaseInsensitive() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		String keywords = "keywords";

		Map<Locale, String> keywordsMap = new HashMap<>();

		keywordsMap.put(LocaleUtil.getDefault(), keywords);
		keywordsMap.put(LocaleUtil.GERMANY, keywords);
		keywordsMap.put(LocaleUtil.SPAIN, keywords);

		String articleId = "Article.Id";

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, articleId, false,
			keywordsMap, keywordsMap, keywordsMap, null,
			LocaleUtil.getDefault(), null, true, true, serviceContext);

		updateBaseModel(article, keywords, serviceContext);

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		int initialBaseModelsSearchCount = 1;

		assertBaseModelsCount(
			initialBaseModelsSearchCount, "ARTICLE.ID", searchContext);
		assertBaseModelsCount(
			initialBaseModelsSearchCount, "article.id", searchContext);
		assertBaseModelsCount(
			initialBaseModelsSearchCount, "ArtiCle.Id", searchContext);
	}

	@Test
	public void testMatchNotOnlyCompanyIdButAlsoQueryTerms() throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());

		BooleanQuery query = new BooleanQueryImpl();

		query.addTerm("title", RandomTestUtil.randomString());

		assertEquals(0, query, searchContext);
	}

	@Test
	public void testOrderByDDMBooleanField() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMBooleanField();
	}

	@Test
	public void testOrderByDDMBooleanFieldRepeatable() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMBooleanFieldRepeatable();
	}

	@Test
	public void testOrderByDDMDateField() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMDateField();
	}

	@Test
	public void testOrderByDDMIntegerField() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMIntegerField();
	}

	@Test
	public void testOrderByDDMIntegerFieldRepeatable() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMIntegerFieldRepeatable();
	}

	@Test
	public void testOrderByDDMNumberField() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMNumberField();
	}

	@Test
	public void testOrderByDDMNumberFieldRepeatable() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMNumberFieldRepeatable();
	}

	@Test
	public void testOrderByDDMRadioField() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMRadioField();
	}

	@Test
	public void testOrderByDDMRadioFieldKeyword() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMRadioFieldKeyword();
	}

	@Test
	public void testOrderByDDMTextField() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMTextField();
	}

	@Test
	public void testOrderByDDMTextFieldKeyword() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMTextFieldKeyword();
	}

	@Test
	public void testOrderByDDMTextFieldRepeatable() throws Exception {
		TestOrderHelper testOrderHelper =
			new JournalArticleSearchTestOrderHelper(_ddmIndexer, group);

		testOrderHelper.testOrderByDDMTextFieldRepeatable();
	}

	@Test
	public void testOrderByRelevancePhrase() throws Exception {
		JournalTestUtil.addArticle(
			group.getGroupId(), "article 1", "test test some");

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), "article 2", "some some test");

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setEntryClassNames(
			new String[] {JournalArticle.class.getName()});

		Sort sort = new Sort(null, Sort.SCORE_TYPE, false);

		searchContext.setSorts(sort);

		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		searchContext.setAttribute(Field.CONTENT, "some test");

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		Assert.assertEquals(
			documents[0].get(Field.ENTRY_CLASS_PK),
			String.valueOf(article.getResourcePrimKey()));
	}

	@Test
	public void testOrderByRelevanceWord() throws Exception {
		JournalArticle article1 = JournalTestUtil.addArticle(
			group.getGroupId(), "article 1", "test test some");
		JournalArticle article2 = JournalTestUtil.addArticle(
			group.getGroupId(), "article 2", "some some test");

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setEntryClassNames(
			new String[] {JournalArticle.class.getName()});

		Sort sort = new Sort(null, Sort.SCORE_TYPE, false);

		searchContext.setSorts(sort);

		Indexer indexer = IndexerRegistryUtil.getIndexer(JournalArticle.class);

		searchContext.setAttribute(Field.CONTENT, "test");

		Hits hits = indexer.search(searchContext);

		Document[] documents = hits.getDocs();

		Assert.assertEquals(
			documents[0].get(Field.ENTRY_CLASS_PK),
			String.valueOf(article1.getResourcePrimKey()));

		searchContext.setAttribute(Field.CONTENT, "some");

		hits = indexer.search(searchContext);

		documents = hits.getDocs();

		Assert.assertEquals(
			documents[0].get(Field.ENTRY_CLASS_PK),
			String.valueOf(article2.getResourcePrimKey()));
	}

	@Ignore
	@Override
	@Test
	public void testSearchAttachments() throws Exception {
	}

	protected BaseModel<?> addArticleWithXmlContent(
			BaseModel<?> parentBaseModel, String content,
			DDMStructure ddmStructure, DDMTemplate ddmTemplate,
			ServiceContext serviceContext)
		throws Exception {

		_ddmStructure = ddmStructure;

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalTestUtil.addArticleWithXMLContent(
			folder.getFolderId(), content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithDDMStructure(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			keywords);

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm("name");

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			serviceContext.getScopeGroupId(), JournalArticle.class.getName(),
			ddmForm);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			serviceContext.getScopeGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class));

		return addArticleWithXmlContent(
			parentBaseModel, content, ddmStructure, ddmTemplate,
			serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			Map<Locale, String> keywordsMap, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addArticleWithWorkflow(
			group.getGroupId(), keywordsMap, null, keywordsMap, approved);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		long folderId = JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (folder != null) {
			folderId = folder.getFolderId();
		}

		return JournalTestUtil.addArticleWithWorkflow(
			group.getGroupId(), folderId, keywords,
			RandomTestUtil.randomString(50), approved, serviceContext);
	}

	protected void assertEquals(
			long length, BooleanQuery query, SearchContext searchContext)
		throws Exception {

		Hits hits = IndexSearcherHelperUtil.search(searchContext, query);

		Assert.assertEquals(hits.toString(), length, hits.getLength());
	}

	@Override
	protected void deleteBaseModel(BaseModel<?> baseModel) throws Exception {
		JournalArticleLocalServiceUtil.deleteArticle((JournalArticle)baseModel);
	}

	@Override
	protected void expireBaseModelVersions(
			BaseModel<?> baseModel, boolean expireAllVersions,
			ServiceContext serviceContext)
		throws Exception {

		JournalArticle article = (JournalArticle)baseModel;

		if (expireAllVersions) {
			JournalArticleLocalServiceUtil.expireArticle(
				article.getUserId(), article.getGroupId(),
				article.getArticleId(), article.getUrlTitle(), serviceContext);
		}
		else {
			JournalArticleLocalServiceUtil.expireArticle(
				article.getUserId(), article.getGroupId(),
				article.getArticleId(), article.getVersion(),
				article.getUrlTitle(), serviceContext);
		}
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalArticle.class;
	}

	@Override
	protected Long getBaseModelClassPK(ClassedModel classedModel) {
		JournalArticle article = (JournalArticle)classedModel;

		if ((article.isDraft() || article.isPending()) &&
			(article.getVersion() != JournalArticleConstants.VERSION_DEFAULT)) {

			return article.getPrimaryKey();
		}

		return article.getResourcePrimKey();
	}

	@Override
	protected String getDDMStructureFieldName() {
		return _ddmIndexer.encodeName(
			_ddmStructure.getStructureId(), "name",
			LocaleUtil.getSiteDefault());
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			(Long)parentBaseModel.getPrimaryKeyObj(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), serviceContext);
	}

	@Override
	protected String getParentBaseModelClassName() {
		return JournalFolder.class.getName();
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected boolean isCheckBaseModelPermission() {
		return _journalServiceConfiguration.
			articleViewPermissionsCheckEnabled();
	}

	@Override
	protected boolean isExpirableAllVersions() {
		return _journalServiceConfiguration.expireAllArticleVersionsEnabled();
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			primaryKey);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);
	}

	@Override
	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {

		JournalFolderServiceUtil.moveFolderToTrash(primaryKey);
	}

	@Override
	protected Hits searchGroupEntries(long groupId, long creatorUserId)
		throws Exception {

		return JournalArticleServiceUtil.search(
			groupId, creatorUserId, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	protected void setUpDDMIndexer() {
		Registry registry = RegistryUtil.getRegistry();

		_ddmIndexer = registry.getService(DDMIndexer.class);
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		JournalArticle article = (JournalArticle)baseModel;

		return JournalTestUtil.updateArticle(
			article, keywords, article.getContent(), false, true,
			serviceContext);
	}

	@Override
	protected void updateDDMStructure(ServiceContext serviceContext)
		throws Exception {

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm("title");

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		DDMStructureLocalServiceUtil.updateStructure(
			_ddmStructure.getUserId(), _ddmStructure.getStructureId(),
			_ddmStructure.getParentStructureId(), _ddmStructure.getNameMap(),
			_ddmStructure.getDescriptionMap(), ddmForm, ddmFormLayout,
			serviceContext);
	}

	protected class JournalArticleSearchTestOrderHelper
		extends TestOrderHelper {

		protected JournalArticleSearchTestOrderHelper(
				DDMIndexer ddmIndexer, Group group)
			throws Exception {

			super(ddmIndexer, group);
		}

		@Override
		protected BaseModel<?> addSearchableAssetEntry(
				String fieldValue, BaseModel<?> parentBaseModel,
				DDMStructure ddmStructure, DDMTemplate ddmTemplate,
				ServiceContext serviceContext)
			throws Exception {

			String content = DDMStructureTestUtil.getSampleStructuredContent(
				fieldValue);

			return addArticleWithXmlContent(
				parentBaseModel, content, ddmStructure, ddmTemplate,
				serviceContext);
		}

		@Override
		protected BaseModel<?> addSearchableAssetEntryRepeatable(
				String[] fieldValues, BaseModel<?> parentBaseModel,
				DDMStructure ddmStructure, DDMTemplate ddmTemplate,
				ServiceContext serviceContext)
			throws Exception {

			ArrayList<Map<Locale, String>> contents = new ArrayList<>(
				fieldValues.length);

			for (String fieldValue : fieldValues) {
				Map<Locale, String> map = HashMapBuilder.<Locale, String>put(
					LocaleUtil.US, fieldValue
				).build();

				contents.add(map);
			}

			String content = DDMStructureTestUtil.getSampleStructuredContent(
				"name", contents, "en_US");

			return addArticleWithXmlContent(
				parentBaseModel, content, ddmStructure, ddmTemplate,
				serviceContext);
		}

		@Override
		protected long getClassNameId() {
			return PortalUtil.getClassNameId(JournalArticle.class);
		}

		@Override
		protected String getSearchableAssetEntryClassName() {
			return getBaseModelClassName();
		}

		@Override
		protected BaseModel<?> getSearchableAssetEntryParentBaseModel(
				Group group, ServiceContext serviceContext)
			throws Exception {

			return getParentBaseModel(group, serviceContext);
		}

		@Override
		protected String getSearchableAssetEntryStructureClassName() {
			return getBaseModelClassName();
		}

	}

	private DDMIndexer _ddmIndexer;
	private DDMStructure _ddmStructure;
	private JournalServiceConfiguration _journalServiceConfiguration;
	private String _originalPortalPreferencesXML;

}
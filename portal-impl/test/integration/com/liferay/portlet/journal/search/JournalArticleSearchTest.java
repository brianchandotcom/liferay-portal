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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.test.IdempotentRetryAssert;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.test.BaseSearchTestCase;
import com.liferay.portal.search.test.TestOrderHelper;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.io.DDMFormXSDDeserializerUtil;
import com.liferay.portlet.dynamicdatamapping.model.DDMForm;
import com.liferay.portlet.dynamicdatamapping.model.DDMFormLayout;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.test.DDMTemplateTestUtil;
import com.liferay.portlet.journal.asset.JournalArticleAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Juan Fernández
 * @author Tibor Lipusz
 */
@Sync
public class JournalArticleSearchTest extends BaseSearchTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testMatchNotOnlyCompanyIdButAlsoQueryTerms() throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());

		BooleanQuery query = BooleanQueryFactoryUtil.create(searchContext);

		query.addTerm("title", RandomTestUtil.randomString());

		assertEquals(0, query, searchContext);
	}

	@Test
	public void testOrderByDDMBooleanField() throws Exception {
		TestOrderHelper orderTestHelper =
			new JournalArticleSearchTestOrderHelper(group);

		orderTestHelper.orderByDDMBooleanField();
	}

	@Test
	public void testOrderByDDMIntegerField() throws Exception {
		TestOrderHelper orderTestHelper =
			new JournalArticleSearchTestOrderHelper(group);

		orderTestHelper.orderByDDMIntegerField();
	}

	@Ignore
	@Test
	public void testOrderByDDMNumberField() throws Exception {
		TestOrderHelper orderTestHelper =
			new JournalArticleSearchTestOrderHelper(group);

		orderTestHelper.orderByDDMNumberField();
	}

	@Test
	public void testOrderByDDMTextField() throws Exception {
		TestOrderHelper orderTestHelper =
			new JournalArticleSearchTestOrderHelper(group);

		orderTestHelper.orderByDDMTextField();
	}

	@Ignore()
	@Override
	@Test
	public void testSearchAttachments() throws Exception {
	}

	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, String keywords,
			DDMStructure ddmStructure, DDMTemplate ddmTemplate,
			ServiceContext serviceContext)
		throws Exception {

		_ddmStructure = ddmStructure;

		JournalFolder folder = (JournalFolder)parentBaseModel;

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			"name", keywords);

		return JournalTestUtil.addArticleWithXMLContent(
			folder.getFolderId(), content, ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithDDMStructure(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		String definition = DDMStructureTestUtil.getSampleStructureDefinition(
			"name");

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			serviceContext.getScopeGroupId(), JournalArticle.class.getName(),
			definition);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			serviceContext.getScopeGroupId(), ddmStructure.getStructureId());

		return addBaseModel(
			parentBaseModel, keywords, ddmStructure, ddmTemplate,
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
			final long length, final BooleanQuery query,
			final SearchContext searchContext)
		throws Exception {

		IdempotentRetryAssert.retryAssert(
			3, TimeUnit.SECONDS,
			new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					Hits hits = SearchEngineUtil.search(searchContext, query);

					Assert.assertEquals(length, hits.getLength());

					return null;
				}

			});
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
		return JournalArticleAssetRenderer.getClassPK(
			(JournalArticle)classedModel);
	}

	@Override
	protected String getDDMStructureFieldName() {
		return DDMIndexerUtil.encodeName(
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
		return PropsValues.JOURNAL_ARTICLE_VIEW_PERMISSION_CHECK_ENABLED;
	}

	@Override
	protected boolean isExpirableAllVersions() {
		return PropsValues.JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS;
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
	protected long searchGroupEntriesCount(long groupId, long creatorUserId)
		throws Exception {

		Hits hits =  JournalArticleServiceUtil.search(
			groupId, creatorUserId, WorkflowConstants.STATUS_APPROVED,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		return hits.getLength();
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

		String definition = DDMStructureTestUtil.getSampleStructureDefinition(
			"title");

		DDMForm ddmForm = DDMFormXSDDeserializerUtil.deserialize(definition);

		DDMFormLayout ddmFormLayout = DDMUtil.getDefaultDDMFormLayout(ddmForm);

		DDMStructureLocalServiceUtil.updateStructure(
			_ddmStructure.getStructureId(),
			_ddmStructure.getParentStructureId(), _ddmStructure.getNameMap(),
			_ddmStructure.getDescriptionMap(), ddmForm, ddmFormLayout,
			serviceContext);
	}

	protected class JournalArticleSearchTestOrderHelper
		extends TestOrderHelper {

		protected JournalArticleSearchTestOrderHelper(Group group)
			throws Exception {

			super(group);
		}

		@Override
		protected BaseModel<?> addSearchableAsset(
				BaseModel<?> parentBaseModel, String keywords,
				DDMStructure ddmStructure, DDMTemplate ddmTemplate,
				ServiceContext serviceContext)
			throws Exception {

			return addBaseModel(
				parentBaseModel, keywords, ddmStructure, ddmTemplate,
				serviceContext);
		}

		@Override
		protected String getSearchableAssetClassName() {
			return getBaseModelClassName();
		}

		@Override
		protected BaseModel<?> getSearchableAssetParentBaseModel(
				Group group, ServiceContext serviceContext)
			throws Exception {

			return getParentBaseModel(group, serviceContext);
		}

		@Override
		protected String getSearchableAssetStructureClassName() {
			return getBaseModelClassName();
		}

	}

	private DDMStructure _ddmStructure;

}
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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.search.BaseSearchTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.util.DDMIndexerUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMStructureTestUtil;
import com.liferay.portlet.dynamicdatamapping.util.DDMTemplateTestUtil;
import com.liferay.portlet.journal.asset.JournalArticleAssetRenderer;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalArticleSearchTest extends BaseSearchTestCase {

	@Override
	public void testSearchAttachments() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	protected BaseModel<?> addBaseModelWithDDMStructure(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		String xsd = DDMStructureTestUtil.getSampleStructureXSD("name");

		_ddmStructure = DDMStructureTestUtil.addStructure(
			serviceContext.getScopeGroupId(), JournalArticle.class.getName(),
			xsd);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			serviceContext.getScopeGroupId(), _ddmStructure.getStructureId());

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			"name", getSearchKeywords());

		return JournalTestUtil.addArticleWithXMLContent(
			folder.getFolderId(), content, _ddmStructure.getStructureKey(),
			ddmTemplate.getTemplateKey(), serviceContext);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalTestUtil.addArticleWithWorkflow(
			folder.getFolderId(), keywords, approved, serviceContext);
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
			_ddmStructure.getStructureId(), "name", LocaleUtil.getDefault());
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test Folder",
			serviceContext);
	}

	@Override
	protected String getSearchKeywords() {
		return "Title";
	}

	@Override
	protected boolean isExpirableAllVersions() {
		return PropsValues.JOURNAL_ARTICLE_EXPIRE_ALL_VERSIONS;
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		JournalArticle article = (JournalArticle)baseModel;

		return JournalTestUtil.updateArticle(
			article, keywords, article.getContent(), serviceContext);
	}

	private DDMStructure _ddmStructure;

}
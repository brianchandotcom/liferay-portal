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

package com.liferay.portlet.journal.trash;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.util.TrashUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class JournalArticleTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	public void testTrashParentAndDeleteParent() throws Exception {
		Assert.assertTrue("This test does not apply yet", true);
	}

	@Override
	public void testTrashParentAndDeleteTrashEntries() throws Exception {
		Assert.assertTrue("This test does not apply yet", true);
	}

	@Override
	public void testTrashParentAndRestoreModel() throws Exception {
		Assert.assertTrue("This test does not apply yet", true);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		Map<Locale, String> titleMap = new HashMap<Locale, String>();

		titleMap.put(Locale.US, "Test Article");

		Map<Locale, String> descriptionMap = new HashMap<Locale, String>();

		descriptionMap.put(Locale.US, "Description");

		Document document = createDocument("en_US", "en_US");

		Element dynamicElement = addDynamicElement(
			document.getRootElement(), "text", "name");

		addDynamicContent(dynamicElement, "en_US", "Joe Bloggs");

		String content = document.asXML();

		serviceContext = (ServiceContext)serviceContext.clone();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		if (approved) {
			serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);
		}

		return JournalArticleLocalServiceUtil.addArticle(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(), 0,
			JournalArticleConstants.CLASSNAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT, titleMap, null,
			content, "general", null, null, null, 1, 1, 1965, 0, 0, 0, 0, 0, 0,
			0, true, 0, 0, 0, 0, 0, true, true, false, null, null, null, null,
			serviceContext);
	}

	protected void addDynamicContent(
		Element dynamicElement, String languageId, String value) {

		Element dynamicContent = dynamicElement.addElement("dynamic-content");

		dynamicContent.addAttribute("language-id", languageId);

		dynamicContent.setText(value);
	}

	protected Element addDynamicElement(
		Element element, String type, String name) {

		Element dynamicElement = element.addElement("dynamic-element");

		dynamicElement.addAttribute("name", name);
		dynamicElement.addAttribute("type", type);

		return dynamicElement;
	}

	protected Document createDocument(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
	}

	@Override
	protected Long getAssetClassPK(ClassedModel classedModel) {
		JournalArticle article = (JournalArticle)classedModel;

		try {
			JournalArticleResource journalArticleResource =
				JournalArticleResourceLocalServiceUtil.getArticleResource(
					article.getResourcePrimKey());

			return journalArticleResource.getResourcePrimKey();
		}
		catch (Exception e) {
			return super.getAssetClassPK(classedModel);
		}
	}

	@Override
	protected BaseModel<?> getBaseModel(long primaryKey) throws Exception {
		return JournalArticleLocalServiceUtil.getArticle(primaryKey);
	}

	@Override
	protected Class<?> getBaseModelClass() {
		return JournalArticle.class;
	}

	@Override
	protected String getBaseModelName(ClassedModel classedModel) {
		JournalArticle article = (JournalArticle)classedModel;

		return article.getArticleId();
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		return JournalArticleLocalServiceUtil.getNotInTrashArticlesCount(
			((Group)parentBaseModel).getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	protected String getSearchKeywords() {
		return "Article";
	}

	@Override
	protected long getTrashEntryClassPK(ClassedModel classedModel) {
		JournalArticle article = (JournalArticle)classedModel;

		return article.getResourcePrimKey();
	}

	@Override
	protected String getUniqueTitle(BaseModel<?> baseModel) {
		JournalArticle article = (JournalArticle)baseModel;

		String articleId = article.getArticleId();

		return TrashUtil.getOriginalTitle(articleId);
	}

	@Override
	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			primaryKey);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);
	}

}
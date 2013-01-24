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
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;
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
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
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

		return JournalTestUtil.addArticleWithWorkflow(
			serviceContext.getScopeGroupId(), getSearchKeywords(),
			getSearchKeywords(), approved);
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
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

package com.liferay.portlet.journal.trash;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.service.JournalFolderServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;
import com.liferay.portlet.trash.BaseTrashHandlerTestCase;
import com.liferay.portlet.trash.util.TrashUtil;

import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalArticleTrashHandlerTest extends BaseTrashHandlerTestCase {

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			ServiceContext serviceContext)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalTestUtil.addArticleWithWorkflow(
			serviceContext.getScopeGroupId(), folder.getFolderId(),
			getSearchKeywords(), getSearchKeywords(), approved);
	}

	@Override
	protected BaseModel<?> addBaseModelWithWorkflow(
			boolean approved, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addArticleWithWorkflow(
			serviceContext.getScopeGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			getSearchKeywords(), getSearchKeywords(), approved);
	}

	@Override
	protected void deleteParentBaseModel(
			BaseModel<?> parentBaseModel, boolean includeTrashedEntries)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		JournalFolderServiceUtil.deleteFolder(folder.getFolderId(), false);
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
	protected int getMineBaseModelsCount(long groupId, long userId)
		throws Exception {

		return JournalArticleServiceUtil.getGroupArticlesCount(
			groupId, userId, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Override
	protected int getNotInTrashBaseModelsCount(BaseModel<?> parentBaseModel)
		throws Exception {

		JournalFolder folder = (JournalFolder)parentBaseModel;

		return JournalArticleLocalServiceUtil.getNotInTrashArticlesCount(
			folder.getGroupId(), folder.getFolderId());
	}

	@Override
	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return JournalTestUtil.addFolder(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString(_FOLDER_NAME_MAX_LENGTH));
	}

	@Override
	protected Class<?> getParentBaseModelClass() {
		return JournalFolder.class;
	}

	@Override
	protected int getRecentBaseModelsCount(long groupId) throws Exception {
		return JournalArticleServiceUtil.getGroupArticlesCount(
			groupId, 0, JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
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
	protected BaseModel<?> moveBaseModelFromTrash(
			ClassedModel classedModel, Group group,
			ServiceContext serviceContext)
		throws Exception {

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		JournalArticleServiceUtil.moveArticleFromTrash(
			group.getGroupId(), getAssetClassPK(classedModel),
			(Long)parentBaseModel.getPrimaryKeyObj(), serviceContext);

		return parentBaseModel;
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
	protected void restoreParentBaseModelFromTrash(long primaryKey)
		throws Exception {

		JournalFolderServiceUtil.restoreFolderFromTrash(primaryKey);
	}

	@Override
	protected BaseModel<?> updateBaseModel(
			long primaryKey, ServiceContext serviceContext)
		throws Exception {

		JournalArticle article = JournalArticleLocalServiceUtil.getArticle(
			primaryKey);

		return JournalTestUtil.updateArticle(
			article, "Content: Enterprise. Open Source. For Life.",
			article.getContent(), serviceContext);
	}

	private static final int _FOLDER_NAME_MAX_LENGTH = 100;

}
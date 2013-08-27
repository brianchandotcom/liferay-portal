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

package com.liferay.portlet.journal.social;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;
import com.liferay.portlet.social.BaseSocialActivityInterpreterTestCase;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityInterpreter;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class JournalArticleActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@Override
	protected void addActivities() throws Exception {
		_article = JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			ServiceTestUtil.randomString(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString(), LocaleUtil.getDefault(), false,
			true);
	}

	@Override
	protected SocialActivityInterpreter getActivityInterpreter() {
		return new JournalArticleActivityInterpreter();
	}

	@Override
	protected int[] getActivityTypes() {
		return new int[] {
			JournalActivityKeys.ADD_ARTICLE, JournalActivityKeys.UPDATE_ARTICLE,
			SocialActivityConstants.TYPE_MOVE_TO_TRASH,
			SocialActivityConstants.TYPE_RESTORE_FROM_TRASH
		};
	}

	@Override
	protected void moveModelsToTrash() throws Exception {
		_article = JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), _article);
	}

	@Override
	protected void renameModels() throws Exception {
		_article = JournalTestUtil.updateArticle(
			_article, ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString());
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		JournalArticleLocalServiceUtil.restoreArticleFromTrash(
			TestPropsValues.getUserId(), _article);
	}

	private JournalArticle _article;

}
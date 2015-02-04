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

package com.liferay.portlet.journal.social;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.util.test.JournalTestUtil;
import com.liferay.portlet.social.model.SocialActivityConstants;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.portlet.social.test.BaseSocialActivityInterpreterTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Zsolt Berentey
 */
@Sync
public class JournalArticleActivityInterpreterTest
	extends BaseSocialActivityInterpreterTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Override
	protected void addActivities() throws Exception {
		_article = JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), LocaleUtil.getDefault(), false,
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
			_article, RandomTestUtil.randomString(), _article.getContent());
	}

	@Override
	protected void restoreModelsFromTrash() throws Exception {
		JournalArticleLocalServiceUtil.restoreArticleFromTrash(
			TestPropsValues.getUserId(), _article);
	}

	private JournalArticle _article;

}
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

package com.liferay.journal.subscriptions.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.subscription.test.util.BaseSubscriptionAuthorTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author José Ángel Jiménez
 */
@RunWith(Arquillian.class)
public class JournalSubscriptionAuthorTest
	extends BaseSubscriptionAuthorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModel(long userId, long containerModelId)
		throws Exception {

		JournalArticle article = JournalTestUtil.addArticle(
			userId, group.getGroupId(), containerModelId);

		return article.getResourcePrimKey();
	}

	@Override
	protected long addContainerModel(long userId, long containerModelId)
		throws Exception {

		JournalFolder folder = JournalTestUtil.addFolder(
			userId, group.getGroupId(), containerModelId,
			RandomTestUtil.randomString());

		return folder.getFolderId();
	}

	@Override
	protected void addSubscription(long userId, long containerModelId)
		throws Exception {

		JournalFolderLocalServiceUtil.subscribe(
			userId, group.getGroupId(), containerModelId);
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		JournalArticle article =
			JournalArticleLocalServiceUtil.getLatestArticle(baseModelId);

		JournalTestUtil.updateArticleWithWorkflow(userId, article, true);
	}

}
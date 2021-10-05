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

package com.liferay.journal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemIdentifier;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Yang Cao
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoItemProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext();
	}

	@Test
	public void testGetInfoItemFromJournalInfoItemProvider() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		JournalArticle updatedArticle = JournalTestUtil.updateArticle(
			article, RandomTestUtil.randomString(), article.getContent(), false,
			false, _serviceContext);

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			article.getResourcePrimKey());

		InfoItemObjectProvider<JournalArticle> journalArticleInfoItemProvider =
			(InfoItemObjectProvider<JournalArticle>)
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					JournalArticle.class.getName(),
					infoItemIdentifier.getInfoItemServiceFilter());

		JournalArticle publishedArticle =
			journalArticleInfoItemProvider.getInfoItem(infoItemIdentifier);

		Assert.assertEquals(article.getTitle(), publishedArticle.getTitle());

		infoItemIdentifier.setVersion(infoItemIdentifier.VERSION_LATEST);

		JournalArticle draftArticle =
			journalArticleInfoItemProvider.getInfoItem(infoItemIdentifier);

		Assert.assertEquals(updatedArticle.getTitle(), draftArticle.getTitle());
	}

	@Test(expected = NoSuchInfoItemException.class)
	public void testGetInvalidInfoItemFromJournalInfoItemProvider()
		throws Exception {

		InfoItemIdentifier infoItemIdentifier = new ClassPKInfoItemIdentifier(
			RandomTestUtil.randomLong());

		InfoItemObjectProvider<JournalArticle> journalArticleInfoItemProvider =
			(InfoItemObjectProvider<JournalArticle>)
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemObjectProvider.class,
					JournalArticle.class.getName(),
					infoItemIdentifier.getInfoItemServiceFilter());

		journalArticleInfoItemProvider.getInfoItem(infoItemIdentifier);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	private ServiceContext _serviceContext;

}
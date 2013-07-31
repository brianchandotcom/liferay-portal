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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolderConstants;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Juan Fernández
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Transactional
public class JournalArticleServiceTest {

	@Before
	public void setUp() {
		FinderCacheUtil.clearCache();
	}

	@Test
	public void testFetchLatestArticleExpiredWithStatusAny() throws Exception {
		testFetchLatestArticle(true, WorkflowConstants.STATUS_ANY);
	}

	@Test
	public void testFetchLatestArticleExpiredWithStatusApproved()
		throws Exception {

		testFetchLatestArticle(true, WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testFetchLatestArticleExpiredWithStatusExpired()
		throws Exception {

		testFetchLatestArticle(true, WorkflowConstants.STATUS_EXPIRED);
	}

	@Test
	public void testFetchLatestArticleNotExpiredWithStatusAny()
		throws Exception {

		testFetchLatestArticle(false, WorkflowConstants.STATUS_ANY);
	}

	@Test
	public void testFetchLatestArticleNotExpiredWithStatusApproved()
		throws Exception {

		testFetchLatestArticle(false, WorkflowConstants.STATUS_APPROVED);
	}

	@Test
	public void testFetchLatestArticleNotExpiredWithStatusExpired()
		throws Exception {

		testFetchLatestArticle(false, WorkflowConstants.STATUS_EXPIRED);
	}

	protected void testFetchLatestArticle(
			boolean expireLatestVersion, int status)
		throws Exception {

		Group group = GroupTestUtil.addGroup();

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Version 1", "This is a test article.");

		Assert.assertTrue(article.isApproved());
		Assert.assertEquals(1.0, article.getVersion(), 0);

		article = JournalTestUtil.updateArticle(
			article, "Version 2", article.getContent());

		Assert.assertTrue(article.isApproved());
		Assert.assertEquals(1.1, article.getVersion(), 0);

		if (expireLatestVersion) {
			article = JournalArticleLocalServiceUtil.expireArticle(
				article.getUserId(), article.getGroupId(),
				article.getArticleId(), 1.1, null,
				ServiceTestUtil.getServiceContext(group.getGroupId()));

			Assert.assertTrue(article.isExpired());
			Assert.assertEquals(1.1, article.getVersion(), 0);
		}

		JournalArticle latestArticle =
			JournalArticleLocalServiceUtil.fetchLatestArticle(
				article.getResourcePrimKey(), status,
				status == WorkflowConstants.STATUS_APPROVED);

		if (expireLatestVersion) {
			if (status == WorkflowConstants.STATUS_APPROVED) {
				Assert.assertNotNull(latestArticle);
				Assert.assertTrue(latestArticle.isApproved());
				Assert.assertEquals(
					"Version 1",
					latestArticle.getTitle(LocaleUtil.getDefault()));
				Assert.assertEquals(1.0, latestArticle.getVersion(), 0);
			}
			else if ((status == WorkflowConstants.STATUS_ANY) ||
					 (status == WorkflowConstants.STATUS_EXPIRED)) {

				Assert.assertNotNull(latestArticle);
				Assert.assertTrue(latestArticle.isExpired());
				Assert.assertEquals(
					"Version 2",
					latestArticle.getTitle(LocaleUtil.getDefault()));
				Assert.assertEquals(1.1, latestArticle.getVersion(), 0);
			}
		}
		else {
			if ((status == WorkflowConstants.STATUS_ANY) ||
				(status == WorkflowConstants.STATUS_APPROVED)) {

				Assert.assertNotNull(latestArticle);
				Assert.assertTrue(latestArticle.isApproved());
				Assert.assertEquals(
					"Version 2",
					latestArticle.getTitle(LocaleUtil.getDefault()));
				Assert.assertEquals(1.1, latestArticle.getVersion(), 0);
			}
			else {
				Assert.assertNull(latestArticle);
			}
		}

		GroupLocalServiceUtil.deleteGroup(group);
	}

}
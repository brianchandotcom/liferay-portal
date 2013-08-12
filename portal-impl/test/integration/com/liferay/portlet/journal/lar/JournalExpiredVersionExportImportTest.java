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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleServiceUtil;
import com.liferay.portlet.journal.util.JournalTestUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Julio Camarero
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
@Transactional
public class JournalExpiredVersionExportImportTest
	extends JournalExportImportTest {

	@Override
	@Test
	public void testExportImportAssetLinks() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

	@Override
	@Test
	public void testExportImportBasicJournalArticle() throws Exception {
		int initialArticlesCount =
			JournalArticleLocalServiceUtil.getArticlesCount(group.getGroupId());

		int initialSearchArticlesCount = JournalTestUtil.getSearchArticlesCount(
			group.getCompanyId(), group.getGroupId());

		JournalArticle article = JournalTestUtil.addArticle(
			group.getGroupId(), ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString());

		Assert.assertEquals(1.0, article.getVersion(), 0);

		article = JournalTestUtil.updateArticle(
			article, ServiceTestUtil.randomString(),
			ServiceTestUtil.randomString());

		Assert.assertEquals(1.1, article.getVersion(), 0);
		Assert.assertEquals(
			initialArticlesCount + 2,
			JournalArticleLocalServiceUtil.getArticlesCount(
				group.getGroupId()));
		Assert.assertEquals(
			initialSearchArticlesCount + 1,
			JournalTestUtil.getSearchArticlesCount(
				group.getCompanyId(), group.getGroupId()));

		doExportImportPortlet(PortletKeys.JOURNAL);

		Assert.assertEquals(
			initialArticlesCount + 2,
			JournalArticleLocalServiceUtil.getArticlesCount(
				importedGroup.getGroupId()));
		Assert.assertEquals(
			initialSearchArticlesCount + 1,
			JournalTestUtil.getSearchArticlesCount(
				importedGroup.getCompanyId(), importedGroup.getGroupId()));

		JournalArticleServiceUtil.expireArticle(
			group.getGroupId(), article.getArticleId(), null,
			ServiceTestUtil.getServiceContext(group.getGroupId()));

		Assert.assertEquals(
			initialSearchArticlesCount,
			JournalTestUtil.getSearchArticlesCount(
				group.getCompanyId(), group.getGroupId()));

		doExportImportPortlet(PortletKeys.JOURNAL);

		Assert.assertEquals(
			initialSearchArticlesCount,
			JournalTestUtil.getSearchArticlesCount(
				importedGroup.getCompanyId(), importedGroup.getGroupId()));
	}

	@Override
	@Test
	public void testExportImportStructuredJournalArticle() throws Exception {
		Assert.assertTrue("This test does not apply", true);
	}

}
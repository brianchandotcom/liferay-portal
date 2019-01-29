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

package com.liferay.journal.service.persistence.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.persistence.JournalFolderFinder;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.spring.orm.LastSessionRecorderHelperUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.TransactionalTestRule;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;

/**
 * @author Zsolt Berentey
 */
@RunWith(Arquillian.class)
public class JournalFolderFinderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			new TransactionalTestRule(
				Propagation.SUPPORTS, "com.liferay.journal.service"));

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_folder1 = JournalTestUtil.addFolder(_group.getGroupId(), "Folder 1");

		_folder2 = JournalTestUtil.addFolder(
			_group.getGroupId(), _folder1.getFolderId(), "Folder 2");

		JournalTestUtil.addArticle(
			_group.getGroupId(), _folder1.getFolderId(), "Article 1",
			StringPool.BLANK);

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), _folder1.getFolderId(), "Article 2",
			StringPool.BLANK);

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), article);

		Bundle bundle = FrameworkUtil.getBundle(getClass());

		_bundleContext = bundle.getBundleContext();

		_serviceReference = _bundleContext.getServiceReference(
			JournalFolderFinder.class);

		_journalFolderFinder = _bundleContext.getService(_serviceReference);
	}

	@After
	public void tearDown() {
		_bundleContext.ungetService(_serviceReference);
	}

	@Test
	public void testCountF_A_ByG_F() throws Exception {
		QueryDefinition<Object> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			3,
			_journalFolderFinder.countF_A_ByG_F(
				_group.getGroupId(), _folder1.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		Assert.assertEquals(
			1,
			_journalFolderFinder.countF_A_ByG_F(
				_group.getGroupId(), _folder1.getFolderId(), queryDefinition));

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		Assert.assertEquals(
			2,
			_journalFolderFinder.countF_A_ByG_F(
				_group.getGroupId(), _folder1.getFolderId(), queryDefinition));
	}

	@Test
	public void testFindF_A_ByG_F() throws Exception {
		QueryDefinition<Object> queryDefinition = new QueryDefinition<>();

		queryDefinition.setStatus(WorkflowConstants.STATUS_ANY);

		List<Object> results = _journalFolderFinder.findF_A_ByG_F(
			_group.getGroupId(), _folder1.getFolderId(), queryDefinition);

		Assert.assertEquals(results.toString(), 3, results.size());

		for (Object result : results) {
			if (result instanceof JournalFolder) {
				JournalFolder folder = (JournalFolder)result;

				Assert.assertEquals("Folder 2", folder.getName());
			}
			else if (result instanceof JournalArticle) {
				JournalArticle article = (JournalArticle)result;

				String title = article.getTitleCurrentValue();

				Assert.assertTrue(
					title,
					title.equals("Article 1") || title.equals("Article 2"));
			}
		}

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH);

		results = _journalFolderFinder.findF_A_ByG_F(
			_group.getGroupId(), _folder1.getFolderId(), queryDefinition);

		Assert.assertEquals(results.toString(), 1, results.size());

		for (Object result : results) {
			if (result instanceof JournalFolder) {
				JournalFolder folder = (JournalFolder)result;

				Assert.assertEquals("Folder 2", folder.getName());
			}
			else if (result instanceof JournalArticle) {
				JournalArticle article = (JournalArticle)result;

				Assert.assertEquals(
					"Article 2", article.getTitleCurrentValue());
			}
		}

		queryDefinition.setStatus(WorkflowConstants.STATUS_IN_TRASH, true);

		results = _journalFolderFinder.findF_A_ByG_F(
			_group.getGroupId(), _folder1.getFolderId(), queryDefinition);

		Assert.assertEquals(results.toString(), 2, results.size());

		for (Object result : results) {
			if (result instanceof JournalFolder) {
				JournalFolder folder = (JournalFolder)result;

				Assert.assertEquals("Folder 2", folder.getName());
			}
			else if (result instanceof JournalArticle) {
				JournalArticle article = (JournalArticle)result;

				Assert.assertEquals(
					"Article 1", article.getTitleCurrentValue());
			}
		}
	}

	@Test
	public void testFindF_ByNoAssets() throws Exception {
		AssetEntryLocalServiceUtil.deleteEntry(
			JournalFolder.class.getName(), _folder2.getFolderId());

		LastSessionRecorderHelperUtil.syncLastSessionState();

		List<JournalFolder> folders = _journalFolderFinder.findF_ByNoAssets();

		Assert.assertEquals(folders.toString(), 1, folders.size());

		JournalFolder folder = folders.get(0);

		Assert.assertEquals(_folder2.getFolderId(), folder.getFolderId());
	}

	private BundleContext _bundleContext;
	private JournalFolder _folder1;
	private JournalFolder _folder2;

	@DeleteAfterTestRun
	private Group _group;

	private JournalFolderFinder _journalFolderFinder;
	private ServiceReference<JournalFolderFinder> _serviceReference;

}
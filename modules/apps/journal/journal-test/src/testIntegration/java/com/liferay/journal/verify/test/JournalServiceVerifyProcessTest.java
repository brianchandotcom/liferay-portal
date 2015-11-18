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

package com.liferay.journal.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.model.Group;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;
import com.liferay.registry.Filter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class JournalServiceVerifyProcessTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() {
		Registry registry = RegistryUtil.getRegistry();

		Filter filter = registry.getFilter(
			"(&(objectClass=" + VerifyProcess.class.getName() +
				")(verify.process.name=com.liferay.journal.service))");

		_serviceTracker = registry.trackServices(filter);

		_serviceTracker.open();
	}

	@AfterClass
	public static void tearDownClass() {
		_serviceTracker.close();
	}

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testJournalArticleTreePathWithJournalArticleInTrash()
		throws Exception {

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(), parentFolder.getFolderId(), "title",
			"content");

		JournalArticleLocalServiceUtil.moveArticleToTrash(
			TestPropsValues.getUserId(), _group.getGroupId(),
			article.getArticleId());

		JournalFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testJournalArticleTreePathWithParentJournalFolderInTrash()
		throws Exception {

		JournalFolder grandparentFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalTestUtil.addArticle(
			_group.getGroupId(), parentFolder.getFolderId(), "title",
			"content");

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testJournalFolderTreePathWithJournalFolderInTrash()
		throws Exception {

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalFolder folder = JournalTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), folder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			parentFolder.getFolderId(), false);

		doVerify();
	}

	@Test
	public void testJournalFolderTreePathWithParentJournalFolderInTrash()
		throws Exception {

		JournalFolder grandparentFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), RandomTestUtil.randomString());

		JournalFolder parentFolder = JournalTestUtil.addFolder(
			_group.getGroupId(), grandparentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalTestUtil.addFolder(
			_group.getGroupId(), parentFolder.getFolderId(),
			RandomTestUtil.randomString());

		JournalFolderLocalServiceUtil.moveFolderToTrash(
			TestPropsValues.getUserId(), parentFolder.getFolderId());

		JournalFolderLocalServiceUtil.deleteFolder(
			grandparentFolder.getFolderId(), false);

		doVerify();
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<VerifyProcess, VerifyProcess> _serviceTracker;

	@DeleteAfterTestRun
	private Group _group;

}
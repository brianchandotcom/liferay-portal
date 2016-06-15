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

package com.liferay.knowledge.base.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.util.comparator.KBObjectsTitleComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class KBFolderLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_user = TestPropsValues.getUser();

		_kbFolder = addKbFolder(KBFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountInKBFolder()
		throws Exception {

		addKBArticle(_kbFolder.getKbFolderId(), RandomTestUtil.randomString());
		addKBArticle(_kbFolder.getKbFolderId(), RandomTestUtil.randomString());

		addKbFolder(_kbFolder.getKbFolderId());

		Assert.assertEquals(
			3,
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), _kbFolder.getKbFolderId(),
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountInRootKBFolder()
		throws Exception {

		addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		Assert.assertEquals(
			2,
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesCountInRootKBFolderByDraftStatus()
		throws Exception {

		addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		Assert.assertEquals(
			1,
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_DRAFT));
	}

	@Test
	public void
			testGetKBFoldersAndKBArticlesCountWithMultipleKBArticleVersions()
		throws Exception {

		KBArticle kbArticle = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		updateKBArticle(kbArticle, RandomTestUtil.randomString());

		Assert.assertEquals(
			2,
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticlesCount(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testGetKBFoldersAndKBArticlesInKBFolder() throws Exception {
		KBArticle kbArticle1 = addKBArticle(_kbFolder.getKbFolderId(), "A");
		KBArticle kbArticle2 = addKBArticle(_kbFolder.getKbFolderId(), "B");

		KBFolder kbFolder = addKbFolder(_kbFolder.getKbFolderId());

		List<Object> kbFolderAndKBArticles =
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticles(
				_group.getGroupId(), _kbFolder.getKbFolderId(),
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS,
				new KBObjectsTitleComparator<KBArticle>(true, true));

		KBFolder currentKBFolder = (KBFolder)kbFolderAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFolderAndKBArticles.get(1);
		KBArticle currentKBArticle2 = (KBArticle)kbFolderAndKBArticles.get(2);

		Assert.assertEquals(
			kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle1.getKbArticleId(), currentKBArticle1.getKbArticleId());
		Assert.assertEquals(
			kbArticle2.getKbArticleId(), currentKBArticle2.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesInRootKBFolder() throws Exception {
		KBArticle kbArticle = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		List<Object> kbFolderAndKBArticles =
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator<>(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFolderAndKBArticles.get(0);
		KBArticle currentKBArticle = (KBArticle)kbFolderAndKBArticles.get(1);

		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			kbArticle.getKbArticleId(), currentKBArticle.getKbArticleId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesInRootKBFolderByDraftStatus()
		throws Exception {

		addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		List<Object> kbFolderAndKBArticles =
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_DRAFT, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator<>(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFolderAndKBArticles.get(0);

		Assert.assertEquals(1, kbFolderAndKBArticles.size());
		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
	}

	@Test
	public void testGetKBFoldersAndKBArticlesWithMultipleKBArticleVersions()
		throws Exception {

		KBArticle kbArticle = addKBArticle(
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

		KBArticle newKBArticle = updateKBArticle(
			kbArticle, RandomTestUtil.randomString());

		List<Object> kbFolderAndKBArticles =
			KBFolderLocalServiceUtil.getKBFoldersAndKBArticles(
				_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new KBObjectsTitleComparator<>(false, true));

		KBFolder currentKBFolder = (KBFolder)kbFolderAndKBArticles.get(0);
		KBArticle currentKBArticle1 = (KBArticle)kbFolderAndKBArticles.get(1);

		Assert.assertEquals(2, kbFolderAndKBArticles.size());
		Assert.assertEquals(
			_kbFolder.getKbFolderId(), currentKBFolder.getKbFolderId());
		Assert.assertEquals(
			newKBArticle.getKbArticleId(), currentKBArticle1.getKbArticleId());
	}

	protected KBArticle addKBArticle(long parentKbFolderId, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		return KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			parentKbFolderId, title, title, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), null, new String[0], new String[0],
			serviceContext);
	}

	protected KBFolder addKbFolder(long parentResourcePrimKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		return KBFolderLocalServiceUtil.addKBFolder(
			_user.getUserId(), _group.getGroupId(),
			PortalUtil.getClassNameId(KBFolderConstants.getClassName()),
			parentResourcePrimKey, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	protected KBArticle updateKBArticle(KBArticle kbArticle, String title)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group, _user.getUserId());

		return KBArticleLocalServiceUtil.updateKBArticle(
			kbArticle.getUserId(), kbArticle.getResourcePrimKey(), title,
			kbArticle.getContent(), kbArticle.getDescription(),
			kbArticle.getSourceURL(), null, new String[0], new long[0],
			serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	private KBFolder _kbFolder;
	private User _user;

}
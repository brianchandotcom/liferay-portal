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
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.knowledge.base.constants.KBArticleConstants;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.KBArticleContentException;
import com.liferay.knowledge.base.exception.KBArticleParentException;
import com.liferay.knowledge.base.exception.KBArticleSourceURLException;
import com.liferay.knowledge.base.exception.KBArticleTitleException;
import com.liferay.knowledge.base.exception.KBArticleUrlTitleException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalServiceUtil;
import com.liferay.knowledge.base.service.KBCommentLocalServiceUtil;
import com.liferay.knowledge.base.service.KBFolderLocalServiceUtil;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
@Sync
public class KBArticleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_kbArticleClassNameId = ClassNameLocalServiceUtil.getClassNameId(
			KBArticleConstants.getClassName());
		_kbFolderClassNameId = ClassNameLocalServiceUtil.getClassNameId(
			KBFolderConstants.getClassName());

		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _user.getUserId());
	}

	@Test(expected = KBArticleContentException.class)
	public void testAddKBArticleWithBlankContent() throws Exception {
		String content = StringPool.BLANK;

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(), content,
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			_serviceContext);
	}

	@Test
	public void testAddKBArticleWithBlankSourceURL() throws Exception {
		String sourceURL = StringPool.BLANK;

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), sourceURL,
			null, null, _serviceContext);

		Assert.assertTrue(Validator.isNull(kbArticle.getSourceURL()));
	}

	@Test(expected = KBArticleTitleException.class)
	public void testAddKBArticleWithBlankTitle() throws Exception {
		String title = StringPool.BLANK;

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			_serviceContext);
	}

	@Test(expected = KBArticleUrlTitleException.class)
	public void testAddKBArticleWithBlankURLTitle() throws Exception {
		String urlTitle = StringPool.BLANK;

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), urlTitle, StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		Assert.assertTrue(Validator.isNotNull(kbArticle.getUrlTitle()));
	}

	@Test(expected = KBArticleUrlTitleException.class)
	public void testAddKBArticleWithDuplicateURLTitle() throws Exception {
		String urlTitle = StringUtil.randomString();

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), urlTitle, StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), urlTitle, StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);
	}

	@Test(expected = KBArticleParentException.class)
	public void testAddKBArticleWithInvalidParentClassNameId()
		throws Exception {

		long invalidParentClassNameId = 123456789L;

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), invalidParentClassNameId,
			RandomTestUtil.nextLong(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);
	}

	@Test(expected = KBArticleSourceURLException.class)
	public void testAddKBArticleWithInvalidSourceURL() throws Exception {
		String sourceURL = "InvalidURL";

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), sourceURL,
			null, null, _serviceContext);
	}

	@Test(expected = KBArticleUrlTitleException.class)
	public void testAddKBArticleWithInvalidURLTitle() throws Exception {
		String invalidURLTitle = "#$%&/(";

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), invalidURLTitle,
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);
	}

	@Test(expected = KBArticleUrlTitleException.class)
	public void testAddKBArticleWithLargeURLTitle() throws Exception {
		int urlTitleMaxSize = ModelHintsUtil.getMaxLength(
			KBArticle.class.getName(), "urlTitle");

		String invalidURLTitle = StringUtil.randomString(urlTitleMaxSize);

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), invalidURLTitle,
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);
	}

	@Test(expected = KBArticleContentException.class)
	public void testAddKBArticleWithNullContent() throws Exception {
		String content = null;

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(), content,
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			_serviceContext);
	}

	@Test
	public void testAddKBArticleWithNullSourceURL() throws Exception {
		String sourceURL = null;

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), sourceURL,
			null, null, _serviceContext);

		Assert.assertTrue(Validator.isNull(kbArticle.getSourceURL()));
	}

	@Test(expected = KBArticleTitleException.class)
	public void testAddKBArticleWithNullTitle() throws Exception {
		String title = null;

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, title,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			_serviceContext);
	}

	@Test
	public void testAddKBArticleWithNullURLTitle() throws Exception {
		String urlTitle = null;

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), urlTitle, StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		Assert.assertTrue(Validator.isNotNull(kbArticle.getUrlTitle()));
	}

	@Test
	public void testAddKBArticleWithValidParentKBArticle() throws Exception {
		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			kbArticle.getResourcePrimKey(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);
	}

	@Test
	public void testAddKBArticleWithValidParentKBFolder() throws Exception {
		KBFolder kbFolder = KBFolderLocalServiceUtil.addKBFolder(
			_user.getUserId(), _group.getGroupId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			_serviceContext);

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId, kbFolder.getPrimaryKey(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);
	}

	@Test
	public void testAddKBArticleWithValidSourceURL() throws Exception {
		String sourceURL = "http://www.liferay.com";

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), sourceURL,
			null, null, _serviceContext);
	}

	@Test
	public void testDeleteGroupKBArticlesDeletesKBArticles() throws Exception {
		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		Assert.assertEquals(
			1,
			KBArticleLocalServiceUtil.getGroupKBArticlesCount(
				_group.getGroupId(), WorkflowConstants.STATUS_ANY));

		KBArticleLocalServiceUtil.deleteGroupKBArticles(_group.getGroupId());

		Assert.assertEquals(
			0,
			KBArticleLocalServiceUtil.getGroupKBArticlesCount(
				_group.getGroupId(), WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testDeleteGroupKBArticlesDeletesSubscriptions()
		throws Exception {

		KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		int subscriptionsCount =
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId());

		KBArticleLocalServiceUtil.subscribeGroupKBArticles(
			_user.getUserId(), _group.getGroupId());

		Assert.assertEquals(
			subscriptionsCount + 1,
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId()));

		KBArticleLocalServiceUtil.deleteGroupKBArticles(_group.getGroupId());

		Assert.assertEquals(
			subscriptionsCount,
			SubscriptionLocalServiceUtil.getUserSubscriptionsCount(
				_user.getUserId()));
	}

	@Test
	public void testDeleteKBArticleDeletesAssetEntry() throws Exception {
		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		Assert.assertNotNull(
			AssetEntryLocalServiceUtil.fetchEntry(
				KBArticleConstants.getClassName(), kbArticle.getClassPK()));

		KBArticleLocalServiceUtil.deleteKBArticle(kbArticle);

		Assert.assertNull(
			AssetEntryLocalServiceUtil.fetchEntry(
				KBArticleConstants.getClassName(), kbArticle.getClassPK()));
	}

	@Test
	public void testDeleteKBArticleDeletesChildKBArticles() throws Exception {
		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBArticle childKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), kbArticle.getClassNameId(),
			kbArticle.getResourcePrimKey(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticleLocalServiceUtil.deleteKBArticle(kbArticle);

		Assert.assertNull(
			KBArticleLocalServiceUtil.fetchKBArticle(
				childKBArticle.getKbArticleId()));
	}

	@Test
	public void testDeleteKBArticleDeletesKBComments() throws Exception {
		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBCommentLocalServiceUtil.addKBComment(
			_user.getUserId(), kbArticle.getClassNameId(),
			kbArticle.getClassPK(), StringUtil.randomString(), _serviceContext);

		KBArticleLocalServiceUtil.deleteKBArticle(kbArticle);

		Assert.assertEquals(
			0,
			KBCommentLocalServiceUtil.getKBCommentsCount(
				KBArticleConstants.getClassName(), kbArticle.getClassPK()));
	}

	@Test
	public void testDeleteKBArticleDeletesRatings() throws Exception {
		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user.getUserId(), KBArticleConstants.getClassName(),
			kbArticle.getClassPK(), 1.0, _serviceContext);

		KBArticleLocalServiceUtil.deleteKBArticle(kbArticle);

		Assert.assertNull(
			RatingsStatsLocalServiceUtil.fetchStats(
				KBArticleConstants.getClassName(), kbArticle.getClassPK()));
	}

	@Test
	public void testGetAllDescendantKBArticles() throws Exception {
		KBArticle parentKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Parent Article",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle childKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			parentKBArticle.getResourcePrimKey(), "Child Article",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle grandchildKBArticleA = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			childKBArticle.getResourcePrimKey(), "Grandchild Article A",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle greatGrandchildKBArticleA =
			KBArticleLocalServiceUtil.addKBArticle(
				_user.getUserId(), _kbArticleClassNameId,
				grandchildKBArticleA.getResourcePrimKey(),
				"GreatGrandchild Article A", StringUtil.randomString(),
				StringUtil.randomString(), StringUtil.randomString(), null,
				null, null, _serviceContext);

		KBArticle grandchildKBArticleB = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			childKBArticle.getResourcePrimKey(), "Grandchild Article B",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle greatGrandchildKBArticleB =
			KBArticleLocalServiceUtil.addKBArticle(
				_user.getUserId(), _kbArticleClassNameId,
				grandchildKBArticleB.getResourcePrimKey(),
				"GreatGrandchild Article B", StringUtil.randomString(),
				StringUtil.randomString(), StringUtil.randomString(), null,
				null, null, _serviceContext);

		List<KBArticle> kbArticleAndAllDescendantKBArticles =
			KBArticleLocalServiceUtil.getAllDescendantKBArticles(
				parentKBArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED,
				new KBArticlePriorityComparator(true));

		Assert.assertEquals(5, kbArticleAndAllDescendantKBArticles.size());

		KBArticle currentChildKBArticle =
			kbArticleAndAllDescendantKBArticles.get(0);
		KBArticle currentGrandchildKBArticleA =
			kbArticleAndAllDescendantKBArticles.get(1);
		KBArticle currentGreatGrandchildKBArticleA =
			kbArticleAndAllDescendantKBArticles.get(2);
		KBArticle currentGrandchildKBArticleB =
			kbArticleAndAllDescendantKBArticles.get(3);
		KBArticle currentGreatGrandchildKBArticleB =
			kbArticleAndAllDescendantKBArticles.get(4);

		Assert.assertEquals(
			childKBArticle.getResourcePrimKey(),
			currentChildKBArticle.getResourcePrimKey());
		Assert.assertEquals(
			grandchildKBArticleA.getResourcePrimKey(),
			currentGrandchildKBArticleA.getResourcePrimKey());
		Assert.assertEquals(
			greatGrandchildKBArticleA.getResourcePrimKey(),
			currentGreatGrandchildKBArticleA.getResourcePrimKey());
		Assert.assertEquals(
			grandchildKBArticleB.getResourcePrimKey(),
			currentGrandchildKBArticleB.getResourcePrimKey());
		Assert.assertEquals(
			greatGrandchildKBArticleB.getResourcePrimKey(),
			currentGreatGrandchildKBArticleB.getResourcePrimKey());
	}

	@Test
	public void testGetKBArticleAndAllDescendantKBArticles() throws Exception {
		KBArticle parentKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Parent Article",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle childKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			parentKBArticle.getResourcePrimKey(), "Child Article",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle grandchildKBArticleA = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			childKBArticle.getResourcePrimKey(), "Grandchild Article A",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle greatGrandchildKBArticleA =
			KBArticleLocalServiceUtil.addKBArticle(
				_user.getUserId(), _kbArticleClassNameId,
				grandchildKBArticleA.getResourcePrimKey(),
				"GreatGrandchild Article A", StringUtil.randomString(),
				StringUtil.randomString(), StringUtil.randomString(), null,
				null, null, _serviceContext);

		KBArticle grandchildKBArticleB = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			childKBArticle.getResourcePrimKey(), "Grandchild Article B",
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle greatGrandchildKBArticleB =
			KBArticleLocalServiceUtil.addKBArticle(
				_user.getUserId(), _kbArticleClassNameId,
				grandchildKBArticleB.getResourcePrimKey(),
				"GreatGrandchild Article B", StringUtil.randomString(),
				StringUtil.randomString(), StringUtil.randomString(), null,
				null, null, _serviceContext);

		List<KBArticle> kbArticleAndAllDescendantKBArticles =
			KBArticleLocalServiceUtil.getKBArticleAndAllDescendantKBArticles(
				parentKBArticle.getResourcePrimKey(),
				WorkflowConstants.STATUS_APPROVED,
				new KBArticlePriorityComparator(true));

		Assert.assertEquals(6, kbArticleAndAllDescendantKBArticles.size());

		KBArticle currentParentKBArticle =
			kbArticleAndAllDescendantKBArticles.get(0);
		KBArticle currentChildKBArticle =
			kbArticleAndAllDescendantKBArticles.get(1);
		KBArticle currentGrandchildKBArticleA =
			kbArticleAndAllDescendantKBArticles.get(2);
		KBArticle currentGreatGrandchildKBArticleA =
			kbArticleAndAllDescendantKBArticles.get(3);
		KBArticle currentGrandchildKBArticleB =
			kbArticleAndAllDescendantKBArticles.get(4);
		KBArticle currentGreatGrandchildKBArticleB =
			kbArticleAndAllDescendantKBArticles.get(5);

		Assert.assertEquals(
			parentKBArticle.getResourcePrimKey(),
			currentParentKBArticle.getResourcePrimKey());
		Assert.assertEquals(
			childKBArticle.getResourcePrimKey(),
			currentChildKBArticle.getResourcePrimKey());
		Assert.assertEquals(
			grandchildKBArticleA.getResourcePrimKey(),
			currentGrandchildKBArticleA.getResourcePrimKey());
		Assert.assertEquals(
			greatGrandchildKBArticleA.getResourcePrimKey(),
			currentGreatGrandchildKBArticleA.getResourcePrimKey());
		Assert.assertEquals(
			grandchildKBArticleB.getResourcePrimKey(),
			currentGrandchildKBArticleB.getResourcePrimKey());
		Assert.assertEquals(
			greatGrandchildKBArticleB.getResourcePrimKey(),
			currentGreatGrandchildKBArticleB.getResourcePrimKey());
	}

	@Test(expected = KBArticleParentException.class)
	public void testMoveKBArticleToInvalidParentKBArticle() throws Exception {
		KBArticle parentKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBArticle childKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			parentKBArticle.getResourcePrimKey(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticle grandChildKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbArticleClassNameId,
			childKBArticle.getResourcePrimKey(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, null, _serviceContext);

		KBArticleLocalServiceUtil.moveKBArticle(
			_user.getUserId(), parentKBArticle.getResourcePrimKey(),
			_kbArticleClassNameId, grandChildKBArticle.getResourcePrimKey(),
			grandChildKBArticle.getPriority());
	}

	@Test
	public void testMoveKBArticleToParentKBArticleInHomeFolder()
		throws Exception {

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBArticle parentKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBArticleLocalServiceUtil.moveKBArticle(
			_user.getUserId(), kbArticle.getResourcePrimKey(),
			_kbArticleClassNameId, parentKBArticle.getResourcePrimKey(),
			parentKBArticle.getPriority());

		kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			_kbArticleClassNameId, kbArticle.getParentResourceClassNameId());
		Assert.assertEquals(
			parentKBArticle.getResourcePrimKey(),
			kbArticle.getParentResourcePrimKey());
	}

	@Test
	public void testMoveKBArticleToParentKBArticleInKBFolder()
		throws Exception {

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBFolder kbFolder = KBFolderLocalServiceUtil.addKBFolder(
			_user.getUserId(), _group.getGroupId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			_serviceContext);

		KBArticle parentKBArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId, kbFolder.getKbFolderId(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBArticleLocalServiceUtil.moveKBArticle(
			_user.getUserId(), kbArticle.getResourcePrimKey(),
			_kbArticleClassNameId, parentKBArticle.getResourcePrimKey(),
			parentKBArticle.getPriority());

		kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			_kbArticleClassNameId, kbArticle.getParentResourceClassNameId());
		Assert.assertEquals(
			parentKBArticle.getResourcePrimKey(),
			kbArticle.getParentResourcePrimKey());
		Assert.assertEquals(
			kbFolder.getKbFolderId(), kbArticle.getKbFolderId());
	}

	@Test
	public void testMoveKBArticleToParentKBFolderInHomeFolder()
		throws Exception {

		KBArticle kbArticle = KBArticleLocalServiceUtil.addKBArticle(
			_user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			null, _serviceContext);

		KBFolder parentKBFolder = KBFolderLocalServiceUtil.addKBFolder(
			_user.getUserId(), _group.getGroupId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			_serviceContext);

		KBArticleLocalServiceUtil.moveKBArticle(
			_user.getUserId(), kbArticle.getResourcePrimKey(),
			_kbFolderClassNameId, parentKBFolder.getKbFolderId(),
			kbArticle.getPriority());

		kbArticle = KBArticleLocalServiceUtil.getLatestKBArticle(
			kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			_kbFolderClassNameId, kbArticle.getParentResourceClassNameId());
		Assert.assertEquals(
			parentKBFolder.getKbFolderId(),
			kbArticle.getParentResourcePrimKey());
	}

	@DeleteAfterTestRun
	private Group _group;

	private long _kbArticleClassNameId;
	private long _kbFolderClassNameId;
	private ServiceContext _serviceContext;
	private User _user;

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.knowledge.base.service.KBArticleService;
import com.liferay.knowledge.base.util.comparator.KBArticlePriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@RunWith(Arquillian.class)
public class KBArticleServiceTest {

	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_kbFolderClassNameId = ClassNameLocalServiceUtil.getClassNameId(
			KBFolderConstants.getClassName());

		_originalName = PrincipalThreadLocal.getName();

		_user = TestPropsValues.getUser();

		PrincipalThreadLocal.setName(_user.getUserId());

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _user.getUserId());

		_testPortletId = "TEST_PORTLET_" + RandomTestUtil.randomString();
	}

	@After
	public void tearDown() {
		PrincipalThreadLocal.setName(_originalName);
	}

	@FeatureFlags("LPD-11003")
	@Test
	public void testForceLockKBArticle() throws Exception {
		KBArticle kbArticle = _addKbArticle(new Date());
		User otherUser = UserTestUtil.addUser(_group.getGroupId());

		try {
			_kbArticleLocalService.lockKBArticle(
				otherUser.getUserId(), kbArticle.getResourcePrimKey());

			Assert.assertTrue(
				_kbArticleLocalService.hasKBArticleLock(
					otherUser.getUserId(), kbArticle.getResourcePrimKey()));
			Assert.assertFalse(
				_kbArticleLocalService.hasKBArticleLock(
					_user.getUserId(), kbArticle.getResourcePrimKey()));

			_kbArticleService.forceLockKBArticle(
				_group.getGroupId(), kbArticle.getResourcePrimKey());

			Assert.assertFalse(
				_kbArticleLocalService.hasKBArticleLock(
					otherUser.getUserId(), kbArticle.getResourcePrimKey()));
			Assert.assertTrue(
				_kbArticleLocalService.hasKBArticleLock(
					_user.getUserId(), kbArticle.getResourcePrimKey()));
		}
		finally {
			_kbArticleService.deleteKBArticle(kbArticle.getResourcePrimKey());
		}
	}

	@Test
	public void testGetKBArticlesByStatus() throws PortalException {
		_serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		_addKbArticle(new Date());
		_addKbArticle(new Date(System.currentTimeMillis() + (2 * Time.DAY)));

		KBArticle kbArticle = _addKbArticle(new Date());

		List<KBArticle> kbArticles = _kbArticleService.getKBArticles(
			_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, KBArticlePriorityComparator.getInstance(true));

		Assert.assertEquals(kbArticles.toString(), 2, kbArticles.size());

		kbArticles = _kbArticleService.getKBArticles(
			_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			WorkflowConstants.STATUS_ANY, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			KBArticlePriorityComparator.getInstance(true));

		Assert.assertEquals(kbArticles.toString(), 3, kbArticles.size());

		_kbArticleService.expireKBArticle(
			kbArticle.getResourcePrimKey(), _serviceContext);

		kbArticles = _kbArticleService.getKBArticles(
			_group.getGroupId(), KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, KBArticlePriorityComparator.getInstance(true));

		Assert.assertEquals(kbArticles.toString(), 1, kbArticles.size());
	}

	private KBArticle _addKbArticle(Date displayDate) throws PortalException {
		return _kbArticleService.addKBArticle(
			null, _testPortletId, _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			displayDate, null, null, null, _serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private KBArticleLocalService _kbArticleLocalService;

	@Inject
	private KBArticleService _kbArticleService;

	private long _kbFolderClassNameId;
	private String _originalName;
	private ServiceContext _serviceContext;
	private String _testPortletId;
	private User _user;

}
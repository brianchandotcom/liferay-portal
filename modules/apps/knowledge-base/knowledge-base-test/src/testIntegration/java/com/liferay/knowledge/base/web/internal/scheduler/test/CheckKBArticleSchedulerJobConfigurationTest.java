/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.web.internal.scheduler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.model.KBFolder;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@FeatureFlags("LPS-188060")
@RunWith(Arquillian.class)
@Sync
public class CheckKBArticleSchedulerJobConfigurationTest {

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

		UserTestUtil.setUser(TestPropsValues.getUser());
	}

	@Test
	public void testDoNotExpireKBArticleIfKBArticleIsScheduled()
		throws Exception {

		Date displayDate = DateUtils.addDays(RandomTestUtil.nextDate(), 1);

		Date expirationDate = DateUtils.addDays(displayDate, 1);

		KBArticle kbArticle = _kbArticleLocalService.addKBArticle(
			null, UserLocalServiceUtil.getGuestUserId(_group.getCompanyId()),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, displayDate, expirationDate, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		kbArticle.setExpirationDate(
			new Date(System.currentTimeMillis() - (Time.MINUTE * 10)));

		kbArticle = _kbArticleLocalService.updateKBArticle(kbArticle);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		kbArticle = _kbArticleLocalService.getLatestKBArticle(
			kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);

		Assert.assertNotEquals(
			WorkflowConstants.STATUS_EXPIRED, kbArticle.getStatus());
	}

	@Test
	public void testExpireKBArticle() throws Exception {
		Date expirationDate = new Date(
			System.currentTimeMillis() + (Time.MINUTE * 5));

		KBArticle kbArticle = _kbArticleLocalService.addKBArticle(
			null, UserLocalServiceUtil.getGuestUserId(_group.getCompanyId()),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, new Date(), expirationDate, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		kbArticle.setExpirationDate(
			new Date(System.currentTimeMillis() - (Time.MINUTE * 10)));

		kbArticle = _kbArticleLocalService.updateKBArticle(kbArticle);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		kbArticle = _kbArticleLocalService.getLatestKBArticle(
			kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			WorkflowConstants.STATUS_EXPIRED, kbArticle.getStatus());
	}

	@Test
	public void testPublishKBArticleIfKBArticleIsScheduled() throws Exception {
		Date displayDate = new Date(
			System.currentTimeMillis() + (Time.MINUTE * 10));

		KBArticle kbArticle = _kbArticleLocalService.addKBArticle(
			null, UserLocalServiceUtil.getGuestUserId(_group.getCompanyId()),
			PortalUtil.getClassNameId(KBFolder.class.getName()), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(), null,
			null, displayDate, null, null, null,
			ServiceContextTestUtil.getServiceContext(
				_group, _user.getUserId()));

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, kbArticle.getStatus());

		kbArticle.setDisplayDate(
			new Date(System.currentTimeMillis() - (Time.MINUTE * 10)));

		kbArticle = _kbArticleLocalService.updateKBArticle(kbArticle);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		kbArticle = _kbArticleLocalService.getLatestKBArticle(
			kbArticle.getResourcePrimKey(), WorkflowConstants.STATUS_ANY);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, kbArticle.getStatus());
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private KBArticleLocalService _kbArticleLocalService;

	private User _user;

}
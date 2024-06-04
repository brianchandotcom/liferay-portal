/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.knowledge.base.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.knowledge.base.constants.KBFolderConstants;
import com.liferay.knowledge.base.exception.KBArticleStatusException;
import com.liferay.knowledge.base.model.KBArticle;
import com.liferay.knowledge.base.service.KBArticleLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Date;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alicia García
 */
@FeatureFlags("LPS-188058")
@RunWith(Arquillian.class)
public class KBArticleScheduledTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
		_kbFolderClassNameId = _classNameLocalService.getClassNameId(
			KBFolderConstants.getClassName());

		_user = TestPropsValues.getUser();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group, _user.getUserId());
	}

	@Test
	public void testChildArticleScheduledAfterParentCanBePublishedOnItsScheduledDate()
		throws Exception {

		Date displayDate = new Date(
			System.currentTimeMillis() + (2 * Time.DAY));

		KBArticle parentKBArticle = _addKbArticle(displayDate);

		KBArticle childKBArticle = _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(), parentKBArticle.getClassNameId(),
			parentKBArticle.getResourcePrimKey(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, displayDate, null, null,
			null, _serviceContext);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		childKBArticle = _kbArticleLocalService.fetchKBArticle(
			childKBArticle.getKbArticleId());
		parentKBArticle = _kbArticleLocalService.fetchKBArticle(
			parentKBArticle.getKbArticleId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, childKBArticle.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, parentKBArticle.getStatus());

		parentKBArticle.setDisplayDate(new Date());

		parentKBArticle = _kbArticleLocalService.updateKBArticle(
			parentKBArticle);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		childKBArticle = _kbArticleLocalService.fetchKBArticle(
			childKBArticle.getKbArticleId());
		parentKBArticle = _kbArticleLocalService.fetchKBArticle(
			parentKBArticle.getKbArticleId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, childKBArticle.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, parentKBArticle.getStatus());

		childKBArticle.setDisplayDate(new Date());

		childKBArticle = _kbArticleLocalService.updateKBArticle(childKBArticle);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		childKBArticle = _kbArticleLocalService.fetchKBArticle(
			childKBArticle.getKbArticleId());
		parentKBArticle = _kbArticleLocalService.fetchKBArticle(
			parentKBArticle.getKbArticleId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, childKBArticle.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, parentKBArticle.getStatus());
	}

	@Test
	public void testChildArticleScheduledBeforeParentCanBePublishedWhenParentIsPublished()
		throws Exception {

		Date displayDate = new Date(
			System.currentTimeMillis() + (2 * Time.DAY));

		KBArticle parentKBArticle = _addKbArticle(displayDate);

		KBArticle childKBArticle = _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(), parentKBArticle.getClassNameId(),
			parentKBArticle.getResourcePrimKey(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, displayDate, null, null,
			null, _serviceContext);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		childKBArticle = _kbArticleLocalService.fetchKBArticle(
			childKBArticle.getKbArticleId());
		parentKBArticle = _kbArticleLocalService.fetchKBArticle(
			parentKBArticle.getKbArticleId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, childKBArticle.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, parentKBArticle.getStatus());

		displayDate = new Date(System.currentTimeMillis() - (2 * Time.MINUTE));

		childKBArticle.setDisplayDate(displayDate);

		childKBArticle = _kbArticleLocalService.updateKBArticle(childKBArticle);

		Assert.assertThrows(
			KBArticleStatusException.class,
			() -> _kbArticleLocalService.checkKBArticles(
				_group.getCompanyId()));

		childKBArticle = _kbArticleLocalService.fetchKBArticle(
			childKBArticle.getKbArticleId());
		parentKBArticle = _kbArticleLocalService.fetchKBArticle(
			parentKBArticle.getKbArticleId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, childKBArticle.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, parentKBArticle.getStatus());

		parentKBArticle.setDisplayDate(displayDate);

		parentKBArticle = _kbArticleLocalService.updateKBArticle(
			parentKBArticle);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		childKBArticle = _kbArticleLocalService.fetchKBArticle(
			childKBArticle.getKbArticleId());
		parentKBArticle = _kbArticleLocalService.fetchKBArticle(
			parentKBArticle.getKbArticleId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, childKBArticle.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, parentKBArticle.getStatus());
	}

	@Test(expected = KBArticleStatusException.class)
	public void testChildArticleScheduledFailsIfScheduledBeforeParent()
		throws Exception {

		Date displayDate = new Date(
			System.currentTimeMillis() + (2 * Time.DAY));

		KBArticle parentKBArticle = _addKbArticle(displayDate);

		KBArticle childKBArticle = _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(), parentKBArticle.getClassNameId(),
			parentKBArticle.getResourcePrimKey(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), null, null, displayDate, null, null,
			null, _serviceContext);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());

		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, parentKBArticle.getStatus());
		Assert.assertEquals(
			WorkflowConstants.STATUS_SCHEDULED, childKBArticle.getStatus());

		childKBArticle.setDisplayDate(new Date());

		_kbArticleLocalService.updateKBArticle(childKBArticle);

		_kbArticleLocalService.checkKBArticles(_group.getCompanyId());
	}

	private KBArticle _addKbArticle(Date displayDate) throws Exception {
		return _kbArticleLocalService.addKBArticle(
			null, _user.getUserId(), _kbFolderClassNameId,
			KBFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(), null, null,
			displayDate, null, null, null, _serviceContext);
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private KBArticleLocalService _kbArticleLocalService;

	private long _kbFolderClassNameId;
	private ServiceContext _serviceContext;
	private User _user;

}
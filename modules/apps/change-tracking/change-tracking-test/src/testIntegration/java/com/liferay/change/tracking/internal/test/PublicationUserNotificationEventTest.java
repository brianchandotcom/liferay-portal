/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTCollectionService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.change.tracking.CTCollectionThreadLocal;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
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
 * @author Gislayne Vitorino
 */
@RunWith(Arquillian.class)
public class PublicationUserNotificationEventTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testFailedPublishCreatesUserNotificationEvent()
		throws Exception {

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), RandomTestUtil.randomString(),
			StringPool.BLANK);

		CTCollection ctCollection = _ctCollectionLocalService.addCTCollection(
			null, TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
			0, RandomTestUtil.randomString(), null);

		try (SafeCloseable safeCloseable =
				CTCollectionThreadLocal.setCTCollectionIdWithSafeCloseable(
					ctCollection.getCtCollectionId())) {

			journalArticle = _journalArticleLocalService.updateArticle(
				journalArticle.getId(), RandomTestUtil.randomString());
		}

		try (SafeCloseable safeCloseable2 =
				CTCollectionThreadLocal.setProductionModeWithSafeCloseable()) {

			_journalArticleLocalService.deleteArticle(
				_group.getGroupId(), journalArticle.getArticleId(),
				ServiceContextTestUtil.getServiceContext());
		}

		try {
			_ctCollectionService.publishCTCollection(
				TestPropsValues.getUserId(), ctCollection.getCtCollectionId());
		}
		catch (SystemException systemException) {
			Assert.assertEquals(
				"Unable to publish " + journalArticle.getTitle() +
					"to Production because of unresolved conflicts",
				systemException.getMessage());
		}
	}

	@Inject
	private static CTCollectionLocalService _ctCollectionLocalService;

	@Inject
	private static CTCollectionService _ctCollectionService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}
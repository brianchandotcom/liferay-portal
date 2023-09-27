/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.wiki.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.subscription.test.util.BaseSubscriptionContainerModelTestCase;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.test.util.WikiTestUtil;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class WikiSubscriptionContainerModelTest
	extends BaseSubscriptionContainerModelTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	@Test
	public void testSubscriptionContainerModelWhenAddingBaseModelInRootContainerModel() {
	}

	@Override
	@Test
	public void testSubscriptionContainerModelWhenAddingBaseModelInSubcontainerModel() {
	}

	@Override
	@Test
	public void testSubscriptionContainerModelWhenUpdatingBaseModelInRootContainerModel() {
	}

	@Override
	@Test
	public void testSubscriptionContainerModelWhenUpdatingBaseModelInSubcontainerModel() {
	}

	@Override
	protected long addBaseModel(long userId, long containerModelId)
		throws Exception {

		WikiPage page = WikiTestUtil.addPage(
			userId, group.getGroupId(), containerModelId,
			RandomTestUtil.randomString(), true);

		return page.getResourcePrimKey();
	}

	@Override
	protected long addContainerModel(long userId, long containerModelId)
		throws Exception {

		WikiNode node = WikiTestUtil.addNode(
			userId, group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		return node.getNodeId();
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		WikiNodeLocalServiceUtil.subscribeNode(
			user.getUserId(), containerModelId);
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId, true);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(page.getGroupId(), userId);

		WikiTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.UPDATE);

		WikiTestUtil.updatePage(
			page, userId, page.getTitle(), RandomTestUtil.randomString(), true,
			serviceContext);
	}

}
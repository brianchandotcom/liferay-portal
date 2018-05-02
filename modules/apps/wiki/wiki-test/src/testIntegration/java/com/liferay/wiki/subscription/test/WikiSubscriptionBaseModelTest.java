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

package com.liferay.wiki.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portlet.subscriptions.test.BaseSubscriptionBaseModelTestCase;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.util.test.WikiTestUtil;

import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class WikiSubscriptionBaseModelTest
	extends BaseSubscriptionBaseModelTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Ignore
	@Override
	@Test
	public void testSubscriptionBaseModelWhenInRootContainerModel() {
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

		_node = WikiTestUtil.addNode(
			userId, group.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(50));

		return _node.getNodeId();
	}

	@Override
	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId);

		WikiPageLocalServiceUtil.subscribePage(
			user.getUserId(), page.getNodeId(), page.getTitle());
	}

	@Override
	protected void removeContainerModelResourceViewPermission()
		throws Exception {

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_node.getNodeId()), ActionKeys.VIEW);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.SITE_MEMBER, WikiNode.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_node.getNodeId()), ActionKeys.VIEW);
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

	private WikiNode _node;

}
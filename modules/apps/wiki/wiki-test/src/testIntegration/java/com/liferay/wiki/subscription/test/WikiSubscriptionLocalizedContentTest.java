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
import com.liferay.subscription.test.util.BaseSubscriptionLocalizedContentTestCase;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.model.WikiNode;
import com.liferay.wiki.model.WikiPage;
import com.liferay.wiki.service.WikiNodeLocalServiceUtil;
import com.liferay.wiki.service.WikiPageLocalServiceUtil;
import com.liferay.wiki.test.util.WikiTestUtil;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class WikiSubscriptionLocalizedContentTest
	extends BaseSubscriptionLocalizedContentTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_node = WikiTestUtil.addNode(group.getGroupId());
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
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		WikiNodeLocalServiceUtil.subscribeNode(
			user.getUserId(), containerModelId);
	}

	@Override
	protected long getDefaultContainerModelId() {
		return _node.getNodeId();
	}

	@Override
	protected String getPortletId() {
		return WikiPortletKeys.WIKI;
	}

	@Override
	protected String getServiceName() {
		return WikiConstants.SERVICE_NAME;
	}

	@Override
	protected String getSubscriptionAddedBodyPreferenceName() {
		return "emailPageAddedBody";
	}

	@Override
	protected String getSubscriptionUpdatedBodyPreferenceName() {
		return "emailPageUpdatedBody";
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		WikiPage page = WikiPageLocalServiceUtil.getPage(baseModelId);

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
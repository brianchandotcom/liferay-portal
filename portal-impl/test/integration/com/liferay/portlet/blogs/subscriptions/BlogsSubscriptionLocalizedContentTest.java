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

package com.liferay.portlet.blogs.subscriptions;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousMailTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portlet.blogs.util.BlogsConstants;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.subscriptions.test.BaseSubscriptionLocalizedContentTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Roberto Díaz
 */
@Sync
public class BlogsSubscriptionLocalizedContentTest
	extends BaseSubscriptionLocalizedContentTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		BlogsEntry entry = BlogsTestUtil.addEntry(group, true);

		return entry.getEntryId();
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		BlogsEntryLocalServiceUtil.subscribe(
			user.getUserId(), group.getGroupId());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.BLOGS;
	}

	@Override
	protected String getServiceName() {
		return BlogsConstants.SERVICE_NAME;
	}

	@Override
	protected String getSubscriptionAddedBodyPreferenceName() {
		return "emailEntryAddedBody";
	}

	@Override
	protected String getSubscriptionUpdatedBodyPreferenceName() {
		return "emailEntryUpdatedBody";
	}

	@Override
	protected void updateBaseModel(long baseModelId) throws Exception {
		BlogsEntry entry = BlogsEntryLocalServiceUtil.getEntry(baseModelId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		serviceContext.setAttribute("sendEmailEntryUpdated", true);

		BlogsTestUtil.updateEntry(
			entry, RandomTestUtil.randomString(), true, serviceContext);
	}

}
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

package com.liferay.blogs.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portlet.blogs.util.test.BlogsTestUtil;
import com.liferay.portlet.subscriptions.test.BaseSubscriptionAuthorTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author José Ángel Jiménez
 */
@RunWith(Arquillian.class)
@Sync
public class BlogsSubscriptionAuthorTest
	extends BaseSubscriptionAuthorTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModel(long userId, long containerModelId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), userId);

		BlogsTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);

		BlogsEntry entry = BlogsEntryLocalServiceUtil.addEntry(
			userId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return entry.getEntryId();
	}

	@Override
	protected void addSubscription(long userId, long containerModelId)
		throws Exception {

		BlogsEntryLocalServiceUtil.subscribe(userId, group.getGroupId());
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), userId);

		BlogsTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.UPDATE);

		serviceContext.setAttribute("sendEmailEntryUpdated", true);

		BlogsEntryLocalServiceUtil.updateEntry(
			userId, baseModelId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

}
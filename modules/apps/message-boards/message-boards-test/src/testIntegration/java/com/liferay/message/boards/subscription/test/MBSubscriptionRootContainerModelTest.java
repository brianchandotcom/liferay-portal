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

package com.liferay.message.boards.subscription.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.subscription.test.util.BaseSubscriptionRootContainerModelTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class MBSubscriptionRootContainerModelTest
	extends BaseSubscriptionRootContainerModelTestCase {

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

		MBTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);

		MBMessage message = MBMessageLocalServiceUtil.addMessage(
			userId, RandomTestUtil.randomString(), group.getGroupId(),
			containerModelId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		return message.getMessageId();
	}

	@Override
	protected long addContainerModel(long userId, long containerModelId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group.getGroupId(), userId);

		MBTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.ADD);

		MBCategory category = MBCategoryLocalServiceUtil.addCategory(
			userId, containerModelId, RandomTestUtil.randomString(),
			StringPool.BLANK, serviceContext);

		return category.getCategoryId();
	}

	@Override
	protected void addSubscriptionContainerModel(long containerModelId)
		throws Exception {

		MBCategoryLocalServiceUtil.subscribeCategory(
			user.getUserId(), group.getGroupId(), containerModelId);
	}

	@Override
	protected void updateBaseModel(long userId, long baseModelId)
		throws Exception {

		MBMessage message = MBMessageLocalServiceUtil.getMessage(baseModelId);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				message.getGroupId(), userId);

		MBTestUtil.populateNotificationsServiceContext(
			serviceContext, Constants.UPDATE);

		MBMessageLocalServiceUtil.updateMessage(
			userId, message.getMessageId(), RandomTestUtil.randomString(),
			serviceContext);
	}

}
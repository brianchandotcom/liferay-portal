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

package com.liferay.portlet.messageboards.subscriptions;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.SynchronousMailTestRule;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.subscriptions.test.BaseSubscriptionBaseModelTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Roberto Díaz
 */
@Sync
public class MBSubscriptionBaseModelTest
	extends BaseSubscriptionBaseModelTestCase {

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

		_category = MBCategoryLocalServiceUtil.addCategory(
			userId, containerModelId, RandomTestUtil.randomString(),
			StringPool.BLANK, serviceContext);

		return _category.getCategoryId();
	}

	@Override
	protected void addSubscriptionBaseModel(long baseModelId) throws Exception {
		MBMessageLocalServiceUtil.subscribeMessage(
			user.getUserId(), baseModelId);
	}

	@Override
	protected void removeContainerModelResourceViewPermission()
		throws Exception {

		RoleTestUtil.removeResourcePermission(
			RoleConstants.GUEST, MBCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_category.getCategoryId()), ActionKeys.VIEW);

		RoleTestUtil.removeResourcePermission(
			RoleConstants.SITE_MEMBER, MBCategory.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			String.valueOf(_category.getCategoryId()), ActionKeys.VIEW);
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

	private MBCategory _category;

}
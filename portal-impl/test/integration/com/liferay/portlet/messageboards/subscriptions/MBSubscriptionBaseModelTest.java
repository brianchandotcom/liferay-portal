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
import com.liferay.portal.kernel.test.rule.SynchronousMailTestRule;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
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
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Override
	protected long addBaseModel(long containerModelId) throws Exception {
		MBMessage message = MBTestUtil.addMessage(
			group.getGroupId(), containerModelId, true);

		return message.getMessageId();
	}

	@Override
	protected long addContainerModel(long containerModelId) throws Exception {
		_category = MBTestUtil.addCategory(
			group.getGroupId(), containerModelId);

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
	protected void updateBaseModel(long baseModelId) throws Exception {
		MBMessage message = MBMessageLocalServiceUtil.getMessage(baseModelId);

		MBTestUtil.updateMessage(message, true);
	}

	private MBCategory _category;

}
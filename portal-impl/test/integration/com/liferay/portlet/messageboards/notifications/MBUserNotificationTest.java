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

package com.liferay.portlet.messageboards.notifications;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousMailTestRule;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.notifications.test.BaseUserNotificationTestCase;

import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@Sync
public class MBUserNotificationTest extends BaseUserNotificationTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
			SynchronousMailTestRule.INSTANCE);

	@Override
	protected BaseModel<?> addBaseModel() throws Exception {
		return MBTestUtil.addMessage(
			group.getGroupId(), _category.getCategoryId(), true);
	}

	@Override
	protected void addContainerModel() throws Exception {
		_category = MBTestUtil.addCategory(group.getGroupId());
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.MESSAGE_BOARDS;
	}

	@Override
	protected void subscribeToContainer() throws Exception {
		MBCategoryLocalServiceUtil.subscribeCategory(
			user.getUserId(), group.getGroupId(), _category.getCategoryId());
	}

	@Override
	protected BaseModel<?> updateBaseModel(BaseModel<?> baseModel)
		throws Exception {

		return MBTestUtil.updateMessage((MBMessage)baseModel, true);
	}

	private MBCategory _category;

}
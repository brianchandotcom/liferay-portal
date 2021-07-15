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

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.BaseEntityModelListener;
import com.liferay.analytics.message.sender.model.listener.EntityModelListener;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class UserGroupModelListener extends BaseEntityModelListener<UserGroup> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return _attributeNames;
	}

	@Override
	public long[] getMembershipIds(User user) {
		return user.getUserGroupIds();
	}

	@Override
	public String getModelClassName() {
		return UserGroup.class.getName();
	}

	@Override
	public void onAfterRemove(UserGroup userGroup)
		throws ModelListenerException {

		if (!analyticsConfigurationTracker.isActive() ||
			isExcluded(userGroup)) {

			return;
		}

		updateConfigurationProperties(
			userGroup.getCompanyId(), "syncedUserGroupIds",
			String.valueOf(userGroup.getUserGroupId()), null);
	}

	@Override
	protected ActionableDynamicQuery getActionableDynamicQuery() {
		return _userGroupLocalService.getActionableDynamicQuery();
	}

	@Override
	protected UserGroup getModel(long id) throws Exception {
		return _userGroupLocalService.getUserGroup(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userGroupId";
	}

	private static final List<String> _attributeNames =
		Collections.singletonList("name");

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}
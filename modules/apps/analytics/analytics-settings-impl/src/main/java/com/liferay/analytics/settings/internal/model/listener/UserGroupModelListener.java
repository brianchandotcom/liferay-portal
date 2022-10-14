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

package com.liferay.analytics.settings.internal.model.listener;

import com.liferay.analytics.batch.exportimport.model.listener.BaseAnalyticsDXPEntityModelListener;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserGroup;
import com.liferay.portal.kernel.service.UserGroupLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class UserGroupModelListener
	extends BaseAnalyticsDXPEntityModelListener<UserGroup> {

	@Override
	public Class<?> getModelClass() {
		return UserGroup.class;
	}

	@Override
	public void onAfterRemove(UserGroup userGroup)
		throws ModelListenerException {

		super.onAfterRemove(userGroup);

		if (!analyticsConfigurationTracker.isActive() ||
			!isTracked(userGroup)) {

			return;
		}

		updateConfigurationProperties(
			userGroup.getCompanyId(), "syncedUserGroupIds",
			String.valueOf(userGroup.getUserGroupId()), null);
	}

	@Override
	protected UserGroup getModel(Object classPK) {
		return _userGroupLocalService.fetchUserGroup((long)classPK);
	}

	@Reference
	private UserGroupLocalService _userGroupLocalService;

}
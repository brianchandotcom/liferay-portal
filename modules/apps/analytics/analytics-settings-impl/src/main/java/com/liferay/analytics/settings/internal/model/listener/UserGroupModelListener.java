/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (!analyticsConfigurationRegistry.isActive() ||
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
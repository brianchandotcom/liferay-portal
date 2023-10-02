/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.UserGroup;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = ModelListener.class)
public class UserGroupModelListener extends BaseModelListener<UserGroup> {

	@Override
	public void onAfterRemove(UserGroup userGroup)
		throws ModelListenerException {

		if (!analyticsConfigurationRegistry.isActive() ||
			isExcluded(userGroup)) {

			return;
		}

		updateConfigurationProperties(
			userGroup.getCompanyId(), "syncedUserGroupIds",
			String.valueOf(userGroup.getUserGroupId()), null);
	}

	@Override
	protected AnalyticsEntityModel<UserGroup> getAnalyticsEntityModel() {
		return _userGroupAnalyticsEntityModel;
	}

	@Reference(target = "(analytics.entity.model.type=userGroup)")
	private AnalyticsEntityModel<UserGroup> _userGroupAnalyticsEntityModel;

}
/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.analytics.entity.model;

import com.liferay.analytics.message.sender.internal.util.AnalyticsModelUtil;
import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.model.User;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "analytics.entity.model.type=user",
	service = AnalyticsEntityModel.class
)
public class UserAnalyticsEntityModel extends BaseAnalyticsEntityModel<User> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return AnalyticsModelUtil.getUserAttributeNames(
			analyticsConfigurationRegistry.getAnalyticsConfiguration(
				companyId));
	}

	@Override
	public User getModel(long id) throws Exception {
		return userLocalService.getUser(id);
	}

	@Override
	public boolean isExcluded(User user) {
		return AnalyticsModelUtil.isUserExcluded(
			analyticsConfigurationRegistry.getAnalyticsConfiguration(
				user.getCompanyId()),
			user);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userId";
	}

}
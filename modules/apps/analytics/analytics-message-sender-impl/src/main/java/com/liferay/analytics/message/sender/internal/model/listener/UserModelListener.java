/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.sender.internal.model.listener;

import com.liferay.analytics.message.sender.internal.util.AnalyticsModelUtil;
import com.liferay.analytics.message.sender.model.listener.AnalyticsEntityModel;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(service = {AnalyticsEntityModel.class, ModelListener.class})
public class UserModelListener extends BaseModelListener<User> {

	@Override
	public List<String> getAttributeNames(long companyId) {
		return AnalyticsModelUtil.getUserAttributeNames(
			analyticsConfigurationRegistry.getAnalyticsConfiguration(
				companyId));
	}

	@Override
	protected User getModel(long id) throws Exception {
		return userLocalService.getUser(id);
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userId";
	}

	@Override
	protected boolean isExcluded(User user) {
		return AnalyticsModelUtil.isUserExcluded(
			analyticsConfigurationRegistry.getAnalyticsConfiguration(
				user.getCompanyId()),
			user);
	}

}
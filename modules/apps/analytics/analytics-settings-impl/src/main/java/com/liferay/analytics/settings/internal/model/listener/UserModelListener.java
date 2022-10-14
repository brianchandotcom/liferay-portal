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
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marcos Martins
 */
@Component(service = ModelListener.class)
public class UserModelListener
	extends BaseAnalyticsDXPEntityModelListener<User> {

	@Override
	protected boolean isTracked(User user) {
		if ((user == null) ||
			Objects.equals(
				user.getScreenName(),
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN) ||
			Objects.equals(
				user.getStatus(), WorkflowConstants.STATUS_INACTIVE)) {

			return false;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				user.getCompanyId());

		if (analyticsConfiguration.syncAllContacts()) {
			return true;
		}

		long[] organizationIds = null;

		try {
			organizationIds = user.getOrganizationIds();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			return false;
		}

		for (long organizationId : organizationIds) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedOrganizationIds(),
					String.valueOf(organizationId))) {

				return true;
			}
		}

		for (long userGroupId : user.getUserGroupIds()) {
			if (ArrayUtil.contains(
					analyticsConfiguration.syncedUserGroupIds(),
					String.valueOf(userGroupId))) {

				return true;
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

}
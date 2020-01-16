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

import com.liferay.analytics.message.sender.model.EntityModelListener;
import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.analytics.settings.security.constants.AnalyticsSecurityConstants;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.model.User;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rachael Koestartyo
 */
@Component(
	immediate = true, service = {EntityModelListener.class, ModelListener.class}
)
public class UserModelListener extends BaseEntityModelListener<User> {

	@Override
	public List<String> getAttributeNames() {
		return _attributeNames;
	}

	@Override
	protected User getOriginalModel(User user) throws Exception {
		return userLocalService.getUser(user.getUserId());
	}

	@Override
	protected String getPrimaryKeyName() {
		return "userId";
	}

	@Override
	protected boolean isExcluded(User user) {
		if (!user.isActive() ||
			Objects.equals(
				user.getScreenName(),
				AnalyticsSecurityConstants.SCREEN_NAME_ANALYTICS_ADMIN)) {

			return true;
		}

		AnalyticsConfiguration analyticsConfiguration =
			analyticsConfigurationTracker.getAnalyticsConfiguration(
				user.getCompanyId());

		if (analyticsConfiguration.syncAllContacts()) {
			return false;
		}

		try {
			return isExcluded(analyticsConfiguration, user);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return true;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UserModelListener.class);

	private static final List<String> _attributeNames = Arrays.asList(
		"agreedToTermsOfUse", "comments", "companyId", "contactId",
		"createDate", "defaultUser", "emailAddress", "emailAddressVerified",
		"externalReferenceCode", "facebookId", "firstName", "googleUserId",
		"greeting", "jobTitle", "languageId", "lastName", "ldapServerId",
		"middleName", "modifiedDate", "openId", "portraitId", "screenName",
		"status", "timeZoneId", "uuid");

}
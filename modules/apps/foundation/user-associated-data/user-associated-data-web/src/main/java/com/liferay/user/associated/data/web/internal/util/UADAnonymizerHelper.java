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

package com.liferay.user.associated.data.web.internal.util;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfigurationRetriever;

import java.util.Optional;

import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(immediate = true, service = UADAnonymizerHelper.class)
public class UADAnonymizerHelper {

	public User getAnonymousUser() throws Exception {
		return _getAnonymousUser(CompanyThreadLocal.getCompanyId());
	}

	public User getAnonymousUser(long companyId) throws Exception {
		return _getAnonymousUser(companyId);
	}

	public boolean isAnonymousUser(User user) {
		try {
			User anonymousUser = getAnonymousUser(user.getCompanyId());

			if (user.getUserId() == anonymousUser.getUserId()) {
				return true;
			}

			return false;
		}
		catch (Exception e) {
			return false;
		}
	}

	private User _getAnonymousUser(long companyId) throws Exception {
		Optional<Configuration> configurationOptional =
			_anonymousUserConfigurationRetriever.getOptional(companyId);

		User anonymousUser = configurationOptional.map(
			Configuration::getProperties
		).map(
			properties -> ConfigurableUtil.createConfigurable(
				AnonymousUserConfiguration.class, properties)
		).map(
			AnonymousUserConfiguration::userId
		).map(
			_userLocalService::fetchUser
		).orElse(
			_userLocalService.getDefaultUser(companyId)
		);

		return anonymousUser;
	}

	@Reference
	private AnonymousUserConfigurationRetriever
		_anonymousUserConfigurationRetriever;

	@Reference
	private UserLocalService _userLocalService;

}
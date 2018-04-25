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

package com.liferay.user.associated.data.web.internal.configuration;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration",
	service = ConfigurationModelListener.class
)
public class AnonymousUserConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			long companyId = (long)properties.get("companyId");

			_validateCompanyId(companyId);
			_validateUserId(companyId, (long)properties.get("userId"));
		}
		catch (Exception e) {
			throw new ConfigurationModelListenerException(
				e.getMessage(), AnonymousUserConfiguration.class, getClass(),
				properties);
		}
	}

	private void _validateCompanyId(long companyId) throws Exception {
		if (companyId == 0) {
			throw new Exception("companyId must not be 0.");
		}

		if (_companyLocalService.fetchCompanyById(companyId) == null) {
			throw new Exception(
				StringBundler.concat(
					"The given company ID does not belong to an existing ",
					"company: ", String.valueOf(companyId)));
		}
	}

	private void _validateUserId(long companyId, long userId) throws Exception {
		if (userId == 0) {
			throw new Exception("userId must not be 0.");
		}

		try {
			_userLocalService.getUserById(companyId, userId);
		}
		catch (PortalException pe) {
			throw new Exception(
				StringBundler.concat(
					"The given user ID does not belong to an existing user on ",
					"the given company: ", String.valueOf(userId)),
				pe);
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
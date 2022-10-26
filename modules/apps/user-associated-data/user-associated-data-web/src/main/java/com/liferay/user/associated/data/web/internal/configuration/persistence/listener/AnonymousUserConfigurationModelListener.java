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

package com.liferay.user.associated.data.web.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration;
import com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfigurationRetriever;

import java.util.Dictionary;
import java.util.Optional;
import java.util.ResourceBundle;

import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = "model.class.name=com.liferay.user.associated.data.web.internal.configuration.AnonymousUserConfiguration",
	service = ConfigurationModelListener.class
)
public class AnonymousUserConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		// LPS-142491

		long companyThreadLocalCompanyId = CompanyThreadLocal.getCompanyId();

		try {
			long companyId = (long)properties.get("companyId");

			CompanyThreadLocal.setCompanyId(companyId);

			_companyLocalService.getCompanyById(companyId);

			_userLocalService.getUserById(
				companyId, (long)properties.get("userId"));

			_validateUniqueConfiguration(pid, companyId);
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(), AnonymousUserConfiguration.class,
				getClass(), properties);
		}
		finally {
			CompanyThreadLocal.setCompanyId(companyThreadLocalCompanyId);
		}
	}

	private void _validateUniqueConfiguration(String pid, long companyId)
		throws Exception {

		Optional<Configuration> configurationOptional =
			_anonymousUserConfigurationRetriever.getOptional(companyId);

		if (!configurationOptional.isPresent()) {
			return;
		}

		Configuration configuration = configurationOptional.get();

		if (pid.equals(configuration.getPid())) {
			return;
		}

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
			getClass());

		String message = ResourceBundleUtil.getString(
			resourceBundle,
			"an-anonymous-user-is-already-defined-for-the-company-x",
			String.valueOf(companyId));

		throw new Exception(message);
	}

	@Reference
	private AnonymousUserConfigurationRetriever
		_anonymousUserConfigurationRetriever;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private UserLocalService _userLocalService;

}
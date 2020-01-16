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

package com.liferay.password.policies.admin.web.internal.configuration.persistence.listener;

import com.liferay.password.policies.admin.web.internal.configuration.PasswordPoliciesConfiguration;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jonathan McCann
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.password.policies.admin.web.internal.configuration.PasswordPoliciesConfiguration",
	service = ConfigurationModelListener.class
)
public class PasswordPoliciesConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		try {
			_validateDurations(
				(Long[])properties.get("expirationWarningTimeDurations"));
			_validateDurations((Long[])properties.get("lockoutDurations"));
			_validateDurations((Long[])properties.get("maximumAgeDurations"));
			_validateDurations((Long[])properties.get("minimumAgeDurations"));
			_validateDurations((Long[])properties.get("resetFailureDurations"));
			_validateDurations(
				(Long[])properties.get("resetTicketMaxAgeDurations"));
		}
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(), PasswordPoliciesConfiguration.class,
				getClass(), properties);
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
			getClass());
	}

	private void _validateDurations(Long[] durations) throws Exception {
		for (long duration : durations) {
			if (duration < 0) {
				ResourceBundle resourceBundle = _getResourceBundle();

				String message = ResourceBundleUtil.getString(
					resourceBundle,
					"the-duration-must-be-greater-than-or-equal-to-0");

				throw new Exception(message);
			}
		}
	}

}
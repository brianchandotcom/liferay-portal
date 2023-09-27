/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
				String message = ResourceBundleUtil.getString(
					_getResourceBundle(),
					"the-duration-must-be-greater-than-or-equal-to-0");

				throw new Exception(message);
			}
		}
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.internal.configuration.persistence.listener;

import com.liferay.object.configuration.ObjectConfiguration;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.Objects;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Thalles Montenegro
 */
@Component(
	property = "model.class.name=com.liferay.object.configuration.ObjectConfiguration",
	service = ConfigurationModelListener.class
)
public class ObjectConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", LocaleUtil.getMostRelevantLocale(), getClass());

		long duration = GetterUtil.getLong(properties.get("duration"));

		if (duration < 1) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					resourceBundle, "the-duration-field-cannot-be-less-than-1"),
				ObjectConfiguration.class, getClass(), properties);
		}

		String timeScale = GetterUtil.getString(properties.get("timeScale"));

		if (!(Objects.equals(timeScale, "days") ||
			  Objects.equals(timeScale, "weeks"))) {

			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					resourceBundle, "the-time-scale-is-invalid"),
				ObjectConfiguration.class, getClass(), properties);
		}
	}

}
/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.antisamy.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.security.antisamy.configuration.AntiSamyClassNameConfiguration;

import java.net.URL;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;

/**
 * @author Marta Medio
 */
@Component(
	property = "model.class.name=com.liferay.portal.security.antisamy.configuration.AntiSamyClassNameConfiguration",
	service = ConfigurationModelListener.class
)
public class AntiSamyClassNameConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		Bundle bundle = FrameworkUtil.getBundle(
			AntiSamyClassNameConfiguration.class);

		String configurationFileURL = GetterUtil.getString(
			properties.get("configurationFileURL"));

		URL url = bundle.getResource(configurationFileURL);

		if (url == null) {
			throw new ConfigurationModelListenerException(
				ResourceBundleUtil.getString(
					_getResourceBundle(),
					"specified-file-configuration-not-found",
					configurationFileURL),
				AntiSamyClassNameConfiguration.class, getClass(), properties);
		}
	}

	private ResourceBundle _getResourceBundle() {
		return ResourceBundleUtil.getBundle(
			LocaleThreadLocal.getThemeDisplayLocale(), getClass());
	}

}
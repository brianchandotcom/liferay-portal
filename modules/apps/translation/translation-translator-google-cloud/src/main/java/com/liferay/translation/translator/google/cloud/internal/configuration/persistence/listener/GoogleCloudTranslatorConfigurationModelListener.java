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

package com.liferay.translation.translator.google.cloud.internal.configuration.persistence.listener;

import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.translation.translator.google.cloud.internal.configuration.GoogleCloudTranslatorConfiguration;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.translation.translator.google.cloud.internal.configuration.GoogleCloudTranslatorConfiguration",
	service = ConfigurationModelListener.class
)
public class GoogleCloudTranslatorConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		boolean enabled = GetterUtil.getBoolean(properties.get("enabled"));
		String serviceAccountPrivateKey = GetterUtil.getString(
			properties.get("serviceAccountPrivateKey"));

		if (enabled && !_isValid(serviceAccountPrivateKey)) {
			throw new ConfigurationModelListenerException(
				LanguageUtil.get(
					LocaleThreadLocal.getThemeDisplayLocale(),
					"the-service-account-private-key-must-be-in-json-format"),
				GoogleCloudTranslatorConfiguration.class, getClass(),
				properties);
		}
	}

	private boolean _isValid(String serviceAccountPrivateKey) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
				serviceAccountPrivateKey);

			if (jsonObject.length() > 0) {
				return true;
			}

			return false;
		}
		catch (Exception exception) {
			return false;
		}
	}

}
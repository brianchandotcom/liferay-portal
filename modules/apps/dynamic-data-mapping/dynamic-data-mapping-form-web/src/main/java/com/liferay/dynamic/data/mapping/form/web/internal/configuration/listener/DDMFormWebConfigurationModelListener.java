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

package com.liferay.dynamic.data.mapping.form.web.internal.configuration.listener;

import com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListenerException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Dictionary;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.dynamic.data.mapping.form.web.internal.configuration.DDMFormWebConfiguration",
	service = ConfigurationModelListener.class
)
public class DDMFormWebConfigurationModelListener
	implements ConfigurationModelListener {

	@Override
	public void onBeforeSave(String pid, Dictionary<String, Object> properties)
		throws ConfigurationModelListenerException {

		DDMFormWebConfiguration ddmFormWebConfiguration =
			ConfigurableUtil.createConfigurable(
				DDMFormWebConfiguration.class, new HashMapDictionary<>());

		try {
			int autosaveInterval = GetterUtil.getInteger(
				properties.get("autosaveInterval"),
				ddmFormWebConfiguration.autosaveInterval());

			_validateAutosaveInterval(autosaveInterval);
		}
		catch (Exception e) {
			throw new ConfigurationModelListenerException(
				e.getMessage(), DDMFormWebConfiguration.class, getClass(),
				properties);
		}
	}

	private void _validateAutosaveInterval(int autosaveInterval)
		throws Exception {

		if (autosaveInterval < 0) {
			ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
				"content.Language", LocaleThreadLocal.getThemeDisplayLocale(),
				getClass());

			String message = ResourceBundleUtil.getString(
				resourceBundle,
				"the-autosave-interval-must-be-greater-than-or-equal-to-0");

			throw new Exception(message);
		}
	}

}
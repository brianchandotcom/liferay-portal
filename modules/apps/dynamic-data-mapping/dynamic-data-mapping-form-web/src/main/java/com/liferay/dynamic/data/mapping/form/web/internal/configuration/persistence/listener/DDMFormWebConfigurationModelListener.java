/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.form.web.internal.configuration.persistence.listener;

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
		catch (Exception exception) {
			throw new ConfigurationModelListenerException(
				exception.getMessage(), DDMFormWebConfiguration.class,
				getClass(), properties);
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
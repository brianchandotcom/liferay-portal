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

package com.liferay.portal.configuration.upgrade.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.upgrade.PrefsPropsToConfigurationUpgradeHelper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.PrefsProps;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.Dictionary;
import java.util.Objects;

import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;

import org.osgi.framework.Constants;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true, service = PrefsPropsToConfigurationUpgradeHelper.class
)
public class PrefsPropsToConfigurationUpgradeHelperImpl
	implements PrefsPropsToConfigurationUpgradeHelper {

	@Override
	public void mapConfigurations(
			Class<?> configurationClass, KeyValuePair... keyValuePairs)
		throws Exception {

		String filterString = StringBundler.concat(
			"(", Constants.SERVICE_PID, "=", configurationClass.getName(), ")");

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			filterString);

		if (configurations != null) {
			return;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		PortletPreferences portletPreferences = _prefsProps.getPreferences();

		Object defaultConfiguration = ConfigurableUtil.createConfigurable(
			configurationClass, properties);

		for (KeyValuePair keyValuePair : keyValuePairs) {
			String valueString = _prefsProps.getString(
				keyValuePair.getKey(), null);

			if (Validator.isNull(valueString)) {
				continue;
			}

			Method method = configurationClass.getMethod(
				keyValuePair.getValue());

			Object defaultValueObject = method.invoke(defaultConfiguration);

			Class<?> returnType = method.getReturnType();

			Object value = null;

			if (returnType == boolean.class) {
				value = GetterUtil.getBoolean(valueString);
			}
			else if (returnType == double.class) {
				value = GetterUtil.getDouble(valueString);
			}
			else if (returnType == int.class) {
				value = GetterUtil.getInteger(valueString);
			}
			else if (returnType == float.class) {
				value = GetterUtil.getFloat(valueString);
			}
			else if (returnType == long.class) {
				value = GetterUtil.getLong(valueString);
			}
			else if (returnType == short.class) {
				value = GetterUtil.getShort(valueString);
			}
			else if (returnType == String.class) {
				value = GetterUtil.getString(valueString);
			}
			else if (returnType == String[].class) {
				value = StringUtil.split(valueString);

				if (!Arrays.equals(
						(Object[])value, (Object[])defaultValueObject)) {

					_writeProperty(
						properties, portletPreferences, keyValuePair, value);
				}

				continue;
			}
			else {
				throw new IllegalArgumentException(
					"No valid return type found: " + method);
			}

			if (!Objects.equals(value, defaultValueObject)) {
				_writeProperty(
					properties, portletPreferences, keyValuePair, value);
			}
		}

		if (properties.isEmpty()) {
			return;
		}

		Configuration configuration = _configurationAdmin.getConfiguration(
			configurationClass.getName(), StringPool.QUESTION);

		configuration.update(properties);

		portletPreferences.store();
	}

	private void _writeProperty(
		Dictionary<String, Object> properties,
		PortletPreferences portletPreferences, KeyValuePair keyValuePair,
		Object value) {

		properties.put(keyValuePair.getValue(), value);

		if (!portletPreferences.isReadOnly(keyValuePair.getKey())) {
			try {
				portletPreferences.reset(keyValuePair.getKey());
			}
			catch (ReadOnlyException roe) {
				throw new RuntimeException(roe);
			}
		}
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

	@Reference
	private PrefsProps _prefsProps;

}
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

package com.liferay.portal.configuration.easyconf;

import com.germinus.easyconf.AggregatedProperties;
import com.germinus.easyconf.ComponentConfiguration;
import com.germinus.easyconf.ComponentProperties;
import com.germinus.easyconf.ConfigurationNotFoundException;
import com.germinus.easyconf.Conventions;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.exception.LoggedExceptionInInitializerError;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.SystemProperties;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;

/**
 * @author Raymond Augé
 */
public class ClassLoaderComponentConfiguration extends ComponentConfiguration {

	public ClassLoaderComponentConfiguration(
		ClassLoader classLoader, String companyId, String componentName) {

		super(companyId, componentName);

		_classLoader = classLoader;
		_companyId = companyId;
		_componentName = componentName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ComponentConfiguration)) {
			return false;
		}

		ComponentConfiguration componentConfiguration =
			(ComponentConfiguration)obj;

		return _componentName.equals(componentConfiguration.getComponentName());
	}

	@Override
	public String getComponentName() {
		return _componentName;
	}

	@Override
	public Object getConfigurationObject() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getConfigurationObject(String configurationName) {
		throw new UnsupportedOperationException();
	}

	@Override
	public ComponentProperties getProperties() {
		ComponentProperties componentProperties = _getAvailableProperties();

		if (!componentProperties.hasBaseConfiguration()) {
			throw new ConfigurationNotFoundException(
				_componentName, "The base properties file was not found");
		}

		return componentProperties;
	}

	@Override
	public int hashCode() {
		return _componentName.hashCode();
	}

	@Override
	public void saveConfigurationObject(Object configurationObject) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void saveConfigurationObject(
		String confName, Object configurationObject) {

		throw new UnsupportedOperationException();
	}

	protected static String decode(String s) {
		int index = -1;
		int openUnderLine = -1;
		int position = 0;
		StringBundler sb = new StringBundler();

		while ((index = s.indexOf(CharPool.UNDERLINE, index + 1)) != -1) {
			if (openUnderLine == -1) {
				sb.append(s.substring(position, index));

				openUnderLine = index;
				position = index;

				continue;
			}

			String encoded = s.substring(openUnderLine + 1, index);

			Character character = _charPoolChars.get(
				StringUtil.toUpperCase(encoded));

			if (character == null) {
				int value = GetterUtil.get(encoded, -1);

				if (Character.isDefined(value)) {
					sb.append(new String(Character.toChars(value)));
				}
				else {
					if (_log.isWarnEnabled()) {
						_log.warn(
							StringBundler.concat(
								"Unable to decode part \"", encoded,
								"\" from \"", s, "\", preserve it literally"));
					}

					sb.append(s.substring(openUnderLine, index + 1));
				}
			}
			else {
				sb.append(character);
			}

			openUnderLine = -1;
			position = index + 1;
		}

		sb.append(s.substring(position));

		return sb.toString();
	}

	private ComponentProperties _getAvailableProperties() {
		if (_properties != null) {
			return _properties;
		}

		SystemProperties.set("base.path", ".");

		ClassLoaderAggregateProperties classLoaderAggregateProperties =
			new ClassLoaderAggregateProperties(
				_classLoader, _companyId, _componentName);

		classLoaderAggregateProperties.addGlobalFileName(
			Conventions.GLOBAL_CONFIGURATION_FILE +
				Conventions.PROPERTIES_EXTENSION);

		classLoaderAggregateProperties.addBaseFileName(
			_componentName + Conventions.PROPERTIES_EXTENSION);

		if (_log.isInfoEnabled()) {
			_log.info(
				StringBundler.concat(
					"Properties for ", _componentName, " loaded from ",
					String.valueOf(
						classLoaderAggregateProperties.loadedSources())));
		}

		_loadEnvOverrides(classLoaderAggregateProperties);

		try {
			_properties = _CONSTRUCTOR.newInstance(
				new Object[] {classLoaderAggregateProperties});
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return _properties;
	}

	private void _loadEnvOverrides(Configuration configuration) {
		Map<String, String> env = System.getenv();

		for (Map.Entry<String, String> entry : env.entrySet()) {
			String key = entry.getKey();

			if (!key.startsWith(_ENV_OVERRIDE_PREFIX)) {
				continue;
			}

			String newKey = decode(
				StringUtil.toLowerCase(
					key.substring(_ENV_OVERRIDE_PREFIX.length())));

			configuration.setProperty(newKey, entry.getValue());

			if (_log.isInfoEnabled()) {
				_log.info(
					StringBundler.concat(
						"Overrode property ", newKey,
						" with the value from the environment variable ", key));
			}
		}
	}

	private static final Constructor<ComponentProperties> _CONSTRUCTOR;

	private static final String _ENV_OVERRIDE_PREFIX = "LIFERAY_";

	private static final Log _log = LogFactoryUtil.getLog(
		ClassLoaderComponentConfiguration.class);

	private static final Map<String, Character> _charPoolChars =
		new HashMap<>();

	static {
		try {
			for (Field field : CharPool.class.getFields()) {
				if (Modifier.isStatic(field.getModifiers()) &&
					(field.getType() == char.class)) {

					_charPoolChars.put(
						StringUtil.removeChar(
							field.getName(), CharPool.UNDERLINE),
						field.getChar(null));
				}
			}
		}
		catch (ReflectiveOperationException roe) {
			throw new ExceptionInInitializerError(roe);
		}

		Constructor<ComponentProperties> constructor = null;

		try {
			constructor = ComponentProperties.class.getDeclaredConstructor(
				AggregatedProperties.class);

			constructor.setAccessible(true);
		}
		catch (Exception e) {
			throw new LoggedExceptionInInitializerError(e);
		}

		_CONSTRUCTOR = constructor;
	}

	private final ClassLoader _classLoader;
	private final String _companyId;
	private final String _componentName;
	private ComponentProperties _properties;

}
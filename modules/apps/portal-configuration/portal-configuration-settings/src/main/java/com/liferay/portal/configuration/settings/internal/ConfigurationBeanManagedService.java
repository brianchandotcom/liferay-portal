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

package com.liferay.portal.configuration.settings.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;

import java.util.Dictionary;
import java.util.function.Consumer;

import org.osgi.service.cm.ManagedService;

/**
 * @author Iván Zaera
 * @author Shuyang Zhou
 */
public class ConfigurationBeanManagedService implements ManagedService {

	public ConfigurationBeanManagedService(
		Class<?> configurationBeanClass,
		Consumer<Object> configurationBeanConsumer) {

		_configurationBeanClass = configurationBeanClass;
		_configurationBeanConsumer = configurationBeanConsumer;
	}

	@Override
	public void updated(Dictionary<String, ?> properties) {
		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		Object configurationBean = ConfigurableUtil.createConfigurable(
			_configurationBeanClass, properties);

		_configurationBeanConsumer.accept(configurationBean);
	}

	private final Class<?> _configurationBeanClass;
	private final Consumer<Object> _configurationBeanConsumer;

}
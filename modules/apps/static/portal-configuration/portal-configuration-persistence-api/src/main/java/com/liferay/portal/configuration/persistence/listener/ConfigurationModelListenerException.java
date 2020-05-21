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

package com.liferay.portal.configuration.persistence.listener;

import java.io.IOException;

import java.util.Dictionary;

/**
 * @author Drew Brokke
 */
public class ConfigurationModelListenerException extends IOException {

	public ConfigurationModelListenerException(
		Exception exception, Class<?> configurationClass,
		Class<?> listenerClass, Dictionary<String, Object> properties) {

		super(
			String.format(
				"The listener %s was unable to save configuration %s. Caused by: %s",
				listenerClass.getName(), configurationClass.getName(), exception.getMessage()), 
			exception);

		this.causeMessage = exception.getMessage();
		this.configurationClass = configurationClass;
		this.listenerClass = listenerClass;
		this.properties = properties;
	}
	
	public ConfigurationModelListenerException(
			String causeMessage, Class<?> configurationClass,
			Class<?> listenerClass, Dictionary<String, Object> properties) {

			super(
				String.format(
					"The listener %s was unable to save configuration %s. Caused by: %s",
					listenerClass.getName(), configurationClass.getName(), causeMessage));

			this.causeMessage = causeMessage;
			this.configurationClass = configurationClass;
			this.listenerClass = listenerClass;
			this.properties = properties;
		}

	public final String causeMessage;
	public final Class<?> configurationClass;
	public final Class<?> listenerClass;
	public final Dictionary<String, Object> properties;

}
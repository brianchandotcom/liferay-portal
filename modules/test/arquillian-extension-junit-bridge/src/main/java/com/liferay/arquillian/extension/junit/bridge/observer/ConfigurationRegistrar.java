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

package com.liferay.arquillian.extension.junit.bridge.observer;

import java.io.IOException;
import java.io.InputStream;

import java.util.Properties;

import org.jboss.arquillian.config.descriptor.api.ArquillianDescriptor;
import org.jboss.arquillian.config.impl.extension.PropertiesParser;
import org.jboss.arquillian.core.api.InstanceProducer;
import org.jboss.arquillian.core.api.annotation.ApplicationScoped;
import org.jboss.arquillian.core.api.annotation.Inject;
import org.jboss.arquillian.core.api.annotation.Observes;
import org.jboss.arquillian.core.api.event.ManagerStarted;
import org.jboss.shrinkwrap.descriptor.api.Descriptors;

/**
 * @author Matthew Tambara
 */
public class ConfigurationRegistrar {

	public void loadConfiguration(@Observes ManagerStarted event)
		throws IOException {

		ArquillianDescriptor arquillianDescriptor = Descriptors.create(
			ArquillianDescriptor.class);

		final PropertiesParser propertiesParser = new PropertiesParser();

		ClassLoader classLoader = JUnitBridgeObserver.class.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"arquillian.properties")) {

			Properties properties = new Properties();

			properties.load(inputStream);

			propertiesParser.addProperties(arquillianDescriptor, properties);
		}

		Properties envProperties = new Properties();

		envProperties.putAll(System.getenv());

		propertiesParser.addProperties(arquillianDescriptor, envProperties);

		_arquillianDescriptor.set(arquillianDescriptor);
	}

	@ApplicationScoped
	@Inject
	private InstanceProducer<ArquillianDescriptor> _arquillianDescriptor;

}
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
package com.liferay.osgi.config.admin.model;

import java.io.IOException;
import java.io.InputStream;

import org.osgi.service.cm.Configuration;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Raymond Augé
 */
public class ConfigurationModel implements ObjectClassDefinition {

	public ConfigurationModel(
		ObjectClassDefinition objectClassDefinition, String bundleLocation,
		boolean factory) {

		_objectClassDefinition = objectClassDefinition;
		_bundleLocation = bundleLocation;
		_factory = factory;
	}

	@Override
	public AttributeDefinition[] getAttributeDefinitions(int filter) {
		return _objectClassDefinition.getAttributeDefinitions(filter);
	}

	public String getBundleLocation() {
		return _bundleLocation;
	}

	public Configuration getConfiguration() {
		return _configuration;
	}

	@Override
	public String getDescription() {
		return _objectClassDefinition.getDescription();
	}

	public String getFactoryPid() {
		return _objectClassDefinition.getID();
	}

	@Override
	public InputStream getIcon(int size) throws IOException {
		return _objectClassDefinition.getIcon(size);
	}

	@Override
	public String getID() {
		if (_configuration != null) {
			return _configuration.getPid();
		}

		return _objectClassDefinition.getID();
	}

	@Override
	public String getName() {
		return _objectClassDefinition.getName();
	}

	public boolean isFactory() {
		return _factory;
	}

	public void setConfiguration(Configuration configuration) {
		_configuration = configuration;
	}

	private final String _bundleLocation;
	private Configuration _configuration;
	private final boolean _factory;
	private final ObjectClassDefinition _objectClassDefinition;

}
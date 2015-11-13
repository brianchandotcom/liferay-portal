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

package com.liferay.configuration.admin.web.model;

import com.liferay.configuration.admin.ConfigurationAdmin;
import com.liferay.configuration.admin.ExtendedAttributeDefinition;
import com.liferay.configuration.admin.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.util.GetterUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Map;
import java.util.Set;

import org.osgi.service.cm.Configuration;

/**
 * @author Raymond Augé
 */
public class ConfigurationModel implements ExtendedObjectClassDefinition {

	public ConfigurationModel(
		ExtendedObjectClassDefinition extendedObjectClassDefinition,
		Configuration configuration, String bundleLocation, boolean factory) {

		_extendedObjectClassDefinition = extendedObjectClassDefinition;
		_configuration = configuration;
		_bundleLocation = bundleLocation;
		_factory = factory;
	}

	@Override
	public ExtendedAttributeDefinition[] getAttributeDefinitions(int filter) {
		return _extendedObjectClassDefinition.getAttributeDefinitions(filter);
	}

	public String getBundleLocation() {
		return _bundleLocation;
	}

	public String getCategory() {
		Map<String, String> extensionAttributes =
			_extendedObjectClassDefinition.getExtensionAttributes(
				ConfigurationAdmin.XML_NAMESPACE);

		return GetterUtil.get(extensionAttributes.get("category"), "other");
	}

	public Configuration getConfiguration() {
		return _configuration;
	}

	@Override
	public String getDescription() {
		return _extendedObjectClassDefinition.getDescription();
	}

	public ExtendedObjectClassDefinition getExtendedObjectClassDefinition() {
		return _extendedObjectClassDefinition;
	}

	@Override
	public Map<String, String> getExtensionAttributes(String uri) {
		return _extendedObjectClassDefinition.getExtensionAttributes(uri);
	}

	@Override
	public Set<String> getExtensionUris() {
		return _extendedObjectClassDefinition.getExtensionUris();
	}

	public String getFactoryPid() {
		return _extendedObjectClassDefinition.getID();
	}

	public Map<String, String> getHintAttributes() {
		return _extendedObjectClassDefinition.getExtensionAttributes(
			"http://www.liferay.com/xsd/meta-type-hints_7_0_0");
	}

	@Override
	public InputStream getIcon(int size) throws IOException {
		return _extendedObjectClassDefinition.getIcon(size);
	}

	@Override
	public String getID() {
		if (_configuration != null) {
			return _configuration.getPid();
		}

		return _extendedObjectClassDefinition.getID();
	}

	@Override
	public String getName() {
		return _extendedObjectClassDefinition.getName();
	}

	public boolean isFactory() {
		return _factory;
	}

	private final String _bundleLocation;
	private final Configuration _configuration;
	private final ExtendedObjectClassDefinition _extendedObjectClassDefinition;
	private final boolean _factory;

}
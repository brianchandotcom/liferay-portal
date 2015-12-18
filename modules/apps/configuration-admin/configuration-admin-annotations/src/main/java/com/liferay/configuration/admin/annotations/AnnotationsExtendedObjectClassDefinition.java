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

package com.liferay.configuration.admin.annotations;

import com.liferay.configuration.admin.ConfigurationAdmin;
import com.liferay.configuration.admin.ExtendedAttributeDefinition;
import com.liferay.configuration.admin.ExtendedObjectClassDefinition;

import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.metatype.AttributeDefinition;
import org.osgi.service.metatype.ObjectClassDefinition;

/**
 * @author Iván Zaera
 */
public class AnnotationsExtendedObjectClassDefinition
	implements ExtendedObjectClassDefinition {

	public AnnotationsExtendedObjectClassDefinition(
		Bundle bundle, ObjectClassDefinition objectClassDefinition) {

		_objectClassDefinition = objectClassDefinition;

		loadConfigurationBeanClass(bundle);

		if (_configurationBeanClass != null) {
			processConfigurationAdminFields();
		}
	}

	@Override
	public ExtendedAttributeDefinition[] getAttributeDefinitions(int filter) {
		ExtendedAttributeDefinition[] extendedAttributeDefinitions =
			_extendedAttributeDefinitions.get(filter);

		if (extendedAttributeDefinitions != null) {
			return extendedAttributeDefinitions;
		}

		AttributeDefinition[] attributeDefinitions =
			_objectClassDefinition.getAttributeDefinitions(filter);

		extendedAttributeDefinitions =
			new ExtendedAttributeDefinition[attributeDefinitions.length];

		for (int i = 0; i < attributeDefinitions.length; i++) {
			extendedAttributeDefinitions[i] =
				new AnnotationsExtendedAttributeDefinition(
					_configurationBeanClass, attributeDefinitions[i]);
		}

		_extendedAttributeDefinitions.put(filter, extendedAttributeDefinitions);

		return extendedAttributeDefinitions;
	}

	@Override
	public String getDescription() {
		return _objectClassDefinition.getDescription();
	}

	@Override
	public Map<String, String> getExtensionAttributes(String uri) {
		Map<String, String> extensionAttributes = _extensionAttributes.get(uri);

		if (extensionAttributes == null) {
			extensionAttributes = Collections.emptyMap();
		}

		return extensionAttributes;
	}

	@Override
	public Set<String> getExtensionUris() {
		return _extensionAttributes.keySet();
	}

	@Override
	public InputStream getIcon(int size) throws IOException {
		return _objectClassDefinition.getIcon(size);
	}

	@Override
	public String getID() {
		return _objectClassDefinition.getID();
	}

	@Override
	public String getName() {
		return _objectClassDefinition.getName();
	}

	protected void loadConfigurationBeanClass(Bundle bundle) {
		try {
			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			ClassLoader classLoader = bundleWiring.getClassLoader();

			_configurationBeanClass = classLoader.loadClass(
				_objectClassDefinition.getID());
		}
		catch (ClassNotFoundException cnfe) {
		}
	}

	protected void processConfigurationAdminFields() {
		ConfigurationAdmin configurationAdmin =
			_configurationBeanClass.getAnnotation(ConfigurationAdmin.class);

		if (configurationAdmin != null) {
			Map<String, String> map = new HashMap<>();

			map.put("category", configurationAdmin.category());
			map.put(
				"factoryInstanceLabelAttribute",
				configurationAdmin.factoryInstanceLabelAttribute());

			_extensionAttributes.put(ConfigurationAdmin.XML_NAMESPACE, map);
		}
	}

	private Class<?> _configurationBeanClass;
	private final Map<Integer, ExtendedAttributeDefinition[]>
		_extendedAttributeDefinitions = new HashMap<>();
	private final Map<String, Map<String, String>> _extensionAttributes =
		new HashMap<>();
	private final ObjectClassDefinition _objectClassDefinition;

}
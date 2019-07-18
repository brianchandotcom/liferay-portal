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

package com.liferay.portal.configuration.metatype.definitions.annotations.internal;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.configuration.metatype.definitions.ExtendedAttributeDefinition;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

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
	implements com.liferay.portal.configuration.metatype.definitions.
				   ExtendedObjectClassDefinition {

	public AnnotationsExtendedObjectClassDefinition(
		Bundle bundle, ObjectClassDefinition objectClassDefinition) {

		_objectClassDefinition = objectClassDefinition;

		_loadConfigurationBeanClass(bundle);

		if (_configurationBeanClass != null) {
			_processExtendedMetatypeFields(_configurationBeanClass);
		}
		else {
			_processExtendedMetatypeFields(bundle);
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

	private JSONObject _createJSONObject(Bundle bundle, String resourcePath) {
		URL url = bundle.getResource(resourcePath);

		if (url != null) {
			try (InputStream is = url.openStream()) {
				return JSONFactoryUtil.createJSONObject(StringUtil.read(is));
			}
			catch (Exception e) {
				_log.error("Unable to process resource " + resourcePath, e);
			}
		}

		return null;
	}

	private void _loadConfigurationBeanClass(Bundle bundle) {
		try {
			BundleWiring bundleWiring = bundle.adapt(BundleWiring.class);

			ClassLoader classLoader = bundleWiring.getClassLoader();

			_configurationBeanClass = classLoader.loadClass(
				_objectClassDefinition.getID());
		}
		catch (ClassNotFoundException cnfe) {
		}
	}

	private void _processExtendedMetatypeFields(Bundle bundle) {
		JSONObject metatypeJSONObject = _createJSONObject(
			bundle, "features/metatype.json");

		if (metatypeJSONObject != null) {
			Map<String, String> attributes = new HashMap<>();

			String category = metatypeJSONObject.getString("category");

			if (Validator.isNotNull(category)) {
				attributes.put("category", category);
			}
			else {
				JSONObject packageJSONObject = _createJSONObject(
					bundle, "META-INF/resources/package.json");

				if (packageJSONObject != null) {
					attributes.put(
						"category", packageJSONObject.getString("name"));
				}
			}

			_extensionAttributes.put(
				ExtendedObjectClassDefinition.XML_NAMESPACE, attributes);
		}
	}

	private void _processExtendedMetatypeFields(
		Class<?> configurationBeanClass) {

		ExtendedObjectClassDefinition extendedObjectClassDefinition =
			configurationBeanClass.getAnnotation(
				ExtendedObjectClassDefinition.class);

		if (extendedObjectClassDefinition != null) {
			Map<String, String> attributes = new HashMap<>();

			attributes.put(
				"category", extendedObjectClassDefinition.category());
			attributes.put(
				"description-arguments",
				StringUtil.merge(
					extendedObjectClassDefinition.descriptionArguments()));
			attributes.put(
				"factoryInstanceLabelAttribute",
				extendedObjectClassDefinition.factoryInstanceLabelAttribute());
			attributes.put(
				"generateUI",
				Boolean.toString(extendedObjectClassDefinition.generateUI()));
			attributes.put(
				"name-arguments",
				StringUtil.merge(
					extendedObjectClassDefinition.nameArguments()));

			ExtendedObjectClassDefinition.Scope scope =
				extendedObjectClassDefinition.scope();

			attributes.put("scope", scope.toString());

			_extensionAttributes.put(
				ExtendedObjectClassDefinition.XML_NAMESPACE, attributes);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		AnnotationsExtendedObjectClassDefinition.class);

	private Class<?> _configurationBeanClass;
	private final Map<Integer, ExtendedAttributeDefinition[]>
		_extendedAttributeDefinitions = new HashMap<>();
	private final Map<String, Map<String, String>> _extensionAttributes =
		new HashMap<>();
	private final ObjectClassDefinition _objectClassDefinition;

}
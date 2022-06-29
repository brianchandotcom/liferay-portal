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

package com.liferay.portal.vulcan.internal.extension;

import com.liferay.portal.vulcan.extension.ExtendedPropertyDefinition;
import com.liferay.portal.vulcan.extension.ExtensionProvider;

import java.io.Serializable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Javier de Arcos
 */
public class ExtensionProviders {

	public ExtensionProviders(
		String className, List<ExtensionProvider> extensionProviderList) {

		_className = className;
		_extensionProviderList = extensionProviderList;
	}

	public Map<String, Serializable> getExtendedProperties(
			long companyId, Object entity)
		throws Exception {

		Map<String, Serializable> extendedProperties = new HashMap<>();

		for (ExtensionProvider extensionProvider : _extensionProviderList) {
			extendedProperties.putAll(
				extensionProvider.getExtendedProperties(companyId, entity));
		}

		return extendedProperties;
	}

	public Set<String> getFilteredPropertyNames(long companyId, Object entity) {
		Set<String> filteredPropertyNames = new HashSet<>();

		for (ExtensionProvider extensionProvider : _extensionProviderList) {
			filteredPropertyNames.addAll(
				extensionProvider.getFilteredPropertyNames(companyId, entity));
		}

		return filteredPropertyNames;
	}

	public void setExtendedProperties(
			long companyId, Object entity,
			Map<String, Serializable> extendedProperties)
		throws Exception {

		for (ExtensionProvider extensionProvider : _extensionProviderList) {
			Map<String, ExtendedPropertyDefinition>
				extendedPropertyDefinitionMap =
					extensionProvider.getExtendedPropertyDefinitions(
						companyId, _className);

			Set<String> extendedPropertyNames =
				extendedPropertyDefinitionMap.keySet();

			Set<Map.Entry<String, Serializable>> entries =
				extendedProperties.entrySet();

			Map<String, Serializable> extensionProviderExtendedProperties =
				new HashMap<>();

			for (Map.Entry<String, Serializable> entry : entries) {
				if (extendedPropertyNames.contains(entry.getKey())) {
					extensionProviderExtendedProperties.put(
						entry.getKey(), entry.getValue());
				}
			}

			extensionProvider.setExtendedProperties(
				companyId, entity, extensionProviderExtendedProperties);
		}
	}

	public void validate(
		long companyId, Map<String, Serializable> extendedProperties,
		boolean partialUpdate) {

		// TODO: Validate extended properties

	}

	private final String _className;
	private final List<ExtensionProvider> _extensionProviderList;

}
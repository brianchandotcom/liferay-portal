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

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.vulcan.extension.ExtensionProvider;
import com.liferay.portal.vulcan.extension.PropertyDefinition;
import com.liferay.portal.vulcan.extension.validation.PropertyValidator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ValidationException;

/**
 * @author Javier de Arcos
 */
public class EntityExtensionHandler {

	public EntityExtensionHandler(
		String className, List<ExtensionProvider> extensionProviders) {

		_className = className;
		_extensionProviders = extensionProviders;
	}

	public Map<String, Serializable> getExtendedProperties(
			long companyId, Object entity)
		throws Exception {

		Map<String, Serializable> extendedProperties = new HashMap<>();

		for (ExtensionProvider extensionProvider : _extensionProviders) {
			extendedProperties.putAll(
				extensionProvider.getExtendedProperties(companyId, entity));
		}

		return extendedProperties;
	}

	public Set<String> getFilteredPropertyNames(long companyId, Object entity) {
		Set<String> filteredPropertyNames = new HashSet<>();

		for (ExtensionProvider extensionProvider : _extensionProviders) {
			filteredPropertyNames.addAll(
				extensionProvider.getFilteredPropertyNames(companyId, entity));
		}

		return filteredPropertyNames;
	}

	public void setExtendedProperties(
			long companyId, Object entity,
			Map<String, Serializable> extendedProperties)
		throws Exception {

		for (ExtensionProvider extensionProvider : _extensionProviders) {
			Map<String, Serializable> extensionProviderExtendedProperties =
				new HashMap<>();

			Map<String, PropertyDefinition> extendedPropertyDefinitions =
				extensionProvider.getExtendedPropertyDefinitions(
					companyId, _className);

			for (Map.Entry<String, Serializable> entry :
					extendedProperties.entrySet()) {

				if (extendedPropertyDefinitions.containsKey(entry.getKey())) {
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

		Map<String, PropertyDefinition> propertyDefinitionMap = new HashMap<>();

		for (ExtensionProvider extensionProvider : _extensionProviders) {
			propertyDefinitionMap.putAll(
				extensionProvider.getExtendedPropertyDefinitions(
					companyId, _className));
		}

		List<String> unknownProperties = new ArrayList<>();

		for (Map.Entry<String, Serializable> extendedPropertyEntry :
				extendedProperties.entrySet()) {

			String extendedPropertyName = extendedPropertyEntry.getKey();

			if (!propertyDefinitionMap.containsKey(extendedPropertyName)) {
				unknownProperties.add(extendedPropertyName);

				continue;
			}

			PropertyDefinition propertyDefinition = propertyDefinitionMap.get(
				extendedPropertyName);

			PropertyValidator propertyValidator =
				propertyDefinition.getPropertyValidator();

			propertyValidator.validate(
				propertyDefinition, extendedPropertyEntry.getValue());

			propertyDefinitionMap.remove(extendedPropertyName);
		}

		if (ListUtil.isNotEmpty(unknownProperties)) {
			throw new ValidationException(
				"The properties [" + ListUtil.toString(unknownProperties, "") +
					"] are unknown");
		}

		if (partialUpdate) {
			return;
		}

		List<String> missingMandatoryProperties = new ArrayList<>();

		for (PropertyDefinition propertyDefinition :
				propertyDefinitionMap.values()) {

			if (propertyDefinition.isRequired()) {
				missingMandatoryProperties.add(
					propertyDefinition.getPropertyName());
			}
		}

		if (ListUtil.isNotEmpty(missingMandatoryProperties)) {
			throw new ValidationException(
				"The properties [" +
					ListUtil.toString(missingMandatoryProperties, "") +
						"] are mandatory");
		}
	}

	private final String _className;
	private final List<ExtensionProvider> _extensionProviders;

}
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

package com.liferay.portal.tools.rest.builder.internal.yaml.util;

import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.Security;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Items;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.PathItem;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Peter Shin
 */
public class YAMLUtil {

	public static ConfigYAML loadConfigYAML(String yamlString) {
		List<TypeDescription> typeDescriptions = new ArrayList<>();

		// Security

		TypeDescription typeDescription = new TypeDescription(Security.class);

		typeDescription.substituteProperty(
			"oAuth2", String.class, "getOAuth2", "setOAuth2");

		typeDescriptions.add(typeDescription);

		TypeDescription[] typeDescriptionsArray = typeDescriptions.toArray(
			new TypeDescription[typeDescriptions.size()]);

		return _load(ConfigYAML.class, yamlString, typeDescriptionsArray);
	}

	public static OpenAPIYAML loadOpenAPIYAML(String yamlString) {
		List<TypeDescription> typeDescriptions = new ArrayList<>();

		// Items

		TypeDescription typeDescription = new TypeDescription(Items.class);

		typeDescription.substituteProperty(
			"$ref", String.class, "getReference", "setReference");

		typeDescriptions.add(typeDescription);

		// Open API YAML

		typeDescription = new TypeDescription(OpenAPIYAML.class);

		typeDescription.substituteProperty(
			"paths", Map.class, "getPathItems", "setPathItems");

		typeDescription.addPropertyParameters(
			"paths", String.class, PathItem.class);

		typeDescriptions.add(typeDescription);

		// Parameter

		typeDescription = new TypeDescription(Parameter.class);

		typeDescription.substituteProperty(
			"$ref", String.class, "getReference", "setReference");

		typeDescriptions.add(typeDescription);

		// Schema

		typeDescription = new TypeDescription(Schema.class);

		typeDescription.substituteProperty(
			"$ref", String.class, "getReference", "setReference");

		typeDescription.substituteProperty(
			"allOf", List.class, "getAllOfSchemas", "setAllOfSchemas");

		typeDescription.addPropertyParameters("allOf", Schema.class);

		typeDescription.substituteProperty(
			"anyOf", List.class, "getAnyOfSchemas", "setAnyOfSchemas");

		typeDescription.addPropertyParameters("anyOf", Schema.class);

		typeDescription.substituteProperty(
			"oneOf", List.class, "getOneOfSchemas", "setOneOfSchemas");

		typeDescription.addPropertyParameters("oneOf", Schema.class);

		typeDescription.substituteProperty(
			"properties", Map.class, "getPropertySchemas",
			"setPropertySchemas");

		typeDescription.addPropertyParameters(
			"properties", String.class, Schema.class);

		typeDescriptions.add(typeDescription);

		TypeDescription[] typeDescriptionsArray = typeDescriptions.toArray(
			new TypeDescription[typeDescriptions.size()]);

		return _load(OpenAPIYAML.class, yamlString, typeDescriptionsArray);
	}

	private static <T> T _load(
		Class<T> clazz, String yamlString,
		TypeDescription... typeDescriptions) {

		Constructor constructor = new Constructor(clazz);

		for (TypeDescription typeDescription : typeDescriptions) {
			constructor.addTypeDescription(typeDescription);
		}

		Representer representer = new Representer();

		PropertyUtils propertyUtils = representer.getPropertyUtils();

		propertyUtils.setSkipMissingProperties(true);

		Yaml yaml = new Yaml(constructor, representer);

		return yaml.loadAs(yamlString, clazz);
	}

}
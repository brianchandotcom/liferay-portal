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

package com.liferay.portal.vulcan.yaml;

import com.liferay.portal.vulcan.yaml.config.ConfigYAML;
import com.liferay.portal.vulcan.yaml.config.Security;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Parameter;
import com.liferay.portal.vulcan.yaml.openapi.PathItem;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

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
		return _CONFIG_YAML.loadAs(yamlString, ConfigYAML.class);
	}

	public static OpenAPIYAML loadOpenAPIYAML(String yamlString) {
		return _OPEN_API_YAML.loadAs(yamlString, OpenAPIYAML.class);
	}

	private static final Yaml _CONFIG_YAML;

	private static final Yaml _OPEN_API_YAML;

	static {
		Representer representer = new Representer();

		PropertyUtils propertyUtils = representer.getPropertyUtils();

		propertyUtils.setSkipMissingProperties(true);

		Constructor configYAMLConstructor = new Constructor(ConfigYAML.class);

		TypeDescription securityTypeDescription = new TypeDescription(
			Security.class);

		securityTypeDescription.substituteProperty(
			"oAuth2", String.class, "getOAuth2", "setOAuth2");

		configYAMLConstructor.addTypeDescription(securityTypeDescription);

		_CONFIG_YAML = new Yaml(configYAMLConstructor, representer);

		Constructor openAPIYAMLConstructor = new Constructor(OpenAPIYAML.class);

		TypeDescription itemTypeDescription = new TypeDescription(Items.class);

		itemTypeDescription.substituteProperty(
			"$ref", String.class, "getReference", "setReference");

		openAPIYAMLConstructor.addTypeDescription(itemTypeDescription);

		TypeDescription openAPIYAMLTypeDescription = new TypeDescription(
			OpenAPIYAML.class);

		openAPIYAMLTypeDescription.substituteProperty(
			"paths", Map.class, "getPathItems", "setPathItems");

		openAPIYAMLTypeDescription.addPropertyParameters(
			"paths", String.class, PathItem.class);

		openAPIYAMLConstructor.addTypeDescription(openAPIYAMLTypeDescription);

		TypeDescription parameterTypeDescription = new TypeDescription(
			Parameter.class);

		parameterTypeDescription.substituteProperty(
			"$ref", String.class, "getReference", "setReference");

		openAPIYAMLConstructor.addTypeDescription(parameterTypeDescription);

		// Schema

		TypeDescription schemaTypeDescription = new TypeDescription(
			Schema.class);

		schemaTypeDescription.substituteProperty(
			"$ref", String.class, "getReference", "setReference");

		schemaTypeDescription.substituteProperty(
			"allOf", List.class, "getAllOfSchemas", "setAllOfSchemas");

		schemaTypeDescription.addPropertyParameters("allOf", Schema.class);

		schemaTypeDescription.substituteProperty(
			"anyOf", List.class, "getAnyOfSchemas", "setAnyOfSchemas");

		schemaTypeDescription.addPropertyParameters("anyOf", Schema.class);

		schemaTypeDescription.substituteProperty(
			"oneOf", List.class, "getOneOfSchemas", "setOneOfSchemas");

		schemaTypeDescription.addPropertyParameters("oneOf", Schema.class);

		schemaTypeDescription.substituteProperty(
			"properties", Map.class, "getPropertySchemas",
			"setPropertySchemas");

		schemaTypeDescription.addPropertyParameters(
			"properties", String.class, Schema.class);

		openAPIYAMLConstructor.addTypeDescription(schemaTypeDescription);

		_OPEN_API_YAML = new Yaml(openAPIYAMLConstructor, representer);
	}

}
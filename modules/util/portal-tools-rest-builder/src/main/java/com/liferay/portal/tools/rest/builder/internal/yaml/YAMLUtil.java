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

package com.liferay.portal.tools.rest.builder.internal.yaml;

import com.liferay.portal.tools.rest.builder.internal.yaml.config.ConfigYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.config.Security;
import com.liferay.portal.tools.rest.builder.internal.yaml.exception.InvalidYAMLException;
import com.liferay.portal.tools.rest.builder.internal.yaml.exception.OpenAPIValidatorException;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Items;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Parameter;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.PathItem;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.util.List;
import java.util.Map;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.TypeDescription;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.error.MarkedYAMLException;
import org.yaml.snakeyaml.introspector.PropertyUtils;
import org.yaml.snakeyaml.representer.Representer;

/**
 * @author Peter Shin
 */
public class YAMLUtil {

	public static ConfigYAML loadConfigYAML(String yamlString) {
		try {
			return _YAML_CONFIG.loadAs(yamlString, ConfigYAML.class);
		}
		catch (MarkedYAMLException markedYAMLException) {
			throw new InvalidYAMLException(markedYAMLException);
		}
	}

	public static OpenAPIYAML loadOpenAPIYAML(String yamlString) {
		try {
			return _YAML_OPEN_API.loadAs(yamlString, OpenAPIYAML.class);
		}
		catch (MarkedYAMLException markedYAMLException) {
			throw new InvalidYAMLException(markedYAMLException);
		}
	}

	public static void validateOpenAPIYAML(String fileName, String yamlString)
		throws OpenAPIValidatorException {

		OpenAPIValidator.validate(fileName, yamlString, _YAML_OPEN_API);
	}

	private static final Yaml _YAML_CONFIG;

	private static final Yaml _YAML_OPEN_API;

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

		LoaderOptions loaderOptions = new LoaderOptions();

		loaderOptions.setAllowDuplicateKeys(false);

		_YAML_CONFIG = new Yaml(
			configYAMLConstructor, representer, new DumperOptions(),
			loaderOptions);

		Constructor openAPIYAMLConstructor = new Constructor(OpenAPIYAML.class);

		TypeDescription itemsTypeDescription = new TypeDescription(Items.class);

		itemsTypeDescription.substituteProperty(
			"$ref", String.class, "getReference", "setReference");
		itemsTypeDescription.substituteProperty(
			"additionalProperties", Schema.class, "getAdditionalPropertySchema",
			"setAdditionalPropertySchema");
		itemsTypeDescription.substituteProperty(
			"properties", Map.class, "getPropertySchemas",
			"setPropertySchemas");

		itemsTypeDescription.addPropertyParameters(
			"properties", String.class, Schema.class);

		openAPIYAMLConstructor.addTypeDescription(itemsTypeDescription);

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
			"additionalProperties", Schema.class, "getAdditionalPropertySchema",
			"setAdditionalPropertySchema");

		schemaTypeDescription.substituteProperty(
			"allOf", List.class, "getAllOfSchemas", "setAllOfSchemas");

		schemaTypeDescription.addPropertyParameters("allOf", Schema.class);

		schemaTypeDescription.substituteProperty(
			"anyOf", List.class, "getAnyOfSchemas", "setAnyOfSchemas");

		schemaTypeDescription.addPropertyParameters("anyOf", Schema.class);

		schemaTypeDescription.substituteProperty(
			"enum", List.class, "getEnumValues", "setEnumValues");

		schemaTypeDescription.addPropertyParameters("enum", String.class);

		schemaTypeDescription.substituteProperty(
			"oneOf", List.class, "getOneOfSchemas", "setOneOfSchemas");

		schemaTypeDescription.addPropertyParameters("oneOf", Schema.class);

		schemaTypeDescription.substituteProperty(
			"properties", Map.class, "getPropertySchemas",
			"setPropertySchemas");

		schemaTypeDescription.addPropertyParameters(
			"properties", String.class, Schema.class);

		schemaTypeDescription.substituteProperty(
			"required", List.class, "getRequiredPropertySchemaNames",
			"setRequiredPropertySchemaNames");

		schemaTypeDescription.addPropertyParameters("required", String.class);

		schemaTypeDescription.substituteProperty(
			"x-json-map", boolean.class, "getJsonMap", "setJsonMap");

		openAPIYAMLConstructor.addTypeDescription(schemaTypeDescription);

		_YAML_OPEN_API = new Yaml(
			openAPIYAMLConstructor, representer, new DumperOptions(),
			loaderOptions);
	}

}
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

package com.liferay.portal.tools.rest.builder.internal.freemarker.util;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.yaml.openapi.Components;
import com.liferay.portal.vulcan.yaml.openapi.Info;
import com.liferay.portal.vulcan.yaml.openapi.Items;
import com.liferay.portal.vulcan.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.vulcan.yaml.openapi.Schema;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Peter Shin
 */
public class OpenAPIUtil {

	public static Map<String, Schema> getAllSchemas(OpenAPIYAML openAPIYAML) {
		Map<String, Schema> allSchemas = new TreeMap<>();

		Queue<Map<String, Schema>> queue = new LinkedList<>();

		Components components = openAPIYAML.getComponents();

		queue.add(components.getSchemas());

		Map<String, Schema> map = null;

		while ((map = queue.poll()) != null) {
			for (Map.Entry<String, Schema> entry : map.entrySet()) {
				Schema schema = entry.getValue();

				Map<String, Schema> propertySchemas = null;

				Items items = schema.getItems();

				if (items != null) {
					propertySchemas = items.getPropertySchemas();
				}
				else {
					propertySchemas = schema.getPropertySchemas();
				}

				if (propertySchemas == null) {
					continue;
				}

				String schemaName = StringUtil.upperCaseFirstLetter(
					entry.getKey());

				allSchemas.put(schemaName, schema);

				queue.add(propertySchemas);
			}
		}

		return allSchemas;
	}

	public static String escapeVersion(OpenAPIYAML openAPIYAML) {
		Info info = openAPIYAML.getInfo();

		String version = info.getVersion();

		if (Validator.isNull(version)) {
			return null;
		}

		Matcher matcher = _nondigitPattern.matcher(version);

		String versionDirName = matcher.replaceAll("_");

		matcher = _leadingUnderscorePattern.matcher(versionDirName);

		return "v" + matcher.replaceFirst("");
	}

	private static final Pattern _leadingUnderscorePattern = Pattern.compile(
		"^_+");
	private static final Pattern _nondigitPattern = Pattern.compile("\\D");

}